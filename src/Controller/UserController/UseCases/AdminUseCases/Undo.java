package Controller.UserController.UseCases.AdminUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.Memento.Caretaker;
import Model.Memento.Snapshot;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.User.Developer;
import Model.User.User;
import Model.User.UserService;

import java.util.List;


public class Undo extends UseCase 
{
	private Caretaker caretaker;
	
    public Undo(IUI ui, UserService userService, ProjectService projectService, 
    				BugReportService bugReportService, User currentUser, Caretaker caretaker) {
        super(ui, userService, projectService, bugReportService, currentUser);
        changeSystem = true;
        this.caretaker = caretaker;
    }

    /**
     *
     * Lets an administrator create a new project.
     *
     * 2. The system shows a form to enter the project details: name,
     * description, starting date and budget estimate.
     * 3. The administrator enters all the project details.
     * 4. The system shows a list of possible lead developers.
     * 5. The administrator selects a lead developer.
     * 6. The system creates the project and shows an overview.
     *
     * @throws ReportErrorToUserException
     *          in case that the method encounters invalid input.
     * @throws IndexOutOfBoundsException
     * 			thrown when a user puts an incorrect option index.
     *
     */
    @Override
    public void run() throws ReportErrorToUserException,IndexOutOfBoundsException 
    {
    	// Step 2
    	int numberOfUseCase = 10;
    	getUi().display("The list of the last " + numberOfUseCase + " usecases that modified the state of Bugtrap\n" );
    	List<Snapshot> snapshots = caretaker.getSnapshots(numberOfUseCase);
    	String stringSnapshots = Formatter.formatSnapshots(snapshots);
    	getUi().display(stringSnapshots);
    	
    	// Step 3
    	getUi().display("Please indicate how many usecases you want to revert.");
    	
    	int number = getUi().readInt();
    	caretaker.restoreState(number);
    	
    	getUi().display("System restore completed !");
    	
    }
    
    @Override
	public String toString()
	{
		return "Undo";
	}
}
