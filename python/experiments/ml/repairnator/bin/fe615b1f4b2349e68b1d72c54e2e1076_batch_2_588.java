package fr.inria.spirals.repairnator.process.maven.output;

import fr.inria.spirals.repairnator.process.inspectors.ProjectInspector;
import fr.inria.spirals.repairnator.process.maven.MavenHelper;
import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.slf4j.Logger;
import org.

slf4j
. LoggerFactory ; import java . io

    . FileWriter ; import java .io.IOException;/**
 * Created by urli on 15/02/2017.
 */publicabstractclassMavenOutputHandlerimplements

    InvocationOutputHandler { privatefinal
    Logger logger =LoggerFactory
    . getLogger (this
    . getClass ()

    ) ;privateMavenHelper mavenHelper; protected
        ProjectInspectorinspector; protected Stringname
        ;privateFileWriter fileWriter ;publicMavenOutputHandler(MavenHelpermavenHelper
        ){this . mavenHelper=mavenHelper;this.
        inspector=mavenHelper.getInspector(
    )

    ; this .name= mavenHelper
        . getName();
    this

    . initFileWriter (); }
        protected Logger getLogger ( ) {returnthis.logger ; }private
        void initFileWriter ( ){Stringfilename= "repairnator.maven." + name .toLowerCase

        ()+".log";StringfilePath=inspector.getRepoLocalPath
        ( )
            +"/"+ filename ; inspector.getJobStatus()
        . addFileToPush (filename ); try
            {this.fileWriter=newFileWriter(filePath ) ; } catch( IOExceptione)
        {
    this

    . getLogger (). error( "Cannot create file writer for file "
        + filePath+".", e ); }
            } private
                voidwriteToFile(Strings){if(
                this.fileWriter!=null){try
            { this .fileWriter .write (
                s);this.fileWriter.flush() ;}catch
            (
        IOException
    e

    ){
    this . getLogger() .error (
        "Error while writing to repairnator.maven log.",e);}}}
        @OverridepublicvoidconsumeLine ( Strings)
    {
this