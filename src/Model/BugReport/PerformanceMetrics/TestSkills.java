package Model.BugReport.PerformanceMetrics;

import CustomExceptions.ReportErrorToUserException;
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
    MetricsComponent construct(User user) throws ReportErrorToUserException {
        if (!(user instanceof Developer))
            throw new ReportErrorToUserException("This user doesn't have a performance matrics.");

        MetricsComponent metricsComponent = new MetricsComponent("Reporting");

        metricsComponent.addInformation("The average number of lines of code for each submitted tests", getBugReportService().getAverageLinesOfTestCodeByUser(user));
        metricsComponent.addInformation("The total number of tests submitted", (double) getBugReportService().getAllTestsSubmittedByDeveloper(user).size());

        return metricsComponent;
    }


}
