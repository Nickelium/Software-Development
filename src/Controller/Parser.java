package Controller;

import java.util.List;

import Model.User.Admin;
import Model.User.User;

public class Parser
{
	Parser()
	{
		
	}
	
	public String parseUser(User user)
	{
		String fname = user.getFirstName();
		String mname = user.getMiddleName();
		String lname = user.getLastName();
		String uname = user.getUserName();
		
		String parsed = fname + " " + mname + " " + lname + " : " + uname;
		
		return parsed;
	}
	
	public String parseUserList(List<User> listUser)
	{
		String parsed ="";
		for(User user : listUser)
			parsed += parseUser(user) +"\n";
		return parsed;
		
	}

}
