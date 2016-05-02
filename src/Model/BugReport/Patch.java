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

    //TODO doc
    private void setLines() {
        lines = value.length() - value.replaceAll("\\n", "").length() + 1;
    }

    public int getLines() {
        return lines;
    }

    public User getCreator() {
        return creator;
    }

    private void setCreator(User creator) {
        this.creator = creator;
    }

    public String toString() {
        return this.getValue();
    }

}
