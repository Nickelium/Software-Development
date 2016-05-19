package Model.HealthIndicator;

import java.util.List;

/**
 * Interface for health indicator classes
 */
public interface IHealthIndicator {

    /**
     * Method to get health indicator components of an object.
     * @return List of the health indicator components.
     */
    List<IHealthIndicator> getDirectHealthIndicatorComponents();
}
