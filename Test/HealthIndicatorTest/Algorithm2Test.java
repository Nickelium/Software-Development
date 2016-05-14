package HealthIndicatorTest;

import CustomExceptions.ReportErrorToUserException;
import Model.HealthIndicator.HealthIndicator;
import Model.HealthIndicator.HealthIndicatorA2;
import Model.HealthIndicator.IHealthIndicatorAlgorithm;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tom on 7/05/16.
 */
public class Algorithm2Test extends HealthIndicatorTestInitialization {

    @Test
    public void algorithm1Test_Subsystem3() {
        IHealthIndicatorAlgorithm algorithm = new HealthIndicatorA2();
        HealthIndicator h = algorithm.get(this.subSystem3);

        assertEquals(HealthIndicator.SATISFACTORY, h);
    }

    //Healthy instead of Satisfactory in algoritm1;
    @Test
    public void algorithm1Test_Project1() {
        IHealthIndicatorAlgorithm algorithm = new HealthIndicatorA2();
        HealthIndicator h = algorithm.get(project1);

        assertEquals(HealthIndicator.HEALTHY, h);
    }

    @Test
    public void algorithm1Test_Subsystem3DifferentStates() throws ReportErrorToUserException {
        this.changeBugReportTagToAssigned(bugReport2);
        this.changeBugReportTagToResolved(bugReport4);

        IHealthIndicatorAlgorithm algorithm = new HealthIndicatorA2();
        HealthIndicator h = algorithm.get(this.subSystem3);

        assertEquals(HealthIndicator.HEALTHY, h);
    }
}
