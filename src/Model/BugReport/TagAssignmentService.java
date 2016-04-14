package Model.BugReport;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.TagTypes.Assigned;
import Model.BugReport.TagTypes.Closed;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Roles.Role;
import Model.User.User;

import java.util.Arrays;
import java.util.List;

/**
 * Class creates a service that is responsible for assigning tags to bug reports.
 *
 * Contains a checker to check whether a user can assign a tag to a specific bug report.
 * Also contains a method to assign a tag to a specific bug report.
 */
public class TagAssignmentService {
    private ProjectService projectService;
    private List<Class<? extends Tag>> creatorTagPermissons;


    /**
     * Default constructor for tagAssignmentService.
     *
     * @param projectService The project service the tagAssignmentService can use.
     *
     * @throws  IllegalArgumentException the given projectservice is null.
     */

    public TagAssignmentService(ProjectService projectService){
        if (projectService == null) throw new IllegalArgumentException("Projectservice is null");

        this.projectService = projectService;
        this.creatorTagPermissons = Arrays.asList(Closed.class, Assigned.class);
    }

    /**
     * Method for changing the tag of the given bug report
     *
     * @param user The user requesting the tag change.
     * @param bugReport The bug report to which to assign the tag.
     * @param tag The tag to which to switch the bug report.
     *
     * @throws ReportErrorToUserException The user doesn't have the permission to assign the tag to the bug report.
     * @throws IllegalArgumentException One of the arguments is null.
     */
    public void assignTag(User user, BugReport bugReport, Tag tag) throws ReportErrorToUserException {
        if (!canAssignTag(user, bugReport, tag.getClass()))
            throw new ReportErrorToUserException("Not allowed to perform tag change!");

        bugReport.setTag(tag);
    }

    /**
     * Checker to check if the user has the permission to assign the tag to the given bug report
     *
     * @param user The user requesting the tag change.
     * @param bugReport The bug report to which to assign the tag.
     * @param tag The tag to which to switch the bug report.
     *
     * @return True if the user has the permission to assign the tag to the burgreport.
     *
     * @throws IllegalArgumentException One of the given arguments is null.
     * @throws ReportErrorToUserException One of the arguments doesn't match.
     */
    public boolean canAssignTag(User user, BugReport bugReport, Class<? extends Tag> tag) throws ReportErrorToUserException {
        if (user == null) throw new IllegalArgumentException("User is null");
        if (bugReport == null) throw new IllegalArgumentException("BugReport is null");
        if (tag == null) throw new IllegalArgumentException("Tag is null");

        if (!validTagChangePolicy(bugReport, tag)){
            return false;
        }
        if (bugReport.getCreator().equals(user) && this.validCreatorPermissions(tag)) {
            return true;
        }
        try
        {
            Project project = projectService.getProjectsContainingBugReport(bugReport);
            Role role = getUserRoleWithinProject(user, project);
	
	        if (role == null){
                return false;
            }

            return role.canAssignTag(tag);
        }
        catch(ReportErrorToUserException e)
        {
        	return false;
        }

    }

    private boolean validTagChangePolicy(BugReport bugReport, Class<? extends Tag> tag) {
        return bugReport.getTag().canChangeToTag(tag);
    }

    private boolean validCreatorPermissions(Class<? extends Tag> tag) {
        return this.creatorTagPermissons.contains(tag);
    }

    private Role getUserRoleWithinProject(User user, Project project){
        if (project.getLeadRole().getDeveloper().equals(user)) {
            return project.getLeadRole();
        }
        for (Role role: project.getDevsRoles()){
            if (role.getDeveloper().equals(user)){
                return role;
            }
        }
        return null;
    }

}
