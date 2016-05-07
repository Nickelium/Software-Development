package UseCaseTests.IssuerControllerTest;

import CustomExceptions.ReportErrorToUserException;
import Model.User.User;
import UseCaseTests.InitializerTest;
import UseCaseTests.Logger;
import org.junit.Before;

/**
 * Created by Karina on 10.03.2016.
 */
public class IssuerControllerInit extends InitializerTest {

    public enum IssuerUseCase {
        CREATE_BUGREPORT(3), INSPECT_BUGREPORT(4), CREATE_COMMENT(5),
        UPDATE_BUGREPORT(6), SHOW_NOTIFICATIONS(7), REGISTER_FOR_NOTIFICATION(8),
        UNREGISTER_FOR_NOTIFICATION(9);

        public final int value;

        IssuerUseCase(int value) {
            this.value = value;
        }
    }

    protected User currentUser;

    @Before
    public void subInit() throws ReportErrorToUserException {
        currentUser = Logger.issuerLogger(userService, 0);
    }
}
