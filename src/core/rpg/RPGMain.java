package core.rpg;

import java.util.Scanner;

public class RPGMain
{
	private static RPGFramework framework;
	private static Scanner scanner;
	
	public static void RPGmain(String[] args)
	{
		Init();
		while(Run()) { };
		Shutdown();
	}
	
	// Initializes stuff (small factory, ish)
	private static void Init()
	{
		framework = new RPGFramework();
		scanner = new Scanner(System.in);
	}
	
	// Main loop
	private static boolean Run()
	{
		// move outside
		String out = framework.Run("Test", scanner.nextLine()); 
		RPGLog.Log(out);
		return true;
	}
	
	// Cleanup
	private static void Shutdown()
	{
		scanner.close();
	}
}