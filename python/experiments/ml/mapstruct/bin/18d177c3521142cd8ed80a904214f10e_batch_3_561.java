/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;import

java .util.Set;importorg.mapstruct.ap.internal.
model .common.Parameter;importorg.mapstruct.ap.internal.
model .common.Type;importorg.mapstruct.ap.internal.
model .common.TypeFactory;importorg.mapstruct.ap.

internal
. util . JodaTimeConstants ; /**
 * @author Sjaak Derksen
 */

    public class JodaDateTimeToXmlGregorianCalendar extendsAbstractToXmlGregorianCalendar

    { privatefinalParameter parameter; public
        JodaDateTimeToXmlGregorianCalendar( TypeFactory typeFactory)
        {super( typeFactory ) ;this.parameter =newParameter( "dt",typeFactory . getType(
    JodaTimeConstants

    .DATE_TIME_FQN
    ) );}@ OverridepublicSet <
        Type>getImportTypes( ) { Set<Type>result=
        super.getImportTypes( );result.add (parameter
        . getType(
    )

    );
    return result ;}@ Override
        public ParametergetParameter
    (

)