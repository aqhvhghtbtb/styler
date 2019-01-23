/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.promise;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A Promise represents a proxy for a value not necessarily known when the promise is created. It
 * allows you to associate dependent promises to an asynchronous action's eventual success value or
 * failure reason. This lets asynchronous methods return values like synchronous methods: instead 
 * of the final value, the asynchronous method returns a promise of having a value at some point 
 * in the future.
 * 
 * @param <T> type of result.
 */
public class Promise<T> extends PromiseSupport<T> {

  private Runnable fulfillmentAction;
  private Consumer<? super Throwable> exceptionHandler;

  /**
   * Creates a promise that will be fulfilled in future.
   */
  public Promise() {
  }

  /**
   * Fulfills the promise with the provided value.
   * @param value the fulfilled value that can be accessed using {@link #get()}.
   */
  @Override
  public void fulfill(T value) {
    super.fulfill(value);
    postFulfillment();
  }

  /**
   * Fulfills the promise with exception due to error in execution.
   * @param exception the exception will be wrapped in {@link ExecutionException}
   *        when accessing the value using {@link #get()}.
   */
  @Override
  public void fulfillExceptionally(Exception exception) {
    super.fulfillExceptionally(exception);
    handleException(exception);
    postFulfillment();
  }

  private void handleException(Exception exception) {
    if (exceptionHandler == null) {
      return ;}exceptionHandler.accept(exception
    )

    ; } privatevoidpostFulfillment (
      ) {if ( fulfillmentAction== null
        ){
      return
      ;}fulfillmentAction.run(
    )

    ;
    } /**
   * Executes the task using the executor in other thread and fulfills the promise returned
   * once the task completes either successfully or with an exception.
   * 
   * @param task the task that will provide the value to fulfill the promise.
   * @param executor the executor in which the task should be run.
   * @return a promise that represents the result of running the task provided.
   */publicPromise< T>fulfillInAsync (finalCallable< T> task ,Executor executor
      ){executor.execute( ( )
        -> {
          try{fulfill(task.call()
        ) ; }catch (Exception ex
          ){fulfillExceptionally(ex
        )
      ;}}
      ) ;return
    this

    ;
    } /**
   * Returns a new promise that, when this promise is fulfilled normally, is fulfilled with 
   * result of this promise as argument to the action provided.
   * @param action action to be executed.
   * @return a new promise.
   */publicPromise< Void>thenAccept(Consumer < ?super T> action
      ){Promise< Void > dest =newPromise<>(
      ) ; fulfillmentAction =newConsumeAction( this, dest,action
      ) ;return
    dest

    ;
    } /**
   * Set the exception handler on this promise.
   * @param exceptionHandler a consumer that will handle the exception occurred while fulfilling
   *            the promise.
   * @return this
   */publicPromise< T>onError(Consumer < ?super Throwable> exceptionHandler
      ){this . exceptionHandler=
      exceptionHandler ;return
    this

    ;
    } /**
   * Returns a new promise that, when this promise is fulfilled normally, is fulfilled with 
   * result of this promise as argument to the function provided.
   * @param func function to be executed.
   * @return a new promise.
   */public< V>Promise< V>thenApply(Function < ?super T, V> func
      ){Promise< V > dest =newPromise<>(
      ) ; fulfillmentAction =newTransformAction<V>( this, dest,func
      ) ;return
    dest

    ;
    } /**
   * Accesses the value from source promise and calls the consumer, then fulfills the
   * destination promise.
   */ private class ConsumeAction implements

      Runnable { privatefinalPromise< T>
      src ; privatefinalPromise< Void>
      dest ; privatefinalConsumer < ?super T>

      action ;privateConsumeAction(Promise< T> src,Promise< Void> dest,Consumer < ?super T> action
        ){this . src=
        src;this . dest=
        dest;this . action=
      action

      ;}
      @ Override publicvoidrun (
        ) {
          try{action.accept(src.get()
          );dest.fulfill(null
        ) ; }catch (Throwable throwable
          ){dest.fulfillExceptionally(( Exception)throwable.getCause()
        )
      ;
    }

    }
    } /**
   * Accesses the value from source promise, then fulfills the destination promise using the
   * transformed value. The source value is transformed using the transformation function.
   */ privateclassTransformAction< V > implements

      Runnable { privatefinalPromise< T>
      src ; privatefinalPromise< V>
      dest ; privatefinalFunction < ?super T, V>

      func ;privateTransformAction(Promise< T> src,Promise< V> dest,Function < ?super T, V> func
        ){this . src=
        src;this . dest=
        dest;this . func=
      func

      ;}
      @ Override publicvoidrun (
        ) {
          try{dest.fulfill(func.apply(src.get())
        ) ; }catch (Throwable throwable
          ){dest.fulfillExceptionally(( Exception)throwable.getCause()
        )
      ;
    }
  }
  