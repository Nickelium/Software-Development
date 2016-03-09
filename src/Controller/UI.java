package Controller;

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
		return scanner.next();
	}
	
	public double readDouble()
	{
		return scanner.nextDouble();
	}
	
	public int readInt()
	{
		return scanner.nextInt();
	}
	
	public void errorDisplay(String str)
	{
		System.out.println("! Error: " + str);
	}
}
