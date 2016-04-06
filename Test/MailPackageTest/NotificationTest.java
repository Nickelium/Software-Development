package MailPackageTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Model.Mail.Notification;

public class NotificationTest {

	@Test
	public void constructor_SUCCESS()
	{
		String test = "TEST";
		Notification not = new Notification(test);
		assertEquals(not.getContent(), test);
	}
	
	@Test
	public void toString_SUCCESS()
	{
		String test = "TEST";
		Notification not = new Notification(test);
		assertEquals(not.toString(), test);
	}

}
