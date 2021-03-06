package Model.HealthIndicator;

/**
 * Class representing the second health indicator algorithm.
 */
public class HealthIndicatorA2 implements IHealthIndicatorAlgorithm {

    /**
     * Method to apply the health indicator algorithm.
     * Returns the appropriate health indicator value.
     *
     * @param object The object of which the health indicator should be calculated.
     * @return the appropriate health indicator value
     */
    @Override
    public HealthIndicator get(IHealthIndicator object) {
        HealthIndicator compHealth = getHealthIndicatorComponentsHealth(object);
        HealthIndicator impactHealth = getBugImpactContainerHealth(object);

        if (compHealth.compareTo(impactHealth) == -1) {
            return compHealth;
        } else {
            return impactHealth;
        }

    }

    /**
     * Method to get which health value is the lowest of a list of health indicator components.
     *
     * @param object The object of which the health indicator should be calculated.
     * @return the health indicator with the lowest value
     */
    private HealthIndicator getHealthIndicatorComponentsHealth(IHealthIndicator object) {
        HealthIndicator minHealthValue = HealthIndicator.HEALTHY;
        for (IHealthIndicator obj : object.getDirectHealthIndicatorComponents()) {
            HealthIndicator healthValue = this.get(obj);
            if (healthValue.compareTo(minHealthValue) == -1) {
                minHealthValue = healthValue;
            }
        }

        if (minHealthValue == HealthIndicator.SATISFACTORY) {
            return HealthIndicator.HEALTHY;
        } else {
            return minHealthValue;
        }
    }

    /**
     * Method implementing the metrics of a specific algorithm.
     * The impact of a bug is deciding for the kind of health indicator being returned.
     *
     * Healthy All direct components3 are Healthy and
     * if S is a subsystem, then BugImpact(S) < 50
     *
     * Satisfactory All direct components are Satisfactory or higher and
     * if S is a subsystem, then BugImpact(S) < 100
     *
     * Stable All direct components are Stable or higher and
     * if S is a subsystem, then BugImpact(S) < 500
     *
     * Serious All direct components are Serious or higher and
     * if S is a subsystem, then BugImpact(S) < 5000
     *
     * Critical Otherwise
     *
     * @param object the object for which a bug impact is being calculated,
     *               usually a subsystem.
     * @return the applicable health indicator value for the given object
     */
    private HealthIndicator getBugImpactContainerHealth(IHealthIndicator object) {
        double impact = 0.0;

        if (object instanceof IHealthIndicatorAndBugImpact) {
            impact = ((IHealthIndicatorAndBugImpact) object).getBugImpact();
        }

        if (impact < 50) {
            return HealthIndicator.HEALTHY;
        }
        if (impact < 100) {
            return HealthIndicator.SATISFACTORY;
        }
        if (impact < 500) {
            return HealthIndicator.STABLE;
        }
        if (impact < 5000) {
            return HealthIndicator.SERIOUS;
        } else {
            return HealthIndicator.CRITICAL;
        }
    }
}
