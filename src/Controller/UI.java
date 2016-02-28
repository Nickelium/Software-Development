package Controller;

import java.util.Scanner;

public class UI 
{
	private Scanner scanner = new Scanner(System.in);
	public UI()
	{
		
	}
	
	public void display(String str)
	{
		System.out.println(str);
	}
	
	public String readString()
	{
		return scanner.next();
	}
	
	public double readDouble()
	{
		return scanner.nextDouble();
	}
}
