package Controller.UserController.UseCases.IssuerUseCases;

import Controller.Formatter;
import Controller.IUI;
import CustomExceptions.ModelException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.Comment;
import Model.Project.ProjectService;
import Model.User.Issuer;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

/**
 * Created by Karina on 24.03.2016.
 */
public class CreateComment extends IssuerUseCase {

    public CreateComment(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, null, currentUser);
    }

    /**
     *
     * Lets an Issuer create a comment onto a bug report or an other comment.
     *
     * 2. Include use case Select Bug Report.
     * 3. The system shows a list of all comments of the selected bug report.
     * 4. The issuer indicates if he wants to comment directly on the bug report
     *    or on some other comment.
     * 5. The system asks for the text of the comment.
     * 6. The issuer writes his comment.
     * 7. The system adds the comment to the selected use case.
     *
     *
     * @throws ModelException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     *
     */
    @Override
    public void run() throws ModelException, IndexOutOfBoundsException {
        // Step 2
        BugReport bugReport = selectBugReport();

        // Step 3
        List<Comment> listComment = bugReport.getAllComments();
        if (listComment.size() > 0) {
            getUi().display("List of all comments of this bugreport:");
            String parsedListComment = Formatter.formatCommentList(listComment);
            getUi().display(parsedListComment);
        }

        // Step 4
        getUi().display("Create a comment on the bugreport or on one of the comments (B/C) : ");
        String input = getUi().readString();

        if (input.equalsIgnoreCase("b")) {

            // Step 5
            getUi().display("Comment (Terminate with a . on a new line):");

            // Step 6
            String text = getUi().readMultiline();

            // Step 7
            Comment newComment = getBugReportService().createComment(text, (Issuer) getCurrentUser(), bugReport);
            getUi().display("The comment was:\n"
                    + "-------------------------\n"
                    + newComment
                    + "\n-------------------------\n"
                    + "It has successfully been created.\n");

        } else if (input.equalsIgnoreCase("c")) {
            int index;
            getUi().display("Choose a comment from one of above: ");
            index = getUi().readInt();
            Comment comm = listComment.get(index);

            // Step 5
            getUi().display("Comment (Sluit af met . op nieuwe lijn):");

            // Step 6
            String text = getUi().readMultiline();

            // Step 7
            Comment newComment = getBugReportService().createComment(text, (Issuer) getCurrentUser(), comm);
            getUi().display("The comment was:\n" + newComment + "\nIt has successfully been created.\n");
        } else {
            throw new ModelException("This is an invalid input");
        }
    }
}
