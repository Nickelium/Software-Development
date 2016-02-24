package Model.BugReport;

import Model.Issuer;
import Model.TheDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Tom on 19/02/16.
 */
public class Comment {

    //region Attributes

    private String text;
    private Issuer issuer;
    private TheDate creationDate;
    private List<Comment> comments;

    //endregion

    //region Constructor

    /**
     * The constructor for a comment.
     *
     * @param text The text of the comment.
     * @param issuer The issuer of the comment.
     *
     * @throws IllegalArgumentException The given arguments are not valid.
     */
    public Comment(String text, Issuer issuer) throws IllegalArgumentException{
        if (!isValidText(text)) throw new IllegalArgumentException("Invalid text for comment");
        if (issuer == null) throw new IllegalArgumentException("Invalid issuer for comment");

        this.text = text;
        this.issuer = issuer;
        this.creationDate = new TheDate();
        this.comments = new ArrayList<Comment>();
    }

    //endregion

    //region Getters

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
    public TheDate getCreationDate(){
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

    //endregion

    //region Checkers

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

    //endregion

    //region Functions

    /**
     * Function for adding a comment to the list of comments.
     *
     * @param comment Comment to add to the list of comments.
     *
     * @throws IllegalArgumentException The comment is null
     */
    public void addComment(Comment comment){
        if (comment == null) throw new IllegalArgumentException("Comment is null");

        this.comments.add(comment);
    }

    //endregion
}
