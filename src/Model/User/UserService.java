package Model.User;
import java.util.Collections;
import java.util.List;
import DAL.IRepository;

import java.util.Collection;

/**
 * Created by Karina on 19.02.2016.
 */
public class UserService {

    private IRepository<User> repository;

    public UserService(IRepository<User> userList){
        setUserList(userList);
    }

    /**
     * Returns an IRepository object with all users.
     * @return An IRepository object with all users.
     */
    public IRepository<User> getUserList() {
        return repository;
    }

    /**
     * Sets the IRepository object with all users.
     * @param userList The new IRepository object with users.
     */
    private void setUserList(IRepository<User> userList) {
        this.repository = userList;
    }

    /**
     * Prompts a query to the UserRepository returning all users being an instance of Admin
     * @return A list object with all administrators saved in the UserRepository
     */
    public List<User> getAdministrators(){
        return Collections.unmodifiableList(repository.getAllMatching((s)->s instanceof Admin));
    }

    /**
     * Prompts a query to the UserRepository returning all users being an instance of issuer
     * @return A list object with all issuers saved in the UserRepository
     */
    public List<User> getIssuers(){
        return Collections.unmodifiableList(repository.getAllMatching((s)->s instanceof Issuer));
    }

    /**
     * Prompts a query to the UserRepository returning all users being an instance of Developer
     * @return A list object with all developers saved in the UserRepository
     */
    public List<User> getDeveloper(){
        return Collections.unmodifiableList(repository.getAllMatching((s)->s instanceof Developer));
    }

}
