package Model.BugReport;

import CustomExceptions.ReportErrorToUserException;
import Model.Mail.Observer;
import Model.Mail.Subject;
import Model.Project.TheDate;
import Model.User.Issuer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a comment object.
 *
 * Provides methods to create a new comment, and add it to another comment.
 * Also provides methods to get comments of the comment object itself.
 *
 */
public class Comment extends Subject implements Observer<Comment>{

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
     * @throws ReportErrorToUserException The text of the comment is empty.
     * @throws IllegalArgumentException The given issuer is null.
     */
    Comment(String text, Issuer issuer) throws ReportErrorToUserException {
        if (!isValidText(text)) throw new ReportErrorToUserException("The text of the comment is empty");
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
        comment.addObserver(this);
		notifyObservers(null,comment);
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
    public boolean isValidText(String text) {
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
        return "Comment text: \n" + getText() + "\nIssuer: " + getIssuer()
                + "\nCreation date: " + getCreationDate();
    }

    /**
     * Method called to notify this observer
     * 
     * @param s The subject
     * @param aspect The aspect that has changed
     */
	@Override
	public void update(Subject structure, Comment s, Object aspect)
	{
		notifyObservers(this, aspect);
	}

    //endregion
}
