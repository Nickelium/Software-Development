package Controller;

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
	
	public static String parseSubSystemList(List<SubSystem> listSubSystem)
	{
		String parsed ="";
		for(int i=0; i< listSubSystem.size(); i++)
			parsed += i + " " + listSubSystem.get(i).toString() +"\n";
		return parsed;
		
	}
	
	public static String parseDetailedProject(Project project)
	{
		String parsed = project.toString();
		for(SubSystem subSystem : project.getSubSystems())
			parsed += "\n\t" +Parser.parseDetailedSubSystem(subSystem);
		return parsed;
	}
	
	public static String parseDetailedSubSystem(SubSystem subSystem)
	{
		if(subSystem == null) return "";
		String parsed = subSystem.toString();
		for(SubSystem sub : subSystem.getSubSystems())
			parsed += "\n\t " + Parser.parseDetailedSubSystem(sub); 
		return parsed;
	}

}
