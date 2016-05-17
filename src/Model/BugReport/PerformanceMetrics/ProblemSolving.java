package Model.BugReport.PerformanceMetrics;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.BugReport.Patch;
import Model.BugReport.SearchMethod.SearchOnAssignedWithTag;
import Model.BugReport.TagTypes.Assigned;
import Model.BugReport.TagTypes.Closed;
import Model.BugReport.TagTypes.New;
import Model.BugReport.TagTypes.UnderReview;
import Model.User.Developer;
import Model.User.User;

import java.util.List;

/**
 * Class extending the performance metrics class, representing a problem solving metric.
 *
 * A developer's problem solving skill is measured by:
 *
 * The number of Closed bug reports the developer is assigned to
 * The number of unfinished (i.e. New, Assigned or Under Review) bug reports the developer is assigned to
 * The average lines of code for each submitted patch
 * The total number of patches submitted
 */
public class ProblemSolving extends PerformanceMetrics {

    /**
     * Package visible constructor to create a new ProblemSolving object.
     *
     * @param bugReportService the bug report service needed to gather
     *                         information about the tags and patches of bug reports.
     */
    ProblemSolving(BugReportService bugReportService) {
        super(bugReportService);
    }

    /**
     * Method returning a metrics component, containing all required information for the
     * problem solving metric. Method looks up the information, and adds the information holders
     * to the information array.
     *
     * @param user the developer of who the performance metrics have to be looked up.
     * @return a metrics component containing all needed information (information about bug reports)
     * @throws IllegalArgumentException is thrown if the user in the argument is not a developer.
     */
    @Override
    MetricsComponent construct(User user) throws IllegalArgumentException, ReportErrorToUserException {
        if (!(user instanceof Developer))
            throw new IllegalArgumentException("This user doesn't have performance metrics.");

        MetricsComponent metricsComponent = new MetricsComponent("Problem solving");

        metricsComponent.addInformation(new InformationHolderInt("The number of Closed bug reports the developer is assigned to",
                getBugReportService().search(new SearchOnAssignedWithTag(user, Closed.class), user).size()));
        int numberOfUnfinishedBugReports = getBugReportService().search(new SearchOnAssignedWithTag(user, New.class), user).size()
                + getBugReportService().search(new SearchOnAssignedWithTag(user, Assigned.class), user).size()
                + getBugReportService().search(new SearchOnAssignedWithTag(user, UnderReview.class), user).size();
        metricsComponent.addInformation(new InformationHolderInt("The number of unfinished bug reports the developer is assigned to",
                numberOfUnfinishedBugReports));
        metricsComponent.addInformation(new InformationHolderDouble("The average lines of code for each submitted patch",
                getAverageLinesOfPatchCodeByUser(user)));
        metricsComponent.addInformation(new InformationHolderInt("The total number of patches submitted",
                getBugReportService().getAllPatchesSubmittedByDeveloper(user).size()));

        return metricsComponent;
    }


    private double getAverageLinesOfPatchCodeByUser(User user) {
        List<Patch> patches = getBugReportService().getAllPatchesSubmittedByDeveloper(user);
        int linesOfCode = 0;

        for (Patch patch : patches) {
            linesOfCode += patch.getLines();
        }

        if (linesOfCode != 0) return ((double) linesOfCode) / patches.size();
        return 0.0;
    }

}
