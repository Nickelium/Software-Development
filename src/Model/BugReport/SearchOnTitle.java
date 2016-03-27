package Model.BugReport;

import java.util.Collections;
import java.util.List;

import CustomExceptions.ReportErrorToUserException;

public class SearchOnTitle extends Search
{
	private String title;
	
	public SearchOnTitle(String title)
	{
		this.title = title;
	}
	
	@Override
	protected List<BugReport> apply(BugReportService bugReportService) throws ReportErrorToUserException
	{
		if (!isValidTitleString(title)) throw new ReportErrorToUserException("Invalid title");
	    List<BugReport> bugreports = getAllBugReportsWrapped(bugReportService).getAllMatching(x -> x.getTitle().contains(title));
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
