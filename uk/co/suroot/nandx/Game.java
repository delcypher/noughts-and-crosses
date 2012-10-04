package uk.co.suroot.nandx;

import uk.co.suroot.nandx.BoardInterface.BoardException;
import uk.co.suroot.nandx.BoardInterface.InvalidBoardPositionException;
import uk.co.suroot.nandx.BoardInterface.Piece;
import uk.co.suroot.nandx.Player.NoMoveAvailableException;

/**
 *  Class that represents a Noughts and Crosses Game.
 *  Clients cannot instantiate this class so they should use the GameFactory.
 *  @see GameFactory
 *
 */
public class Game 
{
	private Player crossPlayer;
	private Player noughtPlayer;
	private Player winner;
	private Player firstToPlay;
	private int numberOfMoves=0;
	BoardInterface board;
		
	protected Game(Player cross, Player nought, BoardInterface board, Player first)
	{
		crossPlayer=cross;
		if(crossPlayer.getPieceType() != Piece.CROSS)
			throw new IllegalArgumentException();
		
		noughtPlayer=nought;
		if(noughtPlayer.getPieceType() != Piece.NOUGHT)
			throw new IllegalArgumentException();

		firstToPlay=first;
		
		winner=null;
		this.board=board;
		
	}
	
	public enum State
	{
		NOT_FINISHED,
		FINISHED_WITH_DRAW,
		FINISHED_WITH_WINNER,
	}
	
	State gameState=State.NOT_FINISHED;
	
	
	public State getState() { return gameState;}
	
	public void drawBoard() 
	{  
		//Check if players are sharing a user interface
		if(crossPlayer.getUserInterface() == noughtPlayer.getUserInterface())
			crossPlayer.getUserInterface().drawBoard(board);
		else
		{
			//Both players need to told to draw the new board
			crossPlayer.getUserInterface().drawBoard(board);
			noughtPlayer.getUserInterface().drawBoard(board);
		}
	}
	
	public boolean isFinished() { return (getState() != State.NOT_FINISHED); }
	
	public Player getPlayer(Piece p)
	{
		if(p==Piece.CROSS)
			return crossPlayer;
		if(p==Piece.NOUGHT)
			return noughtPlayer;
		else
			return null;
	}
	
	//Applies move from the next player to play
	public void applyNextMove() throws BoardException, NoMoveAvailableException, GameOverException
	{
		if(gameState!=State.NOT_FINISHED)
			throw new GameOverException();
		
		Player p = getNextPlayer();
		Piece piece = p.getPieceType();	
		Position pos = p.getNextMove(board.clone());
		
		board.add(piece, pos);
		
		numberOfMoves++;
		
		//inform the other player what move was just made
		getNextPlayer().getUserInterface().printMessage("Player " + p.getName() + " made move at " + pos);
		
		//check if the player made a line of three
		if(lineOfThree(pos))
		{
			winner=p;
			gameState=State.FINISHED_WITH_WINNER;
			return;
		}
		
		if(numberOfMoves >= (BoardInterface.LENGTH* BoardInterface.LENGTH))
		{
			gameState=State.FINISHED_WITH_DRAW;
		}
		
	}
	
	public Player getNextPlayer()
	{
		if(numberOfMoves % 2 != 0)
			return (firstToPlay==crossPlayer)?noughtPlayer:crossPlayer;
		else
			return firstToPlay;
	}
	
	public Player getWinner() { return winner;}
	
	//Returns true if there is a line of three at position p
	protected boolean lineOfThree(Position p) throws InvalidBoardPositionException
	{
		int xInitial=p.getX();
		int yInitial=p.getY();
		int count=0;
		
		Piece pieceToMatch= board.get(p);
		
		/* Test for line in vertical direction
		 * 
		 */
		for(int y=yInitial; y < BoardInterface.LENGTH; y= (y+1) % BoardInterface.LENGTH)
		{
			if(board.get(new Position(xInitial,y)) != pieceToMatch)
				break;
			
			count++;
			if(count==BoardInterface.LENGTH) return true;
		}
		
		
		
		count=0;
		/* Test for line in horizontal direction
		 * 
		 */
		for(int x=xInitial; x < BoardInterface.LENGTH; x = (x+1) %BoardInterface.LENGTH)
		{
			if(board.get(new Position(x,yInitial)) != pieceToMatch)
				break;
			
			count++;
			if(count==BoardInterface.LENGTH) return true;
		}
		
		
		
		count=0;
		/* Test for line like "/".
		 * We first check we're on the diagonal (y=x)
		 */
		if(xInitial==yInitial)
		{
			for(int x=0, y=0; x < BoardInterface.LENGTH && y < BoardInterface.LENGTH; x++,y++)
			{
				if(board.get(new Position(x,y)) != pieceToMatch)
					break;
				
				count++;
				if(count==BoardInterface.LENGTH) return true;
			}
		}
		
		
		
		count=0;
		/* Test for line like "\"
		 * We first check we're on the diagonal (y= -x +2)
		 */
		if((xInitial + yInitial)==2)
		{
			for(int x=0, y=(BoardInterface.LENGTH -1); x < BoardInterface.LENGTH && y < BoardInterface.LENGTH; x++, y-- )
			{
				if(board.get(new Position(x,y)) != pieceToMatch)
					break;
				
				count++;
				if(count==BoardInterface.LENGTH) return true;
			}
		}
		
		//Couldn't find a line of three
		return false;
	}
	
	protected void printMessageToPlayers(String msg)
	{
		//Check if players are sharing a user interface
		if(crossPlayer.getUserInterface() == noughtPlayer.getUserInterface())
			crossPlayer.getUserInterface().printMessage(msg);
		else
		{
			//Both players need to be given the message
			crossPlayer.getUserInterface().printMessage(msg);
			noughtPlayer.getUserInterface().printMessage(msg);
		}
	}
	
	public void run() throws GameOverException
	{
		if(gameState!=State.NOT_FINISHED)
			throw new GameOverException();
		
		printMessageToPlayers("Starting game with players \"" + crossPlayer.getName() + "\" and \"" + noughtPlayer.getName() +
				"\". Player " + firstToPlay.getName() + " will start first.");
	
		while(isFinished() == false)
		{
			try 
			{
				drawBoard();
				applyNextMove();

			} catch (BoardException e) 
			{
				getNextPlayer().getUserInterface().printErrorMessage("Move not accepted:" + e.getMessage() + " Try again.");
				continue;
			} catch (NoMoveAvailableException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GameOverException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		drawBoard(); //Draw the board state for the finished game.
		
		System.out.print("Game Over.");
		if(getState() != Game.State.FINISHED_WITH_DRAW)
		{
			printMessageToPlayers(" Player \"" + getWinner().getName() + "\" won the game!");
		}
		else
			printMessageToPlayers("There was a no winner because the players drew.");
		
		
	}
		
	public class GameOverException extends Exception
	{
		public GameOverException() 
		{
			super("Game has finished!");
		}
	}

}
