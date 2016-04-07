package Model.BugReport.SearchMethod;

import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.Search;
import Model.User.User;
import Model.Wrapper.IListWrapper;

import java.util.Collections;
import java.util.List;

public class SearchOnFiled extends Search
{
	private User user;
	
	public SearchOnFiled(User user)
	{
		this.user = user;
	}
	
	@Override
	protected List<BugReport> apply(BugReportService bugReportService, User user)
	{
		IListWrapper<BugReport> bugReportList = getAllBugReportsWrapped(bugReportService, user);

		List<BugReport> bugReports = bugReportList.getAllMatching(x -> x.getCreator().equals(this.user));
		return Collections.unmodifiableList(bugReports);
		
	}
}
