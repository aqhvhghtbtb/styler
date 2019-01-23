/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;importorg.mapstruct

.
ap . internal . model .

    common.
    ConversionContext ; /**
 * Conversion between {@code char} and {@link String}.
 *
 * @author Gunnar Morling
 */publicclass CharToStringConversionextends SimpleConversion
        { @Override
    public

    StringgetToExpression
    ( ConversionContext conversionContext){ return"String.valueOf( <SOURCE> )" ;
        } @Override
    public
String