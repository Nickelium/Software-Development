package Model.HealthIndicator;

/**
 * Interface for health indicator algorithms.
 */
public interface IHealthIndicatorAlgorithm {

    /**
     * Method applying a health indicator algorithm and returning
     * the resulting health indicator.
     *
     * @param object The object of which the health indicator should be calculated.
     * @return the applicable health indicator object.
     */
    HealthIndicator get(IHealthIndicator object);
}
