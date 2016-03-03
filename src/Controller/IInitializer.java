package Controller;

import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;
import Model.Wrapper.IListWrapper;

public interface IInitializer 
{
	public void init();

	public UserService getUserService();

	public ProjectService getProjectService();

	public BugReportService getBugReportService();

}
