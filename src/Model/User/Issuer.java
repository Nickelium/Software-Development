package Model.User;

import CustomExceptions.ReportErrorToUserException;

/**
 * Class extending the User class, representing an Issuer user object.
 *
 * An issuer is a user that can publish new bug reports.
 */
public class Issuer extends User {

    /**
     * Default constructor for an issuer.
     *
     * @param firstName The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @throws ReportErrorToUserException Username is empty.
     * @throws IllegalArgumentException One of the name arguments is null. (use empty string instead)
     */
    Issuer(String firstName, String middleName, String lastName, String userName) throws ReportErrorToUserException {
        super(firstName,middleName,lastName,userName);
    }

}
