/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.apache.kylin.cube.inmemcubing2;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Queue;
importjava.util.concurrent.BlockingQueue
; importjava.util.concurrent.ForkJoinPool
; importjava.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory
; importjava.util.concurrent.ForkJoinTask
; importjava.util.concurrent.ForkJoinWorkerThread
; importjava.util.concurrent.LinkedBlockingQueue
; importjava.util.concurrent.atomic.AtomicInteger

; importorg.apache.kylin.common.util.Dictionary
; importorg.apache.kylin.common.util.ImmutableBitSet
; importorg.apache.kylin.common.util.MemoryBudgetController
; importorg.apache.kylin.common.util.MemoryBudgetController.MemoryWaterLevel
; importorg.apache.kylin.common.util.Pair
; importorg.apache.kylin.cube.cuboid.Cuboid
; importorg.apache.kylin.cube.cuboid.CuboidScheduler
; importorg.apache.kylin.cube.gridtable.CubeGridTable
; importorg.apache.kylin.cube.inmemcubing.AbstractInMemCubeBuilder
; importorg.apache.kylin.cube.inmemcubing.ConcurrentDiskStore
; importorg.apache.kylin.cube.inmemcubing.CuboidResult
; importorg.apache.kylin.cube.inmemcubing.ICuboidWriter
; importorg.apache.kylin.cube.inmemcubing.InMemCubeBuilderUtils
; importorg.apache.kylin.cube.inmemcubing.InputConverter
; importorg.apache.kylin.cube.inmemcubing.InputConverterUnit
; importorg.apache.kylin.cube.inmemcubing.RecordConsumeBlockingQueueController
; importorg.apache.kylin.cube.kv.CubeDimEncMap
; importorg.apache.kylin.gridtable.GTAggregateScanner
; importorg.apache.kylin.gridtable.GTBuilder
; importorg.apache.kylin.gridtable.GTInfo
; importorg.apache.kylin.gridtable.GTRecord
; importorg.apache.kylin.gridtable.GTScanRequest
; importorg.apache.kylin.gridtable.GTScanRequestBuilder
; importorg.apache.kylin.gridtable.GridTable
; importorg.apache.kylin.gridtable.IGTScanner
; importorg.apache.kylin.gridtable.IGTStore
; importorg.apache.kylin.metadata.model.IJoinedFlatTableDesc
; importorg.apache.kylin.metadata.model.MeasureDesc
; importorg.apache.kylin.metadata.model.TblColRef
; importorg.slf4j.Logger
; importorg.slf4j.LoggerFactory

; importcom.google.common.base.Stopwatch
; importcom.google.common.collect.Lists

;
/**
 * Build a cube (many cuboids) in memory. Calculating multiple cuboids at the same time as long as memory permits.
 * Assumes base cuboid fits in memory or otherwise OOM exception will occur.
 */ public class InMemCubeBuilder2 extends AbstractInMemCubeBuilder
    { private static Logger logger =LoggerFactory.getLogger(InMemCubeBuilder2.class)

    ;
    // by experience private static final double DERIVE_AGGR_CACHE_CONSTANT_FACTOR =0.1
    ; private static final double DERIVE_AGGR_CACHE_VARIABLE_FACTOR =0.9

    ; protected finalString[ ]metricsAggrFuncs
    ; protected finalMeasureDesc[ ]measureDescs
    ; protected final intmeasureCount

    ; private MemoryBudgetControllermemBudget
    ; protected final longbaseCuboidId
    ; private CuboidResultbaseResult

    ; privateQueue<CuboidTask >completedTaskQueue
    ; private AtomicIntegertaskCuboidCompleted

    ; private ICuboidCollectorWithCallBackresultCollector

    ; publicInMemCubeBuilder2( CuboidSchedulercuboidScheduler , IJoinedFlatTableDescflatDesc
            ,Map<TblColRef ,Dictionary<String> >dictionaryMap )
        {super(cuboidScheduler ,flatDesc ,dictionaryMap)
        ;this. measureCount =cubeDesc.getMeasures().size()
        ;this. measureDescs =cubeDesc.getMeasures().toArray( newMeasureDesc[measureCount])
        ;List<String > metricsAggrFuncsList =Lists.newArrayList()

        ; for( int i =0 ; i <measureCount ;i++ )
            { MeasureDesc measureDesc =measureDescs[i]
            ;metricsAggrFuncsList.add(measureDesc.getFunction().getExpression())
        ;
        }this. metricsAggrFuncs =metricsAggrFuncsList.toArray( newString[metricsAggrFuncsList.size()])
        ;this. baseCuboidId =Cuboid.getBaseCuboidId(cubeDesc)
    ;

    } public intgetBaseResultCacheMB( )
        { returnbaseResult.aggrCacheMB
    ;

    } private GridTablenewGridTableByCuboidID( longcuboidID ) throws IOException
        { GTInfo info =CubeGridTable.newGTInfo(Cuboid.findForMandatory(cubeDesc ,cuboidID)
                , newCubeDimEncMap(cubeDesc ,dictionaryMap))

        ;
        // Below several store implementation are very similar in performance. The ConcurrentDiskStore is the simplest.
        // MemDiskStore store = new MemDiskStore(info, memBudget == null ? MemoryBudgetController.ZERO_BUDGET : memBudget);
        // MemDiskStore store = new MemDiskStore(info, MemoryBudgetController.ZERO_BUDGET); IGTStore store = newConcurrentDiskStore(info)

        ; GridTable gridTable = newGridTable(info ,store)
        ; returngridTable
    ;

    }@
    Override public<T > voidbuild(BlockingQueue<T >input ,InputConverterUnit<T >inputConverterUnit , ICuboidWriteroutput
            ) throws IOException
        {NavigableMap<Long ,CuboidResult > result =buildAndCollect
                (RecordConsumeBlockingQueueController.getQueueController(inputConverterUnit ,input) ,null)
        ; try
            { for( CuboidResult cuboidResult :result.values() )
                {outputCuboid(cuboidResult.cuboidId ,cuboidResult.table ,output)
                ;cuboidResult.table.close()
            ;
        } } finally
            {output.close()
        ;
    }

    }
    /**
     * Build all the cuboids and wait for all the tasks finished. 
     * 
     * @param input
     * @param listener
     * @return
     * @throws IOException
     */ private<T >NavigableMap<Long ,CuboidResult >buildAndCollect( finalRecordConsumeBlockingQueueController<T >input
            , final ICuboidResultListenerlistener ) throws IOException

        { long startTime =System.currentTimeMillis()
        ;logger.info( "In Mem Cube Build2 start, " +cubeDesc.getName())

        ;
        // build base cuboidbuildBaseCuboid(input ,listener)

        ; ForkJoinWorkerThreadFactory factory = newForkJoinWorkerThreadFactory( )
            {@
            Override public ForkJoinWorkerThreadnewThread( ForkJoinPoolpool )
                { final ForkJoinWorkerThread worker =ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool)
                ;worker.setName( "inmem-cubing-cuboid-worker-" +worker.getPoolIndex())
                ; returnworker
            ;
        }}
        ; ForkJoinPool builderPool = newForkJoinPool(taskThreadCount ,factory ,null ,true)
        ; ForkJoinTask rootTask =builderPool.submit( newRunnable( )
            {@
            Override public voidrun( )
                {startBuildFromBaseCuboid()
            ;
        }})
        ;rootTask.join()

        ; long endTime =System.currentTimeMillis()
        ;logger.info( "In Mem Cube Build2 end, " +cubeDesc.getName( ) + ", takes " +( endTime -startTime ) +" ms")
        ;logger.info( "total CuboidResult count:" +resultCollector.getAllResult().size())
        ; returnresultCollector.getAllResult()
    ;

    } public ICuboidCollectorWithCallBackgetResultCollector( )
        { returnresultCollector
    ;

    } public<T > CuboidResultbuildBaseCuboid(RecordConsumeBlockingQueueController<T >input
            , final ICuboidResultListenerlistener ) throws IOException
        { completedTaskQueue = newLinkedBlockingQueue<CuboidTask>()
        ; taskCuboidCompleted = newAtomicInteger(0)

        ; resultCollector = newDefaultCuboidCollectorWithCallBack(listener)

        ;MemoryBudgetController. MemoryWaterLevel baseCuboidMemTracker = newMemoryWaterLevel()
        ;baseCuboidMemTracker.markLow()
        ; baseResult =createBaseCuboid(input ,baseCuboidMemTracker)

        ; if(baseResult. nRows ==0 )
            {taskCuboidCompleted.set(cuboidScheduler.getCuboidCount())
            ; returnbaseResult
        ;

        }baseCuboidMemTracker.markLow()
        ;baseResult. aggrCacheMB =Math.max(baseCuboidMemTracker.getEstimateMB() ,10) ;

        // 10 MB at minimalmakeMemoryBudget()
        ; returnbaseResult
    ;

    } public CuboidResultbuildCuboid( CuboidTasktask ) throws IOException
        { CuboidResult newCuboid =buildCuboid(task.parent ,task.childCuboidId)
        ;completedTaskQueue.add(task)
        ;addChildTasks(newCuboid)
        ; returnnewCuboid
    ;

    } private CuboidResultbuildCuboid( CuboidResultparent , longcuboidId ) throws IOException
        { final String consumerName = "AggrCache@Cuboid " +cuboidId
        ;MemoryBudgetController. MemoryConsumer consumer = newMemoryBudgetController.MemoryConsumer( )
            {@
            Override public intfreeUp( intmb )
                { return0 ;
            // cannot free up on demand

            }@
            Override public StringtoString( )
                { returnconsumerName
            ;
        }}

        ;
        // reserve memory for aggregation cache, can't be larger than the parentmemBudget.reserveInsist(consumer ,parent.aggrCacheMB)
        ; try
            { returnaggregateCuboid(parent ,cuboidId)
        ; } finally
            {memBudget.reserve(consumer ,0)
        ;
    }

    } public booleanisAllCuboidDone( )
        { returntaskCuboidCompleted.get( ) ==cuboidScheduler.getCuboidCount()
    ;

    } public voidstartBuildFromBaseCuboid( )
        {addChildTasks(baseResult)
    ;

    } private voidaddChildTasks( CuboidResultparent )
        {List<Long > children =cuboidScheduler.getSpanningCuboid(parent.cuboidId)
        ; if( children != null &&!children.isEmpty() )
            {List<CuboidTask > childTasks =Lists.newArrayListWithExpectedSize(children.size())
            ; for( Long child :children )
                { CuboidTask task = newCuboidTask(parent ,child ,this)
                ;childTasks.add(task)
                ;task.fork()
            ;
            } for( CuboidTask childTask :childTasks )
                {childTask.join()
            ;
        }
    }

    } publicQueue<CuboidTask >getCompletedTaskQueue( )
        { returncompletedTaskQueue
    ;

    } private voidmakeMemoryBudget( )
        { int systemAvailMB =MemoryBudgetController.gcAndGetSystemAvailMB()
        ;logger.info( "System avail " + systemAvailMB +" MB")
        ; int reserve =reserveMemoryMB
        ;logger.info( "Reserve " + reserve +" MB for system basics")

        ; int budget = systemAvailMB -reserve
        ; if( budget <baseResult.aggrCacheMB )
            {
            // make sure we have base aggr cache as minimal budget =baseResult.aggrCacheMB
            ;logger.warn( "System avail memory (" + systemAvailMB +
                    " MB) is less than base aggr cache (" +baseResult. aggrCacheMB + " MB) + minimal reservation (" +
                    reserve +" MB), consider increase JVM heap -Xmx")
        ;

        }logger.info( "Memory Budget is " + budget +" MB")
        ; memBudget = newMemoryBudgetController(budget)
    ;

    } private<T > CuboidResultcreateBaseCuboid(RecordConsumeBlockingQueueController<T >input
            ,MemoryBudgetController. MemoryWaterLevelbaseCuboidMemTracker ) throws IOException
        {logger.info( "Calculating base cuboid " +baseCuboidId)

        ; Stopwatch sw = newStopwatch()
        ;sw.start()
        ; GridTable baseCuboid =newGridTableByCuboidID(baseCuboidId)
        ; GTBuilder baseBuilder =baseCuboid.rebuild()
        ; IGTScanner baseInput = newInputConverter<>(baseCuboid.getInfo() ,input)

        ;Pair<ImmutableBitSet ,ImmutableBitSet > dimensionMetricsBitSet =
                InMemCubeBuilderUtils.getDimensionAndMetricColumnBitSet(baseCuboidId ,measureCount)
        ; GTScanRequest req = newGTScanRequestBuilder().setInfo(baseCuboid.getInfo()).setRanges(null).setDimensions(null
                ).setAggrGroupBy(dimensionMetricsBitSet.getFirst()).setAggrMetrics(dimensionMetricsBitSet.getSecond()
                ).setAggrMetricsFuncs(metricsAggrFuncs).setFilterPushDown(null).createGTScanRequest()
        ; GTAggregateScanner aggregationScanner = newGTAggregateScanner(baseInput ,req)
        ;aggregationScanner.trackMemoryLevel(baseCuboidMemTracker)

        ; int count =0
        ; for( GTRecord r :aggregationScanner )
            { if( count ==0 )
                {baseCuboidMemTracker.markHigh()
            ;
            }baseBuilder.write(r)
            ;count++
        ;
        }aggregationScanner.close()
        ;baseBuilder.close()

        ;sw.stop()
        ;logger.info( "Cuboid " + baseCuboidId + " has " + count + " rows, build takes " +sw.elapsedMillis( ) +"ms")

        ; int mbEstimateBaseAggrCache =(int )(aggregationScanner.getEstimateSizeOfAggrCache(
                ) /MemoryBudgetController.ONE_MB)
        ;logger.info( "Wild estimate of base aggr cache is " + mbEstimateBaseAggrCache +" MB")

        ; returnupdateCuboidResult(baseCuboidId ,baseCuboid ,count ,sw.elapsedMillis() ,0
                ,input.inputConverterUnit.ifChange())
    ;

    } private CuboidResultupdateCuboidResult( longcuboidId , GridTabletable , intnRows , longtimeSpent
            , intaggrCacheMB )
        { returnupdateCuboidResult(cuboidId ,table ,nRows ,timeSpent ,aggrCacheMB ,true)
    ;

    } private CuboidResultupdateCuboidResult( longcuboidId , GridTabletable , intnRows , longtimeSpent , intaggrCacheMB
            , booleanifCollect )
        { if( aggrCacheMB <= 0 && baseResult !=null )
            { aggrCacheMB =(int )Math.round
                    (( DERIVE_AGGR_CACHE_CONSTANT_FACTOR + DERIVE_AGGR_CACHE_VARIABLE_FACTOR * nRows /baseResult.nRows )
                            // *baseResult.aggrCacheMB)
        ;

        } CuboidResult result = newCuboidResult(cuboidId ,table ,nRows ,timeSpent ,aggrCacheMB)
        ;taskCuboidCompleted.incrementAndGet()

        ; if(ifCollect )
            {resultCollector.collectAndNotify(result)
        ;
        } returnresult
    ;

    } protected CuboidResultaggregateCuboid( CuboidResultparent , longcuboidId ) throws IOException
        { finalPair<ImmutableBitSet ,ImmutableBitSet > allNeededColumns =
                InMemCubeBuilderUtils.getDimensionAndMetricColumnBitSet(parent.cuboidId ,cuboidId ,measureCount)
        ; returnscanAndAggregateGridTable(parent.table ,newGridTableByCuboidID(cuboidId) ,parent.cuboidId
                ,cuboidId ,allNeededColumns.getFirst() ,allNeededColumns.getSecond())
    ;

    } private GTAggregateScannerprepareGTAggregationScanner( GridTablegridTable , longparentId , longcuboidId
            , ImmutableBitSetaggregationColumns , ImmutableBitSetmeasureColumns ) throws IOException
        { GTInfo info =gridTable.getInfo()
        ; GTScanRequest req = newGTScanRequestBuilder().setInfo(info).setRanges(null).setDimensions(null
                ).setAggrGroupBy(aggregationColumns).setAggrMetrics(measureColumns).setAggrMetricsFuncs(metricsAggrFuncs
                ).setFilterPushDown(null).createGTScanRequest()
        ; GTAggregateScanner scanner =(GTAggregateScanner )gridTable.scan(req)

        ;
        // for child cuboid, some measures don't need aggregation. if( parentId !=cuboidId )
            {boolean[ ] aggrMask = newboolean[measureDescs.length]
            ; for( int i =0 ; i <measureDescs.length ;i++ )
                {aggrMask[i ] =!measureDescs[i].getFunction().getMeasureType().onlyAggrInBaseCuboid()

                ; if(!aggrMask[i] )
                    {logger.info(measureDescs[i].toString( ) +" doesn't need aggregation.")
                ;
            }
            }scanner.setAggrMask(aggrMask)
        ;

        } returnscanner
    ;

    } protected CuboidResultscanAndAggregateGridTable( GridTablegridTable , GridTablenewGridTable , longparentId
            , longcuboidId , ImmutableBitSetaggregationColumns , ImmutableBitSetmeasureColumns ) throws IOException
        { Stopwatch sw = newStopwatch()
        ;sw.start()
        ;logger.info( "Calculating cuboid " +cuboidId)

        ; GTAggregateScanner scanner =prepareGTAggregationScanner(gridTable ,parentId ,cuboidId ,aggregationColumns
                ,measureColumns)
        ; GTBuilder builder =newGridTable.rebuild()

        ; ImmutableBitSet allNeededColumns =aggregationColumns.or(measureColumns)

        ; GTRecord newRecord = newGTRecord(newGridTable.getInfo())
        ; int count =0
        ; try
            { for( GTRecord record :scanner )
                {count++
                ; for( int i =0 ; i <allNeededColumns.trueBitCount() ;i++ )
                    { int c =allNeededColumns.trueBitAt(i)
                    ;newRecord.set(i ,record.get(c))
                ;
                }builder.write(newRecord)
            ;
        } } finally
            {scanner.close()
            ;builder.close()
        ;
        }sw.stop()
        ;logger.info( "Cuboid " + cuboidId + " has " + count + " rows, build takes " +sw.elapsedMillis( ) +"ms")

        ; returnupdateCuboidResult(cuboidId ,newGridTable ,count ,sw.elapsedMillis() ,0)
    ;
}