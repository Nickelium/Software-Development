package Model.BugReport.SearchMethod;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.Search;
import Model.User.User;

import java.util.Collections;
import java.util.List;

public class SearchOnDescription extends Search
{
	private String description;
	
	public SearchOnDescription(String description)
	{
		this.description = description;
	}
	
	@Override
	protected List<BugReport> apply(BugReportService bugReportService, User user) throws ReportErrorToUserException
	{
		if (!isValidDescriptionString(description)) throw new ReportErrorToUserException("Invalid description");
		List<BugReport> bugreports = getAllBugReportsWrapped(bugReportService, user).getAllMatching(x -> x.getDescription().contains(description));
		return Collections.unmodifiableList(bugreports);
		
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
