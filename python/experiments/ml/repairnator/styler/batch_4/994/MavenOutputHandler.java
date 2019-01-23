package fr.inria.spirals.repairnator.process.maven.output;

import fr.inria.spirals.repairnator.process.inspectors.ProjectInspector;
import fr.inria.spirals.repairnator.process.maven.MavenHelper;
import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by urli on 15/02/2017.
 */
public abstract class MavenOutputHandler implements InvocationOutputHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MavenHelper mavenHelper;
    protected ProjectInspector inspector;
    protected String name;
    private FileWriter fileWriter;

    public MavenOutputHandler(MavenHelper mavenHelper) {
        this.mavenHelper =
            mavenHelper;this . inspector=
            mavenHelper.getInspector ( );this.name=
            mavenHelper.getName ( );this.initFileWriter(
            );}protectedLoggergetLogger
        (

        ) { returnthis. logger
            ; }privatevoidinitFileWriter
        (

        ) { Stringfilename= "repairnator.maven."
            + name . toLowerCase ( )+".log";String filePath =inspector
            . getRepoLocalPath ( )+"/"+filename ; inspector . getJobStatus(

            ).addFileToPush(filename);try{this.
            fileWriter =
                newFileWriter( filePath ) ;}catch(IOException
            e ) {this .getLogger (
                ).error("Cannot create file writer for file "+filePath+"." , e ) ;} }privatevoid
            writeToFile
        (

        String s ){if (this .
            fileWriter !=null){ try {this .
                fileWriter .
                    write(s);this.fileWriter.
                    flush();}catch(IOException
                e ) {this .getLogger (
                    ).error("Error while writing to repairnator.maven log.",e);} }}@
                Override
            public
        void

        consumeLine(
        String s ){this .mavenHelper .
            updateLastOutputDate();this.writeToFile(
            s+"\n"); } }