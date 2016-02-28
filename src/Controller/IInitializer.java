package Controller;

import Model.Project.Project;
import Model.User.User;
import Model.Wrapper.IListWrapper;

public interface IInitializer 
{
	public void init();
	
	public IListWrapper<Project> getListWrapperProject();
	
	public IListWrapper<User> getListWrapperUser();
	
}
