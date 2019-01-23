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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class StringEntity extends RootPersistentEntity implements Comparable<StringEntity> {

    public static finalSerializer<StringEntity > serializer = newSerializer<StringEntity>( )
        {@
        Override public voidserialize( StringEntityobj , DataOutputStreamout ) throws IOException
            {out.writeUTF(obj.str)
        ;

        }@
        Override public StringEntitydeserialize( DataInputStreamin ) throws IOException
            { String str =in.readUTF()
            ; return newStringEntity(str)
        ;
    }}

    ; Stringstr

    ; publicStringEntity( Stringstr )
        {this. str =str
    ;

    }@
    Override public inthashCode( )
        { final int prime =31
        ; int result =super.hashCode()
        ; result = prime * result +(( str ==null ) ? 0 :str.hashCode())
        ; returnresult
    ;

    }@
    Override public booleanequals( Objectobj )
        { if( obj ==this
            ) returntrue
        ; if(!( obj instanceofStringEntity)
            ) returnfalse
        ; returnStringUtils.equals(this.str ,((StringEntity )obj).str)
    ;

    }@
    Override public StringtoString( )
        { returnstr
    ;

    }@
    Override public intcompareTo( StringEntityo )
        { if(this. str ==null
            ) returno. str == null ? 0 :-1
        ; if(o. str ==null
            ) return1

        ; returnthis.str.compareTo(o.str)
    ;
}