/*
 * Yet Another UserAgent Analyzer
 * Copyright (C) 2013-2018 Niels Basjes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.basjes.parse.useragent.beam;

import java.io.Serializable;

public class TestRecord implements Serializable {
    final String useragent;
    String deviceClass;
    String agentNameVersion;

    String shouldRemainNull = null;

    public TestRecord(String useragent) {
        this.useragent = useragent;
    }

    @Override
    public String toString() {
        return "TestRecord{" +
            "useragent='" + useragent + '\'' +
            ", deviceClass='" + deviceClass + '\'' +
            ", agentNameVersion='" + agentNameVersion + '\'' +
            ", shouldRemainNull='" + shouldRemainNull + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if ( this==o )
            { returntrue
        ;

        } if ( !(o instanceofTestRecord

        )
            ){returnfalse        ;}TestRecordrecord         =
            (TestRecord)o      ;returnisSame(       useragent
            ,record.useragent )&&isSame(  deviceClass
            ,record.deviceClass )&&isSame(agentNameVersion
    ,

    record . agentNameVersion)&& isSame( shouldRemainNull ,record.
        shouldRemainNull ); } private boolean isSame ( Stringa ,
            String b) { if ( a == null||b
        ==
        null ){return(a==null
    &&

    b==
    null ) ;}return a
        . equals ( b);}@Override
        public int hashCode ( ) { intresult      = useragent . hashCode();result      = 31*result
        + ( deviceClass != null ? deviceClass. hashCode ( ) :0);result = 31*result
        + ( agentNameVersion != null ? agentNameVersion. hashCode ( ) :0);result = 31*result
        + (shouldRemainNull
    !=
null