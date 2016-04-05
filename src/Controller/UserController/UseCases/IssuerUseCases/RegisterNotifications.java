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
     * Lets an Issuer register for certain changes in a project/subsystem/bugreport.
     *
     * 2. The system asks if he wants to register for a project, subsystem or bug
     * report.
	 * 3. The issuer indicates he wants to register for a project.
	 * 4. The system shows a list of projects.
	 * 5. The issuer selects a project.
	 * 6. The system presents a form describing the specific system changes that
	 * 		can be subscribed to for the selected object of interest:
	 * 			The creation of a new bug report (only applicable if the object of
					interest is a project or subsystem)
	 *			A bug report receiving a new tag
	 *			A bug report receiving a specific tag
	 *			A new comment for a bug report
	 * 7. The issuer selects the system change he wants to be notified of.
	 * 8. The system registers this issuer to receive notifications about the selected object of interest for the specified changes.
	 * Extensions:
	 * 3a. The issuer indicates he wants to register for a subsystem.
	 *		1. The system shows a list of projects.
	 *		2. The user selects a project.
	 *		3. The system shows all the subsystems of the selected project.
	 * 		4. The user selects a subsystem.
	 * 		5. The use case continues with step 6.
	 * 3b. The issuer indicates he wants to register for a bug report.
	 * 		1. Include use case Select Bug Report.
	 * 		2. The use case continues with step 6.
	 * 
     * @throws ReportErrorToUserException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     */
    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {

    	//Step 2
        getUi().display("Register for Project (1) / Subsystem (2) / Bugreport (3) : ");
        int index = getUi().readInt();
        
        //Step 3
	    if(index ==1)
	    {
	    		//Step 4
	        	getUi().display("Select a project:");
	        	List<Project> projectList = getProjectService().getAllProjects();
	        	String projectListString = Formatter.formatProjectList(projectList);
	        	getUi().display(projectListString);
	        	
	        	//Step 5
	        	int indexProject = getUi().readInt();
	            Project project = projectList.get(indexProject);
	        	
	            //Step 6
	        	getUi().display("What kind of change of bugreport do you want to be notified :");
	         	getUi().display("Creation of a new bugreport (1) / A bug report receiving a new tag (2)");
	         	getUi().display("A bug report receiving a specific tag (3) / A new comment for a bug report (4)");
	        	
	         	int indexChange = getUi().readInt();
	         	//Step 7
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
	         	            tag = Class.forName("Model.BugReport.TagTypes." + input);
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
	    //Step 3a
	    else if(index == 2)
	    {
	    		//Step 3a.1
	        	getUi().display("Select a project:");
	        	List<Project> projectList = getProjectService().getAllProjects();
	        	String projectListString = Formatter.formatProjectList(projectList);
	        	getUi().display(projectListString);
	        	
	        	//Step 3a.2
	        	int indexProject = getUi().readInt();
	            Project project = projectList.get(indexProject);
	            
	            //Step 3a.3
	            getUi().display("Select a subsystem:");
	            List<SubSystem> subSystemList = project.getAllSubSystems();
	            String subSystemsOfProject = Formatter.formatSubSystemList(subSystemList);
	            getUi().display(subSystemsOfProject);
	            
	            //Step 3a.4
	            int indexSubsystem = getUi().readInt();
	            SubSystem subsystem = subSystemList.get(indexSubsystem);

	            //Step 6
	        	getUi().display("What kind of change of bugreport do you want to be notified :");
	         	getUi().display("Creation of a new bugreport (1) / A bug report receiving a new tag (2)");
	         	getUi().display("A bug report receiving a specific tag (3) / A new comment for a bug report (4)");
	        	
	         	int indexChange = getUi().readInt();
	         	//Step 7
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
	    //Step 3b
	    else if( index == 3)
	    {
	    	//Step 3b.1
	    	getUi().display("Please select the bug report that you want to be notified: ");
	        BugReport bugReport = selectBugReport();

	        //Step 6
        	getUi().display("What kind of change of bugreport do you want to be notified :");
         	getUi().display("A bug report receiving a new tag (2) / A bug report receiving a specific tag (3)");
         	getUi().display("A new comment for a bug report (3)");
        	
         	int indexChange = getUi().readInt();
         	
         	//Step 7
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
         	            tag = Class.forName("Model.BugReport.TagTypes." + input);
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
