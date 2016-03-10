package Controller;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UI 
{
	private Scanner scanner = new Scanner(System.in);
	
	public void display(String str)
	{
		System.out.print(str + "\n");
	}
	
	public String readString()
	{
		if(scanner.hasNext()) return scanner.next();
		return readString();
	}

	public String readLine() {
		while(!scanner.hasNextLine())
		{
			scanner.next();
		}
		return scanner.nextLine();

	}
	
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
            newText = readLine();
        }
        return text;
	}
	
	public double readDouble()
	{
		while(!scanner.hasNextDouble())
		{
			scanner.next();
		}
		return scanner.nextDouble();

	}
	
	public int readInt()
	{
		while(!scanner.hasNextInt())
		{
			scanner.next();
		}

		return scanner.nextInt();

	}
	
	public void errorDisplay(String str)
	{
		System.out.println("! Error: " + str);
	}
}
