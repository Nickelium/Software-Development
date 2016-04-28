package MilestonePackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.Milestone.Milestone;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Laurens on 27/03/2016.
 */
public class MilestoneTest {

    @Test
    public void milestoneStringToArrayTest() throws ReportErrorToUserException {
        Milestone milestone = new Milestone("M5.6.7");
        int[] array = new int[]{5,6,7};
        int index = 0;
        for(int i : milestone.getLayeredMilestone()){
            assertEquals(i, array[index]);
            index++;
        }

    }

    @Test
    public void isValidIDTest1() throws ReportErrorToUserException {
        Milestone milestoneNoID = new Milestone();
        assertTrue(milestoneNoID.isValidMilestoneID(milestoneNoID.getMilestoneID()));
    }

    @Test
    public void isValidIDTest2() throws ReportErrorToUserException {
        Milestone milestoneValidID = new Milestone("M0.5.60");
        assertTrue(milestoneValidID.isValidMilestoneID(milestoneValidID.getMilestoneID()));
        assertTrue(milestoneValidID.getMilestoneID().equals("M0.5.60"));
    }

    @Test (expected = ReportErrorToUserException.class)
    public void isValidIDTest3() throws ReportErrorToUserException {
        Milestone milestoneInvalidID = new Milestone("X0.5.6");
    }

    @Test (expected = ReportErrorToUserException.class)
    public void isValidIDTest4() throws ReportErrorToUserException {
        Milestone milestoneInvalidID = new Milestone("M0.5.6/4");
    }

    @Test (expected = ReportErrorToUserException.class)
    public void isValidIDTest5() throws ReportErrorToUserException {
        Milestone milestoneInvalidID = new Milestone("M0.5.604.");
    }

    @Test (expected = ReportErrorToUserException.class)
    public void isValidIDTest6() throws ReportErrorToUserException {
        Milestone milestoneInvalidID = new Milestone("M0.5.604µ");
    }

    @Test
    public void isValidIDTest7() throws ReportErrorToUserException{
        Milestone milestoneValidID = new Milestone("M0.500.1");
        assertTrue(milestoneValidID.isValidMilestoneID(milestoneValidID.getMilestoneID()));
        assertTrue(milestoneValidID.getMilestoneID().equals("M0.500.1"));
    }

    @Test (expected = ReportErrorToUserException.class)
    public void isValidIDTest8() throws ReportErrorToUserException{
        Milestone milestoneInvalidID = new Milestone("M0..1");
    }

    @Test
    public void getMilestoneIDTest_VALID() throws ReportErrorToUserException {
        Milestone milestone = new Milestone("M0.8.9");
        assertEquals(milestone.getMilestoneID(),"M0.8.9");
    }

    @Test
    public void getMilestoneIDTest_INVALID() throws ReportErrorToUserException {
        Milestone milestone = new Milestone("M0.8.9");
        assertNotEquals(milestone.getMilestoneID(), "M0.8.8");
    }

    @Test
    public void getLayeredMilestoneTest_VALID() throws ReportErrorToUserException {
        Milestone milestone = new Milestone("M0.80.9");
        assertEquals(milestone.getLayeredMilestone()[0],0);
        assertEquals(milestone.getLayeredMilestone()[1],80);
        assertEquals(milestone.getLayeredMilestone()[2],9);
    }

    @Test
    public void getLayeredMilestoneTest_INVALID() throws ReportErrorToUserException {
        Milestone milestone = new Milestone("M0.80.9");
        assertEquals(milestone.getLayeredMilestone()[0],0);
        assertNotEquals(milestone.getLayeredMilestone()[1],30);
        assertEquals(milestone.getLayeredMilestone()[2],9);
    }

    @Test
    public void testMilestoneComparison1() throws ReportErrorToUserException{
        Milestone milestone1 = new Milestone("M1.5.8");
        Milestone milestone2 = new Milestone("M2.0.0");
        assertEquals(milestone1.compareTo(milestone2), -1);
        assertEquals(milestone2.compareTo(milestone1), 1);
    }

   @Test
    public void testMilestoneComparison2() throws ReportErrorToUserException{
       Milestone milestone1 = new Milestone("M1");
       Milestone milestone2 = new Milestone("M2");
       assertEquals(milestone1.compareTo(milestone2), -1);
       assertEquals(milestone2.compareTo(milestone1), 1);
   }
}
