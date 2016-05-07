package Model.BugReport.PerformanceMetrics;

import Model.BugReport.BugReportService;
import Model.BugReport.TagTypes.Duplicate;
import Model.BugReport.TagTypes.NotABug;
import Model.User.Developer;
import Model.User.User;

/**
 * Created by Karina on 06.05.2016.
 */
public class Reporting extends PerformanceMetrics {

    Reporting(BugReportService bugReportService) {
        super(bugReportService);
    }

    @Override
    MetricsComponent construct(User user) throws IllegalArgumentException {
        if (!(user instanceof Developer))
            throw new IllegalArgumentException("This user doesn't have a performance metrics.");

        MetricsComponent metricsComponent = new MetricsComponent("Reporting");

        metricsComponent.addInformation("The number of Duplicate bug reports submitted by the developer", (double) getBugReportService().getAllBugReportsWithTagCreatedByUser(user, Duplicate.class).size());
        metricsComponent.addInformation("The number of NotABug bug reports submitted by the developer", (double) getBugReportService().getAllBugReportsWithTagCreatedByUser(user, NotABug.class).size());
        metricsComponent.addInformation("The total number of bug reports submitted by the developer", (double) getBugReportService().getAllBugReportsCreatedByUser(user).size());

        return metricsComponent;
    }

}
