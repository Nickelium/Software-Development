package Model.BugReport.PerformanceMetrics;

import Model.BugReport.BugReportService;
import Model.BugReport.TagTypes.Duplicate;
import Model.BugReport.TagTypes.NotABug;
import Model.User.Developer;
import Model.User.User;

/**
 * Class extending the performance metrics class, representing a reporting metric.
 *
 * A developer's reporting skill is measured by:
 *
 * The number of Duplicate bug reports submitted by the developer
 * The number of NotABug bug reports submitted by the developer
 * The total number of bug reports submitted by the developer
 */
public class Reporting extends PerformanceMetrics {

    /**
     * Package visible constructor to create a new Reporting object.
     *
     * @param bugReportService the bug report service needed to gather information about
     *                         the tags assigned to bug reports submitted by the developer.
     */
    Reporting(BugReportService bugReportService) {
        super(bugReportService);
    }

    /**
     * Method returning a metrics component, containing all required information for the
     * reporting metric. Method looks up the information, and adds the information holders
     * to the information array.
     *
     * @param user the developer of who the performance metrics have to be looked up.
     * @return a metrics component containing all needed information (information about tags and bug reports)
     * @throws IllegalArgumentException is thrown if the user in the argument is not a developer.
     */
    @Override
    MetricsComponent construct(User user) throws IllegalArgumentException {
        if (!(user instanceof Developer))
            throw new IllegalArgumentException("This user doesn't have a performance metrics.");

        MetricsComponent metricsComponent = new MetricsComponent("Reporting");

        metricsComponent.addInformation(new InformationHolderInt("The number of Duplicate bug reports submitted by the developer",
                getBugReportService().getAllBugReportsWithTagCreatedByUser(user, Duplicate.class).size()));
        metricsComponent.addInformation(new InformationHolderInt("The number of NotABug bug reports submitted by the developer",
                getBugReportService().getAllBugReportsWithTagCreatedByUser(user, NotABug.class).size()));
        metricsComponent.addInformation(new InformationHolderInt("The total number of bug reports submitted by the developer",
                getBugReportService().getAllBugReportsCreatedByUser(user).size()));

        return metricsComponent;
    }

}
