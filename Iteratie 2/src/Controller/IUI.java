package Controller;

import CustomExceptions.ReportErrorToUserException;

/**
 * Created by Karina on 10.03.2016.
 */
public interface IUI {
    void display(String str);

    String readString();

    String readMultiline();

    double readDouble() throws ReportErrorToUserException;

    int readInt() throws ReportErrorToUserException;

    void errorDisplay(String str);
}
