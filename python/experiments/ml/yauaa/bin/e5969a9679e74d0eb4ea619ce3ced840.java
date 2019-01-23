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
package nl.basjes.parse.useragent.

utils ;importjava.io.
Serializable ;importjava.util.

Map ; public class PrefixLookup implements

    Serializable { public static class PrefixTrie implements
        Serializable {privatePrefixTrie []
        childNodes ;          privateint
        charIndex ;      privateboolean

        caseSensitive ; privateString

        theValue ;publicPrefixTrie (boolean caseSensitive
            ){this( caseSensitive,0
        )

        ; }privatePrefixTrie (boolean caseSensitive ,int charIndex
            ){this . caseSensitive=
            caseSensitive;this . charIndex=
        charIndex

        ; } privatevoidadd (String prefix ,String value
            ) {if ( charIndex==prefix.length( )
                ) { theValue=
                value;
            return

            ; } char myChar=prefix.charAt(charIndex )
            ; // This will give us the ASCII value of the charif ( myChar < 32 || myChar> 126
                ) { thrownewIllegalArgumentException("Only readable ASCII is allowed as key !!!"
            )

            ; }if ( childNodes== null
                ) { childNodes =newPrefixLookup.PrefixTrie[128
            ]

            ; }if( caseSensitive
                )
                { // If case sensitive we 'just' build the treeif(childNodes[ myChar ]== null
                    ){childNodes[ myChar ] =newPrefixTrie( true , charIndex+1
                )
                ;}childNodes[myChar].add( prefix,value
            ) ; }
                else
                {
                // If case INsensitive we build the tree
                // and we link the same child to both the // lower and uppercase entries in the child array. char lower=Character.toLowerCase(myChar
                ) ; char upper=Character.toUpperCase(myChar

                ) ;if(childNodes[ lower ]== null
                    ){childNodes[ lower ] =newPrefixTrie( false , charIndex+1
                )
                ;}childNodes[lower].add( prefix,value

                ) ;if(childNodes[ upper ]== null
                    ){childNodes[ upper ]=childNodes[lower
                ]
            ;
        }

        } } publicStringfind (String input
            ) {if ( charIndex==input.length ( ) || childNodes== null
                ) {return
            theValue

            ; } char myChar=input.charAt(charIndex )
            ; // This will give us the ASCII value of the charif ( myChar < 32 || myChar> 126
                ) {return theValue
            ;

            // Cannot store these, so this is where it ends. } PrefixTrie child=childNodes[myChar
            ] ;if ( child== null
                ) {return
            theValue

            ; } String returnValue=child.find(input
            ) ;return ( returnValue== null ) ? theValue:
        returnValue

    ;

    } } privatePrefixTrie

    prefixPrefixTrie ;publicPrefixLookup(Map< String, String> prefixList ,boolean caseSensitive
        )
        { // Translate the map into a different structure. prefixPrefixTrie =newPrefixTrie(caseSensitive
        );prefixList.forEach(( key, value )->prefixPrefixTrie.add( key,value)
    )

    ; } publicStringfindLongestMatchingPrefix (String input
        ) {returnprefixPrefixTrie.find(input
    )

;