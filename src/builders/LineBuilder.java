package builders;
import java.util.ArrayList;
import java.util.Scanner;

import allignment.*;

public class LineBuilder 
{
	//constants
	private static final String SPACE = " ";
	private static final String UNDERLINE = "_";
	private static final int SINGLE = 1;
	private static final int DOUBLE = 2;
	
	//defaults
	private static final AllignmentType DEFAULTALLIGNMENT = new LeftAllignment();
	private static final boolean DEFAULTWRAPPABLE = true;
	private static final int DEFAULTMAXCHARS = 60;
	private static final int DEFAULTSPACING = SINGLE;
	
	//instance variables
	private String line;
	private String remainder;
	private AllignmentType allignment;
	private boolean wrappable;
	private int maxChars;
	private int spacing;
	
	private ArrayList<String> formattedText;
	
	public LineBuilder()
	{
		line = "";
		remainder = "";
		formattedText = new ArrayList<String>();
		allignment = DEFAULTALLIGNMENT;
		wrappable = DEFAULTWRAPPABLE;
		maxChars = DEFAULTMAXCHARS;
		spacing = DEFAULTSPACING;
	}
	
	public void indent(int numSpaces) throws Exception
	{
		if(numSpaces > maxChars)
			throw new Exception("Error, you cannot indent more spaces than maximum characters in a line");
		remainder = allignment.repeat(SPACE, numSpaces);
	}
	
	public ArrayList<String> format(String rawText)
	{
		line = remainder;
		formattedText = new ArrayList<String>();
		
		Scanner scan = new Scanner(rawText);
		while(scan.hasNext())
			add(scan.next());
		scan.close();
		
		if(line.length() + remainder.length() > 0)
			this.addToFormatted();
		
		return formattedText;
	}
	
	public ArrayList<String> makeTitle(String rawText)
	{
		AllignmentType previousAllignment = this.allignment;
		this.setAllignment(new CenterAllignment());
		
		ArrayList<String> title = format(rawText);
		
		String current;
		String underline;
		
		for(int index = 0; index < title.size(); index++)
		{
			current = title.get(index).trim();
			underline = allignment.repeat(UNDERLINE, current.length());
			
			current = allignment.allign(current, maxChars);
			underline = allignment.allign(underline, maxChars);
			
			title.add(index + 1, underline);
			
			if(spacing == DOUBLE)
				title.add(getBlankLine());
			
			index += spacing;			
		}
		
		this.allignment = previousAllignment;
		
		return title;
	}
	
	public String getBlankLine()
	{
		return allignment.repeat(" ", maxChars);
	}
	
	public void setWrap(boolean wrappable)
	{
		this.wrappable = wrappable;
	}
	
	public void setMaxChars(int maxChars)
	{
		this.maxChars = maxChars;
	}
	
	public ArrayList<String> blankLines(int numLines)
	{
		ArrayList<String> blankLines = new ArrayList<String>();
		for(int index = 0; index < (numLines * spacing); index++)
			blankLines.add(getBlankLine());
		return blankLines;
	}
	
	public void setSpacing(int spacing) throws Exception
	{
		if(spacing != 1 && spacing != 2)
			throw new Exception("Error, spacing must be either 1, or 2");
		
		this.spacing = spacing;
	}
	
	public void setAllignment(AllignmentType allignment)
	{
		this.allignment = allignment;
	}
	
	private void add(String word)
	{
		line += word + SPACE;
		
		if(removeLastSpace(line).length() > maxChars)
			wrap();
	}
	
	private void wrap()
	{
		if(wrappable == true)
		{
			remainder = getLastWord(line).trim();
			line = removeLastWord(line);
		}
		else
		{
			remainder = line.substring(maxChars).trim();
			line = line.substring(0, maxChars);
		}
		
		this.addToFormatted();
	}
	
	private void addToFormatted()
	{
		line = removeLastSpace(line);
		line = allignment.allign(line, maxChars);
		formattedText.add(line);
		
		if(spacing == DOUBLE)
			formattedText.add(getBlankLine());
		
		line = remainder + SPACE;
		remainder = "";
	}
	
	private static String removeLastWord(String line)
	{
		line = removeLastSpace(line);
		String lastWord = getLastWord(line);
		line = line.substring(0, line.lastIndexOf(lastWord));
		return line;
	}
	
	private static String getLastWord(String line)
	{
		line = removeLastSpace(line);
		if(line.length() > 0 && line.contains(SPACE))
			line = line.substring(line.lastIndexOf(SPACE) + 1, line.length());
		return line;
	}
	
	private static String removeLastSpace(String line)
	{
		if(line.length() > 0 && lastChar(line) == ' ')
			line = line.substring(0, line.lastIndexOf(SPACE));
		return line;
	}
	
	private static char lastChar(String line)
	{
		return line.charAt(line.length() - 1);
	}
}
