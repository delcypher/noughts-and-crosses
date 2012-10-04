

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import uk.co.suroot.nandx.BoardInterface.*;
import uk.co.suroot.nandx.Game.*;
import uk.co.suroot.nandx.ConsoleUI;
import uk.co.suroot.nandx.Game;
import uk.co.suroot.nandx.Game.GameOverException;
import uk.co.suroot.nandx.GameFactory;
import uk.co.suroot.nandx.Player;
import uk.co.suroot.nandx.RandomLocalPlayer;
import uk.co.suroot.nandx.Player.NoMoveAvailableException;
import uk.co.suroot.nandx.Position;
import uk.co.suroot.nandx.LocalPlayer;
import uk.co.suroot.nandx.UserInterface;

public class GameTestHarness {

	public static boolean useRandomCrossPlayer=false;
	public static boolean useRandomNoughtPlayer=false;
	
	public static void parseArgs(String[] args)
	{
		for(String argument : args)
		{
			if(argument.equals("--use-random-x-player"))
				useRandomCrossPlayer=true;
			
			else if(argument.equals("--use-random-o-player"))
				useRandomNoughtPlayer=true;
			
			else if(argument.equals("--help"))
			{
				System.out.println("Usage : " + GameTestHarness.class.getCanonicalName() + " [ options ]");
				System.out.println("Options");
				System.out.println("--use-random-x-player : The Cross player will be random instead of interactive.");
				System.out.println("--use-random-o-player : The Nought player will be random instead of interactive.");
				System.exit(0);
			}
			else
			{
				System.err.println("Error : Unrecognised option \"" + argument + "\". See --help");
				System.exit(1);
			}
		}
	}
	

	public static void main(String[] args) 
	{
		parseArgs(args);

		//Establish the user interface that will be used
		UserInterface ui = new ConsoleUI();
		
		//Establish the Game
		Game game = GameFactory.makeSimpleGame(useRandomCrossPlayer, useRandomNoughtPlayer, ui);
				
		//start the game
		try 
		{
			game.run();
		} catch (GameOverException e) 
		{
			e.printStackTrace();
			ui.printErrorMessage("Can't start game");
		}
		

	}

}
