package builders;

import java.util.Scanner;

import allignment.*;

public class LineBuilder
{
	private int maxChars;
	private boolean wrappable;
	
	private String line;
	private String remainder;
	
	private AllignmentType allignment;
	private boolean isComplete;
	
	public LineBuilder(int maxChars, boolean wrappable, AllignmentType allignment)
	{
		this.maxChars = maxChars;
		this.wrappable = wrappable;
		this.allignment = allignment;
		line = "";
		remainder = "";
		isComplete = false;
	}
	
	public void setMaxChars(int maxChars)
	{
		this.maxChars = maxChars;
	}
	
	public int getMaxChars()
	{
		return maxChars;
	}
	
	
	public void setWrappable(boolean wrappable)
	{
		this.wrappable = wrappable;
	}
	
	public void setAllignment(AllignmentType type)
	{
		allignment = type;
	}
	
	public void add(String word) throws Exception
	{
		if(word.length() > maxChars && (wrappable == true))
			throw new Exception("Word longer than line with wrap on");
		
		if(line.length() + word.length() > maxChars)
		{
			if(line.length() > maxChars)
				line = line.trim();
			
			if(wrappable == true)
			{
				remainder = word;
			}
			else
			{
				int diff = maxChars - line.length();
				line += word.substring(0, diff);
				remainder = word.substring(diff);
			}
			isComplete = true;
		}
		else
		{
			line += word + " ";
			isComplete = false;
		}
	}
	
	public boolean isComplete()
	{
		return isComplete;
	}
	
	public boolean isEmpty()
	{
		return line.length() == 0;
	}
	
	public String getLine()
	{
		return allignment.allign(line.trim(), maxChars);
	}
	
	public String makeTitle(String fullLine) throws Exception
	{
		Scanner scan = new Scanner(fullLine);
		String title = "";
		while(scan.hasNext())
		{
			title += scan.next() + " ";
		}
		scan.close();
		title = title.trim();
		if(title.length() > maxChars)
			throw new Exception("Error, title cannot fit on one line.");
		return title;	
	}
	
	public void reset()
	{
		line = remainder + " ";
		remainder = "";
	}
}