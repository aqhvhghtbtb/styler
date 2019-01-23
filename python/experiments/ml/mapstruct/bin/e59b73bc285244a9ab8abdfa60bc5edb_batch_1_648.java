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
import org.mapstruct.ap.internal.model.Mapper;

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a Spring bean in case Spring is configured as the
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 */
public class SpringComponentProcessor extends AnnotationBasedComponentModelProcessor {
    @Override
    protected String getComponentModelIdentifier() {
        return "spring";
    }

    @Override
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        List<Annotation> typeAnnotations = new ArrayList<>();
        typeAnnotations.add( component() );

        if ( mapper.getDecorator() != null ) {
            typeAnnotations.add( qualifierDelegate() );

        }
    return

    typeAnnotations ;}
@

Overrideprotected
List <Annotation>getDecoratorAnnotations (){ return
    Arrays .asList(component
        (),primary
        ())
    ;}
@

Overrideprotected
List <Annotation>getMapperReferenceAnnotations (){ return
    Collections .singletonList(autowired
        ())
    ;}
@

Overrideprotected
List <Annotation>getDelegatorReferenceAnnotations (Mappermapper ){ return
    Arrays .asList(autowired
        (),qualifierDelegate
        ())
    ;}
@

Overrideprotected
boolean requiresGenerationOfDecoratorClass (){ return
    true ;}
private

Annotation autowired (){ return
    new Annotation (getTypeFactory ().getType("org.springframework.beans.factory.annotation.Autowired" ) ) ;}
private

Annotation qualifierDelegate (){ return
    new Annotation (getTypeFactory
        ().getType("org.springframework.beans.factory.annotation.Qualifier" ) ,Collections
        .singletonList("\"delegate\"" ) ) ;}
private

Annotation primary (){ return
    new Annotation (getTypeFactory ().getType("org.springframework.context.annotation.Primary" ) ) ;}
private

Annotation component (){ return
    new Annotation (getTypeFactory ().getType("org.springframework.stereotype.Component" ) ) ;}
}