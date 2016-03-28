package MilestonePackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.Milestone.Milestone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Laurens on 27/03/2016.
 */
public class MilestoneTest {

    @Before
    public void initialization(){

    }

    @Test
    public void isValidIDTest1(){
        Milestone milestoneNoID = new Milestone();
        assertTrue(milestoneNoID.isValidMilestoneID(milestoneNoID.getMilestoneID()));
    }

    @Test
    public void isValidIDTest2() throws ReportErrorToUserException {
        Milestone milestoneValidID = new Milestone("M0.5.60");
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

    @Test(expected = ReportErrorToUserException.class)
    public void isValidIDTest5() throws ReportErrorToUserException{
        Milestone milestoneInvalidID3 = new Milestone("M0.500.1");
    }

    @Test
    public void getIDValueTest1() throws ReportErrorToUserException {
        Milestone milestone = new Milestone("M0.8.9");
        assertEquals(0.0809, milestone.getIDvalue(), 0.0000001);
    }

    @Test
    public void getIDValueTest2() throws ReportErrorToUserException {
        Milestone milestone = new Milestone("M0.80.9");
        assertEquals(0.8009, milestone.getIDvalue(), 0.0000001);
    }

    @Test
    public void getIDValueTest3() throws ReportErrorToUserException {
        Milestone milestone = new Milestone("M0.8.95");
        assertEquals(0.0895, milestone.getIDvalue(), 0.0000001);
    }

    @Test
    public void getIDValueTest4() throws ReportErrorToUserException {
        Milestone milestone = new Milestone("M0.1.2.3.4.5.6.7.8.9");
        assertEquals(0.010203040506070809, milestone.getIDvalue(), 0.0000001);
    }

    @Test
    public void getIDValueTest5() throws ReportErrorToUserException {
        Milestone milestone = new Milestone("M1");
        assertEquals(1.0, milestone.getIDvalue(), 0.0000001);
    }

    @Test
    public void getIDValueTest6() throws ReportErrorToUserException {
        Milestone milestone = new Milestone("M12");
        assertEquals(12.0, milestone.getIDvalue(), 0.0000001);
    }

    @Test
    public void getIDValueTestComparison() throws ReportErrorToUserException{
        Milestone milestone1 = new Milestone("M3.2.1.0");
        Milestone milestone2 = new Milestone("M3.2.1.1");
        assertTrue(milestone1.getIDvalue() < milestone2.getIDvalue());
    }
}
