[1mdiff --git a/Test/ProjectPackageTest/SubSystemTest.java b/Test/ProjectPackageTest/SubSystemTest.java[m
[1mindex 8df0b43..b5fcf6d 100644[m
[1m--- a/Test/ProjectPackageTest/SubSystemTest.java[m
[1m+++ b/Test/ProjectPackageTest/SubSystemTest.java[m
[36m@@ -164,6 +164,18 @@[m [mpublic class SubSystemTest {[m
 	}[m
 	[m
 	@Test[m
[32m+[m	[32mpublic void getHeight_SUCCESS1()[m[41m[m
[32m+[m	[32m{[m[41m[m
[32m+[m		[32massertEquals(subsystem1.getHeight(), 3);[m[41m[m
[32m+[m	[32m}[m[41m[m
[32m+[m[41m	[m
[32m+[m	[32m@Test[m[41m[m
[32m+[m	[32mpublic void getHeight_SUCCESS2()[m[41m[m
[32m+[m	[32m{[m[41m[m
[32m+[m		[32massertEquals(subsystem3.getHeight(), 1);[m[41m[m
[32m+[m	[32m}[m[41m[m
[32m+[m[41m	[m
[32m+[m	[32m@Test[m[41m[m
 	public void toString_SUCCES() throws Exception[m
 	{[m
 		String string = "Subsystem name: " + "Test1" + "\nDescription: " + "Test1 description"[m
[1mdiff --git a/src/Model/Mail/Mailbox.java b/src/Model/Mail/Mailbox.java[m
[1mindex 0bff500..a2c50ce 100644[m
[1m--- a/src/Model/Mail/Mailbox.java[m
[1m+++ b/src/Model/Mail/Mailbox.java[m
[36m@@ -532,6 +532,8 @@[m [mpublic class Mailbox implements Originator<Mailbox.MailboxMemento, Mailbox>[m
 		[m
 	}[m
 	[m
[32m+[m	[32m//Because a registration of a forked project actually is the same as versionID update[m[41m[m
[32m+[m	[32m//We only provide the ObserverFork[m[41m [m
 	private class ObserverFork extends ObserverAspect[m
 	{[m
 		[m
