package Model.User;

import CustomExceptions.ModelException;
import com.sun.tools.internal.ws.processor.model.Model;

/**
 * Created by Karina on 19.02.2016.
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
     * @throws ModelException One of the given arguments is empty.
     */
    Developer(String firstName, String middleName, String lastName, String userName) throws ModelException{
        super(firstName,middleName,lastName,userName);
    }

    /**
     * Default constructor for a developer without middle name.
     *
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param userName The user name of the user.
     *
     * @throws ModelException One of the given arguments is empty.
     */
    Developer(String firstName, String lastName, String userName) throws ModelException{
        super(firstName,lastName,userName);
    }

}
