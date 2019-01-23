/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import static org.mapstruct.ap.internal.util.Strings.getSafeVariableName;
import static org.mapstruct.ap.internal.util.Strings.join;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * A method implemented or referenced by a {@link Mapper} class.
 *
 * @author Gunnar Morling
 */
public abstract class MappingMethod extends ModelElement {

    private final String name;
    private final List<Parameter> parameters;
    private final List<Parameter> sourceParameters;
    private final Type returnType;
    private final Parameter targetParameter;
    private final Accessibility accessibility;
    private final List<Type> thrownTypes;
    private final boolean isStatic;
    private final String resultName;
    private final List<LifecycleCallbackMethodReference> beforeMappingReferencesWithMappingTarget;
    private final List<LifecycleCallbackMethodReference> beforeMappingReferencesWithoutMappingTarget;
    private final List<LifecycleCallbackMethodReference> afterMappingReferences;

    /**
     * constructor to be overloaded when local variable names are required prior to calling this constructor. (e.g. for
     * property mappings). It is supposed to be initialized with at least the parameter names.
     *
     * @param method the method for which this mapping is applicable
     * @param existingVariableNames set of already assigned variable names
     * @param beforeMappingReferences all life cycle methods to be called prior to carrying out mapping
     * @param afterMappingReferences all life cycle methods to be called after carrying out mapping
     */
    protected MappingMethod(Method method, Collection<String> existingVariableNames,
                            List<LifecycleCallbackMethodReference> beforeMappingReferences,
                            List<LifecycleCallbackMethodReference> afterMappingReferences) {
        this( method, method.getParameters(), existingVariableNames, beforeMappingReferences, afterMappingReferences );
    }

    protected MappingMethod(Method method, List<Parameter> parameters, Collection<String> existingVariableNames,
                            List<LifecycleCallbackMethodReference> beforeMappingReferences,
        List<LifecycleCallbackMethodReference> afterMappingReferences) {
        this.name = method.getName();
        this.parameters = parameters;
        this.sourceParameters = Parameter.getSourceParameters( parameters );
        this.returnType = method.getReturnType();
        this.targetParameter = method.getMappingTargetParameter();
        this.accessibility = method.getAccessibility();
        this.thrownTypes = method.getThrownTypes();
        this.isStatic = method.isStatic();
        this.resultName = initResultName( existingVariableNames );
        this.beforeMappingReferencesWithMappingTarget = filterMappingTarget( beforeMappingReferences, true );
        this.beforeMappingReferencesWithoutMappingTarget = filterMappingTarget( beforeMappingReferences, false );
        this.afterMappingReferences = afterMappingReferences;
    }

    protected MappingMethod(Method method, List<Parameter> parameters) {
        this( method, parameters, new ArrayList<>( method.getParameterNames() ), null, null );
    }

    protected MappingMethod(Method method) {
        this( method, new ArrayList<>( method.getParameterNames() ), null, null );
    }

    protected MappingMethod(Method method, List<LifecycleCallbackMethodReference> beforeMappingReferences,
                            List<LifecycleCallbackMethodReference> afterMappingReferences) {
        this( method, new ArrayList<>( method.getParameterNames() ), beforeMappingReferences,
            afterMappingReferences );
    }

    private String initResultName(Collection<String> existingVarNames) {
        if ( targetParameter != null ) {
            return targetParameter.getName();
        }
        else if ( getResultType().isArrayType() ) {
            String name = getSafeVariableName( getResultType().getComponentType().getName() + "Tmp", existingVarNames );
            existingVarNames.add( name );
            return name;
        }
        else {
            String name = getSafeVariableName( getResultType().getName(),existingVarNames);existingVarNames . add(
            name);return name ;}
            } publicString
        getName
    (

    ) { returnname; }
        public List<
    Parameter

    > getParameters(){ returnparameters; }
        public List<
    Parameter

    > getSourceParameters(){ returnsourceParameters; }
        public TypegetResultType
    (

    ) { returntargetParameter!= null
        ? targetParameter . getType ( ):returnType;} public StringgetResultName
    (

    ) { returnresultName; }
        public TypegetReturnType
    (

    ) { returnreturnType; }
        public AccessibilitygetAccessibility
    (

    ) { returnaccessibility; }
        public booleanisExistingInstanceMapping
    (

    ) { returntargetParameter!= null
        ; } public booleanisStatic
    (

    ) { returnisStatic; }
        @ Overridepublic
    Set

    <Type
    > getImportTypes(){ Set<Type >
        types=newHashSet < > ( );for(Parameterparam

        : parameters ) { types . addAll (
            param.getType( ).getImportTypes());}types .addAll
        (

        getReturnType(). getImportTypes());for( Typetype

        : thrownTypes ) { types . addAll (
            type.getImportTypes( ));}return types;
        }

        protected List<
    String

    > getParameterNames(){ List<String >
        parameterNames=newArrayList < > ( parameters.size( ));for( Parameterparameter

        : parameters ) { parameterNames . add (
            parameter.getName( ));}return parameterNames;
        }

        public List<
    Type

    > getThrownTypes(){ returnthrownTypes; }
        @ Overridepublic
    String

    toString(
    ) { returnreturnType+ " "
        + getName ( ) + "("+join ( parameters , ", ") +")" ; } private List<
    LifecycleCallbackMethodReference

    > filterMappingTarget(List< LifecycleCallbackMethodReference>methods,booleanmustHaveMappingTargetParameter ){
                                                                       if (methods ==
        null ) { return null ; }
            List <LifecycleCallbackMethodReference
        >

        result=newArrayList < >
            ( methods.size( ));for( LifecycleCallbackMethodReferencemethod

        : methods ) { if ( mustHaveMappingTargetParameter ==
            method . hasMappingTargetParameter ( )){result. add (
                method);} } returnresult
            ;
        }

        public List<
    LifecycleCallbackMethodReference

    > getAfterMappingReferences(){ returnafterMappingReferences; }
        public List<
    LifecycleCallbackMethodReference

    > getBeforeMappingReferencesWithMappingTarget(){ returnbeforeMappingReferencesWithMappingTarget; }
        public List<
    LifecycleCallbackMethodReference

    > getBeforeMappingReferencesWithoutMappingTarget(){ returnbeforeMappingReferencesWithoutMappingTarget; }
        @ Overridepublic
    int

    hashCode(
    ) { inthash= 7
        ; hash = 83*
        hash + ( this . parameters !=null?this . parameters . hashCode():0); hash =83*
        hash + ( this . returnType !=null?this . returnType . hashCode():0); return hash;}
        @ Overridepublic
    boolean

    equals(
    Object obj ){if (this ==
        obj ) { return true ; }
            if (obj
        ==
        null ) { return false ; }
            if (getClass
        (
        ) != obj.getClass ( )){returnfalse ; }
            //Do not add name to the equals check. //Reason: Whenever we forge methods we can reuse mappings if they are the same. However, if we take the name// into consideration, they'll never be the same, because we create safe methods names.
        final
        MappingMethod
        other
        =
        ( MappingMethod ) obj ;if( this.
        parameters != other.parameters && (this. parameters
            ==null||! this . parameters .equals(other.parameters) )){ returnfalse ; }
            if (this
        .
        returnType != other.returnType && (this. returnType
            ==null||! this . returnType .equals(other.returnType) )){ returnfalse ; }
            return true;
        }
        } 