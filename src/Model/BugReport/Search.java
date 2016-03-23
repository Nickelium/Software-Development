package Model.BugReport;

import java.util.ArrayList;
import java.util.List;

import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;

public abstract class Search 
{
	private BugReportService bugReportService;
	
	protected abstract List<BugReport> apply(BugReportService bugReportService);
	
	 protected IListWrapper<BugReport> getAllBugReportsWrapped(BugReportService bugReportService)
	 {
		 List<BugReport> bugReports = new ArrayList<>();
		 bugReports.addAll(bugReportService.getAllBugReports());
	     return new ListWrapper<>(bugReports);
	 }

	public Search() {}
}
