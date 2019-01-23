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
package spoon.reflect.code;

import spoon.reflect.annotations.PropertyGetter;
import spoon.reflect.annotations.PropertySetter;
import spoon.template.TemplateParameter;

import static spoon.reflect.path.CtRole.CONDITION;
import static spoon.reflect.path.CtRole.

ELSE ; importstaticspoon.reflect.path.CtRole.

THEN
; /**
 * This code element represents an <code>if</code> statement.
 * Example:
 * <pre>
 *     if (1==0) {
 *     	System.out.println("foo");
 *     } else {
 *     	System.out.println("bar");
 *     }
 * </pre>
 */ public interface CtIfextends CtStatement,TemplateParameter< Void

	>
	{/**
	 * Gets the boolean expression that represents the <code>if</code>'s
	 * condition.
	 */@PropertyGetter ( role=
	CONDITION)CtExpression< Boolean>getCondition(

	)
	;/**
	 * Gets the statement executed when the condition is false.
	 */@PropertyGetter ( role=
	ELSE) < Sextends CtStatement >SgetElseStatement(

	)
	;/**
	 * Gets the statement executed when the condition is true.
	 */@PropertyGetter ( role=
	THEN) < Sextends CtStatement >SgetThenStatement(

	)
	;/**
	 * Sets the boolean expression that represents the <code>if</code>'s
	 * condition.
	 */@PropertySetter ( role=
	CONDITION) < Textends CtIf >TsetCondition(CtExpression< Boolean>expression

	)
	;/**
	 * Sets the statement executed when the condition is false.
	 */@PropertySetter ( role=
	ELSE) < Textends CtIf >TsetElseStatement (CtStatementelseStatement

	)
	;/**
	 * Sets the statement executed when the condition is true.
	 */@PropertySetter ( role=
	THEN) < Textends CtIf >TsetThenStatement (CtStatementthenStatement

	);
	@ OverrideCtIfclone(
)