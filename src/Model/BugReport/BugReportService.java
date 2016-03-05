package Model.BugReport;

import CustomExceptions.ModelException;
import Model.Project.ProjectService;
import Model.Project.TheDate;
import Model.User.Issuer;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;
import Model.Project.Project;
import Model.Project.SubSystem;
import Model.Tags.Tag;
import Model.User.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IllegalFormatCodePointException;
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
     *
     * @param title The title of the bugreport
     * @param description The description of the bugreport
     * @param creator The creator of the bugreport
     * @param subSystem The subsystem of the bugreport
     *
     * @return The newly created bugreport
     *
     * @throws ModelException the given title of description is empty.
     * @throws IllegalArgumentException The subsystem, creator, creationdata or tag is null.
     */
    public BugReport createBugReport(String title, String description, SubSystem subSystem, Issuer creator, TheDate creationDate, Tag tag) throws ModelException
    {
        BugReport bugReport = new BugReport(title,description,subSystem,creator, creationDate, tag);
        return bugReport;
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
     */
    public BugReport getBugReport(BugReportID id)
    {
        return getAllBugReportsWrapped().getOne(x -> x.getId().equals(id));
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
     * Getter for retrieving all the BugReports assigned to a specific user.
     *
     * @param user The user which is assigned the bugReports
     *
     * @return A list of all the bugReports assigned to the specified user.
     */
    public List<BugReport> getBugReportsAssignedToUser(User user)
    {
    	IListWrapper<BugReport> bugReportList = getAllBugReportsWrapped();
    	
        List<BugReport> bugReports = bugReportList.getAllMatching(x -> x.getAssignees().contains(user));
        return Collections.unmodifiableList(bugReports);
    }

    /**
     * Getter for retrieving all the BugReports issued by a specific user.
     *
     * @param user The user which issued the bugReports
     *
     * @return A list of all the bugReports issued by the specified user.
     */
    public List<BugReport> getBugReportsFiledByUser(User user){
    	IListWrapper<BugReport> bugReportList = getAllBugReportsWrapped();

        List<BugReport> bugReports = bugReportList.getAllMatching(x -> x.getCreator().equals(user));
        return Collections.unmodifiableList(bugReports);
    }

    private IListWrapper<BugReport> getAllBugReportsWrapped()
    {
        return new ListWrapper<>(getAllBugReports());
    }

}
