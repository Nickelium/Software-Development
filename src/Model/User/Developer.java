package Model.User;

import CustomExceptions.ReportErrorToUserException;

/**
 * Class extending the Issuer class, representing a Developer user object.
 *
 * Developers are issuers that are employed by the client for which
 * BugTrap is being developed.
 */
public class Developer extends Issuer {

    /**
     * Default constructor for a developer.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @throws ReportErrorToUserException Username is empty.
     * @throws IllegalArgumentException One of the name arguments is null. (use empty string instead)
     */
    Developer(String firstName, String middleName, String lastName, String userName) throws ReportErrorToUserException {
        super(firstName,middleName,lastName,userName);
    }

}
