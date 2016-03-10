package Controller;

import CustomExceptions.ModelException;

/**
 * Created by Karina on 10.03.2016.
 */
public interface IUI {
    void display(String str);

    String readString();

    String readMultiline();

    double readDouble() throws ModelException;

    int readInt() throws ModelException;

    void errorDisplay(String str);
}
