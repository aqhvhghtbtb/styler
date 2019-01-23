/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.element.TypeElement;
import javax.lang.model.
type . TypeMirror;/**
 * Indicates a type was visited whose hierarchy was erroneous, because it has a non-existing super-type.
 * <p>
 * This exception can be used to signal the MapStruct processor to postpone the generation of the mappers to the next
 * round
 *
 * @author Gunnar Morling
 */publicclass

TypeHierarchyErroneousException extends RuntimeException { private static finallong

serialVersionUID = 1L ;private

final TypeMirrortype; publicTypeHierarchyErroneousException (
    TypeElementelement ){this(element .asType
(

) );} publicTypeHierarchyErroneousException (
    TypeMirrortype) { this.
type

= type ;}public TypeMirror
    getType ()
{
return