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
package com.iluwatar.doublechecked.locking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.
locks .Lock;importjava.util.concurrent.
locks .ReentrantLock;/**
 * 
 * Inventory
 *
 */publicclassInventory{privatestatic

final
Logger LOGGER = LoggerFactory

  . getLogger ( Inventory . class );privatefinalintinventorySize;privatefinal

  List < Item >items
  ; private finalLocklock; /**
   * Constructor
   */public
  Inventory ( int inventorySize)

  {
  this .inventorySize= inventorySize; this
    .items= new ArrayList<
    >(inventorySize ) ; this.lock=newReentrantLock(
    );} /**
   * Add item
   */ public booleanaddItem(Item
  item

  )
  { if (items. size( )
    < inventorySize){lock.lock ( ); try
      {if(items.size
      ( )
        < inventorySize){items.add ( item) ;
          LOGGER.info("{}: items.size()={}, inventorySize={}",Thread
          .currentThread(),items .size(),inventorySize );returntrue;} }finally{
          lock .unlock
        (
      ) ; }
        }returnfalse;}/**
   * Get all the items in the inventory
   *
   * @return All the items of the inventory, as an unmodifiable list
   */
      public
    final
    List <Item
  >

  getItems
  ( ) {returnCollections. unmodifiableList(items )
    ; }}