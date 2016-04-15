package Model.BugReport.SearchMethod;

import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.Search;
import Model.User.User;
import Model.Wrapper.IListWrapper;

import java.util.Collections;
import java.util.List;

/**
 * Class extending the search class, providing functionality for searching bug reports
 * filed by a specific user. The user is passed on to the class through the constructor.
 */
public class SearchOnFiled extends Search
{
	private User user;

	/**
	 * Constructor to create a new Search On Filed object.
	 *
	 * @param user the user requesting the search
	 */
	public SearchOnFiled(User user)
	{
		this.user = user;
	}

	/**
	 * Method to execute the search command. Method returns a list of all bug reports
	 * that meet the search requirements.
	 *
	 * @param bugReportService the bug report service, requesting the search
	 * @param user the user requesting the search
	 * @return an unmodifiable list of all bug reports that meet the search requirements.
	 */
	@Override
	protected List<BugReport> apply(BugReportService bugReportService, User user)
	{
		IListWrapper<BugReport> bugReportList = getAllBugReportsWrapped(bugReportService, user);

		List<BugReport> bugReports = bugReportList.getAllMatching(x -> x.getCreator().equals(this.user));
		return Collections.unmodifiableList(bugReports);
		
	}
}
