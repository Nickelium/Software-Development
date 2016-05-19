package Model.BugReport;

import Model.User.User;

/**
 * This class denotes a patch object.
 *
 * A patch object contains proposed corrections that can solve a bug.
 * The proposed corrections are represented by the 'value' field.
 */
public class Patch {

    private String value;
    private int lines;
    private User creator;

    /**
     * Constructor to create a Patch object.
     *
     * @param value the content of the new patch
     * */
    Patch(String value, User user) {
        this.setValue(value);
        this.setCreator(user);
    }

    /**
     * Method to set a new value for a specific patch.
     * @param value the new value to be set
     *
     * @throws IllegalArgumentException Value is null.
     */
    private void setValue(String value) {
        if (value == null) throw new IllegalArgumentException("Value is null");
        this.value = value;
        setLines();
    }

    /**
     * Returns the value of the patch object.
     * @return the value of the patch object
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Method to calculate and set the amount of lines the patch has.
     */
    private void setLines() {
        lines = value.length() - value.replaceAll("\\n", "").length() + 1;
    }

    /**
     * Method to return the amount of lines in the patch.
     * @return the amount of lines in a patch
     */
    public int getLines() {
        return lines;
    }

    /**
     * Method to return the creator of the patch.
     * @return the creator of the patch
     */
    public User getCreator() {
        return creator;
    }

    /**
     * Method to set the creator of the patch.
     * @param creator the user which has created the patch
     */
    private void setCreator(User creator) {
        this.creator = creator;
    }

    /**
     * Returns a textual representation of the value of the patch object
     * @return a textual representation of the value of the patch object
     */
    public String toString() {
        return this.getValue();
    }

}
