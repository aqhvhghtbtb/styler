/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin

; importjava.util.Date
; importjava.util.GregorianCalendar
; importjava.util.Set

; importorg.mapstruct.ap.internal.model.common.Parameter
; importorg.mapstruct.ap.internal.model.common.Type
; importorg.mapstruct.ap.internal.model.common.TypeFactory

; import staticorg.mapstruct.ap.internal.util.Collections.asSet

;
/**
 * @author Sjaak Derksen
 */ public class DateToXmlGregorianCalendar extends AbstractToXmlGregorianCalendar

    { private final Parameterparameter
    ; private finalSet<Type >importTypes

    ; publicDateToXmlGregorianCalendar( TypeFactorytypeFactory )
        {super ( typeFactory)
        ;this. parameter = newParameter ("date" ,typeFactory.getType (Date. class ))
        ;this. importTypes =asSet
            (parameter.getType()
            ,typeFactory.getType (GregorianCalendar. class
        ))
    ;

    }@
    Override publicSet<Type >getImportTypes( )
        {Set<Type > result =super.getImportTypes()
        ;result.addAll (this. importTypes)
        ; returnresult
    ;

    }@
    Override public ParametergetParameter( )
        { returnparameter
    ;

}