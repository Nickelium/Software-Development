package Model.BugReport.PerformanceMetrics;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.User.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karina on 06.05.2016.
 */
public class PerformanceMetricsService {

    private BugReportService bugReportService;

    public PerformanceMetricsService(BugReportService bugReportService) {
        setBugReportService(bugReportService);
    }

    //region setters & getters

    public BugReportService getBugReportService() {
        return bugReportService;
    }

    private void setBugReportService(BugReportService bugReportService) {
        this.bugReportService = bugReportService;
    }

    //endregion

    public List<MetricsComponent> createPerformanceMetricsForUser(User user) throws ReportErrorToUserException {
        List<MetricsComponent> metrics = new ArrayList<>();

        metrics.add((new Reporting(bugReportService).construct(user)));
        metrics.add((new Leadership(bugReportService).construct(user)));
        metrics.add((new TestSkills(bugReportService).construct(user)));
        metrics.add((new ProblemSolving(bugReportService).construct(user)));

        return metrics;
    }

}
