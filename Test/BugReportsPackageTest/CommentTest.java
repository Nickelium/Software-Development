package BugReportsPackageTest;

import CustomExceptions.ModelException;
import Model.BugReport.Comment;
import Model.Project.TheDate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tom on 28/02/16.
 */
public class CommentTest extends BugReportInitializaton {

    @Test()
    public void getIssuerTest() throws ModelException{
        assertEquals(issuer1, comment1.getIssuer());
    }

    @Test()
    public void getDateTest() throws ModelException{
        assertEquals(TheDate.TheDateNow(), comment1.getCreationDate());
    }

    @Test()
    public void addCommentTest() throws ModelException{
        assertEquals(0, comment1.getComments().size());

        bugReportService.createComment("Dit is een test reactie!", issuer2, comment1);

        assertEquals(1, comment1.getComments().size());
    }

    @Test()
    public void getCommentTest() throws ModelException{
        Comment comment = bugReportService.createComment("Test 2 comment", issuer1, comment1);
        assert comment1.getComments().contains(comment);
    }

    @Test()
    public void getText() throws ModelException{
        assert comment1.getText().equals("Test Comment");
    }

}
