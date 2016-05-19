package Controller.Initializer;

import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.PerformanceMetrics.PerformanceMetricsService;
import Model.BugReport.TagAssignmentService;
import Model.Mail.MailboxService;
import Model.Memento.Caretaker;
import Model.Project.ProjectService;
import Model.User.UserService;

public interface IInitializer 
{
	public String getName();

	public UserService getUserService();

	public ProjectService getProjectService();

	public BugReportService getBugReportService();

	public DeveloperAssignmentService getDeveloperAssignmentService();

	public TagAssignmentService getTagAssignmentService();

	public PerformanceMetricsService getPerformanceMetricsService();
	
	public MailboxService getMailboxService();
	
	public Caretaker getCaretaker();

}
