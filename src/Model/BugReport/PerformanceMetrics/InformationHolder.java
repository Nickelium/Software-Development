package Model.BugReport.PerformanceMetrics;

// VALUE CLASS

/**
 * Value class representing an information holder object.
 * The information holder object contains a description about a certain way
 * that a part of a performance metric is measured. The associated value
 * for that metric is also contained within the object.
 */
public class InformationHolder {

    /**
     * A description denoting what kind of information is provided by the value field.
     */
    private String description;

    /**
     * //TODO value zou string moeten zijn, refactoring nodig voor alle uses met information holders
     */
    private String value;

    InformationHolder(String description, String value) {
        setDescription(description);
        setValue(value);
    }

    //region getters & setters

    //TODO
    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        this.value = value;
    }

    //endregion

}
