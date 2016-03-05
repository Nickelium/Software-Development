package Controller;

import Model.User.User;
import Model.User.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karina on 04.03.2016.
 */
public class LoginController {


    private UserService userService;
    private UI ui;

    public LoginController(UserService userService, UI ui) {
        this.userService = userService;
        this.ui = ui;
    }

    public User run() {

        loginMessage();
        int userType = ui.readInt();

        List<User> users = new ArrayList<User>();

        try {
            switch (userType) {
                case 1://Admin
                    users = userService.getAdministrators();
                    break;
                case 2://Issuer
                    users = userService.getIssuers();
                    break;
                case 3://Developer
                    users = userService.getDevelopers();
                    break;
                default:
                    throw new Exception("Invalid input");
            }
        } catch (Exception e) {
            ui.errorDisplay(e.getMessage());
        }

        ui.display("Choose an user to log in as:");
        String parsedTextUsers = Parser.parseUserList(users);
        ui.display(parsedTextUsers);

        //select User
        int selectedUserIndex = ui.readInt();
        //Waar beheren van get index uit lijst ? In controller of service ?
        User user = users.get(selectedUserIndex);

        //welcome User
        welcomeUserMessage(user);

        return user;
    }

    private void loginMessage() {
        String str = "You can log in as: 1 = administrator, 2 = issuer, 3 = developer.";
        ui.display(str);
    }

    private void welcomeUserMessage(User user) {
        String userText = user.getFirstName();
        String str = "Welcome " + userText + "!";
        ui.display(str);
    }

}
