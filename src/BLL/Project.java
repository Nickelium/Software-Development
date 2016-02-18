package BLL;
import java.util.Date;
import java.util.List;

public class Project 
{

	private String name;
	private String description;
	private Date startingDate;
	private double budget;
	
	private List<SubSystem> subSystems;
	
	//developer todo later
	
	/**
	 * Constructoren
	 */
	public Project();
	
	/**
	 * Getters
	 * @return
	 */
	public String getName();
	
	public String getDescription();
	
	public Date getStartingDate();
	
	public double getBudget();
	
	public List<SubSystem> getSubSystems();
	
	/**
	 * Setters
	 */
	public void setName(String newName);
	
	public void setDescription(String newDescription);
	
	public void setStartingDate(Date newStartingDate);
	
	public void setBudget();
	
	/**
	 * Operations
	 */
	public void addSubSystem();
	
	public List<SubSytem> getAllSubSystem();
	
	
	/**
	 * Destructor
	 */
	public void destructor();
	
	
	
}
