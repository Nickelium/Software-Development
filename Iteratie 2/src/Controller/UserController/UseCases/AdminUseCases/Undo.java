package Controller.UserController.UseCases.AdminUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.Memento.Caretaker;
import Model.Memento.Snapshot;
import Model.Project.ProjectService;
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
     * Lets an administrator revert one or more use cases.
     *
     * 2. The system shows a list of the last 10 completed use case instances
	 * that modified the state of BugTrap.
	 * 3. The administrator indicates how many use cases he wants to revert
	 * starting with the last.
	 * 4. The system reverts the selected use cases starting with the last completed one and,
	 * if necessary, sends the required notifications if some
	 * object of interest is modified by the undoing of a use case.
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
    	getUi().display("The list of the last " + numberOfUseCase + " use cases that modified the state of Bug trap\n" );
    	List<Snapshot> snapshots = caretaker.getSnapshots(numberOfUseCase);
    	String stringSnapshots = Formatter.formatSnapshots(snapshots);
    	getUi().display(stringSnapshots);
    	
    	// Step 3
		getUi().display("Please indicate to which state you want to revert:");

		int number = getUi().readInt();
    	caretaker.restoreState(snapshots.get(number));
    	
    	getUi().display("System restore completed !");
    	
    }
    
    @Override
	public String toString()
	{
		return "Undo";
	}
}
