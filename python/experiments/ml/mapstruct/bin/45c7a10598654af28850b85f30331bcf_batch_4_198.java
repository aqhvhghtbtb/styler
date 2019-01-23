/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.math.BigInteger;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.
ap .internal.model.common.Type;importorg.

mapstruct . ap.internal.util.NativeTypes;importstaticorg.mapstruct.
ap . internal.conversion.ConversionUtils.bigInteger;importstaticorg.mapstruct.

ap
. internal . util . Collections

    . asSet ;/**
 * Conversion between {@link BigInteger} and wrappers of native number types.
 *
 * @author Gunnar Morling
 */publicclass BigIntegerToWrapperConversionextends

    SimpleConversion {privatefinalClass<? >targetType ;
        publicBigIntegerToWrapperConversion( Class <?>targetType ) {this
    .

    targetType=
    NativeTypes . getPrimitiveType(targetType ); }
        @ Override public StringgetToExpression(ConversionContextconversionContext ) {return
    "<SOURCE>."

    +targetType
    . getName ()+ "Value()"; }
        @ Override public StringgetFromExpression
        ( ConversionContext conversionContext ) {StringtoLongValueStr = "" ; if(targetType == float
            . class ||targetType
        ==

        double .class ) { toLongValueStr = ".longValue()" ; } returnbigInteger
    (

    conversionContext)
    + ".valueOf( <SOURCE>"+toLongValueStr+ " )";} @Override protected
        Set <Type >getFromConversionImportTypes(ConversionContextconversionContext){return asSet(conversionContext . getTypeFactory(
    )

.