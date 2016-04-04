package Controller.UserController.UseCases.IssuerUseCases;

import Controller.Formatter;
import Controller.IUI;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.*;
import Model.Mail.MailboxService;
import Model.Mail.Notification;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.User.Issuer;
import Model.User.User;
import Model.User.UserService;

import java.util.List;


public class RegisterNotifications extends IssuerUseCase{

	private MailboxService mailboxService;
	
    public RegisterNotifications(IUI ui, ProjectService projectService, BugReportService bugReportService, MailboxService mailboxService, User currentUser) {
        super(ui, null, projectService, bugReportService,null, currentUser);
        this.mailboxService = mailboxService;
    }

    /**
     *
     * Lets an Issuer create a bug report.
     *
     * 2. The system shows a list of projects.
     * 3. The issuer selects a project.
     * 4. The system shows a list of subsystems of the selected project.
     * 5. The issuer selects a subsystem.
     * 6. The system shows the bug report creation form.
     * 7. The issuer enters the bug report details: title and description.
     * 8. The system shows a list of possible dependencies of this bug report.
     *    These are the bug reports of the same project.
     * 9. The issuer selects the dependencies.
     * 10. The system creates the bug report.
     *
     * @throws ReportErrorToUserException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     */
    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {

     
        getUi().display("Register for Project (1) / Subsystem (2) / Bugreport (3) : ");
        int index = getUi().readInt();
        
	    if(index ==1)
	    {
	        	getUi().display("Select a project:");
	        	List<Project> projectList = getProjectService().getAllProjects();
	        	String projectListString = Formatter.formatProjectList(projectList);
	        	getUi().display(projectListString);
	        	
	        	int indexProject = getUi().readInt();
	            Project project = projectList.get(indexProject);
	        	
	        	getUi().display("What kind of change of bugreport do you want to be notified :");
	         	getUi().display("Creation of a new bugreport (1) / A bug report receiving a new tag (2)");
	         	getUi().display("A bug report receiving a specific tag (3) / A new comment for a bug report (4)");
	        	
	         	int indexChange = getUi().readInt();
	         	
	         	switch(indexChange)
	         	{
	         		case 1:
	         			mailboxService.registerCreationBugReport(getCurrentUser(),  project);
	         			break;
	         		case 2:
	         			mailboxService.registerTag(getCurrentUser(), project);
	         			break;
	         		case 3:
	         	        getUi().display("Please specify the tag to be notified: ");
	         	        String input = getUi().readString();
	         	        Class<?> tag;
	         	        try 
	         	        {
	         	            tag = Class.forName("Model.Tags.TagTypes." + input);
	         	            if (input == "-1") return;
	         	        } 
	         	        catch (ClassNotFoundException e)
	         	        {
	         	            throw new ReportErrorToUserException("The given tag does not exist!");
	         	        }

	         	        // Step 4
	         	        try 
	         	        {
	         	           Tag newTag = (Tag) tag.newInstance();
	         	           mailboxService.registerSpecificTag(getCurrentUser(), project, newTag);
	         	        } 
	         	        catch (InstantiationException | IllegalAccessException e)
	         	        {
	         	            throw new ReportErrorToUserException("The tag you have given does not exist");
	         	        }
	         			break;
	         		case 4:
	         			mailboxService.registerComment(getCurrentUser(), project);
	         			break;
	         		default :
	         			throw new IndexOutOfBoundsException("Invalid index input");
	         			
	         	}
	    }
	    else if(index == 2)
	    {
	        	getUi().display("Select a project:");
	        	List<Project> projectList = getProjectService().getAllProjects();
	        	String projectListString = Formatter.formatProjectList(projectList);
	        	getUi().display(projectListString);
	        	
	        	int indexProject = getUi().readInt();
	            Project project = projectList.get(indexProject);
	            
	            getUi().display("Select a subsystem:");
	            List<SubSystem> subSystemList = project.getAllSubSystems();
	            String subSystemsOfProject = Formatter.formatSubSystemList(subSystemList);
	            getUi().display(subSystemsOfProject);
	            
	            int indexSubsystem = getUi().readInt();
	            SubSystem subsystem = subSystemList.get(indexSubsystem);

	        	getUi().display("What kind of change of bugreport do you want to be notified :");
	         	getUi().display("Creation of a new bugreport (1) / A bug report receiving a new tag (2)");
	         	getUi().display("A bug report receiving a specific tag (3) / A new comment for a bug report (4)");
	        	
	         	int indexChange = getUi().readInt();
	         	
	         	switch(indexChange)
	         	{
	         		case 1:
	         			mailboxService.registerCreationBugReport(getCurrentUser(),  subsystem);
	         			break;
	         		case 2:
	         			mailboxService.registerTag(getCurrentUser(), subsystem);
	         			break;
	         		case 3:
	         	        getUi().display("Please specify the tag to be notified: ");
	         	        String input = getUi().readString();
	         	        Class<?> tag;
	         	        try 
	         	        {
	         	            tag = Class.forName("Model.Tags.TagTypes." + input);
	         	            if (input == "-1") return;
	         	        } 
	         	        catch (ClassNotFoundException e)
	         	        {
	         	            throw new ReportErrorToUserException("The given tag does not exist!");
	         	        }

	         	        // Step 4
	         	        try 
	         	        {
	         	           Tag newTag = (Tag) tag.newInstance();
	         	           mailboxService.registerSpecificTag(getCurrentUser(), subsystem, newTag);
	         	        } 
	         	        catch (InstantiationException | IllegalAccessException e)
	         	        {
	         	            throw new ReportErrorToUserException("The tag you have given does not exist");
	         	        }
	         			break;
	         		case 4:
	         			mailboxService.registerComment(getCurrentUser(), subsystem);
	         			break;
	         		default :
	         			throw new IndexOutOfBoundsException("Invalid index input");
	         			
	         	}
	    }
	    else if( index == 3)
	    {
	    	getUi().display("Please select the bug report that you want to be notified: ");
	        BugReport bugReport = selectBugReport();

        	getUi().display("What kind of change of bugreport do you want to be notified :");
         	getUi().display("A bug report receiving a new tag (2) / A bug report receiving a specific tag (3)");
         	getUi().display("A new comment for a bug report (3)");
        	
         	int indexChange = getUi().readInt();
         	
         	switch(indexChange)
         	{
         		case 1:
         			mailboxService.registerTag(getCurrentUser(), bugReport);
         			break;
         		case 2:
         	        getUi().display("Please specify the tag to be notified: ");
         	        String input = getUi().readString();
         	        Class<?> tag;
         	        try 
         	        {
         	            tag = Class.forName("Model.Tags.TagTypes." + input);
         	            if (input == "-1") return;
         	        } 
         	        catch (ClassNotFoundException e)
         	        {
         	            throw new ReportErrorToUserException("The given tag does not exist!");
         	        }

         	        // Step 4
         	        try 
         	        {
         	           Tag newTag = (Tag) tag.newInstance();
         	           mailboxService.registerSpecificTag(getCurrentUser(), bugReport, newTag);
         	        } 
         	        catch (InstantiationException | IllegalAccessException e)
         	        {
         	            throw new ReportErrorToUserException("The tag you have given does not exist");
         	        }
         			break;
         		case 3:
         			mailboxService.registerComment(getCurrentUser(), bugReport);
         			break;
         		default :
         			throw new IndexOutOfBoundsException("Invalid index input");
         			
         	}
	    }
	    else
	    {
	    	throw new IndexOutOfBoundsException("Invalid index input");
	    }
	    
	       
        getUi().display("Registration completed !\n");
    
    }
}
