package Model.BugReport.PerformanceMetrics;

import Model.BugReport.BugReportService;
import Model.BugReport.TagTypes.Assigned;
import Model.BugReport.TagTypes.Closed;
import Model.BugReport.TagTypes.New;
import Model.BugReport.TagTypes.UnderReview;
import Model.User.Developer;
import Model.User.User;

/**
 * Created by Karina on 06.05.2016.
 */
public class ProblemSolving extends PerformanceMetrics {

    ProblemSolving(BugReportService bugReportService) {
        super(bugReportService);
    }

    @Override
    MetricsComponent construct(User user) throws IllegalArgumentException {
        if (!(user instanceof Developer))
            throw new IllegalArgumentException("This user doesn't have performance metrics.");

        MetricsComponent metricsComponent = new MetricsComponent("Problem solving");

        metricsComponent.addInformation("The number of Closed bug reports the developer is assigned to", (double) getBugReportService().getAllBugReportsWithTagUserAssignedTo(user, Closed.class).size());
        int numberOfUnfinishedBugReports = getBugReportService().getAllBugReportsWithTagUserAssignedTo(user, New.class).size()
                + getBugReportService().getAllBugReportsWithTagUserAssignedTo(user, Assigned.class).size()
                + getBugReportService().getAllBugReportsWithTagUserAssignedTo(user, UnderReview.class).size();
        metricsComponent.addInformation("The number of unfinished bug reports the developer is assigned to", (double) numberOfUnfinishedBugReports);
        metricsComponent.addInformation("The average lines of code for each submitted patch", getBugReportService().getAverageLinesOfPatchCodeByUser(user));
        metricsComponent.addInformation("The total number of patches submitted", (double) getBugReportService().getAllPatchesSubmittedByDeveloper(user).size());

        return metricsComponent;
    }

}
