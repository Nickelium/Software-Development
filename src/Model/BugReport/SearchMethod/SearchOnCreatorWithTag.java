package Model.BugReport.SearchMethod;

import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.Search;
import Model.BugReport.Tag;
import Model.User.Issuer;
import Model.User.User;
import Model.Wrapper.IListWrapper;

import java.util.Collections;
import java.util.List;

/**
 * Class extending the search class, providing functionality to look for which bug reports
 * a specific developer has submitted, containing a specific tag. The tag type and developer name
 * are passed on to the class through the constructor.
 */
public class SearchOnCreatorWithTag extends Search {
    private User user;
    private Class<? extends Tag> tag;

    /**
     * Constructor to create a new Search-On-Creator-With-Tag object.
     *
     * @param user the developer who filed the bug reports
     * @param tag the tag that the bug reports have to contain.
     */
    public SearchOnCreatorWithTag(User user, Class<? extends Tag> tag) {
        if (user == null) throw new IllegalArgumentException("User is null");
        if (!(user instanceof Issuer)) throw new IllegalArgumentException("This is not a issuer.");
        this.user = user;
        this.tag = tag;
    }


    /**
     * Method to execute the search command. Method returns a list of all bug reports
     * that meet the search requirements.
     *
     * @param bugReportService the bug report service, requesting the search
     * @param user the developer who filed the bug reports
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

        List<BugReport> bugReports = bugReportList.getAllMatching(x -> x.getCreator().equals(this.user) && (x.getTag().getClass().equals(tag)));
        return Collections.unmodifiableList(bugReports);

    }
}
