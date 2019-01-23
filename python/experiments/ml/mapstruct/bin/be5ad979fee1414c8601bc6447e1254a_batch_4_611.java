/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap. internal . model . Mapper
    ;/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a Spring bean in case Spring is configured as the
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 */
    public class SpringComponentProcessorextendsAnnotationBasedComponentModelProcessor {
        @ Overrideprotected
    String

    getComponentModelIdentifier(
    ) {return"spring"; }@Override protectedList <
        Annotation>getTypeAnnotations( Mapper mapper ) {List<Annotation>typeAnnotations
        =newArrayList< >() ;typeAnnotations

        . add (component()) ; if ( mapper
            .getDecorator() !=null) {typeAnnotations
        .

        add (qualifierDelegate
    (

    ))
    ; }returntypeAnnotations; }@Override protected
        List <Annotation>getDecoratorAnnotations
            (){return
            Arrays.asList
        (component
    (

    ),
    primary ()); }@Override protected
        List <Annotation>getMapperReferenceAnnotations
            (){
        returnCollections
    .

    singletonList(
    autowired ()); }@Override protectedList <
        Annotation >getDelegatorReferenceAnnotations(Mapper
            mapper){return
            Arrays.asList
        (autowired
    (

    ),
    qualifierDelegate ( )); }
        @ Overrideprotected
    boolean

    requiresGenerationOfDecoratorClass ( ){return true
        ; } privateAnnotation autowired(){returnnew Annotation ( getTypeFactory(
    )

    . getType ("org.springframework.beans.factory.annotation.Autowired") )
        ; } privateAnnotation
            qualifierDelegate(){returnnew Annotation (getTypeFactory
            ().getType ( "org.springframework.beans.factory.annotation.Qualifier" ),
    Collections

    . singletonList ("\"delegate\"") )
        ; } privateAnnotation primary(){returnnew Annotation ( getTypeFactory(
    )

    . getType ("org.springframework.context.annotation.Primary") )
        ; } privateAnnotation component(){returnnew Annotation ( getTypeFactory(
    )
.