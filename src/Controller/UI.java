package Controller;

import CustomExceptions.ModelException;

import java.util.Scanner;

public class UI implements IUI {
	private Scanner scanner = new Scanner(System.in);
	
	@Override
	public void display(String str)
	{
		System.out.print(str + "\n");
	}
	
	@Override
	public String readString()
	{
		return scanner.nextLine();
	}
	
	@Override
	public String readMultiline()
	{
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
	public double readDouble() throws ModelException
	{
		String text = scanner.nextLine();
		try {
			return Double.parseDouble(text);
		} catch (NumberFormatException e) {
			throw new ModelException("The value should be of type double!");
		}

	}
	
	@Override
	public int readInt() throws ModelException
	{
		String text = scanner.nextLine();
		try {
			return Integer.parseInt(text);

		} catch (NumberFormatException e) {
			throw new ModelException("The value should be of type integer!");
		}

	}
	
	@Override
	public void errorDisplay(String str)
	{
		System.out.println("! Error: " + str);
	}
}
