package fr.inria.spirals.repairnator.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.inria.spirals.repairnator.config.RepairnatorConfig;
import fr.inria.spirals.repairnator.process.
inspectors .ProjectInspector;importfr.inria.spirals.repairnator.process.inspectors.
properties .Properties;importfr.inria.spirals.repairnator.process.inspectors.
properties .PropertiesSerializerAdapter;importfr.inria.spirals.repairnator.serializer.
engines .SerializedData;importfr.inria.spirals.repairnator.serializer.

engines .SerializerEngine;importjava.
util .ArrayList;importjava.
util .Date;importjava.
util .List;importjava.

util
. Map ; /**
 * Created by urli on 28/04/2017.
 */ public class

    PropertiesSerializer extendsAbstractDataSerializer{publicPropertiesSerializer( List< SerializerEngine
        >engines){ super(engines,SerializerType
    .

    PROPERTIES)
    ; } @Overridepublic voidserializeData (
        ProjectInspector inspector ) { Gsongson=newGsonBuilder().registerTypeAdapter( Properties .class,newPropertiesSerializerAdapter()).
        create ( ) ;JsonObjectelement=(JsonObject)gson.toJsonTree(inspector.getJobStatus().getProperties

        ());element. addProperty("runId",RepairnatorConfig.getInstance().getRunId
        ( ) ) ;DatereproductionDateBeginning=inspector.getJobStatus().getProperties().getReproductionBuggyBuild().
        getReproductionDateBeginning ( ) ; reproductionDateBeginning = reproductionDateBeginning ==null? new Date(
        ):reproductionDateBeginning;this. addDate( element,"reproductionDate"
        ,reproductionDateBeginning);element. addProperty("buildStatus",inspector.getBuildToBeInspected().getStatus().name
        ());element. addProperty("buggyBuildId",inspector.getBuggyBuild().getId
        ( ));if(inspector . getPatchedBuild( )
            !=null){element. addProperty("patchedBuildId",inspector.getPatchedBuild().getId
        (
        ));}element.addProperty("status",this.getPrettyPrintState(

        inspector ) ) ; JsonObjectelementFreeMemory=new
        JsonObject(); Map< String , Long>freeMemoryByStep=inspector.getJobStatus().
        getFreeMemoryByStep ();for(Map. Entry< String , Long>stepFreeMemory:freeMemoryByStep. entrySet
            ()){elementFreeMemory.addProperty(stepFreeMemory. getKey(),stepFreeMemory.getValue
        (
        ));}element. add("freeMemoryByStep"

        ,elementFreeMemory); List < SerializedData >dataList=newArrayList<

        >();dataList .add( newSerializedData(newArrayList< >(),

        element )) ; for (SerializerEngineengine:this. getEngines
            ()){engine. serialize(dataList,this.getType
        (
    )
)