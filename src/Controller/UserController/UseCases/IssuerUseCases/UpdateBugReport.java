package Controller.UserController.UseCases.IssuerUseCases;

import Controller.IUI;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.Tag;
import Model.BugReport.TagAssignmentService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Karina on 24.03.2016.
 */
public class UpdateBugReport extends IssuerUseCase {

    public UpdateBugReport(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, TagAssignmentService tagAssignmentService, User currentUser) {
        super(ui, userService, projectService, bugReportService, tagAssignmentService,currentUser);
    }

    /**
     *
     * Lets a Developer update the tag of a bug report.
     *
     * 2. Include use case Select Bug Report.
     * 3. The developer suggests a new tag for the bug report.
     * 4. The system gives the selected bug report the new tag.
     *
     * @throws Exception
     *          if something goes wrong during execution, give user the
     *          chance of retrying.
     *
     */
    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {
        // Step 2
        getUi().display("Please select the bug report that you want to update: ");
        BugReport bugReport = selectBugReport();

        // Step 3
        getUi().display("Please specify the new tag for the bug report: ");
        String input = getUi().readString();
        Class<?> tag;
        try {
            tag = Class.forName("Model.BugReport.TagTypes." + input);
            if (input == "-1") return;
        } catch (ClassNotFoundException e) {
            throw new ReportErrorToUserException("The given tag does not exist!");
        }

        // Step 4
        try {
            Tag newTag = (Tag) tag.newInstance();
            getTagAssignmentService().assignTag(getCurrentUser(), bugReport, newTag);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ReportErrorToUserException("The tag you have given does not exist");
        }

        getUi().display("The tag has successfully been changed.");
    }
}
