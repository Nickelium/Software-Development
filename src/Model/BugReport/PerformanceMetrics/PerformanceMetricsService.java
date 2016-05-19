package Model.BugReport.PerformanceMetrics;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karina on 06.05.2016.
 */
public class PerformanceMetricsService {

    private BugReportService bugReportService;
    private Reporting reporting;
    private Leadership leadership;
    private TestSkills testSkills;
    private ProblemSolving problemSolving;

    public PerformanceMetricsService(BugReportService bugReportService, ProjectService projectService) {
        setBugReportService(bugReportService);
        this.reporting = new Reporting(bugReportService);
        this.leadership = new Leadership(bugReportService, projectService);
        this.testSkills = new TestSkills(bugReportService);
        this.problemSolving = new ProblemSolving(bugReportService);
    }

    //region setters & getters

    public BugReportService getBugReportService() {
        return bugReportService;
    }

    private void setBugReportService(BugReportService bugReportService) {
        this.bugReportService = bugReportService;
    }

    //endregion

    public List<MetricsComponent> createPerformanceMetricsForUser(User user) throws IllegalArgumentException, ReportErrorToUserException {
        List<MetricsComponent> metrics = new ArrayList<>();

        metrics.add(reporting.construct(user));
        metrics.add(leadership.construct(user));
        metrics.add(testSkills.construct(user));
        metrics.add(problemSolving.construct(user));

        return metrics;
    }

}
