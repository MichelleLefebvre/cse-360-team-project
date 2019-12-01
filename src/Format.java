import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import allignment.*;
import builders.*;

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
	private static final int DEFAULTNUMCOLUMNS = 1;
	private static final AllignmentType DEFAULTALLIGNMENTTYPE = new LeftAllignment();
	private static final String DEFAULTSPACING = SINGLE;
	private static final boolean DEFAULTWRAPPABLE = true;
	
	//instance variables
	private String spacing;
	private LineBuilder lineBuilder;
	private ColumnBuilder columnBuilder;
	private String filteredText;
	
	private File output;
	
	public Format(File input) throws Exception
	{
		spacing = DEFAULTSPACING;
		lineBuilder = new LineBuilder(DEFAULTMAXCHARS, DEFAULTWRAPPABLE, DEFAULTALLIGNMENTTYPE);
		columnBuilder = new ColumnBuilder(DEFAULTNUMCOLUMNS);
		columnBuilder.setLineSpacing(spacing);
		
		filteredText = "";
		
		this.parseFile(input);
	}
	
	private void parseFile(File input) throws Exception
	{
		Scanner fileScanner = new Scanner(new FileReader(input));
		
		while(fileScanner.hasNextLine())
			parseFileLine(fileScanner.nextLine());
		
		if(!lineBuilder.isEmpty())
		{
			columnBuilder.add(lineBuilder.getLine());
			lineBuilder.reset();
		}
		
		filteredText += columnBuilder.merge();
		
		System.out.println(filteredText);
		
		fileScanner.close();
	}
	
	private void parseFileLine(String fileLine) throws Exception
	{
		if(isCommand(fileLine))
		{
			execute(fileLine);
		}
		else
		{
			Scanner scan = new Scanner(fileLine);
			while(scan.hasNext())
			{
				lineBuilder.add(scan.next());
				if(lineBuilder.isComplete())
				{
					String line = lineBuilder.getLine();
					columnBuilder.add(line);
					lineBuilder.reset();
				}
			}
			scan.close();
		}
	}
	
	//checks to see if the line is a command
	private boolean isCommand(String line)
	{
		line = line.trim();
		return line.charAt(FIRST) == COMMANDTOKEN;
	}
	
	//executes a command
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
			lineBuilder.setAllignment(new RightAllignment());
			break;
			
		case 'l':
			lineBuilder.setAllignment(new LeftAllignment());
			break;
			
		case 'c':
			lineBuilder.setAllignment(new CenterAllignment());
			break;
			
		case 'e':
			lineBuilder.setAllignment(new EqualAllignment());
			break;
		
		case 't':
			break;
			
		case 's':
			spacing = SINGLE;
			columnBuilder.setLineSpacing(spacing);
			break;
			
		case 'd':
			spacing = DOUBLE;
			columnBuilder.setLineSpacing(spacing);
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
			setMaxChars(parameter);
			break;
			
		case 'w':
			setWrappable(parameter.charAt(FIRST));
			break;
			
		case 'a':
			setNumColumns(parameter);
			break;
		
		case 'p':
			
			break;
			
		case 'b':
			break;
			
		default:
			throw INVALIDCOMMAND;
		}
	}
	
	private void setNumColumns(String parameter) throws Exception
	{
		Scanner scan = new Scanner(parameter);
		int numColumns = 0;
		if(scan.hasNextInt())
			numColumns = scan.nextInt();
		scan.close();
		filteredText += columnBuilder.merge();
		columnBuilder = new ColumnBuilder(numColumns);
		columnBuilder.setLineSpacing(spacing);
	}
	
	private void setMaxChars(String parameter) throws Exception
	{
		Scanner scan = new Scanner(parameter);
		if(scan.hasNextInt())
			lineBuilder.setMaxChars(scan.nextInt());
		else
			throw INVALIDCOMMAND;
	}
	
	private void setWrappable(char state) throws Exception
	{
		if(state != '+' && state != '-')
			throw INVALIDCOMMAND;
		else if(state == '+')
			lineBuilder.setWrappable(true);
		else if(state == '-')
			lineBuilder.setWrappable(false);
	}
}
