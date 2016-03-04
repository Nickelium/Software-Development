package Model.BugReport;

import CustomExceptions.ModelException;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Roles.Role;
import Model.Tags.Assigned;
import Model.Tags.Resolved;
import Model.Tags.Tag;
import Model.User.User;
import org.jetbrains.annotations.Nullable;

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
     */
    public TagAssignmentService(ProjectService projectService){
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
     */
    public boolean canAssignTag(User user, BugReport bugReport, Tag tag){
        if (!validTagChangePolicy(bugReport, tag)){
            return false;
        }
        if (bugReport.getCreator().equals(user) && this.validCreatorPermissions(tag)) {
            return true;
        }

        Project project = projectService.getProjectContainingBugReport(bugReport);
        Role role = getUserRoleWithinProject(user, project);

        if (role == null){
            return false;
        }

        return role.canAssignTag(tag);

    }

    private boolean validTagChangePolicy(BugReport bugReport, Tag tag){
        return bugReport.getTag().canChangeToTag(tag);
    }

    private boolean validCreatorPermissions(Tag tag){
        return this.creatorTagPermissons.contains(tag.getClass());
    }

    @Nullable
    private Role getUserRoleWithinProject(User user, Project project){
        for (Role role: project.getDevsRoles()){
            if (role.getDeveloper().equals(user)){
                return role;
            }
        }
        return null;
    }

}
