package Model.BugReport;

/**
 * Created by Tom on 7/04/16.
 */
public class Patch {
    private String value;

    public Patch(String value) {
        this.setValue(value);
    }

    private void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
