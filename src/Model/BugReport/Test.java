package Model.BugReport;

/**
 * This class denotes a test object.
 *
 * A test is a piece of code that triggers a bug. Projects tracked by BugTrap
 * can be implemented in different programming languages and to have maximal
 * flexibility, tests are represented as text.
 *
 * The test text data are represented by the 'value' field.
 */
public class Test {

    private String value;

    Test(String value) {
        this.setValue(value);
    }

    /**
     * Method to set a new value for a specific test.
     * @param value the new value to be set
     */
    private void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns the value of the test object.
     * @return the value of the test object
     */
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.getValue();
    }

}
