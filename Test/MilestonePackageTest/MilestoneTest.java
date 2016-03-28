package MilestonePackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.Milestone.Milestone;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by Laurens on 27/03/2016.
 */
public class MilestoneTest {

    @Before
    public void initialization(){

        // Niets

    }

    @Test
    public void isValidIDTest1(){
        Milestone milestoneNoID = new Milestone();
        assertTrue(milestoneNoID.isValidMilestoneID(milestoneNoID.getMilestoneID()));
    }

    @Test
    public void isValidIDTest2() throws ReportErrorToUserException {
        Milestone milestoneValidID = new Milestone("M0.5.6");
        assertTrue(milestoneValidID.isValidMilestoneID(milestoneValidID.getMilestoneID()));
    }

    @Test (expected = ReportErrorToUserException.class)
    public void isValidIDTest3() throws ReportErrorToUserException {
        Milestone milestoneInvalidID1 = new Milestone("X0.5.6");
    }

    @Test (expected = ReportErrorToUserException.class)
    public void isValidIDTest4() throws ReportErrorToUserException {
        Milestone milestoneInvalidID2 = new Milestone("M0.5.6/4");
    }

}
