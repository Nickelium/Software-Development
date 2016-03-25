package Model.BugReport;

import Model.User.Developer;

/**
 * Created by Tom on 25/03/16.
 */
public class AssignedBugReportState implements BugReportState {
    @Override
    public void addAssignee(BugReport bugReport, Developer developer) {
        bugReport.assignees.add(developer);
    }
}
