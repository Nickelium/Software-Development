package Model.User;
import DAL.IListWrapper;
import DAL.ListWrapper;

import java.util.Collections;
import java.util.List;

/**
 * Created by Karina on 19.02.2016.
 */
public abstract class UserService {

    private IListWrapper<User> userList;

    /**
     * Creates a new UserService with an empty ListWrapper.
     */
    public UserService(){
        IListWrapper<User> userList = new ListWrapper<User>();
        setListWrapper(userList);
    }

    /**
     * Returns an list with all users.
     * @return An list with all users.
     */
    public List<User> getUserList() {
        return Collections.unmodifiableList(userList.getAll());
    }

    /**
     * Sets the IListWrapper object with all users.
     * @param userList The new IListWrapper object with users.
     */
    private void setListWrapper(IListWrapper<User> userList) {
        this.userList = userList;
    }

    /**
     * Prompts a query to the User List returning all users being an instance of Admin
     * @return A list object with all administrators saved in the User List.
     */
    public List<User> getAdministrators(){
        return Collections.unmodifiableList(userList.getAllMatching((s)->s instanceof Admin));
    }

    /**
     * Prompts a query to the User List returning all users being an instance of issuer
     * @return A list object with all issuers saved in the UserRepository
     */
    public List<User> getIssuers(){
        return Collections.unmodifiableList(userList.getAllMatching((s)->s instanceof Issuer));
    }

    /**
     * Prompts a query to the User List returning all users being an instance of Developer
     * @return A list object with all developers saved in the UserRepository
     */
    public List<User> getDeveloper(){
        return Collections.unmodifiableList(userList.getAllMatching((s)->s instanceof Developer));
    }


    /**
     * Adds a new user to the user userList if there doesn't exist a user object with
     * the same username in the User List yet.
     *
     * @throws IllegalArgumentException
     * Throws an IllegalArgumentException in case there already exists a User object
     * in the User Repository with the same username.
     *
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     * @param middleName The middle name of the user.
     *
     * @return The newly created user.
     */
    public User addUser(String firstName, String lastName, String userName, String middleName){
        User user = new User(firstName,lastName,userName,middleName);
        if (!userList.getAllMatching((s)->user.getUserName() == userName).isEmpty()){
            throw new IllegalArgumentException("This user already exists. Choose another user name.");
        } else {
            userList.insert(user);
            return user;
        }
    }


    /**
     * Adds a new user to the user userList if there doesn't exist a user object with
     * the same username in the User List yet.
     *
     * @throws IllegalArgumentException
     * Throws an IllegalArgumentException in case there already exists a User object
     * in the User Repository with the same username.
     *
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @return The newly created user.
     */
    public User addUser(String firstName, String lastName, String userName){
        User user = new User(firstName,lastName,userName);
        if (!userList.getAllMatching((s)->user.getUserName() == userName).isEmpty()){
            throw new IllegalArgumentException("This user already exists. Choose another user name.");
        } else {
            userList.insert(user);
            return user;
        }
    }


    /**
     * Deletes a given user object from the user userList by calling a delete
     * statement of the userList.
     *
     * @param user the user object that needs to be deleted from the user userList.
     */
    public void deleteUser(User user){
        userList.delete(user);
    }

    /**
     * Returns a user object from the user userList defined by the unique user name.
     *
     * @param userName the unique username of which we want to retrieve the user object from the user userList
     * @return the user object from the user userList with given username.
     */
    public User getUser(String userName){
        return userList.getOne((s)->s.getUserName()==userName);
    }
}
