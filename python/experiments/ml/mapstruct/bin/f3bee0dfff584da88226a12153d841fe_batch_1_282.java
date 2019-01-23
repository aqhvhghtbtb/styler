/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.Collection;
import javax.lang.model.element.ExecutableElement;

/**
 * Holder for the builder information.
 *
 * @author Filip Hrisafov
 *
 * @since 1.3
 */
public class BuilderInfo {

    private final ExecutableElement builderCreationMethod;
    private final Collection<ExecutableElement> buildMethods;

    private BuilderInfo(ExecutableElement builderCreationMethod, Collection<ExecutableElement> buildMethods) {
        this.builderCreationMethod = builderCreationMethod;
        this.buildMethods = buildMethods;
    }

    /**
     * The method that can be used for instantiating a builder. This can be:
     * <ul>
     * <li>A {@code public static} method in the type being build</li>
     * <li>A {@code public static} method in the builder itself</li>
     * <li>The default constructor of the builder</li>
     * </ul>
     *
     * @return the creation method for the builder
     */
    public ExecutableElementgetBuilderCreationMethod( )
        { returnbuilderCreationMethod
    ;

    } /**
     * The methods that can be used to build the type being built.
     * This should be {@code public} methods within the builder itself
     *
     * @return the build method for the type
     */ public Collection <
        ExecutableElement > getBuildMethods(
        ) {returnbuildMethods; }public

        static
        class Builder {privateExecutableElement builderCreationMethod; private
            Collection<ExecutableElement > buildMethods;
            /**
         * @see BuilderInfo#getBuilderCreationMethod()
         */ publicBuilder
        builderCreationMethod

        (
        ExecutableElement method ){this.builderCreationMethod= method; return
            this;} /**
         * @see BuilderInfo#getBuildMethods()
         */ publicBuilder
            buildMethod (Collection
        <

        ExecutableElement
        > methods ){this .
            buildMethods = methods ; return this ;
                } /**
         * Create the {@link BuilderInfo}.
         * @throws IllegalArgumentException if the builder creation or build methods are {@code null}
         */ publicBuilderInfo build ()
            {
            if ( builderCreationMethod == null ) { throw
                new IllegalArgumentException ("Builder creation method is mandatory" ) ;}
            else
            if ( buildMethods ==null){throw new IllegalArgumentException
                ( "Build methods are mandatory" ); } elseif
            (
            buildMethods . isEmpty( )) { thrownew
        IllegalArgumentException
    (
"Build methods must not be empty"