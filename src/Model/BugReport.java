package Model;

import Model.Tags.New;
import Model.Tags.Tag;

import java.rmi.activation.ActivationGroupDesc;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tom on 19/02/16.
 */
public class BugReport {

    private String title;
    private String description;
    private Date creationDate;
    private Tag tag;
    private SubSystem subsystem;
    private List<Developer> assignees;
    private List<Comment> comments;

    /**
     * Constructor for a Bugreport.
     *
     * @param title The title of the bugreport.
     * @param description The description of the bugreport.
     * @param subsystem The subsystem the bugreport is about.
     */
    public BugReport(String title, String description, SubSystem subsystem){
        this.title = title;
        this.description = description;
        this.subsystem = subsystem;
        this.creationDate = new Date();
        this.tag = new New();
        this.assignees = new ArrayList<Developer>();
        this.comments = new ArrayList<Comment>();

    }

    /**
     * Getter to request the title of the bugreport.
     *
     * @return The title of the bugreport.
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Getter to request the description of the bugreport.
     *
     * @return The description of the bugreport.
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Getter to request the creationDate of the bugreport.
     *
     * @return The creationDate of the bugreport.
     */
    public Date getCreationDate(){
        return this.creationDate;
    }

    /**
     * Getter to request the subsystem the bugreport is about.
     *
     * @return The subsystem the bugreport is about.
     */
    public SubSystem getSubsystem(){
        return this.subsystem;
    }

    /**
     * Getter to request the tag of the bugreport.
     *
     * @return The tag of the bugreport.
     */
    public Tag getTag(){
        return tag;
    }

    /**
     * Getter to request the developers assigned to the bugreport.
     *
     * @return The list of developers assigned to the bugreport
     */
    public List<Developer> getAssignees(){
        return assignees;
    }

    /**
     * Getter to request the comments given on the bugreport.
     *
     * @return The list of comments given on the bugreport.
     */
    public List<Comment> getComments(){
        return this.comments;
    }

    /**
     * Setter to change the description of the bugreport.
     *
     * @param description The description to which to set the bugreport.
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Setter to change the tag of the bugreport.
     *
     * @param tag The tag to which to set the bugreport.
     */
    public void setTag(Tag tag){
        this.tag = tag;
    }

    /**
     * Setter to change the list of assignees to the bugreport.
     *
     * @param assignees The list of assignees which to assign the bugreport.
     */
    public void setAssignees(List<Developer> assignees){
        this.assignees = assignees;
    }

    /**
     * Setter to change the list of comments given on the bugreport.
     *
     * @param comments The list of comments to set the bugreport to.
     */
    public void setComments(List<Comment> comments){
        this.comments = comments;
    }
}
