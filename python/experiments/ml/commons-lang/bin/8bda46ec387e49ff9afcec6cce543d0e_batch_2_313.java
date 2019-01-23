/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3.time;

import java.util.Date;
import java.util.TimeZone;

/**
 * Custom timezone that contains offset from GMT.
 *
 * @since 3.7
 */
class GmtTimeZone
  extendsTimeZone{privatestatic

  final
  int MILLISECONDS_PER_MINUTE = 60 *

      1000 ; private static final int MINUTES_PER_HOUR = 60;
      private static final int HOURS_PER_DAY = 24;
      // Serializable! static final long serialVersionUID = 1L;

      private
      final int offset ; private finalString

      zoneId ; GmtTimeZone (final
      boolean negate , finalint

      hours,final int minutes) { if (hours >= HOURS_PER_DAY ){ throw
          new IllegalArgumentException( hours +" hours out of range" )
              ; } if(minutes >= MINUTES_PER_HOUR){
          throw
          new IllegalArgumentException( minutes +" minutes out of range" )
              ; } finalintmilliseconds = (minutes+
          (
          hours * MINUTES_PER_HOUR ) )* MILLISECONDS_PER_MINUTE ;offset = negate?- milliseconds :milliseconds
          ; zoneId = twoDigits (twoDigits ( newStringBuilder
          ( 9 ).
              append("GMT" ).append(negate?'-':'+'),hours) . append ( ':'), minutes)
                  .toString();} privatestaticStringBuildertwoDigits(finalStringBuilder

      sb

      , final int n){ return sb. append ( (char )
          ( '0'+(n/10) )) . append( ( char)('0'+(n%10) )) ; }@ Override publicintgetOffset(final
      int

      era,
      final int year,final int month, final int day, final int dayOfWeek, final int milliseconds) { return offset; } @ Overridepublic void
          setRawOffset (final
      int

      offsetMillis)
      { throw newUnsupportedOperationException( ) ;} @
          Override public intgetRawOffset()
      {

      returnoffset
      ; } @Overridepublic String
          getID ()
      {

      returnzoneId
      ; } @Overridepublic boolean
          useDaylightTime ()
      {

      returnfalse
      ; } @Overridepublic boolean
          inDaylightTime (final
      Date

      date)
      { return false;} @ Overridepublic String
          toString ()
      {

      return"[GmtTimeZone id=\""
      + zoneId +"\",offset="+ offset
          + ']' ; } @ Override public int hashCode ()
      {

      returnoffset
      ; } @Overridepublic boolean
          equals (final
      Object

      other)
      { if (!( other instanceofGmtTimeZone )
          ) {returnfalse; } returnzoneId== (
              ( GmtTimeZone)
          other
          ) . zoneId ;}}