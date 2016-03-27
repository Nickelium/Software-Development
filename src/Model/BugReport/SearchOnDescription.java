package Model.BugReport;

import java.util.Collections;
import java.util.List;

import CustomExceptions.ReportErrorToUserException;

public class SearchOnDescription extends Search
{
	private String description;
	
	public SearchOnDescription(String description)
	{
		this.description = description;
	}
	
	@Override
	protected List<BugReport> apply(BugReportService bugReportService) throws ReportErrorToUserException
	{
		if (!isValidDescriptionString(description)) throw new ReportErrorToUserException("Invalid description");
	    List<BugReport> bugreports = getAllBugReportsWrapped(bugReportService).getAllMatching(x -> x.getDescription().contains(description));
	    return Collections.unmodifiableList(bugreports);
		
	}
	
    /**
     * Checker to check if the string for filtering is a valid string.
     *
     * @param title The string part to check.
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
