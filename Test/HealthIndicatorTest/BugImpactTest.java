package HealthIndicatorTest;

import CustomExceptions.ReportErrorToUserException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tom on 7/05/16.
 */
public class BugImpactTest extends HealthIndicatorTestInitialization {

    private static final double DELTA = 1e-15;

    @Test
    public void allNewTagTest() {
        double impact = subSystem3.getBugImpact();
        assertEquals(54.0, impact, DELTA);
    }

    @Test
    public void differentTagsTest() throws ReportErrorToUserException {
        changeBugReportTagToResolved(bugReport1);
        changeBugReportTagToAssigned(bugReport5);

        double impact = subSystem1.getBugImpact();
        assertEquals(4.7, impact, DELTA);
    }


}
