package DAL;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import DAL.IRepository;
import Model.Project;

public class RepositoryProject implements IRepository<Project>
{
	private static List<Project> projects;

	@Override
	public Project getOne(Predicate<Project> criteria) {
		for (Project project : projects){
            if (criteria.test(project)){
                return project;
            }
        }
        return null;
	}

	@Override
	public List<Project> getAllMatching(Predicate<Project> criteria) {
		return projects.stream().filter(criteria).collect(Collectors.toList());
	}

	@Override
	public List<Project> getAll() {
		return new ArrayList<Project>(projects);
	}

	@Override
	public void insert(Project object) throws RuntimeException {
        if (!projects.add(object))
            throw new RuntimeException();
	}

    @Override
    public void update(Project object) throws RuntimeException {
        if (!projects.contains(object))
        {
            throw new RuntimeException();
        }
        else
        {
        	//
        }
    }

	@Override
	public void delete(Project object) throws RuntimeException {
		if (!projects.remove(object))
		{
            throw new RuntimeException();
        }
		
	}

	@Override
	public void deleteAll() throws RuntimeException {
		projects.clear();
	}



}
