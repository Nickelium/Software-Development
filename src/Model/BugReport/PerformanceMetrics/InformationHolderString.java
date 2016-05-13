package Model.BugReport.PerformanceMetrics;

// VALUE CLASS

/**
 * Value class representing an information holder object.
 * The information holder object contains a description about a certain way
 * that a part of a performance metric is measured. The associated value
 * for that metric is also contained within the object.
 */
public class InformationHolderString implements IInformationHolder<String> {

    /**
     * A description denoting what kind of information is provided by the value field.
     */
    private String description;

    /**
     * The value of the information that is being held in the information holder.
     */
    private String value;

    /**
     * Package visible constructor to create a new information holder object.
     *
     * @param description the description for the information holder
     * @param value the value assigned to a specific metric
     */
    InformationHolderString(String description, String value) {
        setDescription(description);
        setValue(value);
    }

    //region getters & setters

    /**
     * Returns the description of an information holder
     * @return the description field of the information holder object
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to set the description of an information holder.
     * @param description the new description to be set.
     */
    private void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the value of an information holder
     * @return the value field of the information holder object
     */
    public String getValue() {
        return value;
    }

    /**
     * Method to set the value of an information holder.
     * @param value the new value to be set.
     */
    private void setValue(String value) {
        this.value = value;
    }

    //endregion

}
