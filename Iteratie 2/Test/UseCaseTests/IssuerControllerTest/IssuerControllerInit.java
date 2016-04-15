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

    protected User currentUser;

    @Before
    public void subInit() throws ReportErrorToUserException {
        currentUser = Logger.issuerLogger(userService, 0);
    }
}
