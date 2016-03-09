package Model.BugReport;

import CustomExceptions.ModelException;
import Model.Project.TheDate;
import Model.User.Issuer;

import java.util.ArrayList;
import java.util.Collections;
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
     * @throws ModelException The text of the comment is empty.
     * @throws IllegalArgumentException The given issuer is null.
     */
    Comment(String text, Issuer issuer) throws ModelException {
        if (!isValidText(text)) throw new ModelException("The text of the comment is empty");
        if (issuer == null) throw new IllegalArgumentException("Invalid issuer for comment");

        this.text = text;
        this.issuer = issuer;
        this.creationDate = TheDate.TheDateNow();
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

	/**
	 * Getter to request all comments of this comment.
     *
	 * @return An unmodifiable list of the comments of this comment. (recursively)
	 */
	public List<Comment> getAllComments()
	{
		List<Comment> list = new ArrayList<>();
		for(Comment comm : comments)
		{
			list.add(comm);
			list.addAll(comm.getAllComments());
		}
		
		return Collections.unmodifiableList(list);
	}

    /**
     * Method for adding a comment to the list of comments.
     *
     * @param comment Comments to add to the list of comments.
     */
    void addComment(Comment comment) {
        if (comment == null) throw new IllegalArgumentException("Comment is null");

        this.comments.add(comment);
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
    private boolean isValidText(String text){
        if (text == null)return false;
        if (text.equals(""))return false;
        else return true;
    }

    //endregion

    //region Functions
    
    /**
	 * Method to represent a comment as a string.
	 * 
	 * @return The comment as a string.
	 */
    public String toString()
    {
    	return "Comment text: " + getText() + "\nIssuer: " + getIssuer() 
    			+ "\nCreation date: " + getCreationDate()+ "\nCreation date: "
    			+ getCreationDate();
    }

    //endregion
}
