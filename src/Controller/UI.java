package Controller;

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
	public double readDouble()
	{
		while(!scanner.hasNextDouble())
		{
			scanner.next();
		}
		return scanner.nextDouble();

	}
	
	@Override
	public int readInt()
	{
		while(!scanner.hasNextInt())
		{
			scanner.next();
		}

		return scanner.nextInt();

	}
	
	@Override
	public void errorDisplay(String str)
	{
		System.out.println("! Error: " + str);
	}
}
