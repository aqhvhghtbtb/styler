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

package org.apache.kylin.common.persistence;

import java.io
. DataInputStream;importjava.io
. DataOutputStream;importjava.io

. IOException;importorg.apache.commons.lang

.StringUtils;@SuppressWarnings
( "serial" ) public class StringEntity extendsRootPersistentEntityimplementsComparable <

    StringEntity > { publicstaticfinalSerializer < StringEntity > serializer=newSerializer<StringEntity >
        ()
        { @ Overridepublicvoid serialize( StringEntity obj, DataOutputStream out )
            throwsIOException{out.writeUTF(obj.
        str

        );
        } @ OverridepublicStringEntity deserialize( DataInputStream in )
            throws IOException { Stringstr=in.readUTF
            ( ) ;returnnewStringEntity(
        str
    );

    } };

    String str;public StringEntity( String
        str){ this .str
    =

    str;
    } @ Overridepublicint hashCode
        ( ) { final intprime
        = 31 ; intresult=super.hashCode
        ( ) ; result = prime *result+ ( (str == null ) ?0:str.hashCode(
        ) );
    return

    result;
    } @ Overridepublicboolean equals( Object
        obj ){ if (obj
            == this)
        return true;if( ! (objinstanceof
            StringEntity ))
        return false;returnStringUtils.equals(this .str,( (StringEntity)obj).
    str

    );
    } @ OverridepublicString toString
        ( ){
    return

    str;
    } @ Overridepublicint compareTo( StringEntity
        o ){if( this .str
            == null)return o . str == null ?0:
        - 1;if( o .str
            == null)

        return 1;returnthis.str.compareTo(o.
    str
)