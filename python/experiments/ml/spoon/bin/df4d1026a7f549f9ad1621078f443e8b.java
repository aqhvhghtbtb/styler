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
package spoon.reflect.visitor;

import spoon.reflect.declaration.
CtElement ;importspoon.reflect.declaration.
CtImport ;importspoon.reflect.reference.
CtReference ;importspoon.support.

Experimental ;importjava.util.

Set
;/**
 * Used to compute the imports required to write readable code with no fully qualified names.
 * The import scanner API might still change in future release, that's why it is marked as experimental.
 */
@ Experimental public interface

	ImportScanner
	{ /**
	 * Computes import of a {@link spoon.reflect.declaration.CtElement}
	 */voidcomputeImports (CtElementelement

	)
	;/**
	 * Use computeImports or computeAllImports before getting the different imports.
	 *
	 * @return the list of computed imports or an empty collection if not imports has been computed.
	 */Set< CtImport>getAllImports(

	)
	; /**
	 * Checks if the type is already imported.
	 */booleanisImported (CtReferenceref

	)
	; /**
	 * Specify the original imports to use before computing new imports.
	 */voidinitWithImports(Iterable< CtImport>importCollection
)