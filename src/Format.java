import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import allignment.*;

public class Format
{
	//exceptions
	private static final Exception INVALIDCOMMAND = new Exception("Invalid Command");
	
	//constants
	private static final int FIRST = 0;
	private static final char COMMANDTOKEN = '-';
	private static final String SINGLE = "\n";
	private static final String DOUBLE = "\n\n";
	
	//defaults
	private static final int DEFAULTMAXCHARS = 60;
	private static final AllignmentType DEFAULTALLIGNMENTTYPE = new LeftAllignment();
	private static final String DEFAULTSPACING = SINGLE;
	private static final boolean DEFAULTWRAPPABLE = true;
	
	//instance variables
	private int maxChars;
	private AllignmentType allignment;
	private String spacing;
	private boolean wrappable;
	
	private File output;
	
	public Format(File input) throws Exception
	{
		maxChars = DEFAULTMAXCHARS;
		allignment = DEFAULTALLIGNMENTTYPE;
		spacing = DEFAULTSPACING;
		wrappable = DEFAULTWRAPPABLE;
		
		this.parseFile(input);
	}
	
	private void parseFile(File input) throws Exception
	{
		Scanner fileScanner = new Scanner(new FileReader(input));
		
		while(fileScanner.hasNextLine())
			parseFileLine(fileScanner.nextLine());
		
		fileScanner.close();
	}
	
	private void parseFileLine(String fileLine) throws Exception
	{
		if(isCommand(fileLine))
			execute(fileLine);
		else
		{
			while(fileLine.length() > 0)
			{
				
			}
		}
	}
	
	private static String getUnwrappedLine(String input, int maxChars)
	{
		if(input.length() > maxChars)
			input = input.substring(0, maxChars);
		return input;
	}
	
	private boolean isCommand(String line)
	{
		line = line.trim();
		return line.charAt(FIRST) == COMMANDTOKEN;
	}
	
	private void execute(String line) throws Exception
	{
		String command = line.substring(line.indexOf(COMMANDTOKEN) + 1);
		command = command.replace(" ", "");
		command = command.toLowerCase();
		
		char symbol = command.charAt(FIRST);	
		try
		{
			if(command.length() > 1)
				execute(symbol, command.substring(1));
			else
				execute(symbol);
		}
		catch(Exception ex)
		{
			throw new Exception("The command " + line + " is invalid");
		}
	}
	
	private void execute(char commandSymbol) throws Exception
	{
		switch(commandSymbol)
		{
		case 'r':
			allignment = new RightAllignment();
			break;
			
		case 'l':
			allignment = new LeftAllignment();
			break;
			
		case 'c':
			allignment = new CenterAllignment();
			break;
			
		case 'e':
			allignment = new EqualAllignment();
			break;
		
		case 't':
			break;
			
		case 's':
			setSpacing(SINGLE);
			break;
			
		case 'd':
			setSpacing(DOUBLE);
			break;
			
		default:
			throw INVALIDCOMMAND;
		}
	}
	
	private void execute(char commandSymbol, String parameter) throws Exception
	{
		switch(commandSymbol)
		{
		case 'n':
			break;
			
		case 'w':
			setWrappable(parameter.charAt(FIRST));
			break;
			
		case 'a':
			break;
		
		case 'p':
			break;
			
		case 'b':
			break;
			
		default:
			throw INVALIDCOMMAND;
		}
	}
	
	private void setSpacing(String newSpacing)
	{
		spacing = newSpacing;
	}
	
	private void setWrappable(char state) throws Exception
	{
		if(state != '+' && state != '-')
			throw INVALIDCOMMAND;
		else if(state == '+')
			wrappable = true;
		else
			wrappable = false;
	}
}
