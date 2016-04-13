package Model.BugReport;

import CustomExceptions.ReportErrorToUserException;
import Model.User.User;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to implement search functionality for bug reports.
 *
 * Different search methods have to implement this class.
 */
public abstract class Search 
{

	/**
	 * Method to execute the search for a bug report.
	 * @param bugReportService the bugreport service, requesting the search
	 * @param user the user requesting the search
	 * @return the list of bug reports that meet the search requirements
	 * @throws ReportErrorToUserException in case that the search is invalid.
     */
	protected abstract List<BugReport> apply(BugReportService bugReportService, User user) throws ReportErrorToUserException;

	/**
	 * Method that returns an ILIstWrapper object with all bugreports.
	 * @param bugReportService the bugreport service, requesting the search
	 * @param user the user requesting the search
     * @return an ILIstWrapper object with all bugreports
     */
	protected IListWrapper<BugReport> getAllBugReportsWrapped(BugReportService bugReportService, User user)
	 {
		 List<BugReport> bugReports = new ArrayList<>();
		 bugReports.addAll(bugReportService.getAllBugReports(user));
		 return new ListWrapper<>(bugReports);
	 }

}
