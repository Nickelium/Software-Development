package Controller;

import Model.Project.Project;
import Model.User.Admin;
import Model.User.Developer;
import Model.User.Issuer;
import Model.User.User;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;

public class Initializer implements IInitializer
{

	private IListWrapper<Project> listWrapperProject;
	private IListWrapper<User> listWrapperUser;
	
	@Override
	public void init() 
	{
		/* 1) we gaan hier niet via service :: aanname dat initieel toestand sowieso correct moet zijn.
		   2) meerdere services kunnen dezelfde repository/listwrapper bevatten -> dus eerder initializeren van 
		   		repository/listwrapper dan service + elke controller beslist zelf wat hij als service nodig heeft en dus
		   		zelf aanmaakt -> gewoon repository/listwrapper doorgeven
		*/
		listWrapperUser = new ListWrapper<>();
		listWrapperUser.insert( new Admin("Frederick","Curtis","curt", "Sam"));
		listWrapperUser.insert( new Issuer("John","","Doctor","doc"));
		listWrapperUser.insert( new Issuer("Charles","Arnold","Berg","charlie"));
		listWrapperUser.insert( new Developer("Joseph","","Mays","major"));
		listWrapperUser.insert( new Developer("Maria","","Carney","maria"));
		
		//TODO 
		
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
