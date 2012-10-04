package uk.co.suroot.nandx;


public class SimpleBoard implements BoardInterface 
{
	//Simple multi-dimensional array to represent the board
	BoardInterface.Piece[][] theBoard;
	
	public SimpleBoard()
	{
		theBoard = new Piece[LENGTH][LENGTH];	
		reset();
	}
	
	public SimpleBoard(BoardInterface b)
	{
		theBoard = new Piece[LENGTH][LENGTH];
		for(int x=0; x < LENGTH; x++)
			for(int y=0; y < LENGTH; y++)
				try 
				{
					theBoard[x][y]=b.get(new Position(x, y));
				} catch (InvalidBoardPositionException e) 
				{
					e.printStackTrace();
				}
	}
	
	@Override
	public void reset() 
	{
		for(int x=0; x < LENGTH; x++)
			for(int y=0; y < LENGTH; y++)
				theBoard[x][y]=Piece.BLANK;	
	}

	@Override
	public BoardInterface clone()
	{
		return new SimpleBoard(this);
	}
	
	@Override
	public void add(Piece type, Position p) throws InvalidBoardPositionException, BoardPositionOccupiedException, InvalidPieceException 
	{
		if(!isCrossOrNought(type))
			throw new InvalidPieceException(type);
		
		if(invalidPosition(p))
			throw new InvalidBoardPositionException(p);
		
		Piece current = theBoard[p.getX()][p.getY()];
		
		if(isCrossOrNought(current))
			throw new BoardPositionOccupiedException(p);
			
		//Checked for all errors now actually modify the board
		theBoard[p.getX()][p.getY()]= type;
	}

	@Override
	public Piece get(Position p) throws InvalidBoardPositionException 
	{

		if(invalidPosition(p))
			throw new InvalidBoardPositionException(p);
		
		return theBoard[p.getX()][p.getY()];
	}
	
	private boolean isCrossOrNought(Piece type)
	{
		return (type == Piece.CROSS || type == Piece.NOUGHT );
	}
	
	private boolean invalidPosition(Position p)
	{
		return (p.getX() < 0 || p.getX() >= LENGTH || p.getY() < 0 || p.getY() >= LENGTH );
	}

}
