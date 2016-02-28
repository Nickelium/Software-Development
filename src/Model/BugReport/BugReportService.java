package Model.BugReport;

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

    private IListWrapper<BugReport> list;

    /**
     * Constructor for the bugReport service.
     *
     */
    public BugReportService(){
        this.list = new ListWrapper<BugReport>();
    }

    /**
     * Getter to request all the BugReports there are.
     *
     * @return A list of all the BugReports.
     */
    public List<BugReport> getAllBugReports(){
        return Collections.unmodifiableList(this.list.getAll());
    }

    /**
     * Getter to get one specific BugReport.
     *
     * @param id The id of the BugReport to get.
     *
     * @return The BugReport matching the given id.
     */
    public BugReport getBugReport(BugReportID id){
        return this.list.getOne(x -> x.getId().equals(id));
    }

    /**
     * Method for adding a new BugReport.
     *
     * @param bugReport The BugReport to Add.
     */
    public void addBugReport(BugReport bugReport){
        this.list.insert(bugReport);
    }

    /**
     * Method for deleting a BugReport.
     *
     * @param bugReport The bugreport to delete.
     */
    public void deleteBugReport(BugReport bugReport){
        this.list.delete(bugReport);
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
        List<BugReport> bugReports = this.list.getAllMatching(x -> subsystems.contains(x.getSubsystem()));
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
        List<BugReport> bugReports = this.list.getAllMatching(x -> x.getAssignees().contains(user));
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
        List<BugReport> bugReports = this.list.getAllMatching(x -> x.getCreator().equals(user));
        return Collections.unmodifiableList(bugReports);
    }

}
