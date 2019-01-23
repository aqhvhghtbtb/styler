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
package org.jdbi.v3.

core .array;importjava.lang.
reflect .Type;importjava.

util .Optional;importorg.jdbi.v3.core.
collector .JdbiCollectors;importorg.jdbi.v3.core.
config .ConfigRegistry;importorg.jdbi.v3.core.
generic .GenericTypes;importorg.jdbi.v3.core.
mapper .ColumnMapper;importorg.jdbi.v3.core.
mapper .ColumnMapperFactory;importorg.jdbi.v3.core.

mapper
. ColumnMappers ; /**
 * Maps SQL array columns into Java arrays or other Java container types.
 * Supports any Java array type for which a {@link ColumnMapper} is registered
 * for the array element type. Supports any other container type for which a
 * {@link org.jdbi.v3.core.collector.CollectorFactory} is registered, and for which
 * a {@link ColumnMapper} is registered for the container element type.
 */ public class
    SqlArrayMapperFactoryimplements
    ColumnMapperFactory{@Override@
    SuppressWarnings ("unchecked")publicOptional<ColumnMapper <?> >build ( Typetype ,
        ConfigRegistry config){final Class < ?>erasedType=GenericTypes.getErasedType

        ( type);if(erasedType. isArray
            ()){ Class < ?>elementType=erasedType.
            getComponentType ();return elementTypeMapper(
                    elementType,config) . map (elementMapper->new ArrayColumnMapper(elementMapper,
        elementType

        ) ) ; }JdbiCollectorscollectorRegistry=config.get(JdbiCollectors
        . class); return(Optional)collectorRegistry.
                findFor(type) . flatMap(collector->collectorRegistry.
                        findElementTypeFor(type) . flatMap(elementType-> elementTypeMapper(elementType
                        ,config)) . map (elementMapper->new CollectorColumnMapper(elementMapper,collector
    )

    ) );}privateOptional<ColumnMapper <?> >elementTypeMapper ( TypeelementType ,
        ConfigRegistryconfig){Optional<ColumnMapper < ? >>mapper=config.get(ColumnMappers.class).findFor

        ( elementType);if(!mapper . isPresent ( )&&elementType== Object
            . class){returnOptional.of (( rs, num ,context)->rs.getObject(
        num

        ) );
    }
return