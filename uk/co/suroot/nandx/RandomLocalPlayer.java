package uk.co.suroot.nandx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.co.suroot.nandx.BoardInterface.InvalidBoardPositionException;
import uk.co.suroot.nandx.BoardInterface.Piece;


public class RandomLocalPlayer extends Player 
{
	Random rndSrc;
	
	public RandomLocalPlayer(String name, Piece type, UserInterface ui)
	{
		super(name,type,ui);
		rndSrc= new Random(System.nanoTime());
	}

	@Override
	public Position getNextMove(BoardInterface currentState) throws NoMoveAvailableException 
	{
		//Build a list of available positions
		List<Position> availablePositions = new ArrayList<Position>();
		
		//Move over the current board to build the list of positions available
		for(int x=0; x < BoardInterface.LENGTH; x++)
			for(int y=0; y < BoardInterface.LENGTH; y++)
			{
				try 
				{
					if(currentState.get(new Position(x,y))==Piece.BLANK)
						availablePositions.add(new Position(x,y));
				} catch (InvalidBoardPositionException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new NoMoveAvailableException();
				}
			}
		
		//Check we can pick a move
		if(availablePositions.size()==0)
			throw new NoMoveAvailableException();
		
		//Pick a random move
		int getIndex= rndSrc.nextInt() % availablePositions.size();
		if(getIndex < 0) getIndex*=-1; //force a positive random index
		Position randomMove= availablePositions.get(getIndex);	
		return randomMove;
		
	}

}
