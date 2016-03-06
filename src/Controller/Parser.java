package Controller;

import java.util.List;

import Model.Project.Project;
import Model.Project.SubSystem;
import Model.User.Admin;
import Model.User.User;

public class Parser
{

	public static String parseUserList(List<User> listUser)
	{
		String parsed ="";
		for(int i=0; i< listUser.size(); i++)
			parsed += i + " " + listUser.get(i).toString() +"\n";
		return parsed;
		
	}
	
	public static String parseProjectList(List<Project> listProject)
	{
		String parsed ="";
		for(int i=0; i< listProject.size(); i++)
			parsed += i + " " + listProject.get(i).toString() +"\n";
		return parsed;
		
	}
	
	public static String parseDetailedProject(Project project)
	{
		String parsed = project.toString();
		for(SubSystem subSystem : project.getAllSubSystems())
			parsed += subSystem.toString() + "\n";
		return parsed;
	}

}
