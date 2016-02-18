package BLL;

import java.util.List;
import java.util.function.Predicate;
import DAL.IRepository;

public class RepositoryProject implements IRepository<Project>
{
	private List<Project> projects;
	
	/**
	 * Constructoren
	 */
	public RepositoryProject();

	@Override

	public Project getOne(Predicate<Project> criteria) {
		// TODO Auto-generated method stub
		for(Project prj : projects)
			if(criteria.test(prj)) return prj;
		
		return null;
	}

	@Override
	public List<Project> getAllMatching(Predicate<Project> criteria) {
		// TODO Auto-generated method stub		
		return null;
	}

	@Override
	public List<Project> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Project object) throws RuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Project object) throws RuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Project object) throws RuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() throws RuntimeException {
		// TODO Auto-generated method stub
		
	}



}
