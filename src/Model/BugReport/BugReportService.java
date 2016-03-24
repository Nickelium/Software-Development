package Model.BugReport;

import CustomExceptions.ModelException;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Tags.Tag;
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
     * @throws ModelException the title or description is empty.
     * @throws IllegalArgumentException the creator or subsystem is null.
     */
    public BugReport createBugReport(String title, String description, Issuer creator, SubSystem subSystem) throws ModelException
    {
        BugReport bugReport = new BugReport(title, description, subSystem, creator);
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
     * @throws ModelException the given title of description is empty.
     * @throws IllegalArgumentException The subsystem, creator, creationdata or tag is null.
     */
    public BugReport createBugReport(String title, String description, Issuer creator, SubSystem subSystem, TheDate creationDate, Tag tag, List<Developer> initialAssignees) throws ModelException
    {
        BugReport bugReport = new BugReport(title,description,subSystem,creator, creationDate, tag, initialAssignees);
        return bugReport;
    }

    /**
     * Method for creating a comment and adding it to the list of comments of the bugreport.
     *
     * @param text      The text of the comment.
     * @param issuer    The issuer writing the comment.
     * @param bugReport The bugreport on which the issuer commented.
     * @return The newly created comment.
     * @throws ModelException One of the given arguments are illegal. See constructor of comment for rules.
     */
    public Comment createComment(String text, Issuer issuer, BugReport bugReport) throws ModelException {
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
     * @throws ModelException One of the given arguments are illegal. See constructor of comment for rules.
     */
    public Comment createComment(String text, Issuer issuer, Comment comment) throws ModelException {
        Comment newComment = new Comment(text, issuer);
        comment.addComment(newComment);
        return newComment;

    }

    /**
     * Getter to request all the BugReports there are.
     *
     * @return An unmodifiable list of all the BugReports.
     */
    public List<BugReport> getAllBugReports()
    {
    	List<BugReport> bugReports = new ArrayList<>();
        for (Project project: projectService.getAllProjects()){
            bugReports.addAll(project.getAllBugReports());
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
     * @throws ModelException
     * 			thrown when no bugreport is found.
     */
    public BugReport getBugReport(BugReportID id) throws ModelException
    {
        BugReport bugreport = getAllBugReportsWrapped().getOne(x -> x.getId().equals(id));

        if (bugreport == null) throw new ModelException("There is no bugreport with the given id.");
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
     * @throws ModelException 
     * 
     */
    public List<BugReport> search(Search searchMethod) throws ModelException 
    {
    	return searchMethod.apply(this);
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
