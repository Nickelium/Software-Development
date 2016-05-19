package Model.HealthIndicator;

/**
 * Enumeration representing different health indicator values.
 */
public enum HealthIndicator {
    //Order as below is natural order.
    CRITICAL("Critical"),           //Ordinal value: 0
    SERIOUS("Serious"),             //Ordinal value: 1
    STABLE("Stable"),               //Ordinal value: 2
    SATISFACTORY("Satisfactory"),   //Ordinal value: 3
    HEALTHY("Healthy");             //Ordinal value: 4

    private String string;

    /**
     * Method to provide string representation of the enum values.
     * @param string the string to be represented
     */
    HealthIndicator(String string) {
        this.string = string;
    }

    /**
     * Method returning a string of the enum values
     * @return the string of the enum value
     */
    public String toString() {
        return this.string;
    }
}
