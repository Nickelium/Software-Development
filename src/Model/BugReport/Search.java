package Model.BugReport;

import java.util.ArrayList;
import java.util.List;

import CustomExceptions.ModelException;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;

public abstract class Search 
{
	
	protected abstract List<BugReport> apply(BugReportService bugReportService) throws ModelException;
	
	 protected IListWrapper<BugReport> getAllBugReportsWrapped(BugReportService bugReportService)
	 {
		 List<BugReport> bugReports = new ArrayList<>();
		 bugReports.addAll(bugReportService.getAllBugReports());
	     return new ListWrapper<>(bugReports);
	 }

	public Search() {}
}
