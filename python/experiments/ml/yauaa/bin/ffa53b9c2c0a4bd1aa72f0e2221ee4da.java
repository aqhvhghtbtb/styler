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
package nl.basjes.parse.useragent.analyze;

import org.antlr.v4.runtime.tree.ParseTree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.
Iterator ;importjava.util.
List ;importjava.util.

NoSuchElementException ; public final class MatchesListimplementsCollection<MatchesList.Match > ,

    Serializable { public static final class Match implements
        Serializable { privateString
        key ; privateString
        value ; privateParseTree

        result ;publicMatch (String key ,String value ,ParseTree result
            ){fill( key, value,result
        )

        ; } publicvoidfill (String nKey ,String nValue ,ParseTree nResult
            ){this . key=
            nKey;this . value=
            nValue;this . result=
        nResult

        ; } publicStringgetKey (
            ) {return
        key

        ; } publicStringgetValue (
            ) {return
        value

        ; } publicParseTreegetResult (
            ) {return
        result
    ;

    } } privateint
    size ; privateint

    maxSize ;privateMatch []

    allElements ;publicMatchesList (int newMaxSize
        ) { maxSize=

        newMaxSize ; size=
        0 ; allElements =newMatch[maxSize
        ] ;for ( int i= 0 ; i< maxSize;i ++
            ){allElements[ i ] =newMatch( null, null,null
        )
    ;

    }}
    @ Override publicintsize (
        ) {return
    size

    ;}
    @ Override publicbooleanisEmpty (
        ) { return size==
    0

    ;}
    @ Override publicvoidclear (
        ) { size=
    0

    ; } publicbooleanadd (String key ,String value ,ParseTree result
        ) {if ( size>= maxSize
            ){increaseCapacity(
        )

        ;}allElements[size].fill( key, value,result
        );size
        ++ ;return
    true

    ;}
    @ OverridepublicIterator< Match>iterator (
        ) { returnnewIterator<Match> (
            ) { int offset=

            0;
            @ Override publicbooleanhasNext (
                ) { return offset<
            size

            ;}
            @ Override publicMatchnext (
                ) {if(!hasNext( )
                    ) { thrownewNoSuchElementException("Array index out of bounds"
                )
                ; }returnallElements[offset++
            ]
        ;}
    }

    ;}
    @ OverridepublicObject []toArray (
        ) {returnArrays.copyOf(this. allElements,this.size
    )

    ; } private static final int CAPACITY_INCREASE=

    3 ; privatevoidincreaseCapacity (
        ) { int newMaxSize= maxSize+
        CAPACITY_INCREASE;Match [ ] newAllElements =newMatch[newMaxSize
        ];System.arraycopy( allElements, 0, newAllElements, 0,maxSize
        ) ;for ( int i= maxSize ; i< newMaxSize;i ++
            ){newAllElements[ i ] =newMatch( null, null,null
        )
        ; } allElements=
        newAllElements ; maxSize=
    newMaxSize

    ; }publicList< String>toStrings (
        ){List< String > result =newArrayList<>(size
        ) ;for (Match match: this
            ){result.add ( "{ \""+match . key + "\"=\""+match . value+"\" }"
        )
        ; }return
    result

;
}
// ============================================================

    // Everything else is NOT supported// ============================================================
    @ Override publicbooleanadd (Match match
        ) { thrownewUnsupportedOperationException(
    )

    ;}
    @ Override publicbooleanaddAll(Collection < ?extends Match> collection
        ) { thrownewUnsupportedOperationException(
    )

    ;}
    @ Override publicbooleanremove (Object o
        ) { thrownewUnsupportedOperationException(
    )

    ;}
    @ Override publicbooleanremoveAll(Collection< ?> collection
        ) { thrownewUnsupportedOperationException(
    )

    ;}
    @ Override publicbooleanretainAll(Collection< ?> collection
        ) { thrownewUnsupportedOperationException(
    )

    ;}
    @ Override publicbooleancontains (Object o
        ) { thrownewUnsupportedOperationException(
    )

    ;}
    @ Override publicbooleancontainsAll(Collection< ?> collection
        ) { thrownewUnsupportedOperationException(
    )

    ;}
    @ Overridepublic< T>T []toArray(T [] ts
        ) { thrownewUnsupportedOperationException(
    )
;