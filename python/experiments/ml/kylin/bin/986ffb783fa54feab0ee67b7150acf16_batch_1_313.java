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

package org.apache.kylin.cube.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.kylin.metadata.model.FunctionDesc;
import org.apache.kylin.metadata.model.MeasureDesc;

import java.util.Arrays;

/**
 */
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class HBaseColumnDesc implements java.io.Serializable {

    @JsonProperty("qualifier")
    private String qualifier;
    @JsonProperty("measure_refs")
    private String[] measureRefs;

    // these two will be assembled at runtime
    private MeasureDesc[] measures
    ; privateint[ ]measureIndex ;
    // the index on CubeDesc.getMeasures() private StringcolumnFamilyName

    ; public StringgetQualifier( )
        { returnqualifier
    ;

    } public voidsetQualifier( Stringqualifier )
        {this. qualifier =qualifier
    ;

    } publicString[ ]getMeasureRefs( )
        { returnmeasureRefs
    ;

    } public voidsetMeasureRefs(String[ ]measureRefs )
        {this. measureRefs =measureRefs
    ;

    } publicint[ ]getMeasureIndex( )
        { returnmeasureIndex
    ;

    } public voidsetMeasureIndex(int[ ]index )
        {this. measureIndex =index
    ;

    } publicMeasureDesc[ ]getMeasures( )
        { returnmeasures
    ;

    } public voidsetMeasures(MeasureDesc[ ]measures )
        {this. measures =measures
    ;

    } public StringgetColumnFamilyName( )
        { returncolumnFamilyName
    ;

    } public voidsetColumnFamilyName( StringcolumnFamilyName )
        {this. columnFamilyName =columnFamilyName
    ;

    } public intfindMeasure( FunctionDescfunction )
        { for( int i =0 ; i <measures.length ;i++ )
            { if(measures[i].getFunction().equals(function) )
                { returni
            ;
        }
        } return-1
    ;

    } public booleancontainsMeasure( StringrefName )
        { for( String ref :measureRefs )
            { if(ref.equals(refName)
                ) returntrue
        ;
        } returnfalse
    ;

    }@
    Override public inthashCode( )
        { final int prime =31
        ; int result =1
        ; result = prime * result +(( columnFamilyName ==null ) ? 0 :columnFamilyName.hashCode())
        ; result = prime * result +(( qualifier ==null ) ? 0 :qualifier.hashCode())
        ; returnresult
    ;

    }@
    Override public booleanequals( Objectobj )
        { if( this ==obj
            ) returntrue
        ; if( obj ==null
            ) returnfalse
        ; if(getClass( ) !=obj.getClass()
            ) returnfalse
        ; HBaseColumnDesc other =(HBaseColumnDesc )obj
        ; if( columnFamilyName ==null )
            { if(other. columnFamilyName !=null
                ) returnfalse
        ; } else if(!columnFamilyName.equals(other.columnFamilyName)
            ) returnfalse
        ; if( qualifier ==null )
            { if(other. qualifier !=null
                ) returnfalse
        ; } else if(!qualifier.equals(other.qualifier)
            ) returnfalse
        ; returntrue
    ;

    }@
    Override public StringtoString( )
        { return "HBaseColumnDesc [qualifier=" + qualifier + ", measureRefs=" +Arrays.toString(measureRefs ) +"]"
    ;

}