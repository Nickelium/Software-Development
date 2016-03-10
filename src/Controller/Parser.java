package Controller;

import Model.BugReport.BugReport;
import Model.BugReport.Comment;
import Model.Project.Project;
import Model.Project.SubSystem;
import Model.Roles.Role;
import Model.User.Developer;
import Model.User.User;

import java.util.ArrayList;
import java.util.List;

public class Parser
{

	public static String parseUserList(List<User> listUser)
	{
		String parsed ="";
		for(int i=0; i< listUser.size(); i++)
            parsed += i + ": " + listUser.get(i).toString() + "\n";
        return parsed;
		
	}

	public static String parseDeveloperList(List<Developer> listDeveloper)
	{
		String parsed ="";
		for(int i=0; i< listDeveloper.size(); i++)
            parsed += i + ": " + listDeveloper.get(i).toString() + "\n";
        return parsed;

	}

	public static String parseSearchMethods(){
		List<String> listMethods = new ArrayList<String>();
		listMethods.add(0,"Search for bug reports with a specific string in the title or description");
		listMethods.add(1,"Search for bug reports filed by some specific user");
		listMethods.add(2,"Search for bug reports assigned to specific user");
		String parsed ="";
		for(int i=0; i< listMethods.size(); i++)
			parsed += i + ": " + listMethods.get(i).toString() +"\n";
		return parsed;
	}
	
	public static String parseProjectList(List<Project> listProject)
	{
		String parsed ="";
		for(int i=0; i< listProject.size(); i++)
            parsed += i + ": " + listProject.get(i).toString() + "\n";
        return parsed;
		
	}
	
	public static String parseSubSystemList(List<SubSystem> listSubSystem)
	{
		String parsed ="";
		for(int i=0; i< listSubSystem.size(); i++)
            parsed += i + ": " + listSubSystem.get(i).toString() + "\n";
        return parsed;
		
	}


	// TODO: List van roles opvragen implementeren
	/*
	public static String parseRoleList()
	{

		String parsed ="";
		for(int i=0; i< listRole.size(); i++)
			parsed += i + ":\n" + listRole.get(i).toString() +"\n";
		return parsed;

	}
	*/

	public static String parseBugReportList(List<BugReport> listBugReport)
	{
		String parsed ="";
		for(int i=0; i< listBugReport.size(); i++)
            parsed += i + ": " + listBugReport.get(i).toString() + "\n";
        return parsed;

	}
	
	public static String parseCommentList(List<Comment> listComment)
	{
		String parsed ="";
		for(int i=0; i< listComment.size(); i++)
            parsed += i + ": " + listComment.get(i).toString() + "\n";
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

    public static String parseProjectRoles(List<Class<? extends Role>> list) {
        String parsed = "";
        for (int i = 0; i < list.size(); i++) {
            parsed += i + ": " + list.get(i).getSimpleName() + "\n";
        }
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
