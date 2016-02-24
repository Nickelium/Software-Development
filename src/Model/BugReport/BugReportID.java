package Model.BugReport;

import java.util.UUID;

/**
 * Created by Tom on 20/02/16.
 */
public class BugReportID {

    private UUID Id;

    public BugReportID(){
        this.Id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return this.Id.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return this.Id.equals(obj);
    }
}