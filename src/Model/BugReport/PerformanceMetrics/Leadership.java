package Model.BugReport.PerformanceMetrics;

import Model.BugReport.BugReportService;
import Model.User.User;

/**
 * Created by Karina on 06.05.2016.
 */
public class Leadership extends PerformanceMetrics {

    Leadership(BugReportService bugReportService) {
        super(bugReportService);
    }

    @Override
    MetricsComponent construct(User user) throws IllegalArgumentException {
        //TODO
        return new MetricsComponent("Leadership");
    }

}
