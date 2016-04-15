package Model.BugReport;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.TagTypes.New;
import Model.Mail.Observer;
import Model.Mail.Subject;
import Model.Memento.Memento;
import Model.Memento.Originator;
import Model.Milestone.TargetMilestone;
import Model.Project.TheDate;
import Model.User.Developer;
import Model.User.Issuer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a Bug report object.
 *
 * A bug report signals that something is wrong with a specific
 * subsystem.
 *
 * This class provides getters and setters for most attributes.
 * This class provides a method to create new bug reports.
 * This class provides public checkers when setting new values to certain attributes.
 *
 */
public class BugReport extends Subject implements Observer<Comment>, Originator<BugReport.BugReportMemento,BugReport>{

    //region Static Attributes
    public static final boolean PUBLIC = true;
    public static final boolean PRIVATE = false;
    //endregion

    //region Attributes

    private BugReportID id;
    private String title;
    private String description;
    private TheDate creationDate;
    private Issuer creator;
    private List<Comment> comments;
    private List<BugReport> dependencies;


    // default access right attributes
    int solutionScore;
    List<Patch> patches;
    List<Test> tests;
    Patch selectedPatch;
    Tag tag;
    List<Developer> assignees;
    boolean pblc;

    //optional attributes
    private TargetMilestone targetMilestone;
    private String procedureBug;
    private String stackTrace;
    private String errorMessage;

    //endregion

    //region Constructor

    /**
     * Default constructor for a BugReport.
     *
     * @param title The title of the BugReport.
     * @param description The description of the BugReport.
     * @param creator The issuer of the bug report.
     * @param pblc True if the bug report is public.
     *
     * @throws ReportErrorToUserException The title or description is empty.
     * @throws IllegalArgumentException The subsystem or creator is null.
     */
    BugReport(String title, String description, Issuer creator, boolean pblc) throws ReportErrorToUserException
    {
        this(title, description, creator, pblc, TheDate.TheDateNow(), new ArrayList<>(), null);
    }
    
    /**
     * Constructor for a Bugreport.
     *
     * @param title The title of the bug report.
     * @param description The description of the bug report.
     * @param creator The creator of the bug report.
     * @param creationDate The creation date of this bug report.
     * @param initialAssignies The list of assignees for this bug report.
     * @param targetMilestone The target milestone of a bug report.
     * @param pblc The access right to this bugreport.
     *
     * @throws ReportErrorToUserException The title or description is empty.
     * @throws IllegalArgumentException The subsystem, creator, creationDate or tag is null.
     */
    BugReport(String title, String description, Issuer creator, boolean pblc, TheDate creationDate, List<Developer> initialAssignies, TargetMilestone targetMilestone) throws ReportErrorToUserException
    {
        setTitle(title);
        setDescription(description);
        setCreator(creator);
        setCreationDate(creationDate);
        this.tag = new New();
        this.pblc = pblc;
        this.id = new BugReportID();

        this.comments = new ArrayList<>();
        this.dependencies = new ArrayList<>();
        this.patches = new ArrayList<>();
        this.tests = new ArrayList<>();
        this.assignees = new ArrayList<>();
        this.targetMilestone = targetMilestone;

        for (Developer dev : initialAssignies) {
            this.addAssignee(dev);
        }
    }

    //endregion

    //region Getters

    /**
     * Getter to request the unique id of the bug report.
     *
     * @return The unique id of the bug report.
     */
    public BugReportID getId(){
        return this.id;
    }

    /**
     * Getter to request the title of the bug report.
     *
     * @return The title of the bug report.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Getter to request the description of the bug report.
     *
     * @return The description of the bug report.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter to request the creationDate of the bug report.
     *
     * @return The creationDate of the bug report.
     */
    public TheDate getCreationDate() {
        return this.creationDate;
    }

    /**
     * Getter to request the creator of the bug report.
     *
     * @return The creator of the bug report.
     */
    public Issuer getCreator(){
        return this.creator;
    }

    /**
     * Getter to request the tag of the bug report.
     *
     * @return The tag of the bug report.
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * Getter to request the developers assigned to the bug report.
     *
     * @return The list of developers assigned to the bug report
     */
    public List<Developer> getAssignees() {
        return Collections.unmodifiableList(this.assignees);
    }

    /**
     * Getter to request the comments given on the bug report.
     *
     * @return The list of comments given on the bug report.
     */
    public List<Comment> getComments() {
        return Collections.unmodifiableList(this.comments);
    }

    /**
     * Getter to request the dependencies of the bug report.
     *
     * @return The list of dependencies of the bug report.
     */
    public List<BugReport> getDependencies(){return Collections.unmodifiableList(this.dependencies);}

    /**
     * Getter to request procedure bug
     * 
     * @return The procedureBug
     */
    public String getProcedureBug()
    {
    	return procedureBug;
    }
    
    /**
     * Getter to request the stacktrace
     * 
     * @return The stacktrace
     */
    public String getStackTrace()
    {
    	return stackTrace;
    }
    
    /**
     * Getter to request error message
     * 
     * @return The error message
     */
    public String getErrorMessage()
    {
    	return errorMessage;
    }

    /**
     * Getter to request the current score of the bugReport.
     *
     * @return 0 if not rated and value between 1 and 5 otherwise.
     */
    public int getSolutionScore() {
        return this.solutionScore;
    }

    /**
     * Getter to request all the patches of the current bug report.
     *
     * @return An unmodifiable list of all the patches.
     */
    public List<Patch> getPatches() {
        return Collections.unmodifiableList(this.patches);
    }

    /**
     * Getter to request all the test of the current bug report.
     *
     * @return An unmodifiable list of all the tests.
     */
    public List<Test> getTests() {
        return Collections.unmodifiableList(this.tests);
    }

    /**
     * Getter to request the selected patch.
     *
     * @return The selected patch or null if no patch available.
     */
    public Patch getSelectedPatch() {
        return this.selectedPatch;
    }

    /**
     * Method to get the target milestone of a bug report.
     *
     * @return the target milestone of a bug report.
     */
    public TargetMilestone getTargetMilestone() {
        return this.targetMilestone;
    }

    /**
     * Getter to get all the comments of the bug report.
     *
     * @return An unmodifiable list of all the comments of the bug report. (recursively)
     */
    public List<Comment> getAllComments() {
        List<Comment> list = new ArrayList<>();
        for (Comment comm : comments) {
            list.add(comm);
            list.addAll(comm.getAllComments());
        }
        return Collections.unmodifiableList(list);
    }

    //endregion

    //region Checkers

    /**
     * Checker to check if the title of the bug report is valid.
     *
     * @param title The title of the bug report.
     *
     * @return True if the title is not null or not empty. False otherwise.
     */
    public boolean isValidTitle(String title){
        if (title == null)return false;
        if (title.equals("")) return false;
        else return true;
    }
    
    /**
     * Checker to check if the description of the bug report is valid.
     *
     * @param description The description of the bug report.
     *
     * @return True if the description is not null or not empty. False otherwise.
     */
    public boolean isValidDescription(String description){
        if (description == null)return false;
        if (description.equals("")) return false;
        else return true;
    }

    /**
     * Checker to check if the procedure is valid
     * @param procedureBug the procedure bug that needs to be checked
     * @return true when valid, false when invalid
     */
    public boolean isValidProcedureBug(String procedureBug){
        if (procedureBug == null) return false;
        else return true;
    }

    /**
     * Checker to check if the stack trace is valid
     * @param stackTrace the stack trace that needs to be checked
     * @return true when valid, false when invalid
     */
    public boolean isValidStackTrace(String stackTrace){
        if (stackTrace == null) return false;
        else return true;
    }

    /**
     * Checker to check fi the error message is valid
     * @param errorMessage the error message that needs to be checked
     * @return true when valid, false when invalid
     */
    public boolean isValidErrorMessage(String errorMessage){
        if (errorMessage == null) return false;
        else return true;
    }


    /**
     * Returns if the bug report is public.
     *
     * @return True if the bug report is public.
     */
    public boolean isPublic() {
        return pblc;
    }

    //endregion

    //region Setters

    /**
     * Method to set the title of a bug report.
     * @param title the title that needs to be set
     * @throws ReportErrorToUserException is thrown if the new title is not valid.
     */
    private void setTitle(String title) throws ReportErrorToUserException {
        if (!isValidTitle(title)) throw new ReportErrorToUserException("The title cannot be empty!");
        this.title = title;
    }

    /**
     * Method to set the description of a bug report.
     * @param description the description that needs to be set
     * @throws ReportErrorToUserException is thrown if the new description is not valid.
     */
    private void setDescription(String description) throws ReportErrorToUserException {
        if (!isValidDescription(description)) throw new ReportErrorToUserException("The description cannot be empty!");
        this.description = description;
    }

    /**
     * Method to set the creation date of a bug report.
     * @param creationDate the new creation date that needs to be set
     */
    private void setCreationDate(TheDate creationDate) {
        if (creationDate == null) throw new IllegalArgumentException("CreationDate is null");
        this.creationDate = creationDate;
    }

    /**
     * Method to set the creator of a bug report.
     * @param creator the new creator that needs to be set
     */
    private void setCreator(Issuer creator) {
        if (creator == null) throw new IllegalArgumentException("The issuer is null");
        this.creator = creator;
    }

    /**
     * Method to set the assignees of a bug report.
     * @param initialAssignees the list of assignees that needs to be set
     */
    private void setAssignees(List<Developer> initialAssignees) {
        if (initialAssignees == null) throw new IllegalArgumentException("List cannot be null");
        this.assignees = new ArrayList<>(initialAssignees);
    }

    /**
     * Setter to change the tag of the bug report.
     *
     * @param tag The tag to which to set the bug report.
     *
     * @throws ReportErrorToUserException is thrown if the tag cannot be changed to the new tag.
     */
    void setTag(Tag tag) throws ReportErrorToUserException {
        if (tag == null) throw new IllegalArgumentException("Tag is null");
        this.getTag().changeTag(this, tag);
        notifyObservers(this, tag);
    }

    /**
     * Method to set a new target milestone of a bug report.
     *
     * @param targetMilestone the new target milestone of a bug report.
     *
     */
    void setTargetMilestone(TargetMilestone targetMilestone){

        this.targetMilestone = targetMilestone;
    }

    /**
     * Method to set a new procedure bug.
     *
     * Method requires the input string to be a valid procedure bug.
     * @param procedureBug the procedure bug to be set.
     * @throws ReportErrorToUserException is thrown if the procedure bug is invalid.
     */
    void setProcedureBug(String procedureBug) throws ReportErrorToUserException
    {
        if (!isValidProcedureBug(procedureBug))
            throw new ReportErrorToUserException("Invalid value for the way to reproduce the bug.");
        this.procedureBug = procedureBug;
   
    }

    /**
     * Method to set a new stack trace.
     *
     * Method requires the input string to be a valid stack trace.
     * @param stackTrace the procedure bug to be set.
     * @throws ReportErrorToUserException is thrown if the stack trace is invalid.
     *
     */
    void setStackTrace(String stackTrace) throws ReportErrorToUserException
    {
        if (!isValidStackTrace(stackTrace)) throw new ReportErrorToUserException("Invalid value for the stack trace.");
        this.stackTrace = stackTrace;
    }

    /**
     * Method to set a new error message.
     *
     * Method requires the input string to be a valid error message.
     * @param errorMessage the procedure bug to be set.
     * @throws ReportErrorToUserException is thrown if the error message is invalid.
     *
     */
    void setErrorMessage(String errorMessage) throws ReportErrorToUserException
    {
        if (!isValidErrorMessage(errorMessage))
            throw new ReportErrorToUserException("Invalid value for the error message.");
        this.errorMessage = errorMessage;
    }

    //endregion

    //region Functions

    /**
     * Function for adding an assignee to the bug report.
     * The bug report is given the "Assigned" tag.
     *
     * @param developer The developer to assign to the bug report.
     *
     * @throws IllegalArgumentException The given developer is null.
     * @throws ReportErrorToUserException Assigning the developer caused an error.
     */

    void addAssignee(Developer developer) throws ReportErrorToUserException {
        if (developer == null) throw new IllegalArgumentException("Developer to assign is null");
        this.getTag().assignDeveloper(this, developer);
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
        comment.addObserver(this);
		notifyObservers(this, comment);
    }
    
    /**
     * Function to add a patch to the list of patches.
     *
     * @param patch The patch to add tot the patches.
     * @throws IllegalArgumentException The given patch is null.
     * @throws ReportErrorToUserException Unable to add patch to bug report.
     */
    void addPatch(Patch patch) throws ReportErrorToUserException {
        if (patch == null) throw new IllegalArgumentException("Patch is null");
        this.getTag().addPatch(this, patch);
    }

    /**
     * Function to add a test to the list of tests.
     *
     * @param test The test to add to the tests.
     * @throws IllegalArgumentException The given test is null.
     * @throws ReportErrorToUserException Unable to add test to the bug report.
     */
    void addTest(Test test) throws ReportErrorToUserException {
        if (test == null) throw new IllegalArgumentException("Test is null");
        this.getTag().addTest(this, test);
    }

    /**
     * Method for adding a dependency to the list of dependencies.
     *
     * @param dependency The dependency to add.
     * @throws IllegalArgumentException The given dependency is null.
     */
    void addDependency(BugReport dependency) {
        if (dependency == null) throw new IllegalArgumentException("Dependency is null");

        this.dependencies.add(dependency);
    }

    //endregion

    //region Object Functions

    /**
     * Overrides the equals method to only look at the id to check for equality.
     *
     * @param obj The bugReport to compare this bugReport to.
     * @return True if the id of both bugReport are the same.
     */
    public boolean equals(Object obj) {
        if (obj instanceof BugReport) {
            return ((BugReport) obj).getId().equals(this.getId());
        } else {
            return false;
        }
    }

    /**
     * Method to represent a bug report as a string.
     * <p>
     * Makes use of getters for bug report attributes to create
     * one full string which will be passed on to the formatter.
     *
     * @return The bug report as a string.
     */
    @Override
    public String toString() {
        String str =
                "Bugreport ID: " + getId() + "\nTitle: " + getTitle()
                        + "\nDescription: " + getDescription() + "\nCreation date: "
                        + getCreationDate() + "\nTag: " + getTag() + "\nCreator: "
                        + getCreator();

        if (this.getTargetMilestone() != null) {
            str += "\nTarget Milestone: " + getTargetMilestone().toString();
        }

        if (!this.getTests().isEmpty()) str += "\nTests : ";
        for (Test test : getTests())
            str += "\n" + test.toString();

        if (!this.getPatches().isEmpty()) str += "\nPatches : ";
        for (Patch patch : getPatches())
            str += "\n" + patch.toString();

        if (this.selectedPatch != null) {
            str += "\nSelected Patch: " + selectedPatch;
        }

        if (this.getSolutionScore() != 0) {
            str += "\nScore of solution: " + getSolutionScore();
        }

        if (this.getProcedureBug() != null) {
            str += "\nProcedure bug: " + getProcedureBug();
        }

        if (this.getErrorMessage() != null) {
            str += "\nError message: " + getErrorMessage();
        }

        if (this.getStackTrace() != null) {
            str += "\nStacktrace: " + getStackTrace();
        }

        str += "\nAssignees: ";

        for (Developer dev : getAssignees())
            str += dev.toString() + ", ";

        //remove last comma
        if (str.length() - 2 > 0 && str.charAt(str.length() - 2) == ',')
            return str.substring(0, str.length() - 2);
        else
            return str;
    }

    //endregion

    //region Memento Functions

    /**
     * Method called to notify this observer
     *
     * @param s      The subject
     * @param aspect The aspect that has changed
     */
    @Override
    public void update(Subject structure, Comment s, Object aspect) {
        notifyObservers(this, aspect);
    }

    /**
     * Method used to create a new memento object for the bug report.
     *
     * @return a memento object for the bug report.
     */
    @Override
    public BugReportMemento createMemento() {
        return new BugReportMemento(this);
    }

    /**
     * Method used to restore an old memento.
     * This method will restore the tag, assignees and comments of the bug report
     * to the values as recorded in the memento object.
     *
     * @param memento the memento object that contains the old values
     */
    @Override
    public void restoreMemento(BugReportMemento memento) {
        this.tag = memento.getTag();
        this.assignees = memento.getAssignees();
        this.comments = memento.getComments();

        this.targetMilestone = memento.getTargetMilestone();

        this.tests = memento.getTests();
        this.patches = memento.getPatches();
    }

    //endregion

    //Innerclass Memento
    /**
     * This class provides utility for saving the state of the system at a certain point in time
     * during execution of the Bug Trap software.
     *
     * The bug report memento saves the state of the following attributes of the bug report:
     * tag, assignees, comments, targetMilestone, tests and patches.
     *
     * This class provides private methods to request the values of the saved fields.
     * This wide interface (private getters) is provided to the class Bugreport,
     * while the narrow interface (public constructor) is provided to any class.
     */
    public class BugReportMemento extends Memento<BugReport>
    {

    	private Tag tag;
    	private List<Developer> assignees;
    	private List<Comment> comments;
    	
    	private TargetMilestone targetMilestone;
    	
    	private List<Test> tests;
    	private List<Patch> patches;
    	
    	/**
    	 * Constructor 
    	 * 
    	 * @param originator The originator to build a memento from
    	 */
    	public BugReportMemento(BugReport originator)
    	{
    		super(originator);
    		this.tag = originator.getTag();
    		this.assignees = new ArrayList<>(originator.getAssignees());
    		this.comments = new ArrayList<>(originator.getComments());
    		
    		this.targetMilestone = originator.getTargetMilestone();
    		
    		this.tests =  new ArrayList<>(originator.getTests());
    		this.patches =  new ArrayList<>(originator.getPatches());
    	}

    	/**
    	 * Returns the tag object of the bug report memento
    	 * @return the tag object of the bug report memento
         */
    	private Tag getTag()
    	{
    		return tag;
    	}

    	/**
    	 * Returns the list with assignees of the bug report memento
    	 * @return the list with assignees of the bug report memento
    	 */
    	private List<Developer> getAssignees()
    	{
    		return new ArrayList<>(assignees);
    	}

    	/**
    	 * Returns the list with comments of the bug report memento
    	 * @return the list with comments of the bug report memento
         */
    	private List<Comment> getComments()
    	{
    		return new ArrayList<>(comments);
    	}

    	/**
    	 * Returns the target milestone object of the bug report memento
    	 * @return the target milestone object of the bug report memento
         */
    	private TargetMilestone getTargetMilestone()
    	{
    		return targetMilestone;
    	}

    	/**
    	 * Returns the list with tests of the bug report memento
    	 * @return the list with tests of the bug report memento
         */
    	private List<Test> getTests()
    	{
    		return new ArrayList<>(tests);
    	}

    	/**
    	 * Returns the list with patches of the bug report memento
    	 * @return the list with patches of the bug report memento
         */
    	private List<Patch> getPatches()
    	{
    		return new ArrayList<>(patches);
    	}
    }


}
