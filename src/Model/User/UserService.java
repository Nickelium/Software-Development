package Model.User;
import CustomExceptions.ModelException;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;
import com.sun.tools.internal.xjc.generator.bean.field.IsSetFieldRenderer;

import java.util.Collections;
import java.util.List;

/**
 * Created by Karina on 19.02.2016.
 */
public class UserService {

    private IListWrapper<User> userList;

    /**
     * Creates a new UserService with an empty ListWrapper.
     */
    public UserService(){
        this.userList = new ListWrapper<>();
    }
    
    /**
     * Getter to request all the users.
     *
     * @return An list with all users.
     */
    public List<User> getUserList() {
        return Collections.unmodifiableList(userList.getAll());
    }

    //region get a specific group of users

    /**
     * Getter to request all the administrators.
     *
     * @return An unmodifiable list of all administrators.
     */
    public List<User> getAdministrators(){
        return Collections.unmodifiableList((userList.getAllMatching((s) -> s instanceof Admin));
    }

    /**
     * Getter to request all the issuers.
     *
     * @return An unmodifiable list of all issuers.
     */
    public List<User> getIssuers(){
        return Collections.unmodifiableList(userList.getAllMatching((s)->s instanceof Issuer));
    }

    /**
     * Getter to request all the developers.
     *
     * @return An unmodifiable list of all developers.
     */
    public List<User> getDevelopers(){
        return Collections.unmodifiableList(userList.getAllMatching((s)->s instanceof Developer));
    }

    //endregion

    //region add users

    /**
     * Method for adding an admin to the list of users.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The unique username of the user.
     *
     * @return The newly created admin.
     *
     * @throws ModelException The username is not unique or empty.
     */
    public User addAdmin(String firstName, String middleName, String lastName, String userName) throws ModelException{
        if (!isValidUserName(userName)) throw new ModelException("The username already exists.");

        User user = new Admin(firstName, middleName, lastName, userName);

        this.userList.insert(user);

        return user;
    }

    /**
     * Method for adding an issuer to the list of users.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user. (null if user doesn't have one)
     * @param lastName The last name of the user.
     * @param userName The unique username of the user.
     *
     * @return The newly created issuer.
     *
     * @throws ModelException The username is not unique or empty.
     */
    public User addIssuer(String firstName, String middleName, String lastName, String userName) throws ModelException{
        if (!isValidUserName(userName)) throw new ModelException("The username already exists.");

        User user = new Issuer(firstName, middleName, lastName, userName);

        this.userList.insert(user);

        return user;
    }

    /**
     * Method for adding an developer to the list of users.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user. (null if user doesn't have one)
     * @param lastName The last name of the user.
     * @param userName The unique username of the user.
     *
     * @return The newly created developer.
     *
     * @throws ModelException The username is not unique or empty.
     */
    public User addDeveloper(String firstName, String middleName, String lastName, String userName) throws ModelException{
        if (!isValidUserName(userName)) throw new ModelException("The username already exists.");

        User user = new Developer(firstName, middleName, lastName, userName);

        this.userList.insert(user);

        return user;
    }

    /**
     * Checker to check if the given username is unique.
     *
     * @param username The username to check.
     *
     * @return True if the username doesn't exist in the list of users.
     */
    public boolean isValidUserName(String username){
        if (this.userList.getOne(x -> x.getUserName().equals(username)) == null){
            return true;
        } else {
            return false;
        }
    }

    //endregions


    /**
     * Getter to request the user with the given username.
     *
     * @param userName The username of the user to find.
     *
     * @return The user with the given username or null if the user does not exist.
     *
     * @throws ModelException There is no user with the given username.
     */
    public User getUser(String userName) throws ModelException{
        User user = this.userList.getOne((s)->s.getUserName()==userName);

        if (user == null) throw new ModelException("The user does not exist.");
        return user;
    }
}
