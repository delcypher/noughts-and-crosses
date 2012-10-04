package uk.co.suroot.nandx;

import java.util.List;

/**
 * Interface for the User Interface. An example class that implements this is ConsoleUI
 * @see ConsoleUI
 */
public interface UserInterface 
{
	/**
	 * Print a message to the user
	 * @param msg The message
	 */
	public void printMessage(String msg);
	
	/**
	 * Print an error message to the user
	 * @param msg
	 */
	public void printErrorMessage(String msg);
	
	/**
	 * Draw a board
	 * @param b the board to draw
	 */
	public void drawBoard(BoardInterface b);
	
	/**
	 * Prompt the user for a list of values based on groupedRegex. The function will not return until
	 * valid input has been entered.
	 * @param prompt The prompt text to show the user
	 * @param groupedRegex The regular expression user input must match. Each item needed should be grouped.
	 * @return A list of the matching text, each element is a regex group.
	 * 
	 * <br>Example: promptForValues("enter x,y:","(\\d+),(\\d+)") 
	 * <br>
	 * This will return a List consisting of two strings.
	 * The first element will contain the string that matched the first regex group and
	 * the second element will contain the string that matched the second regex group.
	 */
	public List<String> promptForValues(String prompt, String groupedRegex);
}
