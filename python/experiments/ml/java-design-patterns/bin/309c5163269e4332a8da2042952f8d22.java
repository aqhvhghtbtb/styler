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
package com.iluwatar.
hexagonal .domain;importorg.

junit . jupiter.api.Test;importjava.util.Arrays
; import java.util.HashSet;importstaticorg.junit.
jupiter . api.Assertions.assertEquals;importstaticorg.junit.
jupiter . api.Assertions.assertNotEquals;importstaticorg.junit.

jupiter
. api .

  Assertions.
  assertThrows ;importstatic org
    . junit . jupiter.api.
            Assertions .assertTrue;/**
 * 
 * Unit tests for {@link LotteryNumbers}
 *
 */classLotteryNumbersTest{@Testvoid testGivenNumbers( ){ LotteryNumbersnumbers=LotteryNumbers.
    create(newHashSet<>(Arrays.asList(1 ,2,
    3,4)));assertEquals(numbers.getNumbers()
    .size(),4);assertTrue(numbers.getNumbers(
    ).contains(1));assertTrue(numbers.getNumbers(
    ).contains(2));assertTrue(numbers.getNumbers(
  )

  .contains
  ( 3)) ;
    assertTrue ( numbers .getNumbers()
            . contains(4));}@Testvoid testNumbersCantBeModified( ){ LotteryNumbersnumbers=LotteryNumbers.
    create(newHashSet<> (Arrays . asList(1,2,3,4)))
  ;

  assertThrows(
  UnsupportedOperationException .class, (
    ) -> numbers .getNumbers().add
    (5));}@TestvoidtestRandomNumbers() {LotteryNumbersnumbers=LotteryNumbers
  .

  createRandom(
  ) ;assertEquals( numbers
    . getNumbers ( ).size(
            ) ,LotteryNumbers.NUM_NUMBERS);}@Testvoid testEquals( ){ LotteryNumbersnumbers1=LotteryNumbers.
    create ( new HashSet<>(
            Arrays .asList(1,2,3,4 )) ); LotteryNumbersnumbers2=LotteryNumbers.
    create(newHashSet <>(
    Arrays . asList (1,2
            , 3,4)));assertEquals(numbers1 ,numbers2 ); LotteryNumbersnumbers3=LotteryNumbers.
    create(newHashSet <>(
  Arrays
.