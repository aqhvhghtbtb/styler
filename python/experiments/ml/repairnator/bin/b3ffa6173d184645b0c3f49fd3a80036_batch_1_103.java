package fr.inria.spirals.repairnator.process.inspectors;

import fr.inria.jtravis.entities.Build;
import fr.inria.spirals.repairnator.BuildToBeInspected;
import fr.inria.spirals.repairnator.Utils;
import fr.inria.spirals.repairnator.config.RepairnatorConfig;
import fr.inria.spirals.repairnator.notifier.ErrorNotifier;
import fr.inria.spirals.repairnator.notifier.PatchNotifier;
import fr.inria.spirals.repairnator.pipeline.RepairToolsManager;
import fr.inria.spirals.repairnator.process.inspectors.properties.Properties;
import fr.inria.spirals.repairnator.process.inspectors.properties.machineInfo.MachineInfo;
import fr.inria.spirals.repairnator.process.step.paths.ComputeClasspath;
import fr.inria.spirals.repairnator.process.step.paths.ComputeModules;
import fr.inria.spirals.repairnator.process.step.paths.ComputeSourceDir;
import fr.inria.spirals.repairnator.process.step.paths.ComputeTestDir;
import fr.
inria .spirals.repairnator.process.step.push.*;importfr.
inria .spirals.repairnator.process.step.repair.AbstractRepairStep
; importfr.inria.spirals.repairnator.notifier.AbstractNotifier;import
fr .inria.spirals.repairnator.process.git.GitHelper;import
fr .inria.spirals.repairnator.process.step.*;importfr.
inria .spirals.repairnator.process.step.checkoutrepository.CheckoutBuggyBuild;importfr.
inria .spirals.repairnator.process.step.checkoutrepository.CheckoutPatchedBuild;importfr.
inria .spirals.repairnator.process.step.checkoutrepository.CheckoutType;importfr.
inria .spirals.repairnator.process.step.gatherinfo.BuildShouldFail;importfr.
inria .spirals.repairnator.process.step.gatherinfo.BuildShouldPass;importfr.
inria .spirals.repairnator.process.step.gatherinfo.GatherTestInformation
; importfr.inria.spirals.repairnator.serializer.AbstractDataSerializer
; importfr.inria.spirals.repairnator
. states.ScannedBuildStatus;importorg.kohsuke
. github.GHRepository;importorg
. kohsuke.github.GitHub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

import
java . text .
    SimpleDateFormat ; import java . util.*;/**
 * This class initialize the pipelines by creating the steps:
 * it's the backbone of the pipeline.
 */publicclassProjectInspector{

    private final Loggerlogger
    = LoggerFactory .getLogger
    ( ProjectInspector .class
    ) ; privateGitHelper

    gitHelper ; privateBuildToBeInspected
    buildToBeInspected ; privateString
    repoLocalPath ;privateStringrepoToPushLocalPath ;private
    String workspace ;private
    String m2LocalPath;privateList <AbstractDataSerializer
    > serializers ;private

    JobStatus jobStatus ;private

    List <AbstractNotifier>notifiers ;private
    PatchNotifier patchNotifier ;private
    CheckoutType checkoutType ;private

    List <AbstractStep> steps; private AbstractStepfinalStep ;privatebooleanpipelineEnding ;public ProjectInspector(BuildToBeInspectedbuildToBeInspected ,String workspace
        ,List< AbstractDataSerializer >serializers

        ,List< AbstractNotifier >notifiers
        ){this . buildToBeInspected = buildToBeInspected;this . workspace=workspace ; this.repoLocalPath = workspace+File.separator+getRepoSlug()+
        File.separator + buildToBeInspected.getBuggyBuild(
        ).getId ( ) ;this.repoToPushLocalPath= repoLocalPath +"_topush"; this .m2LocalPath=newFile(this
        .repoLocalPath+ File .separator
        +".m2") . getAbsolutePath ();this
        .serializers= serializers ; this.gitHelper=new
        GitHelper() ; this.
        jobStatus=new JobStatus (repoLocalPath);
        this.notifiers = notifiers ;this.checkoutType=CheckoutType
        .NO_CHECKOUT;this.steps
    =

    new ArrayList <>( )
        ; this
            . initProperties ( );}protectedvoidinitProperties()

            { try { Propertiesproperties=this.jobStatus
            . getProperties ( );Buildbuild=this
            . getBuggyBuild ( );longid=build.getId() ;Stringurl=Utils.getTravisUrl
            ( build . getId(),this.
            getRepoSlug());Datedate=build.getFinishedAt();fr.inria . spirals . repairnator.process.inspectors.properties.builds.BuildbuggyBuild=newfr.inria.spirals. repairnator. process.inspectors
            .properties.builds.Build(id,url,

            date ) ;properties.getBuilds()
            . setBuggyBuild( buggyBuild ); build
                = this .getPatchedBuild();if
                ( build !=null){id=build.getId( );url=Utils.getTravisUrl
                ( build .getId(),this
                .getRepoSlug());date=build.getFinishedAt();fr.inria . spirals . repairnator.process.inspectors.properties.builds.BuildpatchedBuild=newfr.inria.spirals. repairnator. process.inspectors
                .properties.builds.Build(id,url,
            date

            ) ; properties .getBuilds().setFixerBuild(patchedBuild);
            }MachineInfomachineInfo=properties.getReproductionBuggyBuild().getMachineInfo

            ();machineInfo.setHostName(Utils.getHostname());fr.inria . spirals .repairnator.process.inspectors
            .properties.repository.Repositoryrepository=properties.getRepository
            ();repository.setName(this.getRepoSlug());repository.

            setUrl (Utils.getSimpleGithubRepoUrl(this.getRepoSlug()) )
                ;if(this.getBuggyBuild(
                ).isPullRequest()){repository.setIsPullRequest(true);repository
            .

            setPullRequestId (this
            . getBuggyBuild
                ( ) .getPullRequestNumber());}GitHubgitHub;
                try { gitHub =RepairnatorConfig.getInstance().getGithub();
                GHRepositoryrepo=gitHub.getRepository(this.getRepoSlug(
                ) );repository.setGithubId(repo .
                    getId());if(
                    repo.isFork()){repository.setIsFork(true);repository.getOriginal()
                    .setName(repo.getParent().getFullName());repository.getOriginal()
                    .setGithubId(repo.getParent().getId());repository.getOriginal().setUrl(Utils.
                getSimpleGithubRepoUrl
            ( repo .getParent () .
                getFullName()));} } catch(IOExceptione) { this.logger
                .warn("It was not possible to retrieve information to check if "+this.getRepoSlug()+" is a fork.")
            ;

            this .logger.debug(e.toString()) ;
                } switch(
                    this.getBuildToBeInspected().getStatus
                    ()

                ) {case
                    ONLY_FAIL:properties.setType("only_fail"
                    );

                break ;case
                    FAILING_AND_PASSING:properties.setType("failing_passing"
                    );
            break
        ; case PASSING_AND_PASSING_WITH_TEST_CHANGES: properties. setType
            ("passing_passing");break;}} catch(Exception
        e
    )

    { this .logger. error
        ( "Error while initializing metrics.",
    e

    ) ; }}public JobStatus
        getJobStatus (){return
    jobStatus

    ; }publicGitHelpergetGitHelper (){ return
        this .gitHelper
    ;


    } public List<AbstractDataSerializer >
        getSerializers ()
    {

    return serializers ;}public String
        getWorkspace ()
    {

    return workspace ;}public String
        getM2LocalPath (){return
    m2LocalPath

    ; } publicBuildToBeInspectedgetBuildToBeInspected (
        ) {returnthis.buildToBeInspected;}public
    Build

    getPatchedBuild ( ){return this
        . buildToBeInspected.getPatchedBuild();}public
    Build

    getBuggyBuild ( ){return this
        . buildToBeInspected.getBuggyBuild();}publicStringgetRepoSlug(){returnthis.
    buildToBeInspected

    . getBuggyBuild (). getRepository
        ( ).
    getSlug

    ( ) ;}public String
        getRepoLocalPath ()
    {

    return repoLocalPath ;}public String
        getRepoToPushLocalPath ( ) { returnrepoToPushLocalPath;}public
        String getRemoteBranchName ( ){SimpleDateFormatdateFormat=newSimpleDateFormat("YYYYMMdd-HHmmss");StringformattedDate=dateFormat
        . format(this.getBuggyBuild().getFinishedAt( )) ; return this .getRepoSlug().replace('/', '-' ) + '-'+
    this

    . getBuggyBuild (). getId
        ( )+'-'+formattedDate;}public void run(){ if
            ( this . buildToBeInspected .getStatus()!=
            ScannedBuildStatus
                    .PASSING_AND_PASSING_WITH_TEST_CHANGES){ AbstractStepcloneRepo=new CloneRepository(this
                    );cloneRepo. addNextStep(newCheckoutBuggyBuild(
                    this,true) ).addNextStep(new
                    BuildProject(this) ).addNextStep( newTestProject ( this)). addNextStep(new
                    GatherTestInformation(this, true,newBuildShouldFail(
                    ),false) ).addNextStep( newInitRepoToPush(
                    this)). addNextStep(newComputeClasspath (this ,false)
                    ).addNextStep( newComputeSourceDir(this ,false,false

            ) ). addNextStep ( newComputeTestDir(this,false));for (
                String repairToolName : RepairnatorConfig.getInstance().getRepairTools
                ( )) { AbstractRepairSteprepairStep =
                    RepairToolsManager.getStepFromName(repairToolName);
                    if(repairStep!=null){
                repairStep . setProjectInspector
                    (this);cloneRepo . addNextStep(repairStep
                )
            ;

            }else{logger. error("Error while getting repair step class for following name: "+ repairToolName);}}
                    cloneRepo.addNextStep( newCommitPatch(this ,CommitType.
                    COMMIT_REPAIR_INFO)). addNextStep(newCheckoutPatchedBuild(
                    this,true) ).addNextStep(new
                    BuildProject(this) ).addNextStep( newTestProject ( this)). addNextStep(new
                    GatherTestInformation(this, true,newBuildShouldPass (),true))

            .addNextStep( new CommitPatch (this,CommitType .COMMIT_HUMAN_PATCH )); this


            .finalStep=new
                    ComputeSourceDir(this ,false,true );// this step is used to compute code metrics on the projectthis
                    .finalStep. addNextStep(newComputeModules(this
                    ,false) ).addNextStep(newWritePropertyFile
                    (this) ).addNextStep(newCommitProcessEnd

            (this)).addNextStep(newPushProcessEnd
            (this));cloneRepo.setDataSerializer(

            this.serializers);cloneRepo

            . setNotifiers
                (this.notifiers);
            this . printPipeline( ); try
                {cloneRepo.execute();} catch(Exceptione){this
                .jobStatus.addStepError("Unknown",e .getMessage(
                ));this.logger.error(

                "Exception catch while executing steps: " , e );this.jobStatus.
                setFatalError (e ) ;ErrorNotifier errorNotifier
                    =ErrorNotifier.getInstance();
                if

                ( errorNotifier!= null ) {errorNotifier.observe (
                    this);}for(AbstractDataSerializer
                serializer
            :
        this . serializers
            ){serializer.serializeData(this ) ;}}}else{this.logger . debug("Build "
        +
    this

    . getBuggyBuild (). getId
        ( )+
    " is not a failing build."

    ) ; }}public CheckoutTypegetCheckoutType (
        ){return checkoutType ;}
    public

    void setCheckoutType(CheckoutTypecheckoutType ){this .
        checkoutType =checkoutType
    ;

    } public List<AbstractNotifier >
        getNotifiers ()
    {

    return notifiers ;}public PatchNotifiergetPatchNotifier (
        ){return patchNotifier ;}
    public

    void setPatchNotifier (PatchNotifierpatchNotifier )
        { this.
    patchNotifier

    = patchNotifier ;}public AbstractStepgetFinalStep (
        ){return finalStep ;}
    public

    void setFinalStep (AbstractStepfinalStep )
        { this.
    finalStep

    = finalStep ;}public booleanisPipelineEnding (
        ){return pipelineEnding ;}
    public

    void setPipelineEnding (booleanpipelineEnding ){ this
        .pipelineEnding=pipelineEnding;}publicvoidregisterStep(AbstractStepstep){ this.steps
    .

    add (this.steps .size( )
        , step)
    ;

    } public List<AbstractStep >
        getSteps(){returnsteps;}public
        voidprintPipeline(){this.logger.
        info("----------------------------------------------------------------------");this.logger.
        info ("PIPELINE STEPS" ) ; this. logger . info("----------------------------------------------------------------------");for(int i=0 ;
            i<this.steps.size();i++){this.logger.info(
        this
    .

    steps . get(i )
        .getName());}}public
        voidprintPipelineEnd(){this.logger.
        info("----------------------------------------------------------------------");this.logger.
        info ( "PIPELINE EXECUTION SUMMARY" );
        this .logger . info ("----------------------------------------------------------------------" ) ; inthigherDuration=0;for(int i=0 ;
            i < this .steps.size();i++
            ) { AbstractStep step=this.steps.
            get (i ) ;int stepDuration
                = step .getDuration
            (
        )
        ; if( stepDuration > higherDuration) { higherDuration =stepDuration;}}for(int i=0 ;
            i < this .steps.size();i++
            ) { AbstractStep step=this.steps.
            get ( i );StringstepName=step . getName( ) ;StringstepStatus=(step.getStepStatus()!=null) ? step.
            getStepStatus ( ) .getStatus().name():"NOT RUN";

            String stepDuration = String .valueOf(step
            . getDuration());StringBuilderstepDurationFormatted= new StringBuilder();if(!stepStatus .
                equals("SKIPPED")&&!stepStatus
                . equals( "NOT RUN" ) ){ stepDurationFormatted . append(" [ ");for(intj=0 ; j<(String.valueOf( higherDuration). length
                    ()-stepDuration.length(
                )
                );j++) { stepDurationFormatted.append
            ( " " )
                ; }stepDurationFormatted . append (stepDuration + " s ]" );}else{for(intj=0 ; j<( String.valueOf (
                    higherDuration).length()+
                7
            )

            ; j ++ ){stepDurationFormatted.append ( " ");}} int stringSize=stepName.length(
            ) + stepStatus . length ()
            + stepDurationFormatted . length ();intnbDot
            = 70- stringSize ; StringBuilderstepNameFormatted = new StringBuilder( stepName); for
                (intj=0;j
            <
            nbDot;j++){stepNameFormatted . append ( ".");
        }
        this . logger .info(stepNameFormatted+stepStatus+stepDurationFormatted);}
        String finding =AbstractDataSerializer.getPrettyPrintState(this). toUpperCase ( ) ;finding
        =(finding.equals("UNKNOWN"))
        ?"-":finding;this.logger.info(
        "----------------------------------------------------------------------");this.logger.info(
    "PIPELINE FINDING: "

+