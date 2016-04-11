package Controller.UserController.UseCases.AdminUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.Memento.Caretaker;
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
    	caretaker.restoreState(0);
    	
    }
}
