package Model.BugReport.PerformanceMetrics;

/**
 * Created by Karina on 06.05.2016.
 */

// VALUE CLASS
public class InformationHolder {

    private String description;
    private double value;

    InformationHolder(String description, double value) {
        setDescription(description);
        setValue(value);
    }

    //region getters & setters

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    private void setValue(double value) {
        this.value = value;
    }

    //endregion

}
