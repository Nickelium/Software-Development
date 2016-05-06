package Model.BugReport.PerformanceMetrics;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.User.User;

/**
 * Created by Karina on 06.05.2016.
 */
public abstract class PerformanceMetrics {

    private BugReportService bugReportService;

    PerformanceMetrics(BugReportService bugReportService) {
        setBugReportService(bugReportService);
    }

    abstract MetricsComponent construct(User user) throws ReportErrorToUserException;

    //region setters & getters

    public BugReportService getBugReportService() {
        return bugReportService;
    }

    private void setBugReportService(BugReportService bugReportService) {
        this.bugReportService = bugReportService;
    }

    //endregion

}
