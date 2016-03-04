package Model.BugReport;

import Model.Project.ProjectService;
import Model.User.Issuer;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;
import Model.Project.Project;
import Model.Project.SubSystem;
import Model.Tags.Tag;
import Model.User.User;

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
     */
    public BugReportService(ProjectService projectService)
    {
        this.projectService = projectService;
    }

    /**
     * Function to create a new BugReport and add the bugreport to the list of bugreports.
     *
     * @param title The title of the bugreport
     * @param description The description of the bugreport
     * @param creator The creator of the bugreport
     * @param subsystem The subsystem of the bugreport
     *
     * @return The newly created bugreport
     * @throws Exception 
     */
    public BugReport createBugReport(String title, String description, Issuer creator, SubSystem subSystem) throws Exception
    {
        BugReport bugReport = new BugReport(title, description, null,creator);
        subSystem.addBugReport(bugReport);
        return bugReport;
    }
    
    /**
     * Function to create a new BugReport and add the bugreport to the list of bugreports.
     *
     * @param title The title of the bugreport
     * @param description The description of the bugreport
     * @param creator The creator of the bugreport
     * @param subsystem The subsystem of the bugreport
     *
     * @return The newly created bugreport
     * @throws Exception 
     */
    public BugReport createBugReport(String title, String description, SubSystem subSystem, Tag tag) throws Exception
    {
        BugReport bugReport = new BugReport(title, description,tag);
        subSystem.addBugReport(bugReport);
        return bugReport;
    }

    /**
     * Getter to request all the BugReports there are.
     *
     * @return A list of all the BugReports.
     */
    public List<BugReport> getAllBugReports()
    {
    	List<BugReport> bugReportList = new ArrayList<>();
    	for(Project project : projectService.getProjectList())
    		for(SubSystem subSystem : project.getAllSubSystem())
    				bugReportList.addAll(subSystem.getBugReports());
        return Collections.unmodifiableList(bugReportList);
    }

    private IListWrapper<BugReport> getAllBugReportsWrapped()
    {
    	return new ListWrapper<BugReport>(getAllBugReports());
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
     * @return A list of all the bugreports about the given project.
     */
    public List<BugReport> getBugReportsForProject(Project project){
        List<SubSystem> subSystems = project.getAllSubSystem();
        List<BugReport> bugReportList = new ArrayList<>();
        for(SubSystem subSystem : subSystems)
        	bugReportList.addAll(subSystem.getBugReports());
        
        return Collections.unmodifiableList(bugReportList);
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

}
