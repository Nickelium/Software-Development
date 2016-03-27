package MilestonePackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.Milestone.Milestone;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by Laurens on 27/03/2016.
 */
public class MilestoneTest {

    protected Milestone milestoneNoID;
    protected Milestone milestoneValidID;
    protected Milestone milestoneInvalidID1;
    protected Milestone milestoneInvalidID2;

    @Before
    public void initialization() throws ReportErrorToUserException {

        Milestone milestoneNoID = new Milestone();
        Milestone milestoneValidID = new Milestone("M0.5.6");
        Milestone milestoneInvalidID1 = new Milestone("X0.5.6");
        Milestone milestoneInvalidID2 = new Milestone("M0.5.6/4");

    }

    @Test
    public void isValidIDTest1(){
        assertTrue(milestoneNoID.isValidMilestoneID(milestoneNoID.getMilestoneID()));
    }

    @Test
    public void isValidIDTest2(){
        assertTrue(milestoneValidID.isValidMilestoneID(milestoneValidID.getMilestoneID()));
    }

    @Test
    public void isValidIDTest3(){
        assertFalse(milestoneInvalidID1.isValidMilestoneID(milestoneInvalidID1.getMilestoneID()));
    }

    @Test
    public void isValidIDTest4(){
        assertFalse(milestoneInvalidID2.isValidMilestoneID(milestoneInvalidID2.getMilestoneID()));
    }

}
