package Controller;

/**
 * Created by Karina on 10.03.2016.
 */
public interface IUI {
    void display(String str);

    String readString();

    String readMultiline();

    double readDouble();

    int readInt();

    void errorDisplay(String str);
}
