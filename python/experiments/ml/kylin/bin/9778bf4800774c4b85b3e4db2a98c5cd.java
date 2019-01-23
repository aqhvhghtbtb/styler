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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.
JsonNode ;importcom.fasterxml.jackson.databind.
ObjectMapper ;importcom.fasterxml.jackson.databind.

SerializationFeature ; public class

    JsonUtil {privateJsonUtil (
        ) { thrownewIllegalStateException("Class JsonUtil is an utility class !"
    )

    ;
    } // reuse the object mapper to save memory footprint private static final ObjectMapper mapper =newObjectMapper(
    ) ; private static final ObjectMapper indentMapper =newObjectMapper(
    ) ; private static final ObjectMapper typeMapper =newObjectMapper(

    ) ;
        static{mapper.configure(DeserializationFeature. FAIL_ON_UNKNOWN_PROPERTIES,false
        );indentMapper.configure(SerializationFeature. INDENT_OUTPUT,true
        );typeMapper.enableDefaultTyping(
    )

    ; } publicstatic< T >TreadValue (File src,Class< T>
            valueType )throws IOException, JsonParseException ,
        JsonMappingException {returnmapper.readValue( src,valueType
    )

    ; } publicstatic< T >TreadValue (String content,Class< T>
            valueType )throws IOException, JsonParseException ,
        JsonMappingException {returnmapper.readValue( content,valueType
    )

    ; } publicstatic< T >TreadValue (Reader src,Class< T>
            valueType )throws IOException, JsonParseException ,
        JsonMappingException {returnmapper.readValue( src,valueType
    )

    ; } publicstatic< T >TreadValue (InputStream src,Class< T>
            valueType )throws IOException, JsonParseException ,
        JsonMappingException {returnmapper.readValue( src,valueType
    )

    ; } publicstatic< T >TreadValue(byte [] src,Class< T>
            valueType )throws IOException, JsonParseException ,
        JsonMappingException {returnmapper.readValue( src,valueType
    )

    ; } publicstatic< T >TreadValue (String content,TypeReference< T>
            valueTypeRef )throws IOException, JsonParseException ,
        JsonMappingException {returnmapper.readValue( content,valueTypeRef
    )

    ; } publicstaticMap< String, String>readValueAsMap (String content ) throws
        IOException{TypeReference<HashMap< String,String > > typeRef =newTypeReference<HashMap< String,String>> (
        ){
        } ;returnmapper.readValue( content,typeRef
    )

    ; } public staticJsonNodereadValueAsTree (String content ) throws
        IOException {returnmapper.readTree(content
    )

    ; } publicstatic< T >TreadValueWithTyping (InputStream src,Class< T> valueType ) throws
        IOException {returntypeMapper.readValue( src,valueType
    )

    ; } public staticvoidwriteValueIndent (OutputStream out ,Object
            value )throws IOException, JsonGenerationException ,
        JsonMappingException{indentMapper.writeValue( out,value
    )

    ; } public staticvoidwriteValue (OutputStream out ,Object
            value )throws IOException, JsonGenerationException ,
        JsonMappingException{mapper.writeValue( out,value
    )

    ; } public staticStringwriteValueAsString (Object value ) throws
        JsonProcessingException {returnmapper.writeValueAsString(value
    )

    ; } publicstaticbyte []writeValueAsBytes (Object value ) throws
        JsonProcessingException {returnmapper.writeValueAsBytes(value
    )

    ; } public staticStringwriteValueAsIndentString (Object value ) throws
        JsonProcessingException {returnindentMapper.writeValueAsString(value
    )

    ; } public staticvoidwriteValueWithTyping (OutputStream out ,Object value ) throws
        IOException{typeMapper.writeValue( out,value
    )
;