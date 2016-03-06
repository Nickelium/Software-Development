package BugReportsPackageTest;

import Model.BugReport.BugReportID;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Tom on 28/02/16.
 */
public class BugReportIDTest {
    private BugReportID id1;
    private BugReportID id2;


    @Before
    public void initialization(){
        this.id1 = new BugReportID();
        this.id2 = new BugReportID();
    }

    @Test
    public void equals_Valid(){
        BugReportID id = new BugReportID();
        assertEquals(id, id);
    }

    @Test
    public void equals_Invallid(){
        BugReportID idA = new BugReportID();
        BugReportID idB = new BugReportID();
        assertNotEquals(idA, idB);
    }

    @Test
    public void uniqueBugReportID(){
        BugReportID id = new BugReportID();
        assertNotEquals(id1, id2);
        assertNotEquals(id, id1);
        assertNotEquals(id, id2);
    }


}
