package Model.BugReport;

import CustomExceptions.ReportErrorToUserException;
import Model.Milestone.SetMilestoneHelper;
import Model.Milestone.TargetMilestone;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Programmer;
import Model.Roles.Tester;
import Model.User.Developer;
import Model.User.Issuer;
import Model.User.User;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class containing services for bug reports.
 *
 * Contains methods to provide requested bug reports, visible by the requesting user.
 *
 * Contains methods to create bug reports.
 * Also contains methods to create comments and add them to bug reports or comments.
 */
public class BugReportService {

    //region Attributes

    private ProjectService projectService;

    //endregion

    //region Constructor

    /**
     * Constructor for the bugReport service.
     *
     * @param projectService The projectservice the BugReportService can use for its BugReports.
     *
     * @throws IllegalArgumentException The given projectservice is null.
     */
    public BugReportService(ProjectService projectService)
    {
        if (projectService == null) throw new IllegalArgumentException("The given projectService is null");
        this.projectService = projectService;
    }

    //endregion

    //region Creators

    /**
     * Function to create a new BugReport and add the bug report to the list of bugreports.
     *
     * @param title The title of the bug report
     * @param description The description of the bug report
     * @param creator The creator of the bug report
     * @param subSystem The subsystem of the bug report
     *
     * @return The newly created bug report
     *
     * @throws ReportErrorToUserException the title or description is empty.
     * @throws IllegalArgumentException the creator or subsystem is null.
     */
    public BugReport createBugReport(String title, String description, Issuer creator, SubSystem subSystem, boolean pblc) throws ReportErrorToUserException
    {
        BugReport bugReport = new BugReport(title, description, creator, pblc);
        if (subSystem == null) throw new IllegalArgumentException("Subsystem is null");
        subSystem.addBugReport(bugReport);

        return bugReport;
    }
    
    /**
     * Function to create a new BugReport and add the bug report to the list of bugreports.
     * Van de gebruiker van deze functie wordt verwacht dat de initialAssignies ook developers van het project zijn.
     * Anders zullen tag assignment rechten voor deze gebruiker geweigerd worden.
     *
     * @param title The title of the bug report
     * @param description The description of the bug report
     * @param creator The creator of the bug report
     * @param subSystem The subsystem of the bug report
     * @param creationDate The creation date of the bug report
     * @param initialAssignees The list of initialAssignies van de bug report
     *
     * @return The newly created bug report
     *
     * @throws ReportErrorToUserException the given title of description is empty.
     * @throws IllegalArgumentException The subsystem, creator, creationdata or tag is null.
     */
    public BugReport createBugReport(String title, String description, Issuer creator, SubSystem subSystem, boolean pblc, TheDate creationDate, List<Developer> initialAssignees) throws ReportErrorToUserException
    {
        BugReport bugReport = new BugReport(title, description, creator, pblc, creationDate, initialAssignees, null);
        if (subSystem == null) throw new IllegalArgumentException("Subsystem is null");
        subSystem.addBugReport(bugReport);

        return bugReport;
    }

    /**
     * Method for creating a comment and adding it to the list of comments of the bug report.
     *
     * @param text      The text of the comment.
     * @param issuer    The issuer writing the comment.
     * @param bugReport The bug report on which the issuer commented.
     * @return The newly created comment.
     * @throws ReportErrorToUserException One of the given arguments are illegal. See constructor of comment for rules.
     */
    public Comment createComment(String text, Issuer issuer, BugReport bugReport) throws ReportErrorToUserException {
        Comment comment = new Comment(text, issuer);
        bugReport.addComment(comment);
        return comment;
    }

    /**
     * Method for creating a comment and adding it to the list of comments of the given comment.
     *
     * @param text The text of the comment.
     * @param issuer The issuer writing the comment.
     * @param comment The comment on which the issuer commented.
     *
     * @return The newly created comment.
     *
     * @throws ReportErrorToUserException One of the given arguments are illegal. See constructor of comment for rules.
     */
    public Comment createComment(String text, Issuer issuer, Comment comment) throws ReportErrorToUserException {
        Comment newComment = new Comment(text, issuer);
        comment.addComment(newComment);
        return newComment;
    }

    /**
     * Method used to create and return a new Test object.
     * The newly created test is added to the test list of the specified bug report.
     *
     * @param text the text of the new test
     * @param user the user that wants to add the new test
     * @param bugReport the bug report that the test has to be added to
     * @return the new test object as created by this method.
     *
     * @throws ReportErrorToUserException is thrown if the user has not the rights to add a new test
     *         to the bug report.
     */
    public Test createTest(String text, User user, BugReport bugReport) throws ReportErrorToUserException {
        if (!canAddTest(user, bugReport)) throw new ReportErrorToUserException("You are not allowed to add a test");
        Test test = new Test(text);
        bugReport.addTest(test);
        return test;
    }

    /**
     * Method used to create and return a new Patch object.
     * The newly created patch is added to the patch list of the specified bug report.
     *
     * @param text the text of the new patch
     * @param user the user that wants to add the new patch
     * @param bugReport the bug report that the patch has to be added to
     * @return the new patch object as created by this method.
     *
     * @throws ReportErrorToUserException is thrown if the user has not the rights to add a new patch
     *         to the bug report.
     */
    public Patch createPatch(String text, User user, BugReport bugReport) throws ReportErrorToUserException {
        if (!canAddPatch(user, bugReport)) throw new ReportErrorToUserException("You are not allowed to add a patch");
        Patch patch = new Patch(text);
        bugReport.addPatch(patch);
        return patch;
    }

    //endregion

    //region Getters

    /**
     * Getter to request all the BugReports that are visible to the user.
     *
     * @return An unmodifiable list of all the BugReports visible to the user.
     */
    public List<BugReport> getAllBugReports(User user)
    {
        List<BugReport> bugReports = new ArrayList<>();
        for (Project project : getProjectService().getAllProjects()) {
            for (BugReport bugReport : project.getAllBugReports()) {
                if (this.isVisibleByUser(user, bugReport)) {
                    bugReports.add(bugReport);
                }
            }
        }
        return Collections.unmodifiableList(bugReports);
    }

    /**
     * Getter to get one specific BugReport.
     *
     * @param id The id of the BugReport to get.
     *
     * @return The BugReport matching the given id.
     *
     * @throws ReportErrorToUserException
     * 			thrown when no bug report is found.
     * 		    or bug report cannot be seen by user.
     */
    public BugReport getOneBugReport(BugReportID id, User user) throws ReportErrorToUserException
    {
        BugReport bugReport = getAllBugReportsWrapped().getOne(x -> x.getId().equals(id));
        if (!this.isVisibleByUser(user, bugReport))
            throw new ReportErrorToUserException("You are not allowed to see this bug report.");
        if (bugReport == null) throw new ReportErrorToUserException("There is no bug report with the given id.");
        return bugReport;
    }

    /**
     * Getter for retrieving all bugReports concerning the given project.
     *
     * @param project The project for which to find the bugReports.
     *
     * @return An unmodifyable list of all the bugreports about the given project.
     */
    public List<BugReport> getBugReportsForProject(Project project){
        return project.getAllBugReports();
    }


    private ProjectService getProjectService() {
        return projectService;
    }

    private IListWrapper<BugReport> getAllBugReportsWrapped() {
        List<BugReport> bugReports = new ArrayList<>();
        for (Project project : getProjectService().getAllProjects()) {
            bugReports.addAll(project.getAllBugReports());
        }
        return new ListWrapper<>(bugReports);
    }

    //endregion

    //region Setters

    /**
     * Method to set the Targetmilestone of the bugreport.
     *
     * @param bugReport The bugreport to set the targetmilestone of.
     * @param milestone The milestone to set to the bugreport.
     * @throws ReportErrorToUserException It is not valid to set the target milestone.
     */
    public void setTargetMilestone(BugReport bugReport, TargetMilestone milestone) throws ReportErrorToUserException {
        if (!canUpdateTargetMilestone(bugReport, milestone))
            throw new ReportErrorToUserException("The milestone is not greater than all the subsystems milestones.");
        bugReport.setTargetMilestone(milestone);
    }

    //TODO
    public void setProcedureBug(BugReport bugReport, String procedureBug) throws ReportErrorToUserException {
        bugReport.setProcedureBug(procedureBug);
    }

    //TODO
    public void setStackTrace(BugReport bugReport, String stackTrace) throws ReportErrorToUserException {
        bugReport.setStackTrace(stackTrace);
    }

    //TODO
    public void setErrorMessage(BugReport bugReport, String errorMessage) throws ReportErrorToUserException {
        bugReport.setErrorMessage(errorMessage);
    }

    //TODO
    public void addDependency(BugReport bugReport, BugReport dependency) throws ReportErrorToUserException {
        if (!isValidDependency(bugReport, dependency))
            throw new ReportErrorToUserException("Invalid dependency selected! The dependency is not part of the same project.");
        bugReport.addDependency(dependency);
    }

    //endregion

    //region Checkers

    /**
     * Method to check if the given milestone can be assigned to the given bugreport.
     *
     * @param bugReport The bugreport to assign the milestone to.
     * @param milestone The milestone to check.
     * @return True if the milestone is greater than the milestones of the subsystems to which this bugreport belongs.
     */
    public boolean canUpdateTargetMilestone(BugReport bugReport, TargetMilestone milestone) {
        try {
            SubSystem subSystem = this.getProjectService().getSubsystemWhichContainsBugReport(bugReport);
            if (SetMilestoneHelper.milestoneDoesExceedSubsystemMilestone(subSystem, milestone)) {
                return true;
            } else {
                return false;
            }
        } catch (ReportErrorToUserException e) {
            return false;
        }
    }

    /**
     * Method determining whether a user is allowed to add a patch to a specified bug report.
     *
     * @param user the user that wants to add a patch
     * @param bugReport the bug report that the user wants to add the patch to
     * @return true if the user is a developer and assigned as programmer, false if not.
     * @throws ReportErrorToUserException is thrown if there is invalid input.
     */
    public boolean canAddPatch(User user, BugReport bugReport) throws ReportErrorToUserException {
        Project project = this.getProjectService().getProjectsContainingBugReport(bugReport);
        if (project.getDevsRoles().stream().anyMatch(x -> x.getDeveloper().equals(user) && (x instanceof Programmer))) {
            return true;
        }
        return false;
    }

    /**
     * Method determining whether a user is allowed to add a test to a specified bug report.
     *
     * @param user the user that wants to add a test
     * @param bugReport the bug report that the user wants to add the test to
     * @return true if the user is a developer and assigned as tester, false if not.
     * @throws ReportErrorToUserException is thrown if there is invalid input.
     */
    public boolean canAddTest(User user, BugReport bugReport) throws ReportErrorToUserException {
        Project project = this.getProjectService().getProjectsContainingBugReport(bugReport);
        if (project.getDevsRoles().stream().anyMatch(x -> x.getDeveloper().equals(user) && (x instanceof Tester))) {
            return true;
        }
        return false;
    }

    //TODO Check constraints for the dependency. The dependency should be a bugreport of the same project.
    public boolean isValidDependency(BugReport bugReport, BugReport dependency) {
        try {
            Project bugRepProject = this.getProjectService().getProjectsContainingBugReport(bugReport);
            return (bugRepProject.getAllBugReports().contains(dependency));
        } catch (ReportErrorToUserException e) {
            return false;
        }
    }

    /**
     * Method to determine whether a bug report is visible to a certain user.
     *
     * @param user The user that wants to see a certain bug report
     * @param bugReport The bug report that wants to be looked up by the specified user
     *
     * @return true if the user is allowed to see the bug report, false if not
     */
    public boolean isVisibleByUser(User user, BugReport bugReport) {

        if (bugReport == null || bugReport.isPublic()) {
            return true;
        } else {

            if (bugReport.getCreator().equals(user)) {
                return true;
            } else {
                try {
                    Project project = getProjectService().getProjectsContainingBugReport(bugReport);
                    if (project.getDevsRoles().stream().anyMatch(x -> x.getDeveloper().equals(user))) return true;
                } catch (ReportErrorToUserException e) {
                    return false;
                }
            }
        }
        return false;
    }

    //endregion

    //region Function

    /**
     * Method to search for bugreports based on the given search method
     *
     * @param searchMethod The method to search for the bug report
     * @throws ReportErrorToUserException
     * @return The list of bugreports searched for
     */
    public List<BugReport> search(Search searchMethod, User user) throws ReportErrorToUserException {
        return searchMethod.apply(this, user);
    }

    //endregion

}
