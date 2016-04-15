package UseCaseTests.UseCasesUI;

import Controller.IUI;

import java.util.ArrayList;

/**
 * Created by Karina on 10.03.2016.
 */
public class TestUI implements IUI {

    private ArrayList<String> input;

    public TestUI(ArrayList<String> input){
        this.input = input;
    }

    @Override
    public void display(String str) {
        System.out.println(str);
    }

    @Override
    public String readString() {
        String str = input.remove(0);
        System.out.println("-- User: " + str);
        return str;
    }

    @Override
    public String readMultiline() {
        String text = "";
        String newText = "";
        while (!newText.equals("."))
        {
            if (!text.equals("") || !newText.equals(""))
            {
                text += newText + '\n';
            }
            newText = readString();
        }
        return text;
    }

    @Override
    public double readDouble() {
        String elem = input.remove(0);
        System.out.println("-- User: " + elem);
        double dbl = Double.parseDouble(elem);
        return dbl;
    }

    @Override
    public int readInt() {
        String elem = input.remove(0);
        System.out.println("-- User: " + elem);
        int elemInt = Integer.parseInt(elem);
        return elemInt;
    }

    @Override
    public void errorDisplay(String str) {
        System.out.println("! Error: " + str);
    }

}
