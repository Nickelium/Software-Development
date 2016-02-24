package DAL;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import DAL.DeepCopy.DeepCopy;
import Model.Project.Project;
import Model.Project.SubSystem;
import DAL.IRepository;

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
	
	public SubSystem getOneSubSystem(Predicate<SubSystem> criteria) 
	{
		List<SubSystem> subsystems = getAllSubSystems();
		
		for (SubSystem subsystem : subsystems)
            if (criteria.test(subsystem))
                return (SubSystem) DeepCopy.copy(subsystem);
            
        return null;
	}
	
	public List<SubSystem> getAllSubSystems()
	{
		List<SubSystem> list = new ArrayList<>();
		for(Project p : projects)
			list.addAll(p.getAllSubSystem());
		
		return list;		
	}
}
