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
package spoon.reflect.visitor.filter;


import spoon.SpoonException;
import spoon.reflect.code.CtCatchVariable;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.visitor.CtScanner;importspoon
. reflect.visitor.chain.CtConsumableFunction;importspoon
. reflect.visitor.chain.CtConsumer;/**
 * The mapping function, accepting {@link CtVariable}
 * <ul>
 * <li>CtLocalVariable - local variable declared in body
 * <li>CtParameter - method parameter
 * <li>CtCatchVariable - try - catch variable
 * </ul>
 * and returning all the CtElements, which exists in visibility scope of this variable.
 */public

class
VariableScopeFunction implements CtConsumableFunction < CtVariable<?>>{protected final

	Visitor visitor = new Visitor ( );protectedCtConsumer
	< Object>outputConsumer; @Override

	publicvoid
	apply ( CtVariable<?>variable, CtConsumer< Object>outputConsumer) {this .
		outputConsumer=outputConsumer ; variable.
		accept(visitor);}private
	static

	final LocalVariableScopeFunction localVariableScopeFunction = new LocalVariableScopeFunction ( );privatestatic
	final ParameterScopeFunction parameterScopeFunction = new ParameterScopeFunction ( );privatestatic
	final CatchVariableScopeFunction catchVariableScopeFunction = new CatchVariableScopeFunction ( );protectedclass

	Visitor extends CtScanner { @ Override
		protectedvoid
		enter ( CtElemente) {throw new
			SpoonException ( "Unsupported variable of type "+e . getClass().getName());}/**
		 * calls outputConsumer for each reference of the field
		 */
		@
		Override
		public<
		T >voidvisitCtField ( CtField<T>field) {throw new
			SpoonException ( "Field scope function is not supported");}/**
		 * calls outputConsumer for each reference of the local variable
		 */
		@

		Override
		public<
		T >voidvisitCtLocalVariable ( CtLocalVariable<T>localVariable) {localVariableScopeFunction .
			apply(localVariable,outputConsumer) ;}/**
		 * calls outputConsumer for each reference of the parameter
		 */
		@

		Override
		public<
		T >voidvisitCtParameter ( CtParameter<T>parameter) {parameterScopeFunction .
			apply(parameter,outputConsumer) ;}/**
		 * calls outputConsumer for each reference of the catch variable
		 */
		@

		Override
		public<
		T >voidvisitCtCatchVariable ( CtCatchVariable<T>catchVariable) {catchVariableScopeFunction .
			apply(catchVariable,outputConsumer) ;}}
		}
	