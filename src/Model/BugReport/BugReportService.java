package Model.BugReport;

import DAL.IRepository;

/**
 * Created by Tom on 24/02/16.
 */
public class BugReportService {

    private IRepository<BugReport> bugReportRepository;

    /**
     * Constructor for the bugReport service.
     *
     * @param bugReportRepository The bugreportRepository the BugrepositoryService uses.
     */
    public BugReportService(IRepository<BugReport> bugReportRepository){
        this.bugReportRepository = bugReportRepository;
    }
}
