package Model.Milestone;

import CustomExceptions.ReportErrorToUserException;

/**
 * Created by Laurens on 27/03/2016.
 */
public class TargetMilestone extends Milestone {

    private String targetMilestoneID;

    public TargetMilestone(String targetMilestoneID) throws ReportErrorToUserException {
        setMilestoneID(targetMilestoneID);
    }

    @Override
    public boolean isTargetMilestone(){
        return true;
    }

}
