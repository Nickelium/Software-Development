package Model.Tags;

import Model.BugReport;
import Model.Project;
import Model.Roles.Programmer;
import Model.Roles.Role;
import Model.User;

/**
 * Created by Tom on 19/02/16.
 */
public abstract class Tag{

     public abstract String toString();

    public abstract boolean equals(Object obj);
}
