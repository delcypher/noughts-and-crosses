package uk.co.suroot.nandx;

import java.util.Random;

import uk.co.suroot.nandx.BoardInterface.Piece;


/**
 * Factory for building various types of Noughts and crosses games.
 *
 */
public class GameFactory 
{	
	public static Game makeSimpleGame(boolean useRandomCrossPlayer,boolean useRandomNoughtPlayer, UserInterface ui)
	{
		Player crossPlayer, noughtPlayer;
		if(useRandomCrossPlayer)
			crossPlayer= new RandomLocalPlayer("CrossRandom",Piece.CROSS,ui);
		else	
			crossPlayer= new LocalPlayer("CrossInteractive",Piece.CROSS,ui);
		
		if(useRandomNoughtPlayer)
			noughtPlayer= new RandomLocalPlayer("NoughtRandom",Piece.NOUGHT,ui);
		else
			noughtPlayer= new LocalPlayer("NoughtInteractive",Piece.NOUGHT,ui);
		
		//Randomly pick who starts
		Random rnd = new Random(System.nanoTime());
		
		Player first=null;
		if(rnd.nextInt() % 2 !=0 )
			first=crossPlayer;
		else
			first=noughtPlayer;
		
		return new Game(crossPlayer,noughtPlayer, new SimpleBoard(),first);

	}

}
