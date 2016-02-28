package Model.BugReport;

import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Roles.Role;
import Model.Tags.Assigned;
import Model.Tags.Resolved;
import Model.Tags.Tag;
import Model.User.Developer;
import Model.User.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tom on 24/02/16.
 */
public class TagAssignmentService {
    private ProjectService projectService;
    private List<Tag> creatorTagPermissons;

    public TagAssignmentService(ProjectService projectService){
        this.projectService = projectService;
        this.creatorTagPermissons = Arrays.asList(new Resolved(), new Assigned());
    }

    public void assignTag(User user, BugReport bugReport, Tag tag) throws Exception{
        if (!canAssignTag(user, bugReport, tag)) throw new Exception("Not allowed to preform tag change");

        bugReport.setTag(tag);
    }

    public boolean canAssignTag(User user, BugReport bugReport, Tag tag){
        if (!checkTagChangePolicy(bugReport, tag)){
            return false;
        }
        else if (bugReport.getCreator().equals(user)) {
            return this.checkCreatorPermissions(tag);

        } else {
            List<Project> projects = getRelevantProjects(bugReport);
            List<Role> roles = new ArrayList<>();
            for (Project project: projects){
                roles.addAll(getUserRolesWithin(user, project));
            }
            for (Role role: roles){
                if (checkNonCreatorPermissions(role, tag)){
                    return true;
                }
            }
            return false;
        }
    }

    private boolean checkTagChangePolicy(BugReport bugReport, Tag tag){
        return bugReport.getTag().canChangeToTag(tag);
    }

    private boolean checkCreatorPermissions(Tag tag){
        return this.creatorTagPermissons.contains(tag);
    }

    private boolean checkNonCreatorPermissions(Role role, Tag tag){
        return role.canAssignTag(tag);
    }

    private List<Project> getRelevantProjects(BugReport bugReport){
        List<Project> relevantProjects = new ArrayList<>();

        for (Project project: projectService.getProjectList()){
            if (project.getAllSubSystem().contains(bugReport.getSubsystem())){
                relevantProjects.add(project);
            }
        }
        return  relevantProjects;
    }

    private List<Role> getUserRolesWithin(User user, Project project){
        List<Role> roles = new ArrayList<>();

        for (Role role: project.getDevsRoles()){
            if (role.getDeveloper().equals(user)){
                roles.add(role);
            }
        }

        return roles;
    }

}
