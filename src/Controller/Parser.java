package Controller;

import Model.BugReport.BugReport;
import Model.BugReport.Comment;
import Model.Project.Project;
import Model.Project.SubSystem;
import Model.User.User;

import java.util.List;

public class Parser
{

	public static String parseUserList(List<User> listUser)
	{
		String parsed ="";
		for(int i=0; i< listUser.size(); i++)
			parsed += i + ":\n" + listUser.get(i).toString() +"\n";
		return parsed;
		
	}
	
	public static String parseProjectList(List<Project> listProject)
	{
		String parsed ="";
		for(int i=0; i< listProject.size(); i++)
			parsed += i + ":\n" + listProject.get(i).toString() +"\n";
		return parsed;
		
	}
	
	public static String parseSubSystemList(List<SubSystem> listSubSystem)
	{
		String parsed ="";
		for(int i=0; i< listSubSystem.size(); i++)
			parsed += i + ":\n" + listSubSystem.get(i).toString() +"\n";
		return parsed;
		
	}

	public static String parseBugReportList(List<BugReport> listBugReport)
	{
		String parsed ="";
		for(int i=0; i< listBugReport.size(); i++)
			parsed += i + ":\n" + listBugReport.get(i).toString() +"\n";
		return parsed;

	}
	
	public static String parseDetailedProject(Project project)
	{
		String parsed = project.toString();
		for(SubSystem subSystem : project.getSubSystems())
			parsed += "\n\t" + Parser.addTabulation(Parser.parseDetailedSubSystem(subSystem));
		return parsed;
	}
	
	public static String parseDetailedSubSystem(SubSystem subSystem)
	{
		if(subSystem == null) return "";
		String parsed = subSystem.toString();
		for(SubSystem sub : subSystem.getSubSystems())
			parsed += "\n\t" + Parser.addTabulation(Parser.parseDetailedSubSystem(sub));
		return parsed;
	}
	
	public static String parseDetailBugReport(BugReport bugReport)
	{
		if(bugReport == null) return "";
		String parsed = bugReport.toString();
		for(Comment comm : bugReport.getComments())
			parsed += "\n\t" + Parser.addTabulation(Parser.parseDetailComment(comm));
		return parsed;
	}
	
	private static String parseDetailComment(Comment comment)
	{
		if(comment == null) return "";
		String parsed = comment.toString();
		for(Comment comm : comment.getComments())
			parsed += "\n\t" + Parser.addTabulation(Parser.parseDetailComment(comm));
		return parsed;
	}
	
	private static String addTabulation(String str)
	{
		return str.replace("\n", "\n\t");
	}

}
