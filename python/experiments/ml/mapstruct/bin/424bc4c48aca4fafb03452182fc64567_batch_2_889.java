/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.Set;importorg.mapstruct.ap.internal
. model.common.Parameter;importorg.mapstruct.ap.internal
. model.common.Type;importorg.mapstruct.ap.internal

. model .common.TypeFactory;importstaticorg.mapstruct.ap.internal

.
util . Collections . asSet ;

    /**
 * @author Sjaak Derksen
 */ public class DateToXmlGregorianCalendarextends
    AbstractToXmlGregorianCalendar { privatefinalParameterparameter ;private

    final Set<Type >importTypes ;
        publicDateToXmlGregorianCalendar ( TypeFactorytypeFactory
        ){super ( typeFactory ); this. parameter=newParameter ("date", typeFactory .getType
        (Date. class ))
            ;this.importTypes=asSet
            (parameter.getType (), typeFactory
        .getType
    (

    GregorianCalendar.
    class ));} @Overridepublic Set
        <Type>getImportTypes ( ) {Set<Type>result
        =super.getImportTypes (); result.
        addAll (this
    .

    importTypes)
    ; return result;} @
        Override publicParameter
    getParameter

(