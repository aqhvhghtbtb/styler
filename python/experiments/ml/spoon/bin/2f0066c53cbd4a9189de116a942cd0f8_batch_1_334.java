/**
 * Copyright (C) 2006-2018 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.reflect.meta.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import spoon.SpoonException;
import spoon.reflect.meta.RoleHandler;
import spoon.reflect.path.CtRole;

/**
 * common implementation of {@link RoleHandler}
 * @param <T> the type of node whose attribute has to be manipulated
 * @param <U> the type of container value of the attribute
 * @param <V> the type of item value of the attribute
 */
abstract class AbstractRoleHandler<T, U, V> implements RoleHandler {

	private final CtRole role;
	private final Class<T> targetClass;
	private final Class<V> valueClass;

	@SuppressWarnings({ "unchecked", "rawtypes" } )
	protected AbstractRoleHandler (CtRolerole, Class< T>targetType, Class< ?
		>valueType) { this.
		role=role ; this.
		targetClass=targetType ; this.valueClass =(
	Class

	)valueType
	; } @Overridepublic CtRole
		getRole ()
	{

	returnrole
	; }@Overridepublic Class<? >
		getTargetType ()
	{


	returntargetClass;}@
	SuppressWarnings ( "unchecked")protected TcastTarget (
		Object element){ return(
	T
	)element;}@
	SuppressWarnings ( "unchecked")protected UcastValue (
		Object value){ return(
	U

	) value ;}protectedvoidcheckItemsClass( Iterable< ?
		>
		iterable ){ //check that each item has expected class for (Object value
			:iterable){castItemValue
		(
	value
	);}}@
	SuppressWarnings ( "unchecked")protected VcastItemValue (
		Object
		value ){ //check that item has expected class if ( value!=null&&valueClass. isInstance (value )
			== false ){thrownewClassCastException(value.getClass() . getName ( )+" cannot be cast to "+valueClass.getName
		(
		) );} return(
	V

	)value
	; }@Override public< W ,X> voidsetValue ( Welement ,
		X value ){throw new SpoonException("Setting of CtRole."+role . name ( )+" is not supported for "+element.getClass().getSimpleName
	(

	))
	; }@Overridepublic Class<? >
		getValueClass ()
	{

	returnvalueClass
	; }@Override public< W,X> List<X >asList (
		W element ){throw new SpoonException("The value of CtRole."+getRole() . name ( )+" cannot be adapted to List for "+element.getClass().getSimpleName
	(

	))
	; }@Override public< W,X> Set<X >asSet (
		W element ){throw new SpoonException("The value of CtRole."+getRole() . name ( )+" cannot be adapted to Set for "+element.getClass().getSimpleName
	(

	))
	; }@Override public< W,X> Map< String,X >asMap (
		W element ){throw new SpoonException("The value of CtRole."+getRole() . name ( )+" cannot be adapted to Map for "+element.getClass().getSimpleName
	(

	))
	; } @Overridepublic String
		toString (){returngetTargetType() . getName ( )+"#"+getRole().
	getCamelCaseName
(