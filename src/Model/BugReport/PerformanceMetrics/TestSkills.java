package Model.BugReport.PerformanceMetrics;

import Model.BugReport.BugReportService;
import Model.BugReport.Test;
import Model.User.Developer;
import Model.User.User;

import java.util.List;

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

        metricsComponent.addInformation(new InformationHolderDouble("The average number of lines of code for each submitted test",
                getAverageLinesOfTestCodeByUser(user)));
        metricsComponent.addInformation(new InformationHolderInt("The total number of tests submitted",
                getBugReportService().getAllTestsSubmittedByDeveloper(user).size()));

        return metricsComponent;
    }


    private double getAverageLinesOfTestCodeByUser(User user) {
        List<Test> tests = getBugReportService().getAllTestsSubmittedByDeveloper(user);
        int linesOfCode = 0;

        for (Test test : tests) {
            linesOfCode += test.getLines();
        }

        if (linesOfCode != 0) return ((double) linesOfCode) / tests.size();
        return 0.0;
    }



}
