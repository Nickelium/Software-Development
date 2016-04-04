package Controller;

import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.Project.ProjectService;
import Model.User.UserService;
import Model.Mail.*;

public interface IInitializer 
{

	public UserService getUserService();

	public ProjectService getProjectService();

	public BugReportService getBugReportService();

	public DeveloperAssignmentService getDeveloperAssignmentService();

	public TagAssignmentService getTagAssignmentService();
	
	public MailboxService getMailboxService();

}
