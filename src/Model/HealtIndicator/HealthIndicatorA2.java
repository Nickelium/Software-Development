package Model.HealtIndicator;

/**
 * Created by Tom on 6/05/16.
 */
public class HealthIndicatorA2 implements IHealtIndicatorAlgorithm {
    @Override
    public HealthIndicator get(IHealtIndicator object) {
        int compHealth = getHealthIndicatorComponentsHealth(object);
        int impactHealth = getBugImpactContainerHealth(object);

        int health = Math.min(compHealth, impactHealth);
        return HealthIndicator.getHealtIndicatorByValue(health);

    }

    private int getHealthIndicatorComponentsHealth(IHealtIndicator object) {
        int minHealthValue = HealthIndicator.HEALTHY.getValue();
        for (IHealtIndicator obj : object.getDirectHealthIndicatorComponents()) {
            int healthValue = this.get(obj).getValue();
            if (healthValue < minHealthValue) {
                minHealthValue = healthValue;
            }
        }
        if (minHealthValue == HealthIndicator.SATISFACTORY.getValue()) {
            return HealthIndicator.HEALTHY.getValue();
        } else {
            return minHealthValue;
        }
    }

    private int getBugImpactContainerHealth(IHealtIndicator object) {
        double impact = 0.0;

        if (object instanceof IHealthIndicatorAndBugImpact) {
            impact = ((IHealthIndicatorAndBugImpact) object).getBugImpact();
        }

        if (impact < 50) {
            return HealthIndicator.HEALTHY.getValue();
        }
        if (impact < 100) {
            return HealthIndicator.SATISFACTORY.getValue();
        }
        if (impact < 500) {
            return HealthIndicator.STABLE.getValue();
        }
        if (impact < 500) {
            return HealthIndicator.SERIOUS.getValue();
        } else {
            return HealthIndicator.CRITICAL.getValue();
        }
    }
}
