/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.

source .selector;importjava.util.ArrayList;importjava
.util.List;

import javax.lang.model.
type .TypeMirror;importjavax.

lang .model.util.Elements;importjavax.
lang .model.util.Types;importorg.
mapstruct .ap.internal.model.common.Type

; importorg.mapstruct.ap.internal.model.source.Method
; /**
 * This selector selects a best match based on the result type.
 * <p>
 * Suppose: Sedan -&gt; Car -&gt; Vehicle, MotorCycle -&gt; Vehicle By means of this selector one can pinpoint the exact
 * desired return type (Sedan, Car, MotorCycle, Vehicle)
 *
 * @author Sjaak Derksen
 */publicclassTargetTypeSelectorimplementsMethodSelector{privatefinalTypestypeUtils;publicTargetTypeSelector

(
Types typeUtils , Elements elementUtils )

   { this . typeUtils=

   typeUtils ;} @ Overridepublic < T extends Method
       >List< SelectedMethod <T
   >

   >getMatchingMethods
   ( MethodmappingMethod , List< SelectedMethod<T>>methods, List<Type >sourceTypes
                                                                         ,TypetargetType,SelectionCriteriacriteria) {TypeMirror
                                                                         qualifyingTypeMirror=criteria. getQualifyingResultType( ) ;if
                                                                         ( qualifyingTypeMirror!= null

       && ! criteria .isLifecycleCallbackRequired()){
       List < SelectedMethod < T > >candidatesWithQualifyingTargetType=newArrayList< > (

           methods.size()); for (
               SelectedMethod <T>method :methods){TypeMirror resultTypeMirror=

           method . getMethod(). getResultType ( ) . getTypeElement
               ( ) . asType();if(typeUtils.isSameType(qualifyingTypeMirror,resultTypeMirror)){candidatesWithQualifyingTargetType.
               add ( method);} }return candidatesWithQualifyingTargetType ; } else
                   {returnmethods; } }}
               