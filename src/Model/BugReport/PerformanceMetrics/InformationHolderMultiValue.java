package Model.BugReport.PerformanceMetrics;

// VALUE CLASS

import java.util.ArrayList;
import java.util.List;

/**
 * Value class representing an information holder object with multiple values.
 * The values are a list of Information Holders, containing strings.
 */
public class InformationHolderMultiValue implements IInformationHolder<List<IInformationHolder<String>>> {

    /**
     * The description of what data the information holder holds.
     */
    private String description;

    /**
     * The value of the information holder
     */
    private List<IInformationHolder<String>> value;

    /**
     * Constructor to create an empty multi value information holder object.
     * @param description the description of what data the information holder holds.
     */
    InformationHolderMultiValue(String description) {
        setDescription(description);
        this.value = new ArrayList<IInformationHolder<String>>();
    }

    //region getters & setters

    /**
     * Returns the description of the information holder
     * @return the description field of the information holder object
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to set the description of the information holder object.
     * @param description the new description to be set
     */
    private void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the value of an information holder: a list of information holder values.
     * @return the list of values.
     */
    public List<IInformationHolder<String>> getValue() {
        return value;
    }

    /**
     * Method to add a new value to the information holder object.
     * @param informationHolder the new value (information holder) to added to the list of values
     */
    void addValue(IInformationHolder<String> informationHolder) {
        this.value.add(informationHolder);
    }

    //endregion

    /**
     * Method to represent the information holder as a string.
     * @return a textual representation of the multi value information holder object
     */
    @Override
    public String toString() {
        String str = description + ":";
        for (IInformationHolder<String> value : getValue()) {
            str += "\n   + " + value.toString();
        }
        return str;
    }

}
