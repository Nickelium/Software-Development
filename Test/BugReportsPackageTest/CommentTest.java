package BugReportsPackageTest;

import CustomExceptions.ModelException;
import Model.BugReport.Comment;
import Model.Project.TheDate;
import Model.User.Issuer;
import Model.User.UserService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tom on 28/02/16.
 */
public class CommentTest {
    private Issuer issuer;
    private Comment comment1;
    private UserService userService;

    @Before
    public void initialization() throws ModelException{
        userService = new UserService();
        this.issuer = (Issuer) userService.createIssuer("Test", "T", "Testing", "user1");
        this.comment1 = new Comment("Test Comment", this.issuer);
    }

    @Test(expected = ModelException.class)
    public void constructor_IllegalText() throws ModelException{
        new Comment(null, this.issuer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalIssuer() throws ModelException{
        new Comment("test", null);
    }

    @Test()
    public void getIssuerTest() throws ModelException{
        Comment comment = new Comment("test", issuer);
        assertEquals(this.issuer, comment.getIssuer());
    }

    @Test()
    public void getDateTest() throws ModelException{
        Comment comment = new Comment("test", issuer);
        assertEquals(TheDate.TheDateNow(), comment.getCreationDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addCommentTest_InvalidArgument() throws ModelException{
        Comment comment = new Comment("test", issuer);
        comment.addComment(null);
    }

    @Test()
    public void addCommentTest() throws ModelException{
        Comment comment = new Comment("test", issuer);
        assertEquals(0, comment.getComments().size());

        comment.addComment(this.comment1);

        assertEquals(1, comment.getComments().size());
    }

    @Test()
    public void getCommentTest() throws ModelException{
        Comment comment = new Comment("test", issuer);
        comment.addComment(this.comment1);
        assert comment.getComments().contains(this.comment1);
    }

    @Test()
    public void getText() throws ModelException{
        Comment comment = new Comment("test", issuer);
        assert comment.getText().equals("test");
    }

}
