package Controller.UserController.UseCases.UserUseCases;

import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Karina on 24.03.2016.
 */
public class ExitProgram extends UseCase {

    public ExitProgram(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        changeSystem = false;
    }

    /**
     * The software wishes the user goodbye and exits the java program.
     */
    @Override
    public void run(){
        getUi().display("Bye!");
        System.exit(1);
    }

    @Override
	public String toString()
	{
		return "Exit Program";
	}
}
