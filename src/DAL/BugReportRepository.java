package DAL;

import DAL.DeepCopy.DeepCopy;
import Model.BugReport.BugReport;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Tom on 20/02/16.
 */
public class BugReportRepository implements IRepository<BugReport> {

    private static List<BugReport> bugreports;

    @Override
    public BugReport getOne(Predicate<BugReport> criteria) {
        for (BugReport bugreport : bugreports){
            if (criteria.test(bugreport)){
                return (BugReport) DeepCopy.copy(bugreport);
            }
        }
        return null;
    }

    @Override
    public List<BugReport> getAllMatching(Predicate<BugReport> criteria) {
        List<BugReport> filtered = bugreports.stream().filter(criteria).collect(Collectors.toList());

        return (List<BugReport>) DeepCopy.copy(filtered);
    }

    @Override
    public List<BugReport> getAll() {
        return (List<BugReport>) DeepCopy.copy(bugreports);
    }

    @Override
    public void insert(BugReport object) {
        bugreports.add(object);
    }

    @Override
    public void update(Predicate<BugReport> criteria, BugReport object){
        BugReport toUpdate = this.getOne(criteria);
        int index = this.bugreports.indexOf(toUpdate);
        this.bugreports.remove(index);
        this.bugreports.add(index, object);
    }

    @Override
    public void delete(BugReport object) {
        bugreports.remove(object);
    }

    @Override
    public void deleteAll(){
        bugreports.clear();
    }
}

