package Model.HealthIndicator;

/**
 * Created by Tom on 6/05/16.
 */
public enum HealthIndicator {
    //Order as below is natural order.
    CRITICAL("Critical"),           //Ordinal value: 0
    SERIOUS("Serious"),             //Ordinal value: 1
    STABLE("Stable"),               //Ordinal value: 2
    SATISFACTORY("Satisfactory"),   //Ordinal value: 3
    HEALTHY("Healthy");             //Ordinal value: 4

    private String string;

    HealthIndicator(String string) {
        this.string = string;
    }

    public String toString() {
        return this.string;
    }
}
