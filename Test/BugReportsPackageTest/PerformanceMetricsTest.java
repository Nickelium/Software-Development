package BugReportsPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.PerformanceMetrics.MetricsComponent;
import org.junit.Test;

import java.util.List;

/**
 * Created by Karina on 07.05.2016.
 */
public class PerformanceMetricsTest extends BugReportInitializaton {

    @Test
    public void createPerformanceMetricsForUser_Valid() throws ReportErrorToUserException {
        List<MetricsComponent> metricsComponentList = performanceMetricsService.createPerformanceMetricsForUser(dev3);

        assert metricsComponentList.get(1).getTitle().equals("Leadership");
        // tested in package HealthIndicatorTest

        assert metricsComponentList.get(2).getTitle().equals("Test skills");
        assert ((double) metricsComponentList.get(2).getInformation().get(0).getValue()) == 1.0;
        assert ((int) metricsComponentList.get(2).getInformation().get(1).getValue()) == 2;

        assert metricsComponentList.get(3).getTitle().equals("Problem solving");
        assert ((int) metricsComponentList.get(3).getInformation().get(0).getValue()) == 0;
        assert ((int) metricsComponentList.get(3).getInformation().get(1).getValue()) == 1;
        assert ((double) metricsComponentList.get(3).getInformation().get(2).getValue()) == 2.0;
        assert ((int) metricsComponentList.get(3).getInformation().get(3).getValue()) == 1;

    }

    @Test
    public void createPerformanceMetricsForUser_Valid2() throws ReportErrorToUserException {
        List<MetricsComponent> metricsComponentList = performanceMetricsService.createPerformanceMetricsForUser(dev2);

        assert metricsComponentList.get(0).getTitle().equals("Reporting");
        assert ((int) metricsComponentList.get(0).getInformation().get(0).getValue()) == 0;
        assert ((int) metricsComponentList.get(0).getInformation().get(1).getValue()) == 1;
        assert ((int) metricsComponentList.get(0).getInformation().get(2).getValue()) == 1;

        assert metricsComponentList.get(1).getTitle().equals("Leadership");
        // tested in package HealthIndicatorTest

        assert metricsComponentList.get(3).getTitle().equals("Problem solving");
        assert ((int) metricsComponentList.get(3).getInformation().get(0).getValue()) == 1;
        assert ((int) metricsComponentList.get(3).getInformation().get(1).getValue()) == 0;
        assert ((double) metricsComponentList.get(3).getInformation().get(2).getValue()) == 0.0;
        assert ((int) metricsComponentList.get(3).getInformation().get(3).getValue()) == 0;
    }

    @Test(expected = IllegalArgumentException.class)
    public void createPerformanceMetricsForUser_UnvalidUser() throws Exception {
        performanceMetricsService.createPerformanceMetricsForUser(issuer1);
    }

}
