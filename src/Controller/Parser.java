package Controller;

import java.util.List;

import Model.User.Admin;
import Model.User.User;

public class Parser
{

	public String parseUserList(List<User> listUser)
	{
		String parsed ="";
		for(User user : listUser)
			parsed += user.toString() +"\n";
		return parsed;
		
	}

}
