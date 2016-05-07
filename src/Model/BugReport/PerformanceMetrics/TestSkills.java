package Model.BugReport.PerformanceMetrics;

import Model.BugReport.BugReportService;
import Model.User.Developer;
import Model.User.User;

/**
 * Created by Karina on 06.05.2016.
 */
public class TestSkills extends PerformanceMetrics {

    TestSkills(BugReportService bugReportService) {
        super(bugReportService);
    }

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
