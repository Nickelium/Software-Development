package Model.BugReport;

import Model.Tags.TagTypes.Assigned;
import Model.User.Developer;

/**
 * Created by Tom on 25/03/16.
 */
public class NewBugReportState implements BugReportState {

    @Override
    public void addAssignee(BugReport bugReport, Developer developer) {
        bugReport.assignees.add(developer);
        bugReport.setTag(new Assigned());
        bugReport.changeState(new AssignedBugReportState());
    }
}
