package Model.HealthIndicator;

import java.util.List;

/**
 * Interface for health indicator classes
 */
public interface IHealthIndicator {

    /**
     * Method to get health indicator components of an object.
     * @return the applicable health indicator, calculated by a chosen algorithm.
     */
    List<IHealthIndicator> getDirectHealthIndicatorComponents();
}
