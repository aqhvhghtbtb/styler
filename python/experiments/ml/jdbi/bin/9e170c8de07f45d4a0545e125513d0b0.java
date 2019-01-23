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

package org.jdbi.v3.lib.internal.com_google_guava.guava.v21_0;import

java .lang.reflect.GenericArrayType;import
java .lang.reflect.ParameterizedType;import
java .lang.reflect.Type;import
java .lang.reflect.TypeVariable;import
java .lang.reflect.WildcardType;import
java .util.Arrays;import
java .util.HashMap;import
java .util.LinkedHashMap;import
java .util.Map;import
java .util.Objects;import
java .util.concurrent.atomic.AtomicInteger;import
java .util.stream.Collectors;import
java .util.stream.Stream;import
javax .annotation.Nullable;import

static java .util.Collections.emptyMap;import
static java .util.Collections.unmodifiableMap;import
static org .jdbi.v3.lib.internal.com_google_guava.guava.v21_0.Preconditions.checkArgument;import
static org .jdbi.v3.lib.internal.com_google_guava.guava.v21_0.Preconditions.checkNotNull;import
static org .jdbi.v3.lib.internal.com_google_guava.guava.v21_0.Preconditions.checkState;public

final class TypeResolver { private

    final TypeTable typeTable ;public

    TypeResolver (){ this
        .typeTable= new TypeTable ();}
    private

    TypeResolver (TypeTabletypeTable ){ this
        .typeTable= typeTable ;}
    static

    TypeResolver accordingTo (Typetype ){ return
        new TypeResolver ().where(TypeMappingIntrospector.getTypeMappings(type));}
    public

    TypeResolver where (Typeformal ,Type actual ){ Map
        <TypeVariableKey,Type >mappings = new HashMap <>();populateTypeMappings
        (mappings,checkNotNull (formal,"formal" ),checkNotNull (actual,"actual" ));return
        where (mappings);}
    TypeResolver

    where (Map<TypeVariableKey,? extends Type >mappings ){ return
        new TypeResolver (typeTable.where(mappings));}
    private

    static void populateTypeMappings (finalMap <TypeVariableKey,Type >mappings ,Type from ,final Type to ){ if
        ( from.equals(to)){ return
            ;}
        new
        TypeVisitor (){ @
            Overridevoid
            visitTypeVariable (TypeVariable<?>typeVariable ){ mappings
                .put(newTypeVariableKey (typeVariable),to );}
            @

            Overridevoid
            visitWildcardType (WildcardTypefromWildcardType ){ if
                ( !(toinstanceof WildcardType )){ return
                    ;// okay to say <?> is anything }
                WildcardType
                toWildcardType = ( WildcardType)to ;Type
                []fromUpperBounds = fromWildcardType .getUpperBounds();Type
                []toUpperBounds = toWildcardType .getUpperBounds();Type
                []fromLowerBounds = fromWildcardType .getLowerBounds();Type
                []toLowerBounds = toWildcardType .getLowerBounds();checkArgument
                (fromUpperBounds
                    .length== toUpperBounds .length&&
                        fromLowerBounds .length== toLowerBounds .length,"Incompatible type: %s vs. %s"
                    ,fromWildcardType
                    ,to
                    );for
                ( inti = 0 ;i < fromUpperBounds .length;i ++){ populateTypeMappings
                    (mappings,fromUpperBounds [i],toUpperBounds [i]);}
                for
                ( inti = 0 ;i < fromLowerBounds .length;i ++){ populateTypeMappings
                    (mappings,fromLowerBounds [i],toLowerBounds [i]);}
                }
            @

            Overridevoid
            visitParameterizedType (ParameterizedTypefromParameterizedType ){ if
                ( toinstanceof WildcardType ){ return
                    ;// Okay to say Foo<A> is <?> }
                ParameterizedType
                toParameterizedType = expectArgument (ParameterizedType.class,to );if
                ( fromParameterizedType.getOwnerType()!= null &&
                    toParameterizedType .getOwnerType()!= null ){ populateTypeMappings
                    (mappings
                        ,fromParameterizedType .getOwnerType(),toParameterizedType .getOwnerType());}
                checkArgument
                (fromParameterizedType
                    .getRawType().equals(toParameterizedType.getRawType()),"Inconsistent raw type: %s vs. %s"
                    ,fromParameterizedType
                    ,to
                    );Type
                []fromArgs = fromParameterizedType .getActualTypeArguments();Type
                []toArgs = toParameterizedType .getActualTypeArguments();checkArgument
                (fromArgs
                    .length== toArgs .length,"%s not compatible with %s"
                    ,fromParameterizedType
                    ,toParameterizedType
                    );for
                ( inti = 0 ;i < fromArgs .length;i ++){ populateTypeMappings
                    (mappings,fromArgs [i],toArgs [i]);}
                }
            @

            Overridevoid
            visitGenericArrayType (GenericArrayTypefromArrayType ){ if
                ( toinstanceof WildcardType ){ return
                    ;// Okay to say A[] is <?> }
                Type
                componentType = Types .getComponentType(to);checkArgument
                (componentType!= null ,"%s is not an array type." ,to );populateTypeMappings
                (mappings,fromArrayType .getGenericComponentType(),componentType );}
            @

            Overridevoid
            visitClass (Class<?>fromClass ){ if
                ( toinstanceof WildcardType ){ return
                    ;// Okay to say Foo is <?> }
                // Can't map from a raw class to anything other than itself or a wildcard.
                // You can't say "assuming String is Integer".
                // And we don't support "assuming String is T"; user has to say "assuming T is String".
                throw
                new IllegalArgumentException ("No type mapping from "+ fromClass + " to " + to );}
            }
        .visit(from);}
    public

    Type resolveType (Typetype ){ checkNotNull
        (type,"type" );if
        ( typeinstanceof TypeVariable ){ return
            typeTable .resolve((TypeVariable<?>)type );}
        else if ( typeinstanceof ParameterizedType ){ return
            resolveParameterizedType ((ParameterizedType)type );}
        else if ( typeinstanceof GenericArrayType ){ return
            resolveGenericArrayType ((GenericArrayType)type );}
        else if ( typeinstanceof WildcardType ){ return
            resolveWildcardType ((WildcardType)type );}
        else { // if Class<?>, no resolution needed, we are done.
            return
            type ;}
        }
    private

    Type []resolveTypes (Type[]types ){ Type
        []result = new Type [types.length];for
        ( inti = 0 ;i < types .length;i ++){ result
            [i]= resolveType (types[i]);}
        return
        result ;}
    private

    WildcardType resolveWildcardType (WildcardTypetype ){ Type
        []lowerBounds = type .getLowerBounds();Type
        []upperBounds = type .getUpperBounds();return
        new Types .WildcardTypeImpl(resolveTypes(lowerBounds),resolveTypes (upperBounds));}
    private

    Type resolveGenericArrayType (GenericArrayTypetype ){ Type
        componentType = type .getGenericComponentType();Type
        resolvedComponentType = resolveType (componentType);return
        Types .newArrayType(resolvedComponentType);}
    private

    ParameterizedType resolveParameterizedType (ParameterizedTypetype ){ Type
        owner = type .getOwnerType();Type
        resolvedOwner = ( owner== null )? null : resolveType (owner);Type
        resolvedRawType = resolveType (type.getRawType());Type

        []args = type .getActualTypeArguments();Type
        []resolvedArgs = resolveTypes (args);return
        Types .newParameterizedTypeWithOwner(resolvedOwner
            ,( Class<?>)resolvedRawType ,resolvedArgs );}
    private

    static < T>T expectArgument (Class<T>type ,Object arg ){ try
        { return
            type .cast(arg);}
        catch ( ClassCastExceptione ){ throw
            new IllegalArgumentException (arg+ " is not a " + type .getSimpleName(),e );}
        }
    /**
     * A TypeTable maintains mapping from {@link TypeVariable} to types.
     */

    private
    static class TypeTable { private
        final Map <TypeVariableKey,Type >map ;TypeTable

        (){ this
            .map= emptyMap ();}
        private

        TypeTable (Map<TypeVariableKey,Type >map ){ this
            .map= unmodifiableMap (newLinkedHashMap <>(map));}
        /**
         * Returns a new {@code TypeResolver} with {@code variable} mapping to {@code type}.
         */

        final
        TypeTable where (Map<TypeVariableKey,? extends Type >mappings ){ Map
            <TypeVariableKey,Type >builder = new LinkedHashMap <>();builder
            .putAll(map);for
            ( Map.Entry<TypeVariableKey,? extends Type >mapping : mappings .entrySet()){ TypeVariableKey
                variable = mapping .getKey();Type
                type = mapping .getValue();checkArgument
                (!variable.equalsType(type),"Type variable %s bound to itself" ,variable );builder
                .put(variable,type );}
            return
            new TypeTable (builder);}
        final

        Type resolve (finalTypeVariable <?>var ){ final
            TypeTable unguarded = this ;TypeTable
            guarded = new
                TypeTable (){ @
                    Overridepublic
                    Type resolveInternal (TypeVariable<?>intermediateVar ,TypeTable forDependent ){ if
                        ( intermediateVar.getGenericDeclaration().equals(var.getGenericDeclaration())){ return
                            intermediateVar ;}
                        return
                        unguarded .resolveInternal(intermediateVar,forDependent );}
                    }
                ;return
            resolveInternal (var,guarded );}
        Type

        resolveInternal (TypeVariable<?>var ,TypeTable forDependants ){ Type
            type = map .get(newTypeVariableKey (var));if
            ( type== null ){ Type
                []bounds = var .getBounds();if
                ( bounds.length== 0 ){ return
                    var ;}
                Type
                []resolvedBounds = new TypeResolver (forDependants).resolveTypes(bounds);if
                ( Types.NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY&&
                    Arrays .equals(bounds,resolvedBounds )){ return
                    var ;}
                return
                Types .newArtificialTypeVariable(var
                    .getGenericDeclaration(),var .getName(),resolvedBounds );}
            // in case the type is yet another type variable.
            return
            new TypeResolver (forDependants).resolveType(type);}
        }
    private

    static final class TypeMappingIntrospector extends TypeVisitor { private

        static final WildcardCapturer WILDCARD_CAPTURER = new WildcardCapturer ();private

        final Map <TypeVariableKey,Type >mappings = new HashMap <>();static

        Map <TypeVariableKey,Type >getTypeMappings (TypecontextType ){ TypeMappingIntrospector
            introspector = new TypeMappingIntrospector ();introspector
            .visit(WILDCARD_CAPTURER.capture(contextType));return
            unmodifiableMap (newLinkedHashMap <>(introspector.mappings));}
        @

        Overridevoid
        visitClass (Class<?>clazz ){ visit
            (clazz.getGenericSuperclass());visit
            (clazz.getGenericInterfaces());}
        @

        Overridevoid
        visitParameterizedType (ParameterizedTypeparameterizedType ){ Class
            <?>rawClass = ( Class<?>)parameterizedType .getRawType();TypeVariable
            <?>[]vars = rawClass .getTypeParameters();Type
            []typeArgs = parameterizedType .getActualTypeArguments();checkState
            (vars.length== typeArgs .length,"Expected %s type variables, but got %s"
                ,typeArgs .length,vars .length);for
            ( inti = 0 ;i < vars .length;i ++){ map
                (newTypeVariableKey (vars[i]),typeArgs [i]);}
            visit
            (rawClass);visit
            (parameterizedType.getOwnerType());}
        @

        Overridevoid
        visitTypeVariable (TypeVariable<?>t ){ visit
            (t.getBounds());}
        @

        Overridevoid
        visitWildcardType (WildcardTypet ){ visit
            (t.getUpperBounds());}
        private

        void map (finalTypeVariableKey var ,final Type arg ){ if
            ( mappings.containsKey(var)){ return
                ;}
            for
            ( Typet = arg ;t != null ;t = mappings .get(TypeVariableKey.forLookup(t))){ if
                ( var.equalsType(t)){ Type
                    x = arg ;while
                    ( x!= null ){ x
                        = mappings .remove(TypeVariableKey.forLookup(x));}
                    return
                    ;}
                }
            mappings
            .put(var,arg );}
        }
    private

    static final class WildcardCapturer { private

        final AtomicInteger id = new AtomicInteger ();Type

        capture (Typetype ){ checkNotNull
            (type,"type" );if
            ( typeinstanceof Class ){ return
                type ;}
            if
            ( typeinstanceof TypeVariable ){ return
                type ;}
            if
            ( typeinstanceof GenericArrayType ){ GenericArrayType
                arrayType = ( GenericArrayType)type ;return
                Types .newArrayType(capture(arrayType.getGenericComponentType()));}
            if
            ( typeinstanceof ParameterizedType ){ ParameterizedType
                parameterizedType = ( ParameterizedType)type ;return
                Types .newParameterizedTypeWithOwner(captureNullable
                    (parameterizedType.getOwnerType()),(
                    Class<?>)parameterizedType .getRawType(),capture
                    (parameterizedType.getActualTypeArguments()));}
            if
            ( typeinstanceof WildcardType ){ WildcardType
                wildcardType = ( WildcardType)type ;Type
                []lowerBounds = wildcardType .getLowerBounds();if
                ( lowerBounds.length== 0 ){ // ? extends something changes to capture-of Type
                    []upperBounds = wildcardType .getUpperBounds();String
                    name = "capture#"
                        +
                            id .incrementAndGet()+
                            "-of ? extends " +
                            Stream .of(upperBounds).map(Object::toString).collect(Collectors.joining("&"));return
                    Types .newArtificialTypeVariable(WildcardCapturer
                        .class,name ,wildcardType .getUpperBounds());}
                else { return
                    type ;}
                }
            throw
            new AssertionError ("must have been one of the known types");}
        private

        Type captureNullable (@NullableType type ){ if
            ( type== null ){ return
                null ;}
            return
            capture (type);}
        private

        Type []capture (Type[]types ){ Type
            []result = new Type [types.length];for
            ( inti = 0 ;i < types .length;i ++){ result
                [i]= capture (types[i]);}
            return
            result ;}
        }
    static

    final class TypeVariableKey { private
        final TypeVariable <?>var ;TypeVariableKey

        (TypeVariable<?>var ){ this
            .var= checkNotNull (var,"var" );}
        @

        Overridepublic
        int hashCode (){ return
            Objects .hash(var.getGenericDeclaration(),var .getName());}
        @

        Overridepublic
        boolean equals (Objectobj ){ if
            ( objinstanceof TypeVariableKey ){ TypeVariableKey
                that = ( TypeVariableKey)obj ;return
                equalsTypeVariable (that.var);}
            else { return
                false ;}
            }
        @

        Overridepublic
        String toString (){ return
            var .toString();}
        static

        TypeVariableKey forLookup (Typet ){ if
            ( tinstanceof TypeVariable ){ return
                new TypeVariableKey ((TypeVariable<?>)t );}
            else { return
                null ;}
            }
        boolean

        equalsType (Typetype ){ if
            ( typeinstanceof TypeVariable ){ return
                equalsTypeVariable ((TypeVariable<?>)type );}
            else { return
                false ;}
            }
        private

        boolean equalsTypeVariable (TypeVariable<?>that ){ return
            var .getGenericDeclaration().equals(that.getGenericDeclaration())&&
                var .getName().equals(that.getName());}
        }
    }