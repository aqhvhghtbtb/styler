package fr.inria.spirals.repairnator.process.inspectors;

import fr.inria.jtravis.entities.Build;
import fr.inria.spirals.repairnator.BuildToBeInspected;
import fr.inria.spirals.repairnator.Utils;
import fr.inria.spirals.repairnator.config.RepairnatorConfig;
import fr.inria.spirals.repairnator.notifier.ErrorNotifier;
import fr.inria.spirals.repairnator.notifier.PatchNotifier;
import fr.inria.spirals.repairnator.pipeline.RepairToolsManager;
import fr.inria.spirals.repairnator.process.
inspectors.properties.Properties;importfr.
inria .spirals.repairnator.process.inspectors.properties.machineInfo.MachineInfo;importfr.
inria .spirals.repairnator.process.step.paths.ComputeClasspath;importfr.
inria .spirals.repairnator.process.step.paths.ComputeModules;importfr.
inria .spirals.repairnator.process.step.paths.ComputeSourceDir;importfr.
inria .spirals.repairnator.process.step.paths.ComputeTestDir;importfr.
inria .spirals.repairnator.process.step.push.*;importfr.
inria .spirals.repairnator.process.step.repair.AbstractRepairStep;importfr.
inria .spirals.repairnator.notifier.AbstractNotifier;importfr.
inria .spirals.repairnator.process.git.GitHelper;importfr.
inria .spirals.repairnator.process.step.*;importfr.
inria .spirals.repairnator.process.step.checkoutrepository.CheckoutBuggyBuild;importfr.
inria .spirals.repairnator.process.step.checkoutrepository.CheckoutPatchedBuild;importfr.
inria .spirals.repairnator.process.step.checkoutrepository.CheckoutType;importfr.
inria .spirals.repairnator.process.step.gatherinfo.BuildShouldFail;importfr.
inria .spirals.repairnator.process.step.gatherinfo.BuildShouldPass;importfr.
inria .spirals.repairnator.process.step.gatherinfo.GatherTestInformation;importfr.
inria .spirals.repairnator.serializer.AbstractDataSerializer;importfr.
inria .spirals.repairnator.states.ScannedBuildStatus;importorg.
kohsuke .github.GHRepository;importorg.
kohsuke .github.GitHub;importorg.
slf4j .Logger;importorg.
slf4j .LoggerFactory;importjava.

io .File;importjava.
io .IOException;importjava.
text .SimpleDateFormat;importjava.
util .*;/**
 * This class initialize the pipelines by creating the steps:
 * it's the backbone of the pipeline.
 */publicclass

ProjectInspector
{ private final Logger
    logger = LoggerFactory . getLogger (ProjectInspector.class);privateGitHelpergitHelper

    ; private BuildToBeInspectedbuildToBeInspected
    ; private StringrepoLocalPath
    ; private StringrepoToPushLocalPath
    ; private Stringworkspace

    ; private Stringm2LocalPath
    ; private List<
    AbstractDataSerializer >serializers;private JobStatusjobStatus
    ; private List<
    AbstractNotifier >notifiers;private PatchNotifierpatchNotifier
    ; private CheckoutTypecheckoutType

    ; private List<

    AbstractStep >steps;private AbstractStepfinalStep
    ; private booleanpipelineEnding
    ; public ProjectInspector(

    BuildToBeInspected buildToBeInspected,String workspace, List <AbstractDataSerializer >serializers,List <AbstractNotifier >notifiers){ this. buildToBeInspected
        =buildToBeInspected; this .workspace

        =workspace; this .repoLocalPath
        =workspace+ File . separator +getRepoSlug( ) +File. separator +buildToBeInspected. getBuggyBuild ().getId();this.repoToPushLocalPath
        =repoLocalPath+ "_topush" ;this.m2LocalPath
        =newFile ( this .repoLocalPath+File. separator +".m2") . getAbsolutePath();this.serializers
        =serializers; this .gitHelper
        =newGitHelper ( ) ;this.jobStatus
        =newJobStatus ( repoLocalPath );this.notifiers
        =notifiers; this .checkoutType
        =CheckoutType. NO_CHECKOUT ;this.steps
        =newArrayList < > ();this.initProperties
        ();}protectedvoid
    initProperties

    ( ) {try{ Properties
        properties =
            this . jobStatus .getProperties();Buildbuild=

            this . getBuggyBuild ();longid=
            build . getId ();Stringurl=
            Utils . getTravisUrl (build.getId(),this.getRepoSlug ());Datedate=
            build . getFinishedAt ();fr.inria
            .spirals.repairnator.process.inspectors.properties.builds.BuildbuggyBuild=new fr . inria .spirals.repairnator.process.inspectors.properties.builds.Build(id,url,date ); properties.getBuilds
            ().setBuggyBuild(buggyBuild);build=this

            . getPatchedBuild ();if(build
            != null) { id= build
                . getId ();url=Utils
                . getTravisUrl (build.getId(),this.getRepoSlug ());date=build
                . getFinishedAt ();fr.inria
                .spirals.repairnator.process.inspectors.properties.builds.BuildpatchedBuild=new fr . inria .spirals.repairnator.process.inspectors.properties.builds.Build(id,url,date ); properties.getBuilds
                ().setFixerBuild(patchedBuild);}MachineInfomachineInfo
            =

            properties . getReproductionBuggyBuild ().getMachineInfo();machineInfo.setHostName
            (Utils.getHostname());fr.inria

            .spirals.repairnator.process.inspectors.properties.repository.Repositoryrepository=properties . getRepository ();repository.setName
            (this.getRepoSlug());repository.setUrl
            (Utils.getSimpleGithubRepoUrl(this.getRepoSlug()));if(this

            . getBuggyBuild().isPullRequest()){repository. setIsPullRequest
                (true);repository.setPullRequestId
                (this.getBuggyBuild().getPullRequestNumber());}GitHubgitHub
            ;

            try {gitHub
            = RepairnatorConfig
                . getInstance ().getGithub();GHRepositoryrepo=
                gitHub . getRepository (this.getRepoSlug());repository.setGithubId
                (repo.getId());if(repo
                . isFork()){repository. setIsFork
                    (true);repository.getOriginal
                    ().setName(repo.getParent().getFullName());repository.getOriginal
                    ().setGithubId(repo.getParent().getId());repository.getOriginal
                    ().setUrl(Utils.getSimpleGithubRepoUrl(repo.getParent().getFullName()));}}catch
                (
            IOException e ){ this. logger
                .warn("It was not possible to retrieve information to check if "+this. getRepoSlug ()+" is a fork.") ; this.logger
                .debug(e.toString());}switch(
            this

            . getBuildToBeInspected().getStatus()){caseONLY_FAIL :
                properties .setType
                    ("only_fail");break;case
                    FAILING_AND_PASSING:

                properties .setType
                    ("failing_passing");break;case
                    PASSING_AND_PASSING_WITH_TEST_CHANGES:

                properties .setType
                    ("passing_passing");break;}
                    }catch
            (
        Exception e ){ this. logger
            .error("Error while initializing metrics.",e); }}public
        JobStatus
    getJobStatus

    ( ) {returnjobStatus ;
        } publicGitHelper
    getGitHelper

    ( ) {returnthis .
        gitHelper ;}publicList
    <

    AbstractDataSerializer >getSerializers() {returnserializers ;
        } publicString
    getWorkspace


    ( ) {returnworkspace ;
        } publicString
    getM2LocalPath

    ( ) {returnm2LocalPath ;
        } publicBuildToBeInspected
    getBuildToBeInspected

    ( ) {returnthis .
        buildToBeInspected ;}publicBuild
    getPatchedBuild

    ( ) {returnthis .
        buildToBeInspected .getPatchedBuild();}publicBuild
    getBuggyBuild

    ( ) {returnthis .
        buildToBeInspected .getBuggyBuild();}publicString
    getRepoSlug

    ( ) {returnthis .
        buildToBeInspected .getBuggyBuild().getRepository().getSlug();}publicString
    getRepoLocalPath

    ( ) {returnrepoLocalPath ;
        } publicString
    getRepoToPushLocalPath

    ( ) {returnrepoToPushLocalPath ;
        } publicString
    getRemoteBranchName

    ( ) {SimpleDateFormatdateFormat =
        new SimpleDateFormat ( "YYYYMMdd-HHmmss" );StringformattedDate=
        dateFormat . format (this.getBuggyBuild().getFinishedAt());returnthis.
        getRepoSlug ().replace('/','-')+ '-'+ this . getBuggyBuild ().getId()+'-'+ formattedDate ; } publicvoid
    run

    ( ) {if( this
        . buildToBeInspected.getStatus()!=ScannedBuildStatus. PASSING_AND_PASSING_WITH_TEST_CHANGES ){AbstractStepcloneRepo =
            new CloneRepository ( this );cloneRepo.addNextStep
            (
                    newCheckoutBuggyBuild(this ,true)) .addNextStep(
                    newBuildProject(this )).addNextStep(
                    newTestProject(this )).addNextStep(
                    newGatherTestInformation(this ,true,new BuildShouldFail( ) ,false)) .addNextStep(
                    newInitRepoToPush(this )).addNextStep(
                    newComputeClasspath(this ,false)) .addNextStep(
                    newComputeSourceDir(this ,false,false )) .addNextStep(
                    newComputeTestDir(this ,false)) ;for(String

            repairToolName :RepairnatorConfig . getInstance ().getRepairTools()){AbstractRepairSteprepairStep =
                RepairToolsManager . getStepFromName (repairToolName);if(repairStep
                != null) { repairStep. setProjectInspector
                    (this);cloneRepo.addNextStep
                    (repairStep);}else{
                logger . error
                    ("Error while getting repair step class for following name: "+repairToolName) ; }}cloneRepo
                .
            addNextStep

            (newCommitPatch(this ,CommitType.COMMIT_REPAIR_INFO )).addNextStep(
                    newCheckoutPatchedBuild(this ,true)) .addNextStep(
                    newBuildProject(this )).addNextStep(
                    newTestProject(this )).addNextStep(
                    newGatherTestInformation(this ,true,new BuildShouldPass( ) ,true)) .addNextStep(
                    newCommitPatch(this ,CommitType.COMMIT_HUMAN_PATCH ));this.finalStep

            =newComputeSourceDir ( this ,false,true ); // this step is used to compute code metrics on the projectthis. finalStep


            .addNextStep(new
                    ComputeModules(this ,false)) .addNextStep(new
                    WritePropertyFile(this )).addNextStep(new
                    CommitProcessEnd(this )).addNextStep(new
                    PushProcessEnd(this ));cloneRepo.setDataSerializer

            (this.serializers);cloneRepo.setNotifiers
            (this.notifiers);this.printPipeline

            ();try{cloneRepo

            . execute
                ();}catch(
            Exception e ){ this. jobStatus
                .addStepError("Unknown",e.getMessage ());this.logger
                .error("Exception catch while executing steps: ",e); this.jobStatus
                .setFatalError(e);ErrorNotifiererrorNotifier=

                ErrorNotifier . getInstance ();if(errorNotifier
                != null) { errorNotifier. observe
                    (this);}for(
                AbstractDataSerializer

                serializer :this . serializers ){serializer. serializeData
                    (this);}}}
                else
            {
        this . logger
            .debug("Build "+this. getBuggyBuild ().getId()+" is not a failing build.") ; }}public
        CheckoutType
    getCheckoutType

    ( ) {returncheckoutType ;
        } publicvoid
    setCheckoutType

    ( CheckoutType checkoutType){ this. checkoutType
        =checkoutType; } publicList
    <

    AbstractNotifier >getNotifiers() {returnnotifiers ;
        } publicPatchNotifier
    getPatchNotifier

    ( ) {returnpatchNotifier ;
        } publicvoid
    setPatchNotifier

    ( PatchNotifier patchNotifier){ this. patchNotifier
        =patchNotifier; } publicAbstractStep
    getFinalStep

    ( ) {returnfinalStep ;
        } publicvoid
    setFinalStep

    ( AbstractStep finalStep){ this. finalStep
        =finalStep; } publicboolean
    isPipelineEnding

    ( ) {returnpipelineEnding ;
        } publicvoid
    setPipelineEnding

    ( boolean pipelineEnding){ this. pipelineEnding
        =pipelineEnding; } publicvoid
    registerStep

    ( AbstractStep step){ this. steps
        .add(this.steps.size(),step); }publicList
    <

    AbstractStep >getSteps() {returnsteps ;
        } publicvoid
    printPipeline

    ( ) {this. logger
        .info("----------------------------------------------------------------------");this.logger
        .info("PIPELINE STEPS");this.logger
        .info("----------------------------------------------------------------------");for(int
        i =0 ; i <this . steps .size();i++) {this. logger
            .info(this.steps.get(i).getName());}}public
        void
    printPipelineEnd

    ( ) {this. logger
        .info("----------------------------------------------------------------------");this.logger
        .info("PIPELINE EXECUTION SUMMARY");this.logger
        .info("----------------------------------------------------------------------");inthigherDuration=
        0 ; for (int
        i =0 ; i <this . steps .size();i++) {AbstractStepstep =
            this . steps .get(i);intstepDuration=
            step . getDuration ();if(stepDuration
            > higherDuration) { higherDuration= stepDuration
                ; } }for
            (
        int
        i =0 ; i <this . steps .size();i++) {AbstractStepstep =
            this . steps .get(i);StringstepName=
            step . getName ();StringstepStatus=
            ( step . getStepStatus()!=null) ? step. getStepStatus ().getStatus().name():"NOT RUN"; String stepDuration=
            String . valueOf (step.getDuration());StringBuilderstepDurationFormatted=

            new StringBuilder ( ) ;if(!
            stepStatus .equals("SKIPPED")&&!stepStatus . equals("NOT RUN")){stepDurationFormatted. append
                (" [ ");for(int
                j =0 ; j <( String . valueOf(higherDuration).length()-stepDuration. length ());j++) {stepDurationFormatted. append
                    (" ");}stepDurationFormatted.
                append
                (stepDuration+" s ]") ; }else{
            for ( int
                j =0 ; j <( String . valueOf(higherDuration).length()+7) ; j++) {stepDurationFormatted. append
                    (" ");}}int
                stringSize
            =

            stepName . length ()+stepStatus. length ()+stepDurationFormatted. length ();intnbDot=
            70 - stringSize ; StringBuilder stepNameFormatted=
            new StringBuilder ( stepName );for(int
            j =0 ; j <nbDot ; j ++) {stepNameFormatted. append
                (".");}this.
            logger
            .info(stepNameFormatted+stepStatus+ stepDurationFormatted ) ; }Stringfinding
        =
        AbstractDataSerializer . getPrettyPrintState (this).toUpperCase();finding=(
        finding . equals("UNKNOWN"))?"-": finding ; this .logger
        .info("----------------------------------------------------------------------");this.logger
        .info("PIPELINE FINDING: "+finding);this.logger
        .info("----------------------------------------------------------------------");}}