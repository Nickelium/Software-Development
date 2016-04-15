package Model.BugReport.SearchMethod;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.Search;
import Model.User.User;

import java.util.Collections;
import java.util.List;

/**
 * Class extending the search class, providing functionality for searching bug reports
 * by a specific string in the title. The title string is passed on to
 * the class through the constructor.
 */
public class SearchOnTitle extends Search
{
	private String title;

	/**
	 * Constructor to create a new Search On Title object.
	 *
	 * @param title the title to search for
	 */
	public SearchOnTitle(String title)
	{
		this.title = title;
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
	protected List<BugReport> apply(BugReportService bugReportService, User user) throws ReportErrorToUserException
	{
		if (!isValidTitleString(title)) throw new ReportErrorToUserException("Invalid title");
		List<BugReport> bugreports = getAllBugReportsWrapped(bugReportService, user).getAllMatching(x -> x.getTitle().contains(title));
		return Collections.unmodifiableList(bugreports);
		
	}
	
    /**
     * Checker to check if the string for filtering is a valid string.
     *
     * @param title The string part to check.
     *
     * @return True if the title is not null, empty or whitespace.
     */
    private boolean isValidTitleString(String title) {
        if (title == null) return false;
        if (title.equals("")) return false;
        if (title.equals(" ")) return false;
        return true;
    }





}
