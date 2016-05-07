package Model.BugReport.PerformanceMetrics;

import Model.BugReport.BugReportService;
import Model.User.Developer;
import Model.User.User;

/**
 * Class extending the performance metrics class, representing a test skills metric.
 *
 * A developer's test skill is measured by:
 *
 * The average number of lines of code for each submitted tests
 * The total number of tests submitted
 */
public class TestSkills extends PerformanceMetrics {

    /**
     * Package visible constructor to create a new TestSkills object.
     *
     * @param bugReportService the bug report service needed to gather
     *                         information about the tests assigned to bug reports.
     */
    TestSkills(BugReportService bugReportService) {
        super(bugReportService);
    }

    /**
     * Method returning a metrics component, containing all required information for the
     * test skills metric. Method looks up the information, and adds the information holders
     * to the information array.
     *
     * @param user the developer of who the performance metrics have to be looked up.
     * @return a metrics component containing all needed information (information about tests)
     * @throws IllegalArgumentException is thrown if the user in the argument is not a developer.
     */
    @Override
    MetricsComponent construct(User user) throws IllegalArgumentException {
        if (!(user instanceof Developer))
            throw new IllegalArgumentException("This user doesn't have a performance metrics.");

        MetricsComponent metricsComponent = new MetricsComponent("Test skills");

        metricsComponent.addInformation("The average number of lines of code for each submitted test", getBugReportService().getAverageLinesOfTestCodeByUser(user));
        metricsComponent.addInformation("The total number of tests submitted", (double) getBugReportService().getAllTestsSubmittedByDeveloper(user).size());

        return metricsComponent;
    }


}
