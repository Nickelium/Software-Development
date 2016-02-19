package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tom on 19/02/16.
 */
public class Comment {

    private String text;
    private Issuer issuer;
    private Date creationDate;
    private List<Comment> comments;

    /**
     * The constructor for a comment.
     *
     * @param text The text of the comment.
     * @param issuer The issuer of the comment.
     */
    public Comment(String text, Issuer issuer){
        this.text = text;
        this.issuer = issuer;
        this.creationDate = new Date();
        this.comments = new ArrayList<Comment>();
    }

    /**
     * Getter to request the text of the comment.
     *
     * @return The text of the comment.
     */
    public String getText(){
        return text;
    }

    /**
     * Getter to request the issuer of the comment.
     *
     * @return The issuer of the comment.
     */
    public Issuer getIssuer(){
        return this.issuer;
    }

    /**
     * Getter to request the creationDate of the comment;
     *
     * @return The creationDate of the comment;
     */
    public Date getCreationDate(){
        return this.creationDate;
    }

    /**
     * Getter to request the list of comments given on the comment.
     *
     * @return The list of comments given on the comment.
     */
    public List<Comment> getComments(){
        return this.comments;
    }

    /**
     * Setter to change the list of comments given to comment.
     * @param comments
     */
    public void setComments(List<Comment> comments){
        this.comments = comments;
    }

}
