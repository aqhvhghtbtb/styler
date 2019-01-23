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
package spoon.support.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

public class SortedList<E> extends LinkedList<E> {

	private static final long serialVersionUID = 1L;

	Comparator<? super E> comparator;

	public SortedList(Comparator<? super E> comparator) {
		this.comparator = comparator;
	}

	@Override
	public boolean add(E o) {
		for (ListIterator<E> iterator = this.listIterator(); iterator.hasNext();
) { E e=iterator.next(
) ;if(comparator.compare( o, e )< 0
	){iterator.previous(
	);iterator.add(o
	) ;return
true
;
} }returnsuper.add(o
)

;}
@ Override publicvoidadd (int index ,E element
) { thrownewIllegalArgumentException("cannot force a position with a sorted list that has its own ordering"
)

;}
@ Override publicbooleanaddAll(Collection < ?extends E> c
) { boolean ret=
true ;for ( E e: c
) { ret&=add(e
)
; }return
ret

; }publicComparator < ?super E>getComparator (
) {return
comparator

; } publicvoidsetComparator(Comparator < ?super E> comparator
){this . comparator=
comparator

;