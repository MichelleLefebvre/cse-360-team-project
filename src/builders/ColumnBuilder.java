package builders;

import java.util.ArrayList;

public class ColumnBuilder
{
	private static final int DEFAULTNUMCOLUMNS = 1;
	
	private static final String SPACING = "          ";
	private static final String ENDLINE = "\n";
	
	private int numColumns;
	private ArrayList<String> lineList;
	
	private String finalizedColumns;
	
	public ColumnBuilder()
	{
		this.numColumns = DEFAULTNUMCOLUMNS;
		lineList = new ArrayList<String>();
		
		finalizedColumns = "";
	}
	
	public int getNumColumns()
	{
		return numColumns;
	}
	
	public void setNumColumns(int newNumColumns) throws Exception
	{
		if(newNumColumns != 1 && newNumColumns != 2)
			throw new Exception();
		
		if(numColumns != newNumColumns)
		{
			finalizedColumns += merge();
			lineList = new ArrayList<String>();
			numColumns = newNumColumns;
		}
	}
	
	public void add(ArrayList<String> linesToAdd)
	{
		lineList.addAll(linesToAdd);
	}
	
	public void add(String lineToAdd)
	{
		lineList.add(lineToAdd);
	}
	
	private String merge(int index)
	{
		String mergedIndex = "";
		if(numColumns == 1)
		{
			mergedIndex = lineList.get(index);
		}
		else
		{
			int numLines = lineList.size();
			mergedIndex = lineList.get(index) + SPACING + lineList.get(numLines / 2 + index + numLines % 2);
		}
		return mergedIndex;
	}
	
	private String merge()
	{			
		String mergedLines = "";
		
		int endIndex = lineList.size() / numColumns;
		for(int index = 0; index < endIndex; index++)
			mergedLines += merge(index) + ENDLINE;
		
		if(numColumns == 2 && lineList.size() % 2 == 1)
			mergedLines += lineList.get(lineList.size() / 2) + ENDLINE;
		
		return mergedLines;
	}
	
	public String toString()
	{
		if(!lineList.isEmpty())
		{
			finalizedColumns += merge();
			lineList = new ArrayList<String>();
		}
		
		return finalizedColumns;
	}	
}
