package DAL;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import DAL.DeepCopy.DeepCopy;
import DAL.IRepository;
import Model.Project;

public class ProjectRepository implements IRepository<Project>
{
	private static List<Project> projects;

	@Override
	public Project getOne(Predicate<Project> criteria) {
		for (Project project : projects){
            if (criteria.test(project)){
                return (Project) DeepCopy.copy(project);
            }
        }
        return null;
	}

	@Override
	public List<Project> getAllMatching(Predicate<Project> criteria) {
		List<Project> filtered = projects.stream().filter(criteria).collect(Collectors.toList());

		return (List<Project>) DeepCopy.copy(filtered);
	}

	@Override
	public List<Project> getAll() {
		return (List<Project>) DeepCopy.copy(projects);
	}

	@Override
	public void insert(Project object) {
        projects.add(object);
	}

    @Override
    public void update(Predicate<Project> criteria, Project object){
		Project toUpdate = this.getOne(criteria);
		int index = this.projects.indexOf(toUpdate);
		this.projects.remove(index);
		this.projects.add(index, object);
    }

	@Override
	public void delete(Project object) {
		projects.remove(object);
	}

	@Override
	public void deleteAll(){
		projects.clear();
	}
}
