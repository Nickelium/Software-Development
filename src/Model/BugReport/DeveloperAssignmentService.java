package Model.BugReport;

import CustomExceptions.ReportErrorToUserException;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Roles.Permission;
import Model.Roles.Role;
import Model.User.Developer;
import Model.User.User;


/**
 * Class creates a service that is responsible for assigning developers
 * to bug reports.
 *
 * Also contains checkers for assignment of developers by users.
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
     * Assigns a developer to the given bug report.
     *
     * @param user The user requesting the assignment
     * @param developer The developer to be assigned to the bug report
     * @param bugReport The bug report to which to assign the developer
     *
     * @throws ReportErrorToUserException Assigning the developer to the specified bug report caused an error.
     * @throws IllegalArgumentException One of the given arguments is null.
     */
    public void assignDeveloperToBugReport(User user, Developer developer, BugReport bugReport) throws ReportErrorToUserException {
        if (!canUserAssignDeveloperToBugReport(user, developer, bugReport)) throw new ReportErrorToUserException("Cannot assign developer to bug report!");
        bugReport.addAssignee(developer);
    }

    /**
     * Checker to check if the user has te permission to assign the developer to the bug report
     *
     * @param user The user requesting the assignment
     * @param developer The developer to be assigned to the bug report
     * @param bugReport The bug report to which to assign the developer
     *
     * @return True if the user has the valid permissions to assign the developer to the bug report or bug report has permanent tag..
     *
     * @throws IllegalArgumentException One of the given arguments is null.
     */
    public boolean canUserAssignDeveloperToBugReport(User user, Developer developer, BugReport bugReport) {
        if (user == null) throw new IllegalArgumentException("User is null");
        if (developer == null) throw new IllegalArgumentException("Developer is null");
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");

        Project project;
        try {
            project = this.getProjectService().getProjectsContainingBugReport(bugReport);

            if (canUserAssignDevelopers(user, bugReport)
                    && projectContainDeveloper(developer, project)) {
                return true;
            } else return false;
        } catch (ReportErrorToUserException e) {
            return false;
        }
    }

    /**
     * Checker to check if the user has te permission to assign developers to the bug report
     *
     * @param user      The user requesting the assignment
     * @param bugReport The bug report to which the user wishes to assign developers.
     * @return True if the user has the valid permissions to assign developers to the bug report.
     * @throws IllegalArgumentException One of the given arguments is null.
     *
     */
    public boolean canUserAssignDevelopers(User user, BugReport bugReport) {
        if (user == null) throw new IllegalArgumentException("User is null");
        if (bugReport == null) throw new IllegalArgumentException("Bugreport is null");

        Project project;
        try {
            project = this.getProjectService().getProjectsContainingBugReport(bugReport);

            Role role = this.getUserRoleWithinProject(user, project);

            if (role == null)
                return false;
            else if (role.hasValidAssignmentPermission(Permission.assignDevelopersToBugReport)) {
                return true;
            } else return false;
        } catch (ReportErrorToUserException e) {
            return false;
        }
    }


    private Role getUserRoleWithinProject(User user, Project project) {
        if (project.getLeadRole().getDeveloper().equals(user)) {
            return project.getLeadRole();
        }
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

    private ProjectService getProjectService() {
        return projectService;
    }
}