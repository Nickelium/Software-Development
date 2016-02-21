package DI;

import DAL.BugReportRepository;
import DAL.IRepository;
import DAL.ProjectRepository;
import DAL.UserRepository;
import Model.BugReport;
import Model.Project;
import Model.User;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

/**
 * Created by Tom on 21/02/16.
 */
public class RepositoryInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<IRepository<User>>(){}).to(UserRepository.class);
        bind(new TypeLiteral<IRepository<Project>>(){}).to(ProjectRepository.class);
        bind(new TypeLiteral<IRepository<BugReport>>(){}).to(BugReportRepository.class);
    }
}
