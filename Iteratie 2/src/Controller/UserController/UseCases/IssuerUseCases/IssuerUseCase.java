package Controller.UserController.UseCases.IssuerUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.SearchMethod.SearchOnAssigned;
import Model.BugReport.SearchMethod.SearchOnDescription;
import Model.BugReport.SearchMethod.SearchOnFiled;
import Model.BugReport.SearchMethod.SearchOnTitle;
import Model.BugReport.TagAssignmentService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karina on 24.03.2016.
 */
public abstract class IssuerUseCase extends UseCase {

    private TagAssignmentService tagAssignmentService;

    public IssuerUseCase(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, TagAssignmentService tagAssignmentService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        setTagAssignmentService(tagAssignmentService);
    }

    //region getters & setters

    public TagAssignmentService getTagAssignmentService() {
        return tagAssignmentService;
    }

    public void setTagAssignmentService(TagAssignmentService tagAssignmentService) {
        this.tagAssignmentService = tagAssignmentService;
    }

    //endregion

    /**
     *
     * Lets an Issuer or Developer select a bug report. Bug reports are listed after
     * the execution of a search command.
     *
     * 1. The system shows a list of possible searching modes:
     *     Search for bug reports with a specic string in the title or description
     *     Search for bug reports led by some specic user
     *     Search for bug reports assigned to specic user
     *
     * 2. The issuer selects a searching mode and provides the required search
     *    parameters.
     * 3. The system shows an ordered list of bug reports that matched the
     *    search query.
     * 4. The issuer selects a bug report from the ordered list.
     *
     * @return BugReport the bug report that has been selected by the user.
     * @throws ReportErrorToUserException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     *
     */
    protected BugReport selectBugReport() throws ReportErrorToUserException, IndexOutOfBoundsException {
        int chosenNumber;
        List<BugReport> bugReportList = null;

        // Step 1
        getUi().display("Select the preferred search method: ");

        String searchMethods = "";
        searchMethods += "0 :Search for bug reports with a specific string in the title or description\n";
        searchMethods += "1 :Search for bug reports filed by some specific user\n";
        searchMethods += "2 :Search for bug reports assigned to specific user";

        getUi().display(searchMethods);

        // Step 2
        int methodIndex = getUi().readInt();

        if (methodIndex == 0)
        {
            // Search for bug reports with a specific string in the title or description
            getUi().display("Please enter a search string matching the title or description of the desired bug report.");
            String query = getUi().readString();

            // Make List with possible bug reports
            List<BugReport> list1 = getBugReportService().search(new SearchOnTitle(query), this.getCurrentUser());
            List<BugReport> list2 = getBugReportService().search(new SearchOnDescription(query), this.getCurrentUser());

            // Combine both lists
            bugReportList = new ArrayList<>(list1);
            for (BugReport b : list2) {
                bugReportList.add(b);
            }

            if (bugReportList.size() > 0) {
                // Step 3
                getUi().display("The search result for your query is: ");
                getUi().display(Formatter.formatBugReportList(bugReportList));

            } else {
                throw new ReportErrorToUserException("No bug reports found.");
            }


        }
        else if (methodIndex == 1)
        {

            // Search for bug reports filed by some specific user
            getUi().display("Please enter the username of the user that filed the desired bug report: ");
            String userName = getUi().readString();

            User user = getUserService().getUser(userName);
            bugReportList = getBugReportService().search(new SearchOnFiled(user), getCurrentUser());
            if (bugReportList.size() > 0) {
                // Step 3
                getUi().display("The search result for your query is: ");
                getUi().display(Formatter.formatBugReportList(bugReportList));
            } else {
                throw new ReportErrorToUserException("No bug reports found.");

            }
        } else if (methodIndex == 2) {
            // Search for bug reports assigned to specific user
            getUi().display("Please enter the username of the user that the bug reports are assigned to: ");
            String userName = getUi().readString();

            User user = getUserService().getUser(userName);
            bugReportList = getBugReportService().search(new SearchOnAssigned(user), getCurrentUser());

            if (bugReportList.size() > 0) {
                // Step 3
                getUi().display("The search result for your query is: ");
                getUi().display(Formatter.formatBugReportList(bugReportList));

            } else {
                throw new ReportErrorToUserException("No bug reports found.");
            }
        } else {
            throw new ReportErrorToUserException("Enter a valid number.");
        }

        // Step 4
        getUi().display("Please enter the number of the bug report that you would like to select: ");
        chosenNumber = getUi().readInt();
        return bugReportList.get(chosenNumber);

    }

    public abstract void run() throws ReportErrorToUserException,IndexOutOfBoundsException;
}
