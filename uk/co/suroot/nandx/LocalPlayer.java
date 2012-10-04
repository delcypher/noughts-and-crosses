package uk.co.suroot.nandx;

import java.util.List;

import uk.co.suroot.nandx.BoardInterface.Piece;

public class LocalPlayer extends Player
{

	private Position nextMove;
	
	public LocalPlayer(String name,Piece type, UserInterface ui)
	{
		super(name,type,ui);
		nextMove=null;
	}
	
		
	public Position getNextMove(BoardInterface currentState) throws NoMoveAvailableException 
	{
		//We don't need to use currentState.
		
		List<String> list = ui.promptForValues("Player " + getName() + ", enter move (x,y)?","(\\d),(\\d)");
		nextMove= new Position(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));
		
		return new Position(nextMove); //return a copy of the next move
	}
}
