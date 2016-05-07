package Model.HealtIndicator;

/**
 * Created by Tom on 6/05/16.
 */
public enum HealthIndicator {

    HEALTHY(5, "Healthy"),
    SATISFACTORY(4, "Satisfactory"),
    STABLE(3, "Stable"),
    SERIOUS(2, "Serious"),
    CRITICAL(1, "Critical");

    private int value;
    private String string;

    HealthIndicator(int value, String string) {
        this.value = value;
        this.string = string;
    }

    public static HealthIndicator getHealthIndicatorByValue(int value) {
        for (HealthIndicator indic : HealthIndicator.values()) {
            if (indic.getValue() == value) {
                return indic;
            }
        }
        return null;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return this.string;
    }
}
