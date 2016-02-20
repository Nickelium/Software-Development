package Model;

import java.util.ArrayList;
import java.util.Collections;
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
     *
     * @throws IllegalArgumentException The given arguments are not valid.
     */
    public Comment(String text, Issuer issuer) throws IllegalArgumentException{
        if (!isValidText(text)) throw new IllegalArgumentException("Invalid text for comment!");
        if (!isValidIssuer(issuer)) throw new IllegalArgumentException("Invalid issuer given for comment!");

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
     * Checker to check if the text is a valid text for the comment.
     *
     * @param text The text to check.
     *
     * @return True if the text is not empty or not null. False otherwise.
     */
    public boolean isValidText(String text){
        if (text == null)return false;
        if (text == "")return false;
        else return true;
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
     * Checker to check if the issuer is a valid issuer.
     *
     * @param issuer The issuer to check.
     *
     * @return True if the issuer is not null. False otherwise.
     */
    public boolean isValidIssuer(Issuer issuer){
        return issuer != null;
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
        return Collections.unmodifiableList(this.comments);
    }

    /**
     * Function for adding a comment to the list of comments.
     *
     * @param comment Comment to add to the list of comments.
     *
     * @throws RuntimeException The adding of the comment failed.
     */
    public void addComment(Comment comment) throws RuntimeException{
        if (this.comments.add(comment)){
            throw new RuntimeException("Error while adding comment!");
        }
    }

    /**
     * Function for removing a comment from the list of comments.
     *
     * @param comment Comment to remove from the list of comments.
     *
     * @throws RuntimeException The removal of the comment failed.
     */
    public void removeComment(Comment comment) throws RuntimeException{
        if (this.comments.remove(comment)){
            throw new RuntimeException("Error while removing comment!");
        }
    }

}
