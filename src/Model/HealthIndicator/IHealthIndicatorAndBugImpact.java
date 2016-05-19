package Model.HealthIndicator;

/**
 * Interface extending the IHealthIndicator interface,
 * for health indicator and bug impact classes.
 */
public interface IHealthIndicatorAndBugImpact extends IHealthIndicator {

    /**
     * Method to be implemented to get the bug impact of a given object.
     * @return the bug impact of a given object
     */
    double getBugImpact();
}
