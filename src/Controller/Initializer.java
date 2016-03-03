package Controller;

import Model.Project.Project;
import Model.User.*;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;

public class Initializer implements IInitializer
{

	private UserService userService;
	
	@Override
	public void init() 
	{
		/* 1) we gaan hier niet via service :: aanname dat initieel toestand sowieso correct moet zijn.
		   2) meerdere services kunnen dezelfde repository/listwrapper bevatten -> dus eerder initializeren van 
		   		repository/listwrapper dan service + elke controller beslist zelf wat hij als service nodig heeft en dus
		   		zelf aanmaakt -> gewoon repository/listwrapper doorgeven
		*/

		UserService userService = new UserService();

		userService.addAdmin("Frederick","Curtis","curt", "Sam");
		userService.addIssuer("John","","Doctor","doc");
		userService.addIssuer("Charles","Arnold","Berg","charlie");
		userService.addDeveloper("Joseph","","Mays","major");
		userService.addDeveloper("Maria","","Carney","maria");
		
	}

	@Override
	public IListWrapper<Project> getListWrapperProject()
	{
		return listWrapperProject;
	}

	@Override
	public IListWrapper<User> getListWrapperUser()
	{
		return listWrapperUser;
	}

}
