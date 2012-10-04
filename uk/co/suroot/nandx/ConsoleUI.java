package uk.co.suroot.nandx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.suroot.nandx.BoardInterface.InvalidBoardPositionException;

/**
 * Simple implementation of UserInterface that interacts with the user using the 
 * system's console.
 *
 */
public class ConsoleUI implements UserInterface
{

	@Override
	public void printMessage(String msg) 
	{
		System.out.println(msg);
	}

	@Override
	public void printErrorMessage(String msg) 
	{
		System.err.println(msg);
	}

	@Override
	public void drawBoard(BoardInterface b) 
	{
		for(int y=(BoardInterface.LENGTH -1) ; y >= 0 ; y-- )
		{
			for(int x=0 ; x < BoardInterface.LENGTH; x++)
			{
				char symbol=' ';
				try 
				{
					
					switch(b.get(new Position(x,y)))
					{
						case NOUGHT: symbol='O'; break;
						case CROSS: symbol='X'; break;
						case BLANK:
						default:
							symbol='-'; break;
					}
				} catch (InvalidBoardPositionException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				
				System.out.print(symbol);
		
			}
			
			System.out.println();
			
		}
		System.out.println();
			
	}

	@Override
	public List<String> promptForValues(String prompt, String groupedRegex)
	{
		boolean needToPrompt=true;
		
		List<String> list = new ArrayList<String>();
		
		//Regex 
		Pattern pat = Pattern.compile(groupedRegex);
		
		String line="";
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		
		while(needToPrompt)
		{
			System.out.println();
			System.out.printf(prompt);
			
			try 
			{
				line=buf.readLine();
			} catch (IOException e) 
			{
				e.printStackTrace();
				System.err.println("Giving up!");
				System.exit(1);
			}
			
			Matcher matcher = pat.matcher(line);
			if(! matcher.matches())
			{
				System.err.println("Invalid input. The input must match the regular expression " + groupedRegex);
				continue;
			}
			
			//Build the list
			for(int groupIndex=1; groupIndex <= matcher.groupCount() ; groupIndex++)
				list.add(matcher.group(groupIndex));
				
			
			
			needToPrompt=false;
		}
		
		return list;
	}

}
