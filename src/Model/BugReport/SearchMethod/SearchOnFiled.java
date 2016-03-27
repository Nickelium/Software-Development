package Model.BugReport.SearchMethod;

import java.util.Collections;
import java.util.List;

import Model.BugReport.*;
import Model.User.User;
import Model.Wrapper.IListWrapper;

public class SearchOnFiled extends Search
{
	private User user;
	
	public SearchOnFiled(User user)
	{
		this.user = user;
	}
	
	@Override
	protected List<BugReport> apply(BugReportService bugReportService) 
	{
    	IListWrapper<BugReport> bugReportList = getAllBugReportsWrapped(bugReportService);

        List<BugReport> bugReports = bugReportList.getAllMatching(x -> x.getCreator().equals(user));
        return Collections.unmodifiableList(bugReports);
		
	}
}
