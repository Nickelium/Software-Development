package Controller;

import CustomExceptions.ModelException;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.Project.ProjectService;
import Model.User.UserService;

public interface IInitializer 
{
	public void init() throws ModelException;

	public UserService getUserService();

	public ProjectService getProjectService();

	public BugReportService getBugReportService();

	public DeveloperAssignmentService getDeveloperAssignmentService();

	public TagAssignmentService getTagAssignmentService();

}
