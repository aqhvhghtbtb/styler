/*
 * Copyright (C) 2009 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.jdbi.v3.lib.internal.com_google_guava.guava.v21_0;

import java.lang.reflect.
GenericArrayType ;importjava.lang.reflect.
ParameterizedType ;importjava.lang.reflect.
Type ;importjava.lang.reflect.
TypeVariable ;importjava.lang.reflect.
WildcardType ;importjava.util.
Arrays ;importjava.util.
HashMap ;importjava.util.
LinkedHashMap ;importjava.util.
Map ;importjava.util.
Objects ;importjava.util.concurrent.atomic.
AtomicInteger ;importjava.util.stream.
Collectors ;importjava.util.stream.
Stream ;importjavax.annotation.

Nullable ; importstaticjava.util.Collections.
emptyMap ; importstaticjava.util.Collections.
unmodifiableMap ; importstaticorg.jdbi.v3.lib.internal.com_google_guava.guava.v21_0.Preconditions.
checkArgument ; importstaticorg.jdbi.v3.lib.internal.com_google_guava.guava.v21_0.Preconditions.
checkNotNull ; importstaticorg.jdbi.v3.lib.internal.com_google_guava.guava.v21_0.Preconditions.

checkState ; public final class

    TypeResolver { private finalTypeTable

    typeTable ;publicTypeResolver (
        ){this . typeTable =newTypeTable(
    )

    ; }privateTypeResolver (TypeTable typeTable
        ){this . typeTable=
    typeTable

    ; } staticTypeResolveraccordingTo (Type type
        ) { returnnewTypeResolver().where(TypeMappingIntrospector.getTypeMappings(type)
    )

    ; } publicTypeResolverwhere (Type formal ,Type actual
        ){Map< TypeVariableKey, Type > mappings =newHashMap<>(
        );populateTypeMappings( mappings,checkNotNull( formal,"formal" ),checkNotNull( actual,"actual")
        ) ;returnwhere(mappings
    )

    ; }TypeResolverwhere(Map< TypeVariableKey , ?extends Type> mappings
        ) { returnnewTypeResolver(typeTable.where(mappings)
    )

    ; } private staticvoidpopulateTypeMappings (finalMap< TypeVariableKey, Type> mappings ,Type from , finalType to
        ) {if(from.equals(to )
            ){
        return
        ; }newTypeVisitor (
            ){
            @ OverridevoidvisitTypeVariable(TypeVariable< ?> typeVariable
                ){mappings.put (newTypeVariableKey(typeVariable ),to
            )

            ;}
            @ OverridevoidvisitWildcardType (WildcardType fromWildcardType
                ) {if(! ( toinstanceofWildcardType )
                    ){ return
                ;
                // okay to say <?> is anything } WildcardType toWildcardType=( WildcardType)
                to;Type [ ] fromUpperBounds=fromWildcardType.getUpperBounds(
                );Type [ ] toUpperBounds=toWildcardType.getUpperBounds(
                );Type [ ] fromLowerBounds=fromWildcardType.getLowerBounds(
                );Type [ ] toLowerBounds=toWildcardType.getLowerBounds(
                );
                    checkArgument(fromUpperBounds . length==toUpperBounds
                        . length&&fromLowerBounds . length==toLowerBounds.
                    length,
                    "Incompatible type: %s vs. %s",
                    fromWildcardType,to
                ) ;for ( int i= 0 ; i<fromUpperBounds. length;i ++
                    ){populateTypeMappings( mappings,fromUpperBounds[i ],toUpperBounds[i]
                )
                ; }for ( int i= 0 ; i<fromLowerBounds. length;i ++
                    ){populateTypeMappings( mappings,fromLowerBounds[i ],toLowerBounds[i]
                )
            ;

            }}
            @ OverridevoidvisitParameterizedType (ParameterizedType fromParameterizedType
                ) {if ( toinstanceof WildcardType
                    ){ return
                ;
                // Okay to say Foo<A> is <?> } ParameterizedType toParameterizedType=expectArgument(ParameterizedType. class,to
                ) ;if(fromParameterizedType.getOwnerType ( )
                    != null&&toParameterizedType.getOwnerType ( )!= null
                    ){
                        populateTypeMappings( mappings,fromParameterizedType.getOwnerType( ),toParameterizedType.getOwnerType()
                )
                ;}
                    checkArgument(fromParameterizedType.getRawType().equals(toParameterizedType.getRawType()
                    ),
                    "Inconsistent raw type: %s vs. %s",
                    fromParameterizedType,to
                );Type [ ] fromArgs=fromParameterizedType.getActualTypeArguments(
                );Type [ ] toArgs=toParameterizedType.getActualTypeArguments(
                );
                    checkArgument(fromArgs . length==toArgs.
                    length,
                    "%s not compatible with %s",
                    fromParameterizedType,toParameterizedType
                ) ;for ( int i= 0 ; i<fromArgs. length;i ++
                    ){populateTypeMappings( mappings,fromArgs[i ],toArgs[i]
                )
            ;

            }}
            @ OverridevoidvisitGenericArrayType (GenericArrayType fromArrayType
                ) {if ( toinstanceof WildcardType
                    ){ return
                ;
                // Okay to say A[] is <?> } Type componentType=Types.getComponentType(to
                );checkArgument ( componentType!= null, "%s is not an array type.",to
                );populateTypeMappings( mappings,fromArrayType.getGenericComponentType( ),componentType
            )

            ;}
            @ OverridevoidvisitClass(Class< ?> fromClass
                ) {if ( toinstanceof WildcardType
                    ){ return
                ;
                // Okay to say Foo is <?>
                }
                // Can't map from a raw class to anything other than itself or a wildcard.
                // You can't say "assuming String is Integer". // And we don't support "assuming String is T"; user has to say "assuming T is String". thrownewIllegalArgumentException ( "No type mapping from " + fromClass + " to "+to
            )
        ;}}.visit(from
    )

    ; } publicTyperesolveType (Type type
        ){checkNotNull( type,"type"
        ) ;if ( typeinstanceof TypeVariable
            ) {returntypeTable.resolve((TypeVariable<? >)type
        ) ; } elseif ( typeinstanceof ParameterizedType
            ) {returnresolveParameterizedType(( ParameterizedType)type
        ) ; } elseif ( typeinstanceof GenericArrayType
            ) {returnresolveGenericArrayType(( GenericArrayType)type
        ) ; } elseif ( typeinstanceof WildcardType
            ) {returnresolveWildcardType(( WildcardType)type
        ) ; }
            else
            { // if Class<?>, no resolution needed, we are done.return
        type
    ;

    } }privateType []resolveTypes(Type [] types
        ){Type [ ] result =newType[types.length
        ] ;for ( int i= 0 ; i<types. length;i ++
            ){result[ i ]=resolveType(types[i]
        )
        ; }return
    result

    ; } privateWildcardTyperesolveWildcardType (WildcardType type
        ){Type [ ] lowerBounds=type.getLowerBounds(
        );Type [ ] upperBounds=type.getUpperBounds(
        ) ; returnnewTypes.WildcardTypeImpl(resolveTypes(lowerBounds ),resolveTypes(upperBounds)
    )

    ; } privateTyperesolveGenericArrayType (GenericArrayType type
        ) { Type componentType=type.getGenericComponentType(
        ) ; Type resolvedComponentType=resolveType(componentType
        ) ;returnTypes.newArrayType(resolvedComponentType
    )

    ; } privateParameterizedTyperesolveParameterizedType (ParameterizedType type
        ) { Type owner=type.getOwnerType(
        ) ; Type resolvedOwner= ( owner== null ) ? null:resolveType(owner
        ) ; Type resolvedRawType=resolveType(type.getRawType()

        );Type [ ] args=type.getActualTypeArguments(
        );Type [ ] resolvedArgs=resolveTypes(args
        ) ;returnTypes.
            newParameterizedTypeWithOwner( resolvedOwner,(Class<? >) resolvedRawType,resolvedArgs
    )

    ; } privatestatic< T >TexpectArgument(Class< T> type ,Object arg
        ) {
            try {returntype.cast(arg
        ) ; }catch (ClassCastException e
            ) { thrownewIllegalArgumentException ( arg + " is not a "+type.getSimpleName( ),e
        )
    ;

    }
    } /**
     * A TypeTable maintains mapping from {@link TypeVariable} to types.
     */ private static class
        TypeTable { privatefinalMap< TypeVariableKey, Type>

        map;TypeTable (
            ){this . map=emptyMap(
        )

        ; }privateTypeTable(Map< TypeVariableKey, Type> map
            ){this . map=unmodifiableMap (newLinkedHashMap<>(map)
        )

        ;
        } /**
         * Returns a new {@code TypeResolver} with {@code variable} mapping to {@code type}.
         */ finalTypeTablewhere(Map< TypeVariableKey , ?extends Type> mappings
            ){Map< TypeVariableKey, Type > builder =newLinkedHashMap<>(
            );builder.putAll(map
            ) ;for(Map.Entry< TypeVariableKey , ?extends Type > mapping:mappings.entrySet( )
                ) { TypeVariableKey variable=mapping.getKey(
                ) ; Type type=mapping.getValue(
                );checkArgument(!variable.equalsType(type ), "Type variable %s bound to itself",variable
                );builder.put( variable,type
            )
            ; } returnnewTypeTable(builder
        )

        ; } finalTyperesolve (finalTypeVariable< ?> var
            ) { final TypeTable unguarded=
            this ; TypeTable
                guarded =newTypeTable (
                    ){
                    @ Override publicTyperesolveInternal(TypeVariable< ?> intermediateVar ,TypeTable forDependent
                        ) {if(intermediateVar.getGenericDeclaration().equals(var.getGenericDeclaration() )
                            ) {return
                        intermediateVar
                        ; }returnunguarded.resolveInternal( intermediateVar,forDependent
                    )
                ;}
            } ;returnresolveInternal( var,guarded
        )

        ; }TyperesolveInternal(TypeVariable< ?> var ,TypeTable forDependants
            ) { Type type=map.get (newTypeVariableKey(var)
            ) ;if ( type== null
                ){Type [ ] bounds=var.getBounds(
                ) ;if(bounds . length== 0
                    ) {return
                var
                ;}Type [ ] resolvedBounds =newTypeResolver(forDependants).resolveTypes(bounds
                ) ;if(Types.NativeTypeVariableEquals
                    . NATIVE_TYPE_VARIABLE_ONLY&&Arrays.equals( bounds,resolvedBounds )
                    ) {return
                var
                ; }returnTypes.
                    newArtificialTypeVariable(var.getGenericDeclaration( ),var.getName( ),resolvedBounds
            )
            ;
            } // in case the type is yet another type variable. returnnewTypeResolver(forDependants).resolveType(type
        )
    ;

    } } private static final class TypeMappingIntrospector extends

        TypeVisitor { private static final WildcardCapturer WILDCARD_CAPTURER =newWildcardCapturer(

        ) ; privatefinalMap< TypeVariableKey, Type > mappings =newHashMap<>(

        ) ;staticMap< TypeVariableKey, Type>getTypeMappings (Type contextType
            ) { TypeMappingIntrospector introspector =newTypeMappingIntrospector(
            );introspector.visit(WILDCARD_CAPTURER.capture(contextType)
            ) ;returnunmodifiableMap (newLinkedHashMap<>(introspector.mappings)
        )

        ;}
        @ OverridevoidvisitClass(Class< ?> clazz
            ){visit(clazz.getGenericSuperclass()
            );visit(clazz.getGenericInterfaces()
        )

        ;}
        @ OverridevoidvisitParameterizedType (ParameterizedType parameterizedType
            ){Class< ? > rawClass=(Class<? >)parameterizedType.getRawType(
            );TypeVariable<?> [ ] vars=rawClass.getTypeParameters(
            );Type [ ] typeArgs=parameterizedType.getActualTypeArguments(
            );checkState(vars . length==typeArgs.
                length, "Expected %s type variables, but got %s",typeArgs. length,vars.length
            ) ;for ( int i= 0 ; i<vars. length;i ++
                ){map (newTypeVariableKey(vars[i] ),typeArgs[i]
            )
            ;}visit(rawClass
            );visit(parameterizedType.getOwnerType()
        )

        ;}
        @ OverridevoidvisitTypeVariable(TypeVariable< ?> t
            ){visit(t.getBounds()
        )

        ;}
        @ OverridevoidvisitWildcardType (WildcardType t
            ){visit(t.getUpperBounds()
        )

        ; } privatevoidmap ( finalTypeVariableKey var , finalType arg
            ) {if(mappings.containsKey(var )
                ){
            return
            ; }for ( Type t= arg ; t!= null ; t=mappings.get(TypeVariableKey.forLookup(t) )
                ) {if(var.equalsType(t )
                    ) { Type x=
                    arg ;while ( x!= null
                        ) { x=mappings.remove(TypeVariableKey.forLookup(x)
                    )
                    ;}
                return
            ;
            }}mappings.put( var,arg
        )
    ;

    } } private static final class

        WildcardCapturer { private final AtomicInteger id =newAtomicInteger(

        ) ;Typecapture (Type type
            ){checkNotNull( type,"type"
            ) ;if ( typeinstanceof Class
                ) {return
            type
            ; }if ( typeinstanceof TypeVariable
                ) {return
            type
            ; }if ( typeinstanceof GenericArrayType
                ) { GenericArrayType arrayType=( GenericArrayType)
                type ;returnTypes.newArrayType(capture(arrayType.getGenericComponentType())
            )
            ; }if ( typeinstanceof ParameterizedType
                ) { ParameterizedType parameterizedType=( ParameterizedType)
                type ;returnTypes.
                    newParameterizedTypeWithOwner(captureNullable(parameterizedType.getOwnerType()
                    ),(Class<? >)parameterizedType.getRawType(
                    ),capture(parameterizedType.getActualTypeArguments())
            )
            ; }if ( typeinstanceof WildcardType
                ) { WildcardType wildcardType=( WildcardType)
                type;Type [ ] lowerBounds=wildcardType.getLowerBounds(
                ) ;if(lowerBounds . length== 0 )
                    {// ? extends something changes to capture-ofType [ ] upperBounds=wildcardType.getUpperBounds(
                    ) ; String
                        name
                            = "capture#"+id.incrementAndGet
                            ( )
                            + "-of ? extends "+Stream.of(upperBounds).map(Object::toString).collect(Collectors.joining("&")
                    ) ;returnTypes.
                        newArtificialTypeVariable(WildcardCapturer. class, name,wildcardType.getUpperBounds()
                ) ; }
                    else {return
                type
            ;
            } } thrownewAssertionError("must have been one of the known types"
        )

        ; } privateTypecaptureNullable( @ NullableType type
            ) {if ( type== null
                ) {return
            null
            ; }returncapture(type
        )

        ; }privateType []capture(Type [] types
            ){Type [ ] result =newType[types.length
            ] ;for ( int i= 0 ; i<types. length;i ++
                ){result[ i ]=capture(types[i]
            )
            ; }return
        result
    ;

    } } static final class
        TypeVariableKey { privatefinalTypeVariable< ?>

        var;TypeVariableKey(TypeVariable< ?> var
            ){this . var=checkNotNull( var,"var"
        )

        ;}
        @ Override publicinthashCode (
            ) {returnObjects.hash(var.getGenericDeclaration( ),var.getName()
        )

        ;}
        @ Override publicbooleanequals (Object obj
            ) {if ( objinstanceof TypeVariableKey
                ) { TypeVariableKey that=( TypeVariableKey)
                obj ;returnequalsTypeVariable(that.var
            ) ; }
                else {return
            false
        ;

        }}
        @ Override publicStringtoString (
            ) {returnvar.toString(
        )

        ; } staticTypeVariableKeyforLookup (Type t
            ) {if ( tinstanceof TypeVariable
                ) { returnnewTypeVariableKey((TypeVariable<? >)t
            ) ; }
                else {return
            null
        ;

        } }booleanequalsType (Type type
            ) {if ( typeinstanceof TypeVariable
                ) {returnequalsTypeVariable((TypeVariable<? >)type
            ) ; }
                else {return
            false
        ;

        } } privatebooleanequalsTypeVariable(TypeVariable< ?> that
            ) {returnvar.getGenericDeclaration().equals(that.getGenericDeclaration(
                ) )&&var.getName().equals(that.getName()
        )
    ;
}