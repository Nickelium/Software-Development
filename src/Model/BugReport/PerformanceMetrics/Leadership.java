package Model.BugReport.PerformanceMetrics;

import Model.BugReport.BugReportService;
import Model.User.User;

/**
 * Class extending the performance metrics class, representing a leadership metric.
 *
 * A developer's leadership skill is measured by the health indicator
 * of every project in which the developer is lead.
 */
public class Leadership extends PerformanceMetrics {

    /**
     * Package visible constructor to create a new Leadership object.
     *
     * @param bugReportService the bug report service needed to gather
     *                         information about the health indicators.
     */
    Leadership(BugReportService bugReportService) {
        super(bugReportService);
    }

    /**
     * // TODO Implement
     * @param user the developer of who the performance metrics have to be looked up.
     * @return a metrics component containing all needed information (health indicators)
     * @throws IllegalArgumentException is thrown if the user in the argument is not a developer.
     */
    @Override
    MetricsComponent construct(User user) throws IllegalArgumentException {
        return new MetricsComponent("Leadership");
    }

}
