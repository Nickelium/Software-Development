package Model.BugReport;

import CustomExceptions.ReportErrorToUserException;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.User.Developer;
import Model.User.Issuer;
import Model.User.User;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tom on 24/02/16.
 */
public class BugReportService {

    private ProjectService projectService;
    
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

    /**
     * Function to create a new BugReport and add the bugreport to the list of bugreports.
     *
     * @param title The title of the bugreport
     * @param description The description of the bugreport
     * @param creator The creator of the bugreport
     * @param subSystem The subsystem of the bugreport
     *
     * @return The newly created bugreport
     *
     * @throws ReportErrorToUserException the title or description is empty.
     * @throws IllegalArgumentException the creator or subsystem is null.
     */
    public BugReport createBugReport(String title, String description, Issuer creator, SubSystem subSystem, boolean pblc) throws ReportErrorToUserException
    {
        BugReport bugReport = new BugReport(title, description, subSystem, creator, pblc);
        return bugReport;
    }
    
    /**
     * Function to create a new BugReport and add the bugreport to the list of bugreports.
     * Van de gebruiker van deze functie wordt verwacht dat de initialAssignies ook developers van het project zijn.
     * Anders zullen tag assignment rechten voor deze gebruiker geweigerd worden.
     *
     * @param title The title of the bugreport
     * @param description The description of the bugreport
     * @param creator The creator of the bugreport
     * @param subSystem The subsystem of the bugreport
     * @param creationDate The creation date of the bugreport
     * @param tag The initial tag of the bugreport
     * @param initialAssignees The list of initialAssignies van de bugreport
     *
     * @return The newly created bugreport
     *
     * @throws ReportErrorToUserException the given title of description is empty.
     * @throws IllegalArgumentException The subsystem, creator, creationdata or tag is null.
     */
    public BugReport createBugReport(String title, String description, Issuer creator, boolean pblc, SubSystem subSystem, TheDate creationDate, Tag tag, List<Developer> initialAssignees, List<Patch> patches, List<Test> tests) throws ReportErrorToUserException
    {
        BugReport bugReport = new BugReport(title, description, subSystem, creator, pblc, creationDate, tag, initialAssignees, patches, tests);
        return bugReport;
    }

    /**
     * Method for creating a comment and adding it to the list of comments of the bugreport.
     *
     * @param text      The text of the comment.
     * @param issuer    The issuer writing the comment.
     * @param bugReport The bugreport on which the issuer commented.
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
     * Getter to request all the BugReports that are visible to the user.
     *
     * @return An unmodifiable list of all the BugReports visible to the user.
     */
    public List<BugReport> getAllBugReports(User user)
    {
    	List<BugReport> bugReports = new ArrayList<>();
        for (Project project: projectService.getAllProjects()){
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
     * 			thrown when no bugreport is found.
     * 		    or bugreport cannot be seen by user.
     */
    public BugReport getBugReport(BugReportID id, User user) throws ReportErrorToUserException
    {
        BugReport bugreport = getAllBugReportsWrapped().getOne(x -> x.getId().equals(id));
        if (!this.isVisibleByUser(user, bugreport))
            throw new ReportErrorToUserException("You are not allowed to see this bugreport.");
        if (bugreport == null) throw new ReportErrorToUserException("There is no bugreport with the given id.");
        return bugreport;
    }

    /**
     * Getter for retrieving all bugReports concerning the given project.
     *
     * @param project The project for which to find the bugReports.
     *
     * @return An unmodifieable list of all the bugreports about the given project.
     */
    public List<BugReport> getBugReportsForProject(Project project){
        return project.getAllBugReports();
    }

    /**
     * Method to search for bugreports based on the given search method
     * 
     * @param searchMethod The method to search for the bugreport
     * 
     * @return	The list of bugreports searched for
     * @throws ReportErrorToUserException
     * 
     */
    public List<BugReport> search(Search searchMethod, User user) throws ReportErrorToUserException
    {
        return searchMethod.apply(this, user);
    }


    private boolean isVisibleByUser(User user, BugReport bugReport) {
        if (bugReport.isPublic()) {
            return true;
        } else {

            if (bugReport.getCreator().equals(user)) {
                return true;
            } else {
                try {
                    Project project = projectService.getProjectsContainingBugReport(bugReport);
                    if (project.getDevsRoles().stream().anyMatch(x -> x.getDeveloper().equals(user))) return true;
                } catch (ReportErrorToUserException e) {
                    return false;
                }
            }
        }
        return false;
    }

    private IListWrapper<BugReport> getAllBugReportsWrapped()
    {
        List<BugReport> bugReports = new ArrayList<>();
        for (Project project: projectService.getAllProjects()){
            bugReports.addAll(project.getAllBugReports());
        }
        return new ListWrapper<>(bugReports);
    }

}
