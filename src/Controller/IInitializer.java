package Controller;

import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.UserService;

public interface IInitializer 
{
	public void init();

	public UserService getUserService();

	public ProjectService getProjectService();

	public BugReportService getBugReportService();

}
