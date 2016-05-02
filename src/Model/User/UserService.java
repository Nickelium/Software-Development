package Model.User;
import CustomExceptions.ReportErrorToUserException;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;

import java.util.Collections;
import java.util.List;

/**
 * Class containing services for users.
 *
 * Contains methods to create users of any kind.
 * Also contains methods to return lists of all users of a specific kind,
 * a method to get a user object by its user name,
 * as well as a method to get all users of any kind.
 *
 * This class also deals with the constraint that every user has to be unique:
 * isValidUserName has the responsibility to check whether a specific username
 * is already in use.
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
     * Getter to request all users of any kind.
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
        return Collections.unmodifiableList(userList.getAllMatching((s) -> s instanceof Admin));
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
     * @param userName The unique user name of the user.
     *
     * @return The newly created admin.
     *
     * @throws ReportErrorToUserException is thrown when the user name is not unique or empty.
     */
    public Admin createAdmin(String firstName, String middleName, String lastName, String userName) throws ReportErrorToUserException {
        if (!isValidUserName(userName)) throw new ReportErrorToUserException("The username already exists.");

        User user = new Admin(firstName, middleName, lastName, userName);

        this.userList.insert(user);
        return (Admin) user;
    }

    /**
     * Method for adding an issuer to the list of users.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user. (null if user doesn't have one)
     * @param lastName The last name of the user.
     * @param userName The unique user name of the user.
     *
     * @return The newly created issuer.
     *
     * @throws ReportErrorToUserException is thrown when the user name is not unique or empty.
     */
    public Issuer createIssuer(String firstName, String middleName, String lastName, String userName) throws ReportErrorToUserException {
        if (!isValidUserName(userName)) throw new ReportErrorToUserException("The username already exists.");

        User user = new Issuer(firstName, middleName, lastName, userName);

        this.userList.insert(user);
        return (Issuer) user;
    }

    /**
     * Method for adding an developer to the list of users.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user. (null if user doesn't have one)
     * @param lastName The last name of the user.
     * @param userName The unique user name of the user.
     *
     * @return The newly created developer.
     *
     * @throws ReportErrorToUserException is thrown when the user name is not unique or empty.
     */
    public Developer createDeveloper(String firstName, String middleName, String lastName, String userName) throws ReportErrorToUserException {
        if (!isValidUserName(userName)) throw new ReportErrorToUserException("The user name already exists.");

        User user = new Developer(firstName, middleName, lastName, userName);

        this.userList.insert(user);
        return (Developer) user;
    }

    /**
     * Checker to check if the given user name is unique.
     *
     * @param userName The username to check.
     *
     * @return True if the username doesn't already exist in the list of users.
     *         False if otherwise.
     */
    public boolean isValidUserName(String userName){
        if (this.userList.getOne(x -> x.getUserName().equals(userName)) == null){
            return true;
        } else {
            return false;
        }
    }

    //endregion

    /**
     * Getter to request the user with the given user name.
     *
     * @param userName The user name of the user to find.
     * @return The user with the given user name.
     * @throws ReportErrorToUserException is thrown when there is no user with the given user name.
     */
    public User getUser(String userName) throws ReportErrorToUserException {
        User user = this.userList.getOne((s)->s.getUserName().equals(userName));

        if (user == null) throw new ReportErrorToUserException("The user does not exist.");
        return user;
    }


}
