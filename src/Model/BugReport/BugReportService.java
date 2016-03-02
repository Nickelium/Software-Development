package Model.BugReport;

import Model.Project.ProjectService;
import Model.User.Issuer;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;
import Model.Project.Project;
import Model.Project.SubSystem;
import Model.User.User;

import java.util.Collections;
import java.util.List;

/**
 * Created by Tom on 24/02/16.
 */
public class BugReportService {

    private ListWrapper<BugReport> bugReportList;

    /**
     * Constructor for the bugReport service.
     *
     */
    public BugReportService(List<BugReport> bugReportList){
        this.bugReportList = new ListWrapper<BugReport>(bugReportList);
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
     */
    public BugReport createBugReport(String title, String description, Issuer creator, SubSystem subsystem){
        BugReport bugReport = new BugReport(title, description, subsystem, creator);
        this.bugReportList.insert(bugReport);
        return bugReport;
    }

    /**
     * Getter to request all the BugReports there are.
     *
     * @return A list of all the BugReports.
     */
    public List<BugReport> getAllBugReports(){
        return Collections.unmodifiableList(bugReportList.getAll());
    }

    /**
     * Getter to get one specific BugReport.
     *
     * @param id The id of the BugReport to get.
     *
     * @return The BugReport matching the given id.
     */
    public BugReport getBugReport(BugReportID id){
        return this.bugReportList.getOne(x -> x.getId().equals(id));
    }

    /**
     * Method for adding a new BugReport.
     *
     * @param bugReport The BugReport to Add.
     */
    public void addBugReport(BugReport bugReport){
        this.bugReportList.insert(bugReport);
    }

    /**
     * Method for deleting a BugReport.
     *
     * @param bugReport The bugreport to delete.
     */
    public void deleteBugReport(BugReport bugReport){
        this.bugReportList.delete(bugReport);
    }

    /**
     * Getter for retrieving all bugReports concerning the given project.
     *
     * @param project The project for which to find the bugReports.
     *
     * @return A list of all the bugreports about the given project.
     */
    public List<BugReport> getBugReportsForProject(Project project){
        List<SubSystem> subsystems = project.getAllSubSystem();
        List<BugReport> bugReports = this.bugReportList.getAllMatching(x -> subsystems.contains(x.getSubsystem()));
        return Collections.unmodifiableList(bugReports);
    }

    /**
     * Getter for retrieving all the BugReports assigned to a specific user.
     *
     * @param user The user which is assigned the bugReports
     *
     * @return A list of all the bugReports assigned to the specified user.
     */
    public List<BugReport> getBugReportsAssignedToUser(User user){
        List<BugReport> bugReports = this.bugReportList.getAllMatching(x -> x.getAssignees().contains(user));
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
        List<BugReport> bugReports = this.bugReportList.getAllMatching(x -> x.getCreator().equals(user));
        return Collections.unmodifiableList(bugReports);
    }

}
