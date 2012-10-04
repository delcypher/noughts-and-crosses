package uk.co.suroot.nandx;

/** Public Interface for game boards.
 *  The convention taken is that (0,0) refers to the bottom
 *  left corner of the board.
 *
 */
public interface BoardInterface 
{
	//The length of one side of the board.
	static final int LENGTH=3;
	
	/** Reset the board to its initial state where no pieces 
	 *  are on the board. So every position will have piece set to
	 *  Piece.BLANK
	 *  @see Piece#BLANK
	 */
	void reset();
	
	/** Add a piece to the board.
	 * 
	 * @param type The piece type to add
	 * @param p The position to place the piece
	 */
	void add(Piece type, Position p) throws InvalidBoardPositionException, BoardPositionOccupiedException, InvalidPieceException;
	
	/** Determine what piece is at position on the board.
	 * 
	 * @param p The position to get the piece from
	 * @return The piece at position p
	 */
	Piece get(Position p) throws InvalidBoardPositionException;
	

	/** Make a copy of the board
	 * @return A copy of the board.
	 */
	BoardInterface clone();
	
	/**
	 * Different piece types that may exist on a board.
	 */
	public enum Piece
	{
		/** Represents a cross piece (X) on the board */
		CROSS,
		/** Represents a nought (O) on the board */
		NOUGHT,
		/** Represents no piece on the board */
		BLANK 
	}
	
	/**
	 * Superclass for the exceptions thrown by classes that implement
	 * this interface
	 *
	 */
	public class BoardException extends Exception
	{
		public BoardException(String s)
		{
			super(s);
		}
	}
	/**
	 * This should be thrown if clients request a position not on the board.
	 */
	public class InvalidBoardPositionException extends BoardException
	{
		public InvalidBoardPositionException(Position p) 
		{
			super("Invalid Board Position " + p + ".");
		}
	}
	
	/**
	 * This should be thrown if an attempt is made to add a piece to
	 * an already occupied location.
	 * @see BoardInterface#add(Piece,Position)
	 */
	public class BoardPositionOccupiedException extends BoardException
	{
		public BoardPositionOccupiedException(Position p)
		{
			super("Board Position " + p + " is already occupied.");
		}
	}
	
	/**
	 * This should be thrown if an attempt is made to add a piece to
	 * that isn't valid to the board.
	 * @see BoardInterface#add(Piece,Position)
	 */
	public class InvalidPieceException extends BoardException
	{
		public InvalidPieceException(Piece p)
		{
			super("Piece " + p.toString() + " is not allowed to be added to this board.");
		}
	}
	

}



