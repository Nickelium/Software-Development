package Model.Wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Tom on 20/02/16.
 */
public class ListWrapper<T> implements IListWrapper<T> {

    private List<T> list;

    public ListWrapper()
    {
    	list = new ArrayList<>();
    }
    
    public ListWrapper(List<T> list)
    {
          this.list = list;
    }
    
    @Override
    public T getOne(Predicate<T> criteria) {
        for (T obj : this.list){
            if (criteria.test(obj)) {
                return obj;
            }
        }
        return null;
    }

    @Override
    public List<T> getAllMatching(Predicate<T> criteria) {
        List<T> filtered = this.list.stream().filter(criteria).collect(Collectors.toList());
        return Collections.unmodifiableList(filtered);
    }

    @Override
    public List<T> getAll() {
        return Collections.unmodifiableList(this.list);
    }

    @Override
    public void insert(T object) {
        this.list.add(object);
    }

    @Override
    public void delete(T object) {
        this.list.remove(object);
    }

    @Override
    public void deleteAll(){
        this.list.clear();
    }
}

