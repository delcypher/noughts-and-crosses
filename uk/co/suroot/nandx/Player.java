package uk.co.suroot.nandx;

import uk.co.suroot.nandx.BoardInterface.Piece;

/**
 * Abstract Player Class. See implementations for examples on how this 
 * class should be used.
 *
 */
public abstract class Player
{
	/** Player's name */
	protected String name;
	
	protected UserInterface ui;
	
	Piece type;

	/** Player Constructor
	 * 
	 * @param name The player's name
	 * @param type The Piece type the player is given.
	 * @param ui The player's user interface
	 */
	public Player(String name, Piece type, UserInterface ui) { this.name=name; this.type=type; this.ui=ui;}
	
	/**
	 * @return The Player's name
	 */
	public String getName() { return name;} //okay because String is immutable
	
	/**
	 * Returns the next move the player would like to make
	 * @param currentState The current state of the board before this player makes its move.
	 * @return The position that the player would like to place their piece.
	 * 
	 */
	abstract Position getNextMove(BoardInterface currentState) throws NoMoveAvailableException;

	/**
	 *  @return The player's piece type.
	 */
	public Piece getPieceType() { return type;}
	
	/**
	 *  @return the Player's user interface.
	 */
	UserInterface getUserInterface() { return ui;}

	/**
	 * Superclass for Player class exceptions.
	 *
	 */
	public class PlayerException extends Exception
	{
		public PlayerException(String msg)
		{
			super(msg);
		}
	}
	
	/**
	 * Thrown if move cannot be made
	 * @see Player#getNextMove()
	 *
	 */
	public class NoMoveAvailableException extends PlayerException
	{
		public NoMoveAvailableException()
		{
			super("No move available");
		}
	}
	
}
