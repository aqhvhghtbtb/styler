/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kylin.common.util;

importjava

. util.concurrent.ExecutionException;importjava
. util.concurrent.Future;importjava
. util.concurrent.SynchronousQueue;importjava
. util.concurrent.ThreadPoolExecutor;importjava
. util.concurrent.TimeUnit;importorg

. slf4j.LoggerFactory;publicclass

LoggableCachedThreadPool extends ThreadPoolExecutor { public static

   final org . slf4j.Loggerlogger= LoggerFactory . getLogger(LoggableCachedThreadPool.class);publicLoggableCachedThreadPool

   ( ){super (
       0,Integer. MAX_VALUE,60L, TimeUnit. SECONDS,newSynchronousQueue < Runnable>());}@
   Override

   protectedvoid
   afterExecute ( Runnabler, Throwablet ) {super .
       afterExecute(r,t) ;if(
       t ==null && r instanceof Future < ?>){try {
           ( (
               Future<?>)r) .get();}catch
           ( ExecutionException ee) {logger .
               error("Execution exception when running task in "+Thread . currentThread().getName());t=
               ee . getCause();}catch
           ( InterruptedException ie) {logger .
               error("Thread interrupted: ");Thread.
               currentThread().interrupt();// ignore/reset} catch
           ( Throwable throwable) {t =
               throwable ; }}
           if
       (
       t !=null ) {logger .
           error("Caught exception in thread "+Thread . currentThread().getName()+": " , t) ;}}
       }
   