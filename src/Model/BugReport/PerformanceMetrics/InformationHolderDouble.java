package Model.BugReport.PerformanceMetrics;

/**
 * Created by Karina on 13.05.2016.
 */
public class InformationHolderDouble implements IInformationHolder<Double> {

    /**
     * A description denoting what kind of information is provided by the value field.
     */
    private String description;

    /**
     * The value of the information that is being held in the information holder.
     */
    private Double value;

    /**
     * Package visible constructor to create a new information holder object.
     *
     * @param description the description for the information holder
     * @param value       the value assigned to a specific metric
     */
    InformationHolderDouble(String description, double value) {
        setDescription(description);
        setValue(value);
    }

    @Override
    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Double getValue() {
        return value;
    }

    private void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return description + " : " + value;
    }
}
