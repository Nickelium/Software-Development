package Model.BugReport;

import CustomExceptions.ReportErrorToUserException;
import Model.User.User;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class Search 
{

	protected abstract List<BugReport> apply(BugReportService bugReportService, User user) throws ReportErrorToUserException;

	protected IListWrapper<BugReport> getAllBugReportsWrapped(BugReportService bugReportService, User user)
	 {
		 List<BugReport> bugReports = new ArrayList<>();
		 bugReports.addAll(bugReportService.getAllBugReports(user));
		 return new ListWrapper<>(bugReports);
	 }

	public Search() {}
}
