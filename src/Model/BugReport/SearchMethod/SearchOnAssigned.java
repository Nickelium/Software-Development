package Model.BugReport.SearchMethod;

import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.Search;
import Model.User.User;
import Model.Wrapper.IListWrapper;

import java.util.Collections;
import java.util.List;

public class SearchOnAssigned extends Search
{
	private User user;
	
	public SearchOnAssigned(User user)
	{
		this.user = user;
	}
	
	@Override
	protected List<BugReport> apply(BugReportService bugReportService, User user)
	{
		IListWrapper<BugReport> bugReportList = getAllBugReportsWrapped(bugReportService, user);

        List<BugReport> bugReports = bugReportList.getAllMatching(x -> x.getAssignees().contains(user));
        return Collections.unmodifiableList(bugReports);
		
	}
}
