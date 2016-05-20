package Model.BugReport.PerformanceMetrics;

import Model.BugReport.BugReportService;
import Model.HealthIndicator.HealthIndicatorA1;
import Model.HealthIndicator.HealthIndicatorA2;
import Model.HealthIndicator.HealthIndicatorA3;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.User.Developer;
import Model.User.User;

/**
 * Class extending the performance metrics class, representing a leadership metric.
 *
 * A developer's leadership skill is measured by the health indicator
 * of every project in which the developer is lead.
 */
public class Leadership extends PerformanceMetrics {

    private ProjectService projectService;

    /**
     * Package visible constructor to create a new Leadership object.
     *
     * @param bugReportService the bug report service needed to gather
     *                         information about the health indicators.
     * @param projectService the projectService to get the projects
     */
    Leadership(BugReportService bugReportService, ProjectService projectService) {
        super(bugReportService);
        setProjectService(projectService);
    }

    /**
     * Method returning a metrics component, containing all required information for the
     * leadership metric. Method looks up the information, and adds the multi value information
     * holders to the information array.
     *
     * @param user the developer of who the performance metrics have to be looked up.
     * @return a metrics component containing all needed information (health indicators)
     * @throws IllegalArgumentException is thrown if the user in the argument is not a developer.
     */
    @Override
    MetricsComponent construct(User user) throws IllegalArgumentException {
        if (!(user instanceof Developer))
            throw new IllegalArgumentException("This user doesn't have performance metrics.");

        MetricsComponent metricsComponent = new MetricsComponent("Leadership");

        for (Project project : getProjectService().getProjectsOfLeadRole((Developer) user)) {
            InformationHolderMultiValue holder = new InformationHolderMultiValue(project.getName());
            holder.addValue(new InformationHolderString("Health Indicator Algorithm 1", new HealthIndicatorA1().get(project).toString()));
            holder.addValue(new InformationHolderString("Health Indicator Algorithm 2", new HealthIndicatorA2().get(project).toString()));
            holder.addValue(new InformationHolderString("Health Indicator Algorithm 3", new HealthIndicatorA3().get(project).toString()));

            metricsComponent.addInformation(holder);
        }

        return metricsComponent;
    }

    private ProjectService getProjectService() {
        return projectService;
    }

    private void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

}
