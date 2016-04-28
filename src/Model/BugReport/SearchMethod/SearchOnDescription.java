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
 * by a specific string in the description. The description string is passed on to
 * the class through the constructor.
 */
public class SearchOnDescription extends Search
{
	private String description;

	/**
	 * Constructor to create a new Search On Description object.
	 *
	 * @param description the description to search for
	 *
	 * @throws ReportErrorToUserException The given description is invalid.
	 */
	public SearchOnDescription(String description) throws ReportErrorToUserException
	{
		if (!isValidDescriptionString(description)) throw new ReportErrorToUserException("Invalid description");
		this.description = description;
	}

	/**
	 * Method to execute the search command. Method returns a list of all bug reports
	 * that meet the search requirements.
	 *
	 * @param bugReportService the bug report service, requesting the search
	 * @param user the user requesting the search
	 * 
	 * @return an unmodifiable list of all bug reports that meet the search requirements.
	 *
	 * @throws IllegalArgumentException The user or bugreportservice is null.
	 */
	@Override
	protected List<BugReport> apply(BugReportService bugReportService, User user) throws ReportErrorToUserException
	{
		if (bugReportService == null) throw new IllegalArgumentException("bugreportservice is null");
		if (user == null) throw new IllegalArgumentException("User is null");
		List<BugReport> bugReports = getAllBugReportsWrapped(bugReportService, user).getAllMatching(x -> x.getDescription().contains(description));
		return Collections.unmodifiableList(bugReports);
	}
	
    /**
     * Checker to check if the string for filtering is a valid string.
     *
	 * @param description The string part to check.
	 *
     * @return True if the title is not null, empty or whitespace.
     */
    private boolean isValidDescriptionString(String description) {
        if (description == null) return false;
        if (description.equals("")) return false;
        if (description.equals(" ")) return false;
        return true;
    }





}
