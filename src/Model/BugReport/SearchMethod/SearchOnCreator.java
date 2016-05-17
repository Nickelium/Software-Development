package Model.BugReport.SearchMethod;

import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.Search;
import Model.User.Issuer;
import Model.User.User;
import Model.Wrapper.IListWrapper;

import java.util.Collections;
import java.util.List;

//TODO
public class SearchOnCreator extends Search {
    private User user;

    //TODO
    public SearchOnCreator(User user) {
        if (user == null) throw new IllegalArgumentException("User is null");
        if (!(user instanceof Issuer)) throw new IllegalArgumentException("This is not a issuer.");
        this.user = user;
    }


    //TODO
    @Override
    protected List<BugReport> apply(BugReportService bugReportService, User user) {
        if (bugReportService == null) throw new IllegalArgumentException("BugreportService is null");
        if (user == null) throw new IllegalArgumentException("User is null");

        IListWrapper<BugReport> bugReportList = getAllBugReportsWrapped(bugReportService, user);

        List<BugReport> bugReports = bugReportList.getAllMatching(x -> x.getCreator().equals(this.user));
        return Collections.unmodifiableList(bugReports);

    }
}
