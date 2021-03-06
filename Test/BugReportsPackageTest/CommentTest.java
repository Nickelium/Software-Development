package BugReportsPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.Comment;
import Model.Project.TheDate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tom on 28/02/16.
 */
public class CommentTest extends BugReportInitializaton {

    @Test()
    public void getIssuerTest() throws ReportErrorToUserException {
        assertEquals(issuer1, comment1.getIssuer());
    }

    @Test()
    public void getDateTest() throws ReportErrorToUserException {
        assertEquals(TheDate.TheDateNow(), comment1.getCreationDate());
    }

    @Test()
    public void addCommentTest() throws ReportErrorToUserException {
        assertEquals(0, comment1.getComments().size());

        bugReportService.createComment("Dit is een test reactie!", issuer2, comment1);

        assertEquals(1, comment1.getComments().size());
    }

    @Test()
    public void getCommentTest() throws ReportErrorToUserException {
        Comment comment = bugReportService.createComment("Test 2 comment", issuer1, comment1);
        assert comment1.getComments().contains(comment);
    }

    @Test
    public void getAllCommentTest() throws ReportErrorToUserException {
        Comment comment = bugReportService.createComment("Test 3 comment", issuer1, comment1);
        Comment subComment = bugReportService.createComment("Test 4 comment", issuer2, comment);
        assert comment1.getAllComments().contains(subComment);
        assert comment1.getAllComments().contains(comment);
    }

    @Test()
    public void getText() throws ReportErrorToUserException {
        assert comment1.getText().equals("Test Comment");
    }

}
