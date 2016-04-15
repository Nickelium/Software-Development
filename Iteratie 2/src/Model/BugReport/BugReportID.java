package Model.BugReport;

import java.util.UUID;

/**
 * Class representing a Bug report ID object.
 *
 * Contains an field representing a random ID.
 */
public class BugReportID {

    private UUID Id;

    /**
     * Default constructor for a BugReportId.
     */
    public BugReportID(){
        this.Id = UUID.randomUUID();
    }

    /**
     * To string overrided to print the id
     *
     * @return String representation of the id.
     */
    @Override
    public String toString() {
        return this.Id.toString();
    }

    /**
     * Equals overrided to check for equality of the ids
     *
     * @param obj The other id to check the equality with
     *
     * @return True if the two objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
}
