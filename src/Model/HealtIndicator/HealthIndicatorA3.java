package Model.HealtIndicator;

/**
 * Created by Tom on 6/05/16.
 */
public class HealthIndicatorA3 implements IHealthIndicatorAlgorithm {
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

    private HealthIndicator getHealthIndicatorComponentsHealth(IHealthIndicator object) {
        HealthIndicator minHealthValue = HealthIndicator.HEALTHY;
        for (IHealthIndicator obj : object.getDirectHealthIndicatorComponents()) {
            HealthIndicator healthValue = this.get(obj);
            if (healthValue.compareTo(minHealthValue) == -1) {
                minHealthValue = healthValue;
            }
        }

        if (minHealthValue == HealthIndicator.STABLE) {
            return HealthIndicator.SATISFACTORY;
        } else {
            //Return the health of the minimum health value.
            return minHealthValue;
        }
    }

    private HealthIndicator getBugImpactContainerHealth(IHealthIndicator object) {
        double impact = 0.0;

        if (object instanceof IHealthIndicatorAndBugImpact) {
            impact = ((IHealthIndicatorAndBugImpact) object).getBugImpact();
        }

        if (impact < 10) {
            return HealthIndicator.HEALTHY;
        }
        if (impact < 100) {
            return HealthIndicator.SATISFACTORY;
        }
        if (impact < 250) {
            return HealthIndicator.STABLE;
        }
        if (impact < 500) {
            return HealthIndicator.SERIOUS;
        } else {
            return HealthIndicator.CRITICAL;
        }
    }
}
