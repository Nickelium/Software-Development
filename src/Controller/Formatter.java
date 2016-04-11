package Controller;

import Model.BugReport.BugReport;
import Model.BugReport.Comment;
import Model.Mail.Notification;
import Model.Mail.ObserverAspect;
import Model.Milestone.Milestone;
import Model.Project.Project;
import Model.Project.SubSystem;
import Model.Roles.Role;
import Model.User.Developer;
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
	public static String formatUserList(List<User> listUser)
	{
		String parsed ="";
		for(int i=0; i< listUser.size(); i++)
            parsed += i + ": " + listUser.get(i).toString() + "\n";
        return parsed;
		
	}

	public static String formatMilestoneList(List<Milestone> listMilestone)
	{
		String parsed ="";
		for(int i=0; i< listMilestone.size(); i++)
			parsed += i + ": " + listMilestone.get(i).getMilestoneID() + "\n";
		return parsed;

	}

	/**
	 * Format the given list of developers into a textual representation.
	 * 
	 * @param listDeveloper The list of developers
	 * 
	 * @return The textual representation
	 */
	public static String formatDeveloperList(List<Developer> listDeveloper)
	{
		String parsed ="";
		for(int i=0; i< listDeveloper.size(); i++)
            parsed += i + ": " + listDeveloper.get(i).toString() + "\n";
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
    
	/**
	 * Format the given project into a recursive textual representation.
	 * 
	 * @param project The project to format
	 * 
	 * @return The textual representation
	 */
	public static String formatDetailedProject(Project project)
	{
		String parsed = project.toString();
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
		for(SubSystem sub : subSystem.getSubSystems())
			parsed += "\n\t" + Formatter.addTabulation(Formatter.formatDetailedSubSystem(sub));
		return parsed;
	}

	/**
	 * Format the given bugreport into a recursive textual representation.
	 * 
	 * @param bugReport The bugreport to format
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
	
	private static String addTabulation(String str)
	{
		return str.replace("\n", "\n\t");
	}

}
