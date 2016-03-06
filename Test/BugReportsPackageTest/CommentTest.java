package BugReportsPackageTest;

import Model.BugReport.Comment;
import Model.Project.TheDate;
import Model.User.Issuer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tom on 28/02/16.
 */
public class CommentTest {
    private Issuer issuer;
    private Comment comment1;

    @Before
    public void initialization(){
        this.issuer = new Issuer("Test", "T", "Testing", "user1");
        this.comment1 = new Comment("Test Comment", this.issuer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalText(){
        new Comment(null, this.issuer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_IllegalIssuer(){
        new Comment("test", null);
    }

    @Test()
    public void getIssuerTest(){
        Comment comment = new Comment("test", issuer);
        assertEquals(this.issuer, comment.getIssuer());
    }

    @Test()
    public void getDateTest(){
        Comment comment = new Comment("test", issuer);
        assertEquals(TheDate.TheDateNow(), comment.getCreationDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addCommentTest_InvalidArgument(){
        Comment comment = new Comment("test", issuer);
        comment.addComment(null);
    }

    @Test()
    public void addCommentTest(){
        Comment comment = new Comment("test", issuer);
        assertEquals(0, comment.getComments().size());

        comment.addComment(this.comment1);

        assertEquals(1, comment.getComments().size());
    }

    @Test()
    public void getCommentTest(){
        Comment comment = new Comment("test", issuer);
        comment.addComment(this.comment1);
        assert comment.getComments().contains(this.comment1);
    }

    @Test()
    public void getText() {
        Comment comment = new Comment("test", issuer);
        assert comment.getText().equals("test");
    }

}
