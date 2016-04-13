package Model.BugReport;

/**
 * This class denotes a patch object.
 *
 * A patch object contains proposed corrections that can solve a bug.
 * The proposed corrections are represented by the 'value' field.
 */
public class Patch {

    private String value;

    Patch(String value) {
        this.setValue(value);
    }

    /**
     * Method to set a new value for a specific patch.
     * @param value the new value to be set
     */
    private void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns the value of the patch object.
     * @return the value of the patch object
     */
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.getValue();
    }
}
