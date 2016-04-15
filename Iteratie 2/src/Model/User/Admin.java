package Model.User;

import CustomExceptions.ReportErrorToUserException;

/**
 * Class extending the User class, representing an Administrator user object.
 *
 * An administrator is a user that can set up the configuration of BugTrap.
 */
public class Admin extends User {

    /**
     * Default constructor for an admin.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @throws ReportErrorToUserException Username is empty.
     * @throws IllegalArgumentException One of the name arguments is null. (use empty string instead)
     */
     Admin(String firstName, String middleName, String lastName, String userName) throws ReportErrorToUserException {
        super(firstName,middleName,lastName,userName);
    }

}
