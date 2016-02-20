package DAL;

import DAL.DeepCopy.DeepCopy;
import DAL.IRepository;
import Model.Project;
import Model.User;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Tom on 20/02/16.
 */
public class UserRepository implements IRepository<User>
{
    private static List<User> users;

    @Override
    public User getOne(Predicate<User> criteria) {
        for (User user : users){
            if (criteria.test(user)){
                return (User) DeepCopy.copy(user);
            }
        }
        return null;
    }

    @Override
    public List<User> getAllMatching(Predicate<User> criteria) {
        List<User> filtered = users.stream().filter(criteria).collect(Collectors.toList());

        return (List<User>) DeepCopy.copy(filtered);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) DeepCopy.copy(users);
    }

    @Override
    public void insert(User object) {
        users.add(object);
    }

    @Override
    public void update(Predicate<User> criteria, User object){
        User toUpdate = this.getOne(criteria);
        int index = this.users.indexOf(toUpdate);
        this.users.remove(index);
        this.users.add(index, object);
    }

    @Override
    public void delete(User object) {
        users.remove(object);
    }

    @Override
    public void deleteAll(){
        users.clear();
    }
}
