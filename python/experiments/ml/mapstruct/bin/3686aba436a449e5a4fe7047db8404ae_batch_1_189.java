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
import org.mapstruct.ap.internal.model.

Mapper
; /**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a Spring bean in case Spring is configured as the
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 */ public class SpringComponentProcessor extends
    AnnotationBasedComponentModelProcessor{
    @ Override protectedStringgetComponentModelIdentifier (
        ) {return
    "spring"

    ;}
    @ OverrideprotectedList< Annotation>getTypeAnnotations (Mapper mapper
        ){List< Annotation > typeAnnotations =newArrayList<>(
        );typeAnnotations. add(component ()

        ) ; if(mapper.getDecorator ( ) != null
            ){typeAnnotations. add(qualifierDelegate ()
        )

        ; }return
    typeAnnotations

    ;}
    @ OverrideprotectedList< Annotation>getDecoratorAnnotations (
        ) {returnArrays.
            asList(component(
            ),primary
        ()
    )

    ;}
    @ OverrideprotectedList< Annotation>getMapperReferenceAnnotations (
        ) {returnCollections.
            singletonList(autowired
        ()
    )

    ;}
    @ OverrideprotectedList< Annotation>getDelegatorReferenceAnnotations (Mapper mapper
        ) {returnArrays.
            asList(autowired(
            ),qualifierDelegate
        ()
    )

    ;}
    @ Override protectedbooleanrequiresGenerationOfDecoratorClass (
        ) {return
    true

    ; } privateAnnotationautowired (
        ) { returnnew Annotation(getTypeFactory(). getType ( "org.springframework.beans.factory.annotation.Autowired")
    )

    ; } privateAnnotationqualifierDelegate (
        ) { returnnew
            Annotation(getTypeFactory(). getType ("org.springframework.beans.factory.annotation.Qualifier"
            ),Collections. singletonList ( "\"delegate\"")
    )

    ; } privateAnnotationprimary (
        ) { returnnew Annotation(getTypeFactory(). getType ( "org.springframework.context.annotation.Primary")
    )

    ; } privateAnnotationcomponent (
        ) { returnnew Annotation(getTypeFactory(). getType ( "org.springframework.stereotype.Component")
    )
 ;
 