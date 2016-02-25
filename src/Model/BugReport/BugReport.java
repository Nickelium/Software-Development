package Model.BugReport;

import Model.User.Developer;
import Model.User.Issuer;
import Model.SubSystem;
import Model.TheDate;
import Model.Tags.Assigned;
import Model.Tags.New;
import Model.Tags.Tag;

import java.util.*;

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
    private SubSystem subsystem;
    private Issuer creator;
    private List<Developer> assignees;
    private List<Comment> comments;
    private List<BugReport> dependencies;

    //endregion

    //region Constructor

    /**
     * Constructor for a Bugreport.
     *
     * @param title       The title of the bugreport.
     * @param description The description of the bugreport.
     * @param subsystem   The subsystem the bugreport is about.
     * @param creator     The creator of the bugreport.
     *
     * @throws IllegalArgumentException One or more of the specified arguments are invalid.
     */
    public BugReport(String title, String description, SubSystem subsystem, Issuer creator){
        if (title == null) throw new IllegalArgumentException("Title is null");
        if (description == null) throw new IllegalArgumentException("Description is null");
        if (subsystem == null) throw new IllegalArgumentException("Subsystem is null");
        if (creator == null) throw new IllegalArgumentException("Creator is null");

        this.title = title;
        this.description = description;
        this.subsystem = subsystem;
        this.creator = creator;

        this.id = new BugReportID();
        //this.creationDate = new TheDate();
        //Tag on creation is New();
        this.tag = new New();

        this.assignees = new ArrayList<Developer>();
        this.comments = new ArrayList<Comment>();
        this.dependencies = new ArrayList<BugReport>();

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
     * Getter to request the subsystem the bugreport is about.
     *
     * @return The subsystem the bugreport is about.
     */
    public SubSystem getSubsystem() {
        return this.subsystem;
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
    public boolean isValidTitle(String title){
        if (title == null)return false;
        if (title == "") return false;
        else return true;
    }

    /**
     * Checker to check if the description of the bugreport is valid.
     *
     * @param description The description of the bugreport.
     *
     * @return True if the description is not null or not empty. False otherwise.
     */
    public boolean isValidDescription(String description){
        if (description == null) return false;
        if (description == "") return false;
        else return true;
    }

    /**
     * Checker to check if the subsystem of the bugreport is about is valid.
     *
     * @param subsystem The subsystem to check.
     *
     * @return True if the subsystem is not null. False otherwise.
     */
    public boolean isValidSubSystem(SubSystem subsystem){
        if (subsystem == null) return false;
        else return true;
    }

    /**
     * Checker to check if the creator of the bugreport is valid.
     *
     * @param creator The creator to check.
     *
     * @return True if the creator is not null. False otherwise.
     */
    public boolean isValidCreator(Issuer creator){
        if (creator == null) return false;
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
    void setTag(Tag tag) {
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
    public void addAssignee(Developer developer)  {
        if (developer == null) throw new IllegalArgumentException();

        this.assignees.add(developer);

        if (this.assignees.size() == 1){
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
    public void addComment(Comment comment){
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

    //endregion

}
