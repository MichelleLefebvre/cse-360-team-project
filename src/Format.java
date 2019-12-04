import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import allignment.CenterAllignment;
import allignment.EqualAllignment;
import allignment.LeftAllignment;
import allignment.RightAllignment;
import builders.*;

public class Format
{
	//exceptions
	private static final Exception INVALIDCOMMAND = new Exception("Invalid Command");
	
	//constants
	private static final int FIRST = 0;
	private static final char COMMANDTOKEN = '-';	
	private static final int SINGLE = 1;
	private static final int DOUBLE = 2;
	
	//instance variables
	private LineBuilder lineBuilder;
	private ColumnBuilder columnBuilder;
	private Scanner fileScanner;
	
	public Format(File input) throws Exception
	{
		lineBuilder = new LineBuilder();
		columnBuilder = new ColumnBuilder();
		fileScanner = null;
		
		parse(input);
	}
	
	public String getFormattedText()
	{
		return columnBuilder.toString();
	}
	
	public void saveTo(File output) throws Exception
	{
		if(output == null || output.exists())
			throw new Exception("Error, that file already exists.");
		
		System.out.println(columnBuilder);
		
		FileWriter writer = new FileWriter(output);
		writer.write(columnBuilder.toString());
		writer.close();
	}
	
	private void parse(File input) throws Exception
	{
		fileScanner = new Scanner(new FileReader(input));
		
		while(fileScanner.hasNextLine())
			parse(fileScanner.nextLine());
		
		fileScanner.close();
	}
	
	private void parse(String line) throws Exception
	{
		if(isCommand(line))
		{
			execute(line);
		}
		else
		{
			columnBuilder.add(lineBuilder.format(line));
		}
	}
	
	//checks to see if the line is a command
	private static boolean isCommand(String line)
	{
		if(line.length() <= 0)
			return false;
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
			throw new Exception(ex.getMessage());
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
			title();
			break;
			
		case 's':
			lineBuilder.setSpacing(SINGLE);
			break;
			
		case 'd':
			lineBuilder.setSpacing(DOUBLE);
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
			lineBuilder.setMaxChars(parseInteger(parameter));
			break;
			
		case 'w':
			setWrap(parameter.charAt(FIRST));
			break;
			
		case 'a':
			columnBuilder.setNumColumns(parseInteger(parameter));
			break;
		
		case 'p':
			lineBuilder.indent(parseInteger(parameter));
			break;
			
		case 'b':
			columnBuilder.add(lineBuilder.blankLines(parseInteger(parameter)));
			break;
			
		default:
			throw INVALIDCOMMAND;
		}
	}
	
	private static int parseInteger(String parameter) throws Exception
	{
		int parsedInteger = -1;
		Scanner scan = new Scanner(parameter);
		if(scan.hasNextInt())
			parsedInteger = scan.nextInt();
		scan.close();
		if(parsedInteger < 0)
			throw new Exception();
		return parsedInteger;
	}
	
	private void title() throws Exception
	{
		String nextLine = "";
		while(fileScanner.hasNextLine() && isCommand((nextLine = fileScanner.nextLine())))
			execute(nextLine);
		
		int numColumns = columnBuilder.getNumColumns();
		columnBuilder.setNumColumns(1);
		columnBuilder.add(lineBuilder.makeTitle(nextLine));
		columnBuilder.setNumColumns(numColumns);
	}
	
	private void setWrap(char state) throws Exception
	{
		if(state != '+' && state != '-')
			throw INVALIDCOMMAND;
		else if(state == '+')
			lineBuilder.setWrap(true);
		else if(state == '-')
			lineBuilder.setWrap(false);
	}
}