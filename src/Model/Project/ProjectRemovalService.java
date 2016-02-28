package Model.Project;

import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;

import java.util.List;

/**
 * Created by Tom on 28/02/16.
 */
public class ProjectRemovalService {
    private ProjectService projectService;
    private BugReportService bugReportService;

    /**
     * Constructor for a project removal service.
     *
     * @param projectService The project service containing the projects
     *
     * @param bugReportService The bugreportservice containing the bugreports.
     */
    public ProjectRemovalService(ProjectService projectService, BugReportService bugReportService){
        if (bugReportService == null) throw new IllegalArgumentException("ProjectService is null!");
        if (projectService == null) throw new IllegalArgumentException("BugreportService is null!");
        this.bugReportService = bugReportService;
        this.projectService = projectService;
    }

    public void removeProject(Project project){
        List<BugReport> bugreportlist = this.bugReportService.getBugReportsForProject(project);
        for (BugReport bugReport: bugreportlist){
            this.bugReportService.deleteBugReport(bugReport);
        }

        this.projectService.deleteProject(project);
    }
}
