package Model.BugReport;

import Model.User.Developer;

/**
 * Created by Tom on 25/03/16.
 */
public interface BugReportState {

    void addAssignee(BugReport bugReport, Developer developer);

}
