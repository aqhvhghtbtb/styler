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
package org.neo4j.unsafe.impl.batchimport;

import org.neo4j.kernel.api.StatementConstants;
import org.neo4j.kernel.impl.api.CountsAccessor;
import org.neo4j.kernel.impl.store.NodeLabelsField;
import org.neo4j.kernel.impl.store.NodeStore;
import org.neo4j.kernel.impl.store.record.NodeRecord;
import org.neo4j.kernel.impl.util.monitoring.ProgressReporter;
import org.neo4j.unsafe.

impl
. batchimport . cache .NodeLabelsCache;/**
 * Calculates counts per label and puts data into {@link NodeLabelsCache} for use by {@link
 * RelationshipCountsProcessor}.
 */
public
    class NodeCountsProcessor implements RecordProcessor<
    NodeRecord > {privatefinal NodeStorenodeStore
    ; private finallong
    [ ] labelCounts ;private
    ProgressReporter progressReporter ;privatefinal NodeLabelsCachecache
    ; private final CountsAccessor.

    Updatercounts ; privatefinal int anyLabel; NodeCountsProcessor (NodeStore
            nodeStore,NodeLabelsCache cache, int highLabelId ,
    CountsAccessor
        .Updatercounts , ProgressReporterprogressReporter
        ){this . nodeStore=
        nodeStore;this . cache=
        cache;this . anyLabel=
        highLabelId
        ;this. counts = counts;// Instantiate with high id + 1 since we need that extra slot for the ANY count this .labelCounts=
        newlong[ highLabelId +1
    ]

    ;this
    . progressReporter =progressReporter ; } @
    Override
        publicbooleanprocess ( NodeRecord node){long [] labels =NodeLabelsField
        . get (node, nodeStore ) ;
        if
            ( labels . length > 0 )
            {
                for(longlabelId: labels){labelCounts
            [
            (int)labelId ]++;}cache. put (node
        .
        getId(),labels)
        ;}labelCounts[ anyLabel ]++

        ;
        progressReporter .progress
    (

    1)
    ; // No need to update the store, we're just reading things here returnfalse;
    }
        @ Override public void done () { for (inti= 0; i
        <
            labelCounts.length; i ++ ) { counts.incrementNodeCount ( i== anyLabel?StatementConstants. ANY_LABEL:
        i
    ,

    labelCounts[
    i ] );}
    }
    @
Override