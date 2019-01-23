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
package spoon.support.modelobs;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import spoon.compiler.Environment;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.ModifierKind;importspoon
. reflect.path.CtRole;importspoon
. reflect.visitor.EarlyTerminatingScanner;importspoon
. reflect.visitor.chain.CtScannerListener;importspoon
. reflect.visitor.chain.ScanningMode;/**
 * Listens on changes  on the spoon model and remembers them
 */public

class
ChangeCollector { private final
	Map < CtElement,Set< CtRole>>elementToChangeRole= new IdentityHashMap < >();privatefinal
	ChangeListener changeListener = new ChangeListener ( );/**
	 * @param env to be checked {@link Environment}
	 * @return {@link ChangeCollector} attached to the `env` or null if there is none
	 */public

	static
	ChangeCollector getChangeCollector ( Environmentenv) {FineModelChangeListener mcl
		= env . getModelChangeListener();if(
		mcl instanceofChangeListener ) {return (
			( ChangeListener)mcl) .getChangeCollector();}return
		null
		; }/**
	 * Allows to run code using change collector switched off.
	 * It means that any change of spoon model done by the `runnable` is ignored by the change collector.
	 * Note: it is actually needed to wrap CtElement#toString() calls which sometime modifies spoon model.
	 * See TestSniperPrinter#testPrintChangedReferenceBuilder()
	 * @param env Spoon environment
	 * @param runnable the code to be run
	 */
	public

	static
	void runWithoutChangeListener ( Environmentenv, Runnablerunnable ) {FineModelChangeListener mcl
		= env . getModelChangeListener();if(
		mcl instanceofChangeListener ) {env .
			setModelChangeListener(newEmptyModelChangeListener( ));try{
			runnable .
				run();}finally
			{ env .
				setModelChangeListener(mcl);}}
			}
		/**
	 * Attaches itself to {@link CtModel} to listen to all changes of it's child elements
	 * TODO: it would be nicer if we might listen on changes on {@link CtElement}
	 * @param env to be attached to {@link Environment}
	 * @return this to support fluent API
	 */
	public

	ChangeCollector
	attachTo ( Environmentenv) {env .
		setModelChangeListener(changeListener);returnthis
		; }/**
	 * @param currentElement the {@link CtElement} whose changes has to be checked
	 * @return set of {@link CtRole}s whose attribute was directly changed on `currentElement` since this {@link ChangeCollector} was attached
	 * The 'directly' means that value of attribute of `currentElement` was changed.
	 * Use {@link #getChanges(CtElement)} to detect changes in child elements too
	 */
	public

	Set
	< CtRole>getDirectChanges( CtElementcurrentElement) {Set <
		CtRole>changes= elementToChangeRole . get(currentElement);if(
		changes ==null ) {return Collections
			. emptySet();}return
		Collections
		. unmodifiableSet(changes);}/**
	 * @param currentElement the {@link CtElement} whose changes has to be checked
	 * @return set of {@link CtRole}s whose attribute was changed on `currentElement`
	 * or any child of this attribute was changed
	 * since this {@link ChangeCollector} was attached
	 */
	public

	Set
	< CtRole>getChanges( CtElementcurrentElement) {final Set
		< CtRole>changes= new HashSet < >(getDirectChanges(currentElement));finalScanner
		scanner = new Scanner ( );scanner.
		setListener(newCtScannerListener( ){int depth
			= 0 ; CtRolecheckedRole
			; @Override
			publicScanningMode
			enter ( CtElementelement) {if (
				depth ==0 ) {//we are checking children of role checkedRole checkedRole
					=
					scanner . getScannedRole();}if
				(
				changes .contains(checkedRole)){//we already know that some child of `checkedRole` attribute is modified. Skip others return
					ScanningMode
					. SKIP_ALL;}if
				(
				elementToChangeRole .containsKey(element)){//we have found a modified element in children of `checkedRole` changes
					.
					add(checkedRole);returnScanningMode
					. SKIP_ALL;}depth
				++
				;//continue searching for an modificationreturn
				ScanningMode
				. NORMAL;}@
			Override
			publicvoid
			exit ( CtElementelement) {depth --
				;}}
			)
		;currentElement.
		accept(scanner);returnCollections
		. unmodifiableSet(changes);}private
	static

	class Scanner extends EarlyTerminatingScanner < Void>{CtRole getScannedRole
		( ){return scannedRole
			; }}
		/**
	 * Called whenever anything changes in the spoon model
	 * @param currentElement the modified element
	 * @param role the modified attribute of that element
	 */
	protected

	void
	onChange ( CtElementcurrentElement, CtRolerole ) {Set <
		CtRole>roles= elementToChangeRole . get(currentElement);if(
		roles ==null ) {roles =
			new HashSet < >();elementToChangeRole.
			put(currentElement,roles) ;}if
		(
		role .getSuperRole()!=null ) {role =
			role . getSuperRole();}roles
		.
		add(role);}private
	class

	ChangeListener implements FineModelChangeListener { private ChangeCollector
		getChangeCollector ( ){return ChangeCollector
			. this;}@
		Override

		publicvoid
		onObjectUpdate ( CtElementcurrentElement, CtRolerole , CtElementnewValue , CtElementoldValue ) {onChange (
			currentElement,role) ;}@
		Override

		publicvoid
		onObjectUpdate ( CtElementcurrentElement, CtRolerole , ObjectnewValue , ObjectoldValue ) {onChange (
			currentElement,role) ;}@
		Override

		publicvoid
		onObjectDelete ( CtElementcurrentElement, CtRolerole , CtElementoldValue ) {onChange (
			currentElement,role) ;}@
		Override

		publicvoid
		onListAdd ( CtElementcurrentElement, CtRolerole , Listfield , CtElementnewValue ) {onChange (
			currentElement,role) ;}@
		Override

		publicvoid
		onListAdd ( CtElementcurrentElement, CtRolerole , Listfield , intindex , CtElementnewValue ) {onChange (
			currentElement,role) ;}@
		Override

		publicvoid
		onListDelete ( CtElementcurrentElement, CtRolerole , Listfield , Collection< ?extendsCtElement > oldValue) {onChange (
			currentElement,role) ;}@
		Override

		publicvoid
		onListDelete ( CtElementcurrentElement, CtRolerole , Listfield , intindex , CtElementoldValue ) {onChange (
			currentElement,role) ;}@
		Override

		publicvoid
		onListDeleteAll ( CtElementcurrentElement, CtRolerole , Listfield , ListoldValue ) {onChange (
			currentElement,role) ;}@
		Override

		public<
		K ,V> voidonMapAdd ( CtElementcurrentElement, CtRolerole , Map< K,V> field, Kkey , CtElementnewValue ) {onChange (
			currentElement,role) ;}@
		Override

		public<
		K ,V> voidonMapDeleteAll ( CtElementcurrentElement, CtRolerole , Map< K,V> field, Map< K,V> oldValue) {onChange (
			currentElement,role) ;}@
		Override

		publicvoid
		onSetAdd ( CtElementcurrentElement, CtRolerole , Setfield , CtElementnewValue ) {onChange (
			currentElement,role) ;}@
		Override

		public<
		T extendsEnum > voidonSetAdd ( CtElementcurrentElement, CtRolerole , Setfield , TnewValue ) {onChange (
			currentElement,role) ;}@
		Override

		publicvoid
		onSetDelete ( CtElementcurrentElement, CtRolerole , Setfield , CtElementoldValue ) {onChange (
			currentElement,role) ;}@
		Override

		publicvoid
		onSetDelete ( CtElementcurrentElement, CtRolerole , Setfield , Collection< ModifierKind>oldValue) {onChange (
			currentElement,role) ;}@
		Override

		publicvoid
		onSetDelete ( CtElementcurrentElement, CtRolerole , Setfield , ModifierKindoldValue ) {onChange (
			currentElement,role) ;}@
		Override

		publicvoid
		onSetDeleteAll ( CtElementcurrentElement, CtRolerole , Setfield , SetoldValue ) {onChange (
			currentElement,role) ;}}
		}
	