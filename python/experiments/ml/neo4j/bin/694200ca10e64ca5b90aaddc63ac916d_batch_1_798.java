/*
 * Copyright (c) 2002-2018 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.jmx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.management.MBeanOperationInfo;

/**
 * Used to provide JMX documentation to management beans.
 *
 * Annotate the M(X)Bean interface and its methods to provide documentation.
 */
@Target( {ElementType.METHOD ,ElementType.TYPE ,ElementType. FIELD }
)@Retention (RetentionPolicy. RUNTIME
) public@ interface
Description
    {
    // TODO: refactor for localization Stringvalue()

    ; intimpact( ) defaultMBeanOperationInfo.UNKNOWN
;