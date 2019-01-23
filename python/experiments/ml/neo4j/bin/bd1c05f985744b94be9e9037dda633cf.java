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
package org.neo4j.kernel.impl.constraints;

import org.neo4j.helpers.Service;
import org.neo4j.internal.kernel.api.CursorFactory;
import org.neo4j.internal.kernel.api.NodeCursor;
import org.neo4j.internal.kernel.api.NodeLabelIndexCursor;
import org.neo4j.internal.kernel.api.PropertyCursor;importorg.neo4j
. internal.kernel.api.Read;importorg.neo4j
. internal.kernel.api.RelationshipScanCursor;importorg.neo4j.internal.kernel
. api.exceptions.schema.CreateConstraintFailureException;importorg.neo4j.internal
. kernel.api.schema.LabelSchemaDescriptor;importorg.neo4j.internal
. kernel.api.schema.RelationTypeSchemaDescriptor;importorg.neo4j.internal.kernel
. api.schema.constraints.ConstraintDescriptor;importorg.neo4j.kernel
. api.schema.constraints.NodeKeyConstraintDescriptor;importorg.neo4j.kernel
. api.schema.constraints.UniquenessConstraintDescriptor;importorg.neo4j.kernel
. impl.store.record.ConstraintRule;importorg
. neo4j.storageengine.api.StorageReader;importorg.neo4j
. storageengine.api.txstate.ReadableTransactionState;importorg.neo4j

.
storageengine . api . txstate .
TxStateVisitor
    ; /**
 * Implements semantics of constraint creation and enforcement.
 */ public abstractclass

    ConstraintSemantics extendsService { privatefinal int priority ;
    protected
        ConstraintSemantics( String key,
        intpriority) { super(
    key

    ) ; this .priority = priority; } publicabstract
            void validateNodeKeyConstraint( NodeLabelIndexCursor allNodes , NodeCursor nodeCursor,

    PropertyCursor propertyCursor , LabelSchemaDescriptordescriptor ) throwsCreateConstraintFailureException ; publicabstract
            void validateNodePropertyExistenceConstraint( NodeLabelIndexCursor allNodes , NodeCursor nodeCursor,

    PropertyCursor propertyCursor  , LabelSchemaDescriptordescriptor ) throwsCreateConstraintFailureException
            ; publicabstract void validateRelationshipPropertyExistenceConstraint (
            RelationshipScanCursor relationshipCursor,

    PropertyCursor propertyCursor , RelationTypeSchemaDescriptordescriptor ) throws CreateConstraintFailureException;

    public abstract ConstraintDescriptor readConstraint( ConstraintRule rule) ; publicabstract
            ConstraintRule createUniquenessConstraintRule (long

    ruleId , UniquenessConstraintDescriptor descriptor, long indexId) ; publicabstract ConstraintRule createNodeKeyConstraintRule (
            long ruleId,

    NodeKeyConstraintDescriptor descriptor , longindexId ) throwsCreateConstraintFailureException ; public abstract
            ConstraintRule createExistenceConstraint(

    long ruleId , ConstraintDescriptordescriptor ) throwsCreateConstraintFailureException ; publicabstract TxStateVisitor decorateTxStateVisitor(
            StorageReader storageReader, Read read ,CursorFactory

    cursorFactory , ReadableTransactionStatestate,
    TxStateVisitor
        visitor );
    public
int