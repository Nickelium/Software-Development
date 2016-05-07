package UseCaseTests.AdminControllerTest;

import CustomExceptions.ReportErrorToUserException;
import Model.User.User;
import UseCaseTests.InitializerTest;
import UseCaseTests.Logger;
import org.junit.Before;

/**
 * Created by Karina on 10.03.2016.
 */
public class AdminControllerInit extends InitializerTest {

    public enum AdminUseCase {
        CREATE_PROJECT(3), FORK_PROJECT(4),
        UPDATE_PROJECT(5), DELETE_PROJECT(6), CREATE_SUBSYSTEM(7), UNDO(8),
        SPLIT_SUBSYSTEM(9), MERGE_SUBSYSTEM(10);

        public final int value;

        AdminUseCase(int value) {
            this.value = value;
        }
    }

    protected User currentUser;

    @Before
    public void subInit() throws ReportErrorToUserException {
        currentUser = Logger.adminLogger(userService, 0);
    }

}
