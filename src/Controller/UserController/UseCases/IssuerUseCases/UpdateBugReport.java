package Controller.UserController.UseCases.IssuerUseCases;

import Controller.IUI;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.Tag;
import Model.BugReport.TagAssignmentService;
import Model.BugReport.TagTypes.*;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Karina on 24.03.2016.
 */
public class UpdateBugReport extends IssuerUseCase {

    public UpdateBugReport(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, TagAssignmentService tagAssignmentService, User currentUser) {
        super(ui, userService, projectService, bugReportService, tagAssignmentService,currentUser);
        changeSystem = true;
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

        // Step 3 + 4 + 5
        Tag tag = this.getTagSuggestion(bugReport);
        getTagAssignmentService().assignTag(getCurrentUser(), bugReport, tag);


        getUi().display("The tag has successfully been changed.");
    }

    private Tag getTagSuggestion(BugReport bugReport) throws ReportErrorToUserException {
        //Step 4
        getUi().display("Please specify the new tag for the bug report:");
        String tag = getUi().readString();

        //Step 5
        switch (tag) {
            case "Assigned":
                return requestAssignedTagInfo(bugReport);
            case "Closed":
                return requestCloseTagInfo(bugReport);
            case "Duplicate":
                return requestDuplicateTagInfo(bugReport);
            case "NotABug":
                return requestNotABugTagInfo(bugReport);
            case "Resolved":
                return requestResolvedTagInfo(bugReport);
            case "UnderReview":
                return requestUnderReviewTagInfo(bugReport);
            default:
                throw new ReportErrorToUserException("The tag you entered does not exist.");
        }
    }

    private Assigned requestAssignedTagInfo(BugReport bugReport) throws ReportErrorToUserException {
        if (!getTagAssignmentService().canAssignTag(getCurrentUser(), bugReport, Assigned.class))
            throw new ReportErrorToUserException("Cannot preform tag change! No valid permission.");
        return new Assigned();
    }

    private Closed requestCloseTagInfo(BugReport bugReport) throws ReportErrorToUserException {
        if (!getTagAssignmentService().canAssignTag(getCurrentUser(), bugReport, Closed.class))
            throw new ReportErrorToUserException("Cannot preform tag change! No valid permission.");
        getUi().display("Please enter a score (1 - 5) for the given solution:");
        int score = getUi().readInt();
        return new Closed(score);
    }

    private Duplicate requestDuplicateTagInfo(BugReport bugReport) throws ReportErrorToUserException {
        if (!getTagAssignmentService().canAssignTag(getCurrentUser(), bugReport, Duplicate.class))
            throw new ReportErrorToUserException("Cannot preform tag change! No valid permission.");
        getUi().display("You have to specify the duplicate bugreport.");
        BugReport duplicateBugRep = selectBugReport();
        return new Duplicate(duplicateBugRep);
    }

    private NotABug requestNotABugTagInfo(BugReport bugReport) throws ReportErrorToUserException {
        if (!getTagAssignmentService().canAssignTag(getCurrentUser(), bugReport, NotABug.class))
            throw new ReportErrorToUserException("Cannot preform tag change! No valid permission.");
        return new NotABug();
    }

    private Resolved requestResolvedTagInfo(BugReport bugReport) throws ReportErrorToUserException {
        if (!getTagAssignmentService().canAssignTag(getCurrentUser(), bugReport, Resolved.class))
            throw new ReportErrorToUserException("Cannot preform tag change! No valid permission.");
        getUi().display("Please select a Patch that satisfies you:");
        this.showPatches(bugReport);
        int index = getUi().readInt();
        return new Resolved(index);
    }

    private UnderReview requestUnderReviewTagInfo(BugReport bugReport) throws ReportErrorToUserException {
        if (!getTagAssignmentService().canAssignTag(getCurrentUser(), bugReport, UnderReview.class))
            throw new ReportErrorToUserException("Cannot preform tag change! No valid permission.");
        return new UnderReview();
    }

    private void showPatches(BugReport bugReport) {
        for (int i = 0; i < bugReport.getDependencies().size(); i++) {
            getUi().display(i + ": " + bugReport.getPatches().get(i));
        }
    }
    
    @Override
	public String toString()
	{
		return "Update Bugreport";
	}
}
