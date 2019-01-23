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
package org.apache.commons.lang3.

time ;importjava.util.
Date ;importjava.util.

TimeZone
; /**
 * Custom timezone that contains offset from GMT.
 *
 * @since 3.7
 */ class GmtTimeZone extends

    TimeZone { private static final int MILLISECONDS_PER_MINUTE = 60*
    1000 ; private static final int MINUTES_PER_HOUR=
    60 ; private static final int HOURS_PER_DAY=

    24
    ; // Serializable! static final long serialVersionUID=

    1L ; private finalint
    offset ; private finalString

    zoneId;GmtTimeZone ( finalboolean negate , finalint hours , finalint minutes
        ) {if ( hours>= HOURS_PER_DAY
            ) { thrownewIllegalArgumentException ( hours+" hours out of range"
        )
        ; }if ( minutes>= MINUTES_PER_HOUR
            ) { thrownewIllegalArgumentException ( minutes+" minutes out of range"
        )
        ; } final int milliseconds= ( minutes+ ( hours*MINUTES_PER_HOUR ) )*
        MILLISECONDS_PER_MINUTE ; offset = negate? - milliseconds:
        milliseconds ; zoneId=
            twoDigits(twoDigits (newStringBuilder(9).append("GMT").append ( negate ? '-':'+' ),
                hours).append(':' ),minutes).toString(

    )

    ; } private staticStringBuildertwoDigits ( finalStringBuilder sb , finalint n
        ) {returnsb.append(( char) ( '0'+ ( n/10))).append(( char) ( '0'+ ( n%10))
    )

    ;}
    @ Override publicintgetOffset ( finalint era , finalint year , finalint month , finalint day , finalint dayOfWeek , finalint milliseconds
        ) {return
    offset

    ;}
    @ Override publicvoidsetRawOffset ( finalint offsetMillis
        ) { thrownewUnsupportedOperationException(
    )

    ;}
    @ Override publicintgetRawOffset (
        ) {return
    offset

    ;}
    @ Override publicStringgetID (
        ) {return
    zoneId

    ;}
    @ Override publicbooleanuseDaylightTime (
        ) {return
    false

    ;}
    @ Override publicbooleaninDaylightTime ( finalDate date
        ) {return
    false

    ;}
    @ Override publicStringtoString (
        ) { return "[GmtTimeZone id=\"" + zoneId + "\",offset=" + offset+
    ']'

    ;}
    @ Override publicinthashCode (
        ) {return
    offset

    ;}
    @ Override publicbooleanequals ( finalObject other
        ) {if(! ( otherinstanceofGmtTimeZone )
            ) {return
        false
        ; } return zoneId==(( GmtTimeZone)other).
    zoneId
;