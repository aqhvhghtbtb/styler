/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.sqlobject;importjava

. lang.annotation.Annotation;importjava
. lang.reflect.Method;importjava
. lang.reflect.Parameter;importjava
. util.Optional;importjava
. util.stream.Stream;importorg

. jdbi.v3.sqlobject.customizer.SqlStatementCustomizingAnnotation;classDefaultMethodHandlerFactory

implements HandlerFactory { @ Override
    publicOptional
    < Handler>buildHandler( Class<?>sqlObjectType, Methodmethod ) {if (
        ! method.isDefault()){return Optional
            . empty();}Stream
        .

        of(method.getAnnotations()).map
                (Annotation::annotationType).filter
                (type->type . isAnnotationPresent(SqlStatementCustomizingAnnotation.class)).findFirst
                ().ifPresent
                (type->{ throw new
                    IllegalStateException ( String.format("Default method %s.%s has @%s annotation. Statement customizing annotations don't "+
                            "work on default methods."
                                    , sqlObjectType.
                            getSimpleName(),method.
                            getName(),type.
                            getSimpleName()));})
                ;for(

        Parameter parameter: method . getParameters()){Stream .
            of(parameter.getAnnotations()).map
                    (Annotation::annotationType).filter
                    (type->type . isAnnotationPresent(SqlStatementCustomizingAnnotation.class)).findFirst
                    ().ifPresent
                    (type->{ throw new
                        IllegalStateException ( String.format("Default method %s.%s parameter %s has @%s annotation. Statement customizing "+
                                "annotations don't work on default methods."
                                        , sqlObjectType.
                                getSimpleName(),method.
                                getName(),parameter.
                                getName(),type.
                                getSimpleName()));})
                    ;}return
        Optional

        . of(newDefaultMethodHandler( method));}}
    