package Handler;

import Model.User;
import DAL.IRepository;

/**
 * Created by Karina on 19.02.2016.
 */
public class UserHandler {

    private IRepository<User> userList;

    public UserHandler(IRepository<User> userList){
        setUserList(userList);
    }

    /**
     * Returns an IRepository object with all users.
     * @return An IRepository object with all users.
     */
    public IRepository<User> getUserList() {
        return userList;
    }

    /**
     * Sets the IRepository object with all users.
     * @param userList The new IRepository object with users.
     */
    public void setUserList(IRepository<User> userList) {
        this.userList = userList;
    }

    public IRepository<User> getAdministrators(){
        return userList;
    }
}
