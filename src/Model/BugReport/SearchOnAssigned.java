package Model.BugReport;

import java.util.Collections;
import java.util.List;

import Model.User.User;
import Model.Wrapper.IListWrapper;

public class SearchOnAssigned extends Search
{
	private User user;
	
	public SearchOnAssigned(User user)
	{
		this.user = user;
	}
	
	@Override
	protected List<BugReport> apply(BugReportService bugReportService)
	{
		IListWrapper<BugReport> bugReportList = getAllBugReportsWrapped(bugReportService);

        List<BugReport> bugReports = bugReportList.getAllMatching(x -> x.getAssignees().contains(user));
        return Collections.unmodifiableList(bugReports);
		
	}
}
