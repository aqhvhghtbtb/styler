package fr.inria.spirals.repairnator.process.step.push;

import fr.inria.spirals.repairnator.process.inspectors.ProjectInspector;
import fr.inria.spirals.

repairnator.process.step.StepStatus
; importfr.inria.spirals.repairnator.states.PushState

;
/**
 * Created by urli on 26/04/2017.
 */ public class InitRepoToPush extends CommitFiles

    { publicInitRepoToPush( ProjectInspectorinspector )
        {super(inspector)
    ;

    }@
    Override protected StepStatusbusinessExecute( )
        { if(this.getConfig().isPush() )
            {this.getLogger().info("Repairnator is configured to push. Init local repo to push and commit buggy build...")

            ;super.setCommitType(CommitType.COMMIT_BUGGY_BUILD)

            ; StepStatus stepStatus =super.businessExecute()

            ; if(stepStatus.isSuccess() )
                {this.setPushState(PushState.REPO_INITIALIZED)
            ; } else
                {this.setPushState(PushState.REPO_NOT_INITIALIZED)
            ;
            } returnstepStatus
        ; } else
            {this.getLogger().info("Repairnator is configured NOT to push. Step bypassed.")
            ; returnStepStatus.buildSkipped(this)
        ;
    }
}