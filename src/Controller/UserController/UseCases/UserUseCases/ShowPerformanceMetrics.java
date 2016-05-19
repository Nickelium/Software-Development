package Controller.UserController.UseCases.UserUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.BugReport.PerformanceMetrics.MetricsComponent;
import Model.BugReport.PerformanceMetrics.PerformanceMetricsService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

/**
 * Class extending the use case class, representing a show-performance-metrics use case.
 */
public class ShowPerformanceMetrics extends UseCase {

    private PerformanceMetricsService performanceMetricsService;

    public ShowPerformanceMetrics(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, PerformanceMetricsService performanceMetricsService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        setPerformanceMetricsService(performanceMetricsService);
        changeSystem = false;
    }

    /**
     * Lets a user of any type view the performance metrics of a developer.
     *
     * 2. The system shows a list of all developers.
     * 3. The user selects a developer.
     * 4. The system shows the details of the developer together with the
     *    performance metrics discussed in 3.3.6.
     *
     * @throws ReportErrorToUserException is thrown in case that the method encounters invalid input.
     * @throws IndexOutOfBoundsException is thrown when a user puts an incorrect option index.
     */
    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {

        // Step 2
        getUi().display("Please select the a developer: ");
        List<User> developerList = getUserService().getDevelopers();
        String parsedDevelopersList = Formatter.formatUserList(developerList);
        getUi().display(parsedDevelopersList);

        // Step 3
        int developerIndex = getUi().readInt();
        User developer = developerList.get(developerIndex);

        // Step 4
        List<MetricsComponent> performanceMetrics = getPerformanceMetricsService().createPerformanceMetricsForUser(developer);
        getUi().display(Formatter.formatPerformanceMetrics(performanceMetrics));
    }

    //region Getters & Setters

    public PerformanceMetricsService getPerformanceMetricsService() {
        return performanceMetricsService;
    }

    private void setPerformanceMetricsService(PerformanceMetricsService performanceMetricsService) {
        this.performanceMetricsService = performanceMetricsService;
    }

    //endregion

    @Override
    public String toString() {
        return "Show Performance Metrics";
    }
}
