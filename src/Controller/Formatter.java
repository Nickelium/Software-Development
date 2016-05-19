package Controller;

import Model.BugReport.BugReport;
import Model.BugReport.Comment;
import Model.BugReport.PerformanceMetrics.IInformationHolder;
import Model.BugReport.PerformanceMetrics.MetricsComponent;
import Model.HealthIndicator.HealthIndicatorA1;
import Model.HealthIndicator.HealthIndicatorA2;
import Model.HealthIndicator.HealthIndicatorA3;
import Model.Mail.Notification;
import Model.Mail.ObserverAspect;
import Model.Memento.Snapshot;
import Model.Milestone.Milestone;
import Model.Project.Project;
import Model.Project.SubSystem;
import Model.Roles.Role;
import Model.User.User;

import java.util.List;

/**
 * This class is used to format a given object from the domainlayer into the corresponding textual representation.
 *
 */
public class Formatter
{

	/**
	 * Format the given list of users into a textual representation.
	 * 
	 * @param listUser The list of users
	 * 
	 * @return The textual representation
	 */
	public static String formatUserList(List<? extends User> listUser)
	{
		String parsed ="";
		for(int i=0; i< listUser.size(); i++)
            parsed += i + ": " + listUser.get(i).toString() + "\n";
        return parsed;
		
	}

	/**
	 * Format the given list of milestones into a textual representation
	 *
	 * @param listMilestone The list of milestones
	 * @return The textual representation
     */
	public static String formatMilestoneList(List<Milestone> listMilestone)
	{
		String parsed ="";
		for(int i=0; i< listMilestone.size(); i++)
			parsed += i + ": " + listMilestone.get(i).toString() + "\n";
		return parsed;

	}
	
	/**
	 * Format the given list of projects into a textual representation.
	 * 
	 * @param listProject The list of projects
	 * 
	 * @return The textual representation
	 */
	public static String formatProjectList(List<Project> listProject)
	{
		String parsed ="";
		for(int i=0; i< listProject.size(); i++)
            parsed += i + ": " + listProject.get(i).toString() + "\n";
        return parsed;
		
	}
	
	/**
	 * Format the given list of subsystem into a textual representation.
	 * 
	 * @param listSubSystem The list of subsystems
	 * 
	 * @return The textual representation
	 */
	public static String formatSubSystemList(List<SubSystem> listSubSystem)
	{
		String parsed ="";
		for(int i=0; i< listSubSystem.size(); i++)
            parsed += i + ": " + listSubSystem.get(i).toString() + "\n";
        return parsed;
		
	}

	/**
	 * Format the given list of bugreports into a textual representation.
	 * 
	 * @param listBugReport The list of bugreports
	 * 
	 * @return The textual representation
	 */
	public static String formatBugReportList(List<BugReport> listBugReport)
	{
		String parsed ="";
		for(int i=0; i< listBugReport.size(); i++)
            parsed += i + ": " + listBugReport.get(i).toString() + "\n";
        return parsed;

	}
	
	/**
	 * Format the given list of comments into a textual representation.
	 * 
	 * @param listComment The list of comments
	 * 
	 * @return The textual representation
	 */
	public static String formatCommentList(List<Comment> listComment)
	{
		String parsed ="";
		for(int i=0; i< listComment.size(); i++)
            parsed += i + ": " + listComment.get(i).toString() + "\n";
        return parsed;
	}
	
	/**
	 * Format the given list of roles into a textual representation.
	 * 
	 * @param list The list of roles to format
	 * 
	 * @return The textual representation
	 */
    public static String formatProjectRoles(List<Class<? extends Role>> list) {
        String parsed = "";
        for (int i = 0; i < list.size(); i++) {
            parsed += i + ": " + list.get(i).getSimpleName() + "\n";
        }
        return parsed;
	}
    
    public static String formatSnapshots(List<Snapshot> list)
    {
    	String parsed = "";
        for (int i = 0; i < list.size(); i++) 
            parsed += i + ": " + list.get(i).toString() + "\n";
        
        return parsed;
    }
    
	/**
	 * Format the given project into a recursive textual representation.
	 * 
	 * @param project The project to format
	 * 
	 * @return The textual representation
	 */
	public static String formatDetailedProject(Project project)
	{
		if(project == null) return "";
		String parsed = project.toString();
		parsed += "\n* Health Indicator Algorithm 1: " + new HealthIndicatorA1().get(project);
		parsed += "\n* Health Indicator Algorithm 2: " + new HealthIndicatorA2().get(project);
		parsed += "\n* Health Indicator Algorithm 3: " + new HealthIndicatorA3().get(project);
		for(SubSystem subSystem : project.getSubSystems())
			parsed += "\n\t" + Formatter.addTabulation(Formatter.formatDetailedSubSystem(subSystem));
		return parsed;
	}
	
	/**
	 * Format the given subsystem into a recursive textual representation.
	 * 
	 * @param subSystem The subsystem to format
	 * 
	 * @return The textual representation
	 */
	public static String formatDetailedSubSystem(SubSystem subSystem)
	{
		if(subSystem == null) return "";
		String parsed = subSystem.toString();
		parsed += "\n* Health Indicator Algorithm 1: " + new HealthIndicatorA1().get(subSystem);
		parsed += "\n* Health Indicator Algorithm 2: " + new HealthIndicatorA2().get(subSystem);
		parsed += "\n* Health Indicator Algorithm 3: " + new HealthIndicatorA3().get(subSystem);
		for(SubSystem sub : subSystem.getSubSystems())
			parsed += "\n\t" + Formatter.addTabulation(Formatter.formatDetailedSubSystem(sub));
		return parsed;
	}

	/**
	 * Format the given bug report into a recursive textual representation.
	 * 
	 * @param bugReport The bug report to format
	 * 
	 * @return The textual representation
	 */
	public static String formatDetailedBugReport(BugReport bugReport)
	{
		if(bugReport == null) return "";
		String parsed = bugReport.toString();
		for(Comment comm : bugReport.getComments())
			parsed += "\n\t" + Formatter.addTabulation(Formatter.formatDetailedComment(comm));
        return parsed;
    }
	
	private static String formatDetailedComment(Comment comment)
	{
		if(comment == null) return "";
		String parsed = comment.toString();
		for(Comment comm : comment.getComments())
			parsed += "\n\t" + Formatter.addTabulation(Formatter.formatDetailedComment(comm));
		return parsed;
	}
	
	public static String formatNotificationList(List<Notification> notifications) 
	{
		String parsed ="";
		for(int i=0; i< notifications.size(); i++)
            parsed += i + ": \n" + notifications.get(i).toString() + "\n";
        return parsed;
	}

	public static String formatRegistrationList(List<ObserverAspect> registrations) {
		String parsed ="";
		for(int i=0; i< registrations.size(); i++)
            parsed += i + ": \n" + registrations.get(i).toString() + "\n";
        return parsed;
	}

	public static String formatPatches(BugReport bugReport) {
		if(bugReport == null) return "";
		String parsed = "";
		for (int i = 0; i < bugReport.getPatches().size(); i++) {
			parsed += i + ": " + bugReport.getPatches().get(i) + "\n";
		}
		return parsed;
	}
	
	private static String addTabulation(String str)
	{
		return str.replace("\n", "\n\t");
	}

	public static String formatPerformanceMetrics(List<MetricsComponent> performanceMetrics) {
		String s = "";

		for (MetricsComponent metrics : performanceMetrics) {
			s += metrics.getTitle() + ":\n";
			for (IInformationHolder information : metrics.getInformation()) {
				s += "  - " + information + "\n";
			}
		}

		return s;
	}

}
