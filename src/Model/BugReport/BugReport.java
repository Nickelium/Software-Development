package Model.BugReport;

import CustomExceptions.ModelException;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Tags.Assigned;
import Model.Tags.New;
import Model.Tags.Tag;
import Model.User.Developer;
import Model.User.Issuer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tom on 19/02/16.
 */
public class BugReport {

    //region Attributes

    private BugReportID id;
    private String title;
    private String description;
    private TheDate creationDate;
    private Tag tag;
    private Issuer creator;
    private List<Developer> assignees;
    private List<Comment> comments;
    private List<BugReport> dependencies;

    //endregion

    //region Constructor

    /**
     * Default constructor for a BugReport.
     *
     * @param title The title of the BugReport.
     * @param description The description of the BugReport.
     * @param subSystem The subsystem the bugreport is about.
     * @param creator The issuer of the bugreport.
     *
     * @throws ModelException The title or description is empty.
     * @throws IllegalArgumentException The subsystem or creator is null.
     */
    BugReport(String title, String description, SubSystem subSystem, Issuer creator) throws ModelException
    {
        this(title,description,subSystem, creator, TheDate.TheDateNow(), new New(), new ArrayList<>());
    }
    
    /**
     * Constructor for a Bugreport.
     *
     * @param title The title of the bugreport.
     * @param description The description of the bugreport.
     * @param subSystem The subsystem the bugreport is about.
     * @param creator The creator of the bugreport.
     *
     * @throws ModelException The title or description is empty.
     * @throws IllegalArgumentException The subsystem, creator, creationDate or tag is null.
     */
    BugReport(String title, String description, SubSystem subSystem, Issuer creator, TheDate creationDate, Tag tag, List<Developer> initialAssignies) throws ModelException
    {
         if (!isValidTitle(title)) throw new ModelException("The title cannot be empty!");
         if (!isValidDescription(description)) throw new ModelException("The description cannot be empty!") ;
         if (subSystem == null) throw new IllegalArgumentException("Subsystem is null");
         if (creator == null) throw new IllegalArgumentException("The issuer is null");
         if (creationDate == null) throw new IllegalArgumentException("CreationDate is null");
         if (tag == null) throw new IllegalArgumentException("Tag is null");
         if (initialAssignies == null) throw new IllegalArgumentException("List cannot be null");

         this.title = title;
         this.description = description;
         this.creator = creator;
         this.creationDate = creationDate;
         this.tag = tag;

         //If tag is set to new, this code checks if there aren't any assignees to the bugreport. If so
         // the tag is changed to an assigned tag.
         if (tag.getClass().equals(New.class) && !initialAssignies.isEmpty()) this.tag = new Assigned();

         this.id = new BugReportID();
         subSystem.addBugReport(this);

         this.comments = new ArrayList<>();
         this.dependencies = new ArrayList<>();
         this.assignees = initialAssignies;

    }

    //endregion

    //region Getters

    /**
     * Getter to request the unique id of the bugreport.
     *
     * @return The unique id of the bugreport.
     */
    public BugReportID getId(){
        return this.id;
    }

    /**
     * Getter to request the title of the bugreport.
     *
     * @return The title of the bugreport.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Getter to request the description of the bugreport.
     *
     * @return The description of the bugreport.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter to request the creationDate of the bugreport.
     *
     * @return The creationDate of the bugreport.
     */
    public TheDate getCreationDate() {
        return this.creationDate;
    }

    /**
     * Getter to request the creator of the bugreport.
     *
     * @return The creator of the bugreport.
     */
    public Issuer getCreator(){
        return this.creator;
    }

    /**
     * Getter to request the tag of the bugreport.
     *
     * @return The tag of the bugreport.
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * Getter to request the developers assigned to the bugreport.
     *
     * @return The list of developers assigned to the bugreport
     */
    public List<Developer> getAssignees() {
        return Collections.unmodifiableList(this.assignees);
    }

    /**
     * Getter to request the comments given on the bugreport.
     *
     * @return The list of comments given on the bugreport.
     */
    public List<Comment> getComments() {
        return Collections.unmodifiableList(this.comments);
    }

    /**
     * Getter to request the dependencies of the bugreport.
     *
     * @return The list of dependencies of the bugreport.
     */
    public List<BugReport> getDependencies(){return Collections.unmodifiableList(this.dependencies);}

    //endregion

    //region Checkers

    /**
     * Checker to check if the title of the bugreport is valid.
     *
     * @param title The title of the bugreport.
     *
     * @return True if the title is not null or not empty. False otherwise.
     */
    private boolean isValidTitle(String title){
        if (title == null)return false;
        if (title.equals("")) return false;
        else return true;
    }

    /**
     * Checker to check if the description of the bugreport is valid.
     *
     * @param description The description of the bugreport.
     *
     * @return True if the description is not null or not empty. False otherwise.
     */
    private boolean isValidDescription(String description){
        if (description == null) return false;
        if (description.equals("")) return false;
        else return true;
    }

    //endregion

    //region Setters

    /**
     * Setter to change the tag of the bugreport.
     *
     * @param tag The tag to which to set the bugreport.
     *
     * @throws IllegalArgumentException The tag given is null.
     */
    void setTag(Tag tag){
        if (tag == null) throw new IllegalArgumentException("Tag is null");
        this.tag = tag;
    }

    //endregion

    //region Functions

    /**
     * Function for adding an assignee to the bugreport.
     *
     * @param developer The developer to assign to the bugreport.
     *
     * @throws IllegalArgumentException The given developer is null.
     */

    void addAssignee(Developer developer)  {
        if (developer == null) throw new IllegalArgumentException("Developer to assign is null");

        this.assignees.add(developer);

        if (this.assignees.size() == 1 && this.getTag().getClass().equals(New.class)){
            this.setTag(new Assigned());
        }
    }

    /**
     * Function to add a comment to the list of comments.
     *
     * @param comment The comment to add to the comments.
     *
     * @throws IllegalArgumentException The given comment is null.
     */
    void addComment(Comment comment){
        if (comment == null) throw new IllegalArgumentException("Comment is null");

        this.comments.add(comment);
    }

    /**
     * Overrided the equals method to only look at the id to check for equality.
     *
     * @param obj The bugReport to compare this bugReport to.
     *
     * @return True if the id of both bugReport are the same.
     */
    public boolean equals(Object obj){
        if (obj instanceof BugReport){
            return ((BugReport) obj).getId().equals(this.getId());
        }
        else{
            return false;
        }
    }

    //TODO documentatie
    public void addDependency(BugReport bugReport){
        if (!dependencies.contains(bugReport)) dependencies.add(bugReport);
    }

    
    /**
	 * Method to represent a bugreport as a string.
	 * 
	 * @return The bugreport as a string.
	 */
    @Override
    public String toString()
    {
    	return "Bugreport ID: " + getId() + "\nTitle: " + getTitle() 
    			+ "\nDescription: " + getDescription()+ "\nCreation date: "
    			+ getCreationDate() + "\nTag: " + getTag() + "\nCreator: "
    			+ getCreator(); 
    }

    //endregion

}
