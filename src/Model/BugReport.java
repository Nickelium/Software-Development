package Model;

import Model.Tags.Assigned;
import Model.Tags.New;
import Model.Tags.Tag;
import com.sun.javafx.tools.ant.Application;

import java.rmi.activation.ActivationGroupDesc;
import java.util.*;

/**
 * Created by Tom on 19/02/16.
 */
public class BugReport {

    private BugReportID id;
    private String title;
    private String description;
    private Date creationDate;
    private Tag tag;
    private SubSystem subsystem;
    private Issuer creator;
    private List<Developer> assignees;
    private List<Comment> comments;

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
    public BugReport(String title, String description, SubSystem subsystem, Issuer creator) throws IllegalArgumentException {
        if (!isValidTitle(title)) throw new IllegalArgumentException("Invalid title");
        if (!isValidDescription(description)) throw new IllegalArgumentException("Invalid description");
        if (!isValidSubSystem(subsystem)) throw new IllegalArgumentException("Invalid subsystem");
        if (!isValidCreator(creator)) throw new IllegalArgumentException("Invalid creator");

        this.id = new BugReportID();
        this.title = title;
        this.description = description;
        this.subsystem = subsystem;
        this.creator = creator;
        this.creationDate = new Date();
        this.tag = new New();
        this.assignees = new ArrayList<Developer>();
        this.comments = new ArrayList<Comment>();

    }

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
     * Getter to request the description of the bugreport.
     *
     * @return The description of the bugreport.
     */
    public String getDescription() {
        return this.description;
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
     * Getter to request the creationDate of the bugreport.
     *
     * @return The creationDate of the bugreport.
     */
    public Date getCreationDate() {
        return this.creationDate;
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
     * Getter to request the creator of the bugreport.
     *
     * @return The creator of the bugreport.
     */
    public Issuer getCreator(){
        return this.creator;
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

    /**
     * Getter to request the tag of the bugreport.
     *
     * @return The tag of the bugreport.
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * Setter to change the tag of the bugreport.
     *
     * @param tag The tag to which to set the bugreport.
     *
     * @throws NullPointerException The tag given is null.
     */
    public void setTag(Tag tag) throws NullPointerException {
        if (tag == null) throw new NullPointerException("The given tag is null!");

        this.tag = tag;
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
     * Function for adding an assignee to the bugreport.
     *
     * @param developer The developer to assign to the bugreport.
     *
     * @throws RuntimeException The insertion of the element failed.
     * @throws NullPointerException The developer given is null.
     */
    public void addAssignee(Developer developer) throws RuntimeException{
        if (developer == null) throw new NullPointerException("Developer is null");
        if (!this.assignees.add(developer)) {
            throw new RuntimeException("Error while adding");
        }
        if (this.assignees.size() == 1){
            this.setTag(new Assigned());
        }
    }

    /**
     * Function for removing an assignee of the bugreport.
     *
     * @param developer The developer to remove from the assignementlist.
     *
     * @throws RuntimeException The removal of the developer failed or the removal removes the last assignee from the list.
     * @throws NullPointerException The developer given is null.
     */
    public void removeAssignee(Developer developer) throws RuntimeException{
        if (developer == null) throw new NullPointerException("Developer is null");

        if (this.assignees.size() == 1) throw new RuntimeException("Illegal remove operation");

        if (!this.assignees.remove(developer)){
            throw new RuntimeException("Error while removing");
        }
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
     * Function to add a comment to the list of comments.
     *
     * @param comment The comment to add to the comments.
     *
     * @throws RuntimeException The adding of the object to the list failed.
     * @throws NullPointerException The comment given is null.
     */
    public void addComment(Comment comment) throws RuntimeException, NullPointerException {
        if (comment == null) throw new NullPointerException("Comment is null");
        if (!this.comments.add(comment)) {
            throw new RuntimeException("Error while adding");
        }
    }

    /**
     * Function to remove a comment from the list of comments.
     *
     * @param comment The comment to remove.
     *
     * @throws RuntimeException The removal of the object from the list failed.
     * @throws NullPointerException The given comment is null.
     */
    public void removeComment(Comment comment) throws RuntimeException, NullPointerException {
        if (comment == null) throw new NullPointerException("Comment is null");
        if (!this.comments.remove(comment)) {
            throw new RuntimeException("Error while removing");
        }
    }

}
