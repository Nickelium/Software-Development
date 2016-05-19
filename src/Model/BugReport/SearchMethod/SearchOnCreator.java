package Model.BugReport.SearchMethod;

import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.Search;
import Model.User.Issuer;
import Model.User.User;
import Model.Wrapper.IListWrapper;

import java.util.Collections;
import java.util.List;

/**
 * Class extending the search class, providing functionality to look for which bug reports
 * a specific developer has submitted, containing any tag. The developer name
 * is passed on to the class through the constructor.
 */
public class SearchOnCreator extends Search {
    private User user;

    /**
     * Constructor to create a new Search-On-Creator object.
     *
     * @param user the developer who has submitted the bug reports
     */
    public SearchOnCreator(User user) {
        if (user == null) throw new IllegalArgumentException("User is null");
        if (!(user instanceof Issuer)) throw new IllegalArgumentException("This is not a issuer.");
        this.user = user;
    }


    /**
     * Method to execute the search command. Method returns a list of all bug reports
     * that meet the search requirements.
     *
     * @param bugReportService the bug report service, requesting the search
     * @param user the developer that has submitted the bug report
     *
     * @return an unmodifiable list of all bug reports that meet the search requirements.
     *
     * @throws IllegalArgumentException is thrown if bugReportService or user are null.
     */
    @Override
    protected List<BugReport> apply(BugReportService bugReportService, User user) {
        if (bugReportService == null) throw new IllegalArgumentException("BugreportService is null");
        if (user == null) throw new IllegalArgumentException("User is null");

        IListWrapper<BugReport> bugReportList = getAllBugReportsWrapped(bugReportService, user);

        List<BugReport> bugReports = bugReportList.getAllMatching(x -> x.getCreator().equals(this.user));
        return Collections.unmodifiableList(bugReports);

    }
}
