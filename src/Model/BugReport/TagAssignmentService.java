package Model.BugReport;

import Model.Project.ProjectService;
import Model.Tags.Tag;
import Model.User.User;

/**
 * Created by Tom on 24/02/16.
 */
public class TagAssignmentService {
    private ProjectService projectService;

    public TagAssignmentService(ProjectService projectService){
        this.projectService = projectService;
    }

    public boolean canAssignTag(User user, BugReport bugReport, Tag tag){
        projectService.getProj
    }

}
