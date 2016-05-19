package Model.BugReport;

import Model.User.User;

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
    private int lines;
    private User creator;

    /**
     * Constructor to create a Test object.
     *
     * @param value the content of the new test
     */
    Test(String value, User user) {
        this.setValue(value);
        this.setCreator(user);
    }

    /**
     * Method to set a new value for a specific test.
     * @param value the new value to be set
     * @throws IllegalArgumentException Value is null.
     */
    private void setValue(String value) {
        if (value == null) throw new IllegalArgumentException("Value is null");
        this.value = value;
        setLines();
    }

    /**
     * Returns the value of the test object.
     * @return the value of the test object
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Method to calculate and set the amount of lines the test has.
     */
    private void setLines() {
        lines = value.length() - value.replaceAll("\\n", "").length() + 1;
    }

    /**
     * Method to return the amount of lines in the test.
     * @return the amount of lines in a test
     */
    public int getLines() {
        return lines;
    }

    /**
     * Method to return the creator of the test.
     * @return the creator of the test
     */
    public User getCreator() {
        return creator;
    }

    /**
     * Method to set the creator of the test.
     * @param creator the user which has created the test
     */
    private void setCreator(User creator) {
        this.creator = creator;
    }

    /**
     * Returns a textual representation of the value of the test object
     * @return a textual representation of the value of the test object
     */
    public String toString() {
        return this.getValue();
    }

}
