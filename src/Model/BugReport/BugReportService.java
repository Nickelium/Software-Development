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
import java.util.stream.Collectors;

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
     * @param pblc The access right of the bug report.
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
     * @param pblc The access right of the bug report.
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
     * @throws IllegalArgumentException BugReport is null.
     */
    public Comment createComment(String text, Issuer issuer, BugReport bugReport) throws ReportErrorToUserException {
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");
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
     * @throws IllegalArgumentException Comment is null.
     */
    public Comment createComment(String text, Issuer issuer, Comment comment) throws ReportErrorToUserException {
        if (comment == null) throw new IllegalArgumentException("Comment is null");
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
     * @throws IllegalArgumentException BugReport is null.
     */
    public Test createTest(String text, User user, BugReport bugReport) throws ReportErrorToUserException {
        if (bugReport == null) throw new IllegalArgumentException("BugReport is null");
        if (!canAddTest(user, bugReport)) throw new ReportErrorToUserException("You are not allowed to add a test");
        Test test = new Test(text, user);
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
     * @throws IllegalArgumentException BugReport is null.
     */
    public Patch createPatch(String text, User user, BugReport bugReport) throws ReportErrorToUserException {
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");
        if (!canAddPatch(user, bugReport)) throw new ReportErrorToUserException("You are not allowed to add a patch");
        Patch patch = new Patch(text, user);
        bugReport.addPatch(patch);
        return patch;
    }

    //endregion

    //region Getters

    /**
     * Getter to request all the BugReports that are visible to the user.
     *
     * @param user The user requesting all the bugreports.
     *
     * @return An unmodifiable list of all the BugReports visible to the user.
     * @throws IllegalArgumentException User is null.
     */
    public List<BugReport> getAllBugReports(User user)
    {
        if (user == null) throw new IllegalArgumentException("User is null");
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
     * @param id The id of the BugReport to get
     * @param user The user requesting the bug report.
     *
     * @return The BugReport matching the given id
     *
     * @throws ReportErrorToUserException
     * 			thrown when no bug report is found.
     * 		    or bug report cannot be seen by user.
     * @throws IllegalArgumentException User or bugreportId is null.
     */
    public BugReport getOneBugReport(BugReportID id, User user) throws ReportErrorToUserException
    {
        if (id == null) throw new IllegalArgumentException("BugreportId is null");
        if (user == null) throw new IllegalArgumentException("User is null");
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
     * @return An unmodifiable list of all the bug reports about the given project.
     * @throws IllegalArgumentException project is null
     */
    public List<BugReport> getBugReportsForProject(Project project)
    {
    	if(project == null) throw new IllegalArgumentException("Subsystem cannot be null");
        return project.getAllBugReports();
    }

    /**
     * Method to return the project service object.
     * @return the project service object
     */
    private ProjectService getProjectService() {
        return projectService;
    }

    /**
     * Method to get a IListWrapper containing all bug reports.
     * @return a IListWrapper containing all bug reports
     */
    private IListWrapper<BugReport> getAllBugReportsWrapped() {
        List<BugReport> bugReports = new ArrayList<>();
        for (Project project : getProjectService().getAllProjects()) {
            bugReports.addAll(project.getAllBugReports());
        }
        return new ListWrapper<>(bugReports);
    }

    //endregion

    //region Performance Metrics functions

    //TODO doc

    //region Test information

    public List<Test> getAllTestsSubmittedByDeveloper(User user) {
        return getAllTests(user).stream().filter(x -> x.getCreator().equals(user)).collect(Collectors.toList());
    }

    public List<Test> getAllTests(User user) {
        if (!(user instanceof Developer)) throw new IllegalArgumentException("This is not a developer.");
        List<Test> tests = new ArrayList<>();
        for (BugReport br : getAllBugReports(user)) {
            tests.addAll(br.getTests());
        }
        return tests;
    }

    public Double getAverageLinesOfTestCodeByUser(User user) {
        List<Test> tests = getAllTestsSubmittedByDeveloper(user);
        int linesOfCode = 0;

        for (Test test : tests) {
            linesOfCode += test.getLines();
        }

        if (linesOfCode != 0) return ((double) linesOfCode) / tests.size();
        return 0.0;
    }

    //endregion

    //region Patch information

    public List<Patch> getAllPatchesSubmittedByDeveloper(User user) {
        return getAllPatches(user).stream().filter(x -> x.getCreator().equals(user)).collect(Collectors.toList());
    }

    public List<Patch> getAllPatches(User user) {
        if (!(user instanceof Developer)) throw new IllegalArgumentException("This is not a developer.");
        List<Patch> patches = new ArrayList<>();
        for (BugReport br : getAllBugReports(user)) {
            patches.addAll(br.getPatches());
        }
        return patches;
    }

    public Double getAverageLinesOfPatchCodeByUser(User user) {
        List<Patch> patches = getAllPatchesSubmittedByDeveloper(user);
        int linesOfCode = 0;

        for (Patch patch : patches) {
            linesOfCode += patch.getLines();
        }

        if (linesOfCode != 0) return ((double) linesOfCode) / patches.size();
        return 0.0;
    }

    //endregion

    //region Bug Report information

    public List<BugReport> getAllBugReportsUserAssignedTo(User user) {
        if (!(user instanceof Developer)) throw new IllegalArgumentException("This is not a developer.");
        return getAllBugReports(user).stream().filter(x -> x.getAssignees().contains(user)).collect(Collectors.toList());
    }

    public List<BugReport> getAllBugReportsCreatedByUser(User user) {
        if (!(user instanceof Issuer)) throw new IllegalArgumentException("This is not a issuer.");
        return getAllBugReports(user).stream().filter(x -> x.getCreator().equals(user)).collect(Collectors.toList());
    }

    public List<BugReport> getAllBugReportsWithTagUserAssignedTo(User user, Class<? extends Tag> tag) {
        return getAllBugReportsUserAssignedTo(user).stream().filter(x -> (x.getTag().getClass().equals(tag))).collect(Collectors.toList());
    }

    public List<BugReport> getAllBugReportsWithTagCreatedByUser(User user, Class<? extends Tag> tag) {
        return getAllBugReportsCreatedByUser(user).stream().filter(x -> x.getTag().getClass().equals(tag)).collect(Collectors.toList());
    }

    //endregion

    //endregion

    //region Setters

    /**
     * Method to set the Target milestone of a bug report.
     *
     * @param bugReport The bug report to set the target milestone of.
     * @param milestone The milestone to set to the bug report.
     * @throws ReportErrorToUserException is thrown when the new target milestone
     *                                    is not greater than all the subsystems' milestones.
     * @throws IllegalArgumentException Bugreport or milestone is null.
     */
    public void setTargetMilestone(BugReport bugReport, TargetMilestone milestone) throws ReportErrorToUserException {
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");
        if (milestone == null) throw new IllegalArgumentException("Milestone is null");
        if (!canUpdateTargetMilestone(bugReport, milestone))
            throw new ReportErrorToUserException("The milestone is not greater than all the subsystems milestones.");
        bugReport.setTargetMilestone(milestone);
    }

    /**
     * Method to set a new procedure bug of a bug report.
     *
     * @param bugReport The bug report to set the procedure bug of
     * @param procedureBug The procedure bug that has to be set
     * @throws ReportErrorToUserException is thrown when the new procedure bug is invalid.
     * @throws IllegalArgumentException BugReport is null.
     */
    public void setProcedureBug(BugReport bugReport, String procedureBug) throws ReportErrorToUserException {
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");
        bugReport.setProcedureBug(procedureBug);
    }

    /**
     * Method to set a new stack trace of a bug report.
     *
     * @param bugReport The bug report to set the stack trace of
     * @param stackTrace The stack trace that has to be set
     * @throws ReportErrorToUserException is thrown when the new stack trace is invalid.
     * @throws IllegalArgumentException BugReport is null.
     */
    public void setStackTrace(BugReport bugReport, String stackTrace) throws ReportErrorToUserException {
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");
        bugReport.setStackTrace(stackTrace);
    }

    /**
     * Method to set a new error message of a bug report.
     *
     * @param bugReport The bug report to set the error message of
     * @param errorMessage The error message that has to be set
     * @throws ReportErrorToUserException is thrown when the new error message is invalid.
     * @throws IllegalArgumentException BugReport is null.
     */
    public void setErrorMessage(BugReport bugReport, String errorMessage) throws ReportErrorToUserException {
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");
        bugReport.setErrorMessage(errorMessage);
    }

    /**
     * Method to add a new dependency to a bug report.
     *
     * @param bugReport the bug report to which the new dependency has to be added
     * @param dependency the new dependency to be added to the specified bug report
     * @throws ReportErrorToUserException is thrown when an invalid dependency has been selected.
     */
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
     */
    public boolean canAddPatch(User user, BugReport bugReport) {
        try {
            Project project = this.getProjectService().getProjectsContainingBugReport(bugReport);
            if (project.getDevsRoles().stream().anyMatch(x -> x.getDeveloper().equals(user) && (x instanceof Programmer))) {
                return true;
            }
            return false;
        } catch (ReportErrorToUserException e) {
            return false;
        }
    }

    /**
     * Method determining whether a user is allowed to add a test to a specified bug report.
     *
     * @param user the user that wants to add a test
     * @param bugReport the bug report that the user wants to add the test to
     * @return true if the user is a developer and assigned as tester, false if not.
     */
    public boolean canAddTest(User user, BugReport bugReport) {
        try {
            Project project = this.getProjectService().getProjectsContainingBugReport(bugReport);
            if (project.getDevsRoles().stream().anyMatch(x -> x.getDeveloper().equals(user) && (x instanceof Tester))) {
                return true;
            }
            return false;
        } catch (ReportErrorToUserException e) {
            return false;
        }
    }

    /**
     * Method to check the constraints for the dependency:
     * The dependency should be a bug report of the same project.
     *
     * @param bugReport the bug report to which the dependency needs to be added
     * @param dependency the dependency that has to be added to the specified bug report
     * @return true if the dependency is a bug report of the same project as the specified bug report
     *         false if otherwise
     */
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
     * @param user The user requesting the search
     *
     * @return The list of bugreports searched for
     *
     * @throws ReportErrorToUserException Something went wrong during search
     */
    public List<BugReport> search(Search searchMethod, User user) throws ReportErrorToUserException {
        return searchMethod.apply(this, user);
    }

    //endregion

}
