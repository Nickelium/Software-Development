package Model.BugReport;

import CustomExceptions.ModelException;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Roles.Role;
import Model.Tags.Assigned;
import Model.Tags.Resolved;
import Model.Tags.Tag;
import Model.User.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tom on 24/02/16.
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
        this.creatorTagPermissons = Arrays.asList(Resolved.class, Assigned.class);
    }

    /**
     * Method for changing the tag of the given bugreport
     *
     * @param user The user requesting the tag change.
     * @param bugReport The bugreport to which to assign the tag.
     * @param tag The tag to which to switch the bugreport.
     *
     * @throws ModelException The user doesn't have the permission to assign the tag to the bugreport.
     * @throws IllegalArgumentException One of the arguments is null.
     */
    public void assignTag(User user, BugReport bugReport, Tag tag) throws ModelException{
        if (!canAssignTag(user, bugReport, tag)) throw new ModelException("Not allowed to preform tag change!");

        bugReport.setTag(tag);
    }

    /**
     * Checker to check if the user has the permission to assign the tag to the given bugreport
     *
     * @param user The user requesting the tag change.
     * @param bugReport The bugreport to which to assign the tag.
     * @param tag The tag to which to switch the bugreport.
     *
     * @return True if the user has the permission to assign the tag to the burgreport.
     *
     * @throws IllegalArgumentException One of the given arguments is null.
     * @throws ModelException One of the arguments doesn't match.
     */
    public boolean canAssignTag(User user, BugReport bugReport, Tag tag) throws ModelException{
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
	        Project project = projectService.getProjectContainingBugReport(bugReport);
	        Role role = getUserRoleWithinProject(user, project);
	
	        if (role == null){
                return false;
            }

            return role.canAssignTag(tag);
        }
        catch(ModelException e)
        {
        	return false;
        }

    }

    private boolean validTagChangePolicy(BugReport bugReport, Tag tag){
        return bugReport.getTag().canChangeToTag(tag);
    }

    private boolean validCreatorPermissions(Tag tag){
        return this.creatorTagPermissons.contains(tag.getClass());
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
