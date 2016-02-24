package DAL;

import DAL.DeepCopy.DeepCopy;
import Model.User.User;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Tom on 20/02/16.
 */
public class UserRepository implements IRepository<User>
{
    private static List<User> users;

    /**
     * Returns the first User Object satisfying a set of criteria.
     * @param criteria The criteria on which to filter the objects.
     * @return the first User Object satisfying the criteria specified in parameter criteria.
     */
    @Override
    public User getOne(Predicate<User> criteria) {
        for (User user : users){
            if (criteria.test(user)){
                return (User) DeepCopy.copy(user);
            }
        }
        return null;
    }

    /**
     * Returns a list of all User Objects satisfying a set of criteria
     * @param criteria The criteria on which to filter the objects.
     * @return a list of all User Objects satisfying the criteria specified in parameter criteria.
     */
    @Override
    public List<User> getAllMatching(Predicate<User> criteria) {
        List<User> filtered = users.stream().filter(criteria).collect(Collectors.toList());

        return (List<User>) DeepCopy.copy(filtered);
    }

    /**
     * Returns a list of all User Objects saved in the User Repository, with no criteria specified.
     * @return a list of all User Objects saved in the User Repository.
     */
    @Override
    public List<User> getAll() {
        return (List<User>) DeepCopy.copy(users);
    }

    /**
     * Inserts a User Object into the User Repository.
     * @param object The User Object to be added to the User Repository.
     */
    @Override
    public void insert(User object) {
        users.add(object);
    }

    /**
     * Updates a User Object identified by criteria based off the Username of the object.
     * The User Object satisfying the criteria in the repository will be removed,
     * and a new object will be added in place at the same index of the old user object.
     *
     * Note: this method has been programmed by nominal programming:
     * Checking the criteria needs to be done in the UserService class.
     *
     * @param criteria The criteria on which to select te object to update.
     * @param object The user object replacing the object satisfying the criteria
     */
    @Override
    public void update(Predicate<User> criteria, User object){
        User toUpdate = this.getOne(criteria);
        int index = this.users.indexOf(toUpdate);
        this.users.remove(index);
        this.users.add(index, object);
    }

    /**
     * Deletes the repository instance of the user object given by the parameter
     * from the user repository.
     * @param object The user object to be deleted from the User Repository.
     */
    @Override
    public void delete(User object) {
        users.remove(object);
    }

    /**
     * Deletes all instances of user objects from the user repository.
     */
    @Override
    public void deleteAll(){
        users.clear();
    }
}
