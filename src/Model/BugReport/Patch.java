package Model.BugReport;

import Model.User.User;

/**
 * Created by Tom on 7/04/16.
 */
public class Patch {
    private String value;
    private User user;


    Patch(String value) {
        this.setValue(value);
    }

    private void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.getValue();
    }
}
