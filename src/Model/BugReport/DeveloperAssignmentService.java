package Model.BugReport;

import CustomExceptions.ModelException;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Roles.Permission;
import Model.Roles.Role;
import Model.User.Developer;
import Model.User.User;


/**
 * Created by Tom on 2/03/16.
 */
public class DeveloperAssignmentService {
    private ProjectService projectService;

    /**
     * Default constructor for DeveloperAssignmentService.
     *
     * @param projectService The project service the DeveloperAssignmentService can use.
     *
     * @throws IllegalArgumentException The given projectservice is null.
     */
    public DeveloperAssignmentService(ProjectService projectService){
        if (projectService == null) throw new IllegalArgumentException("Projectservice is null");
        this.projectService = projectService;
    }

    /**
     * Assigns a developer to the given bugreport.
     *
     * @param user The user requesting the assignment
     * @param developer The developer to be assigned to the bugreport
     * @param bugReport The bugreport to which to assign the developer
     *
     * @throws ModelException The user doesn't have the permission to add the developer.
     * @throws IllegalArgumentException One of the given arguments is null.
     */
    public void assignDeveloperToBugReport(User user, Developer developer, BugReport bugReport) throws ModelException{
        if (!canUserAssignDeveloperToBugReport(user, developer, bugReport)) throw new ModelException("Cannot assign developer to bugreport!");

        bugReport.addAssignee(developer);
    }

    /**
     * Checker to check if the user has te permission to assign the developer to the bugreport
     *
     * @param user The user requesting the assignment
     * @param developer The developer to be assigned to the bugreport
     * @param bugReport The bugreport to which to assign the developer
     *
     * @return True if the user has the valid permissions to assign the developer to the bugreport.
     *
     * @throws IllegalArgumentException One of the given arguments is null.
     */
    public boolean canUserAssignDeveloperToBugReport(User user, Developer developer, BugReport bugReport){
        if (user == null) throw new IllegalArgumentException("User is null");
        if (developer == null) throw new IllegalArgumentException("Developer is null");
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");

        Project project = this.projectService.getProjectContainingBugReport(bugReport);
        Role role = this.getUserRoleWithinProject(user, project);

        if (role == null)
            return false;
        else if (role.hasValidAssignmentPermission(Permission.assignDevelopersToBugReport)){
            return true;
        }
        else if (role.hasValidAssignmentPermission(Permission.assignProjectDevelopersToBugReport)
                && projectContainDeveloper(developer, project)){
            return true;
        }
        else return false;
    }



    private Role getUserRoleWithinProject(User user, Project project) {
        for (Role role : project.getDevsRoles()) {
            if (role.getDeveloper().equals(user)) {
                return role;
            }
        }
        return null;
    }

    private boolean projectContainDeveloper(Developer developer, Project project){
        for (Role role: project.getDevsRoles()){
            if (role.getDeveloper().equals(developer)){
                return true;
            }
        }
        return false;
    }

}
