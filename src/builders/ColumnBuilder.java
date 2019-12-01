package builders;

import java.util.ArrayList;

public class ColumnBuilder
{
	private static final Exception INVALIDNUMCOLUMNS = new Exception("numColumns must be either 1, or 2.");

	private static final String SPACING = "          ";
	
	private int numColumns;
	private String lineSpacing;
	private ArrayList<String> lineList;
	
	public ColumnBuilder(int numColumns) throws Exception
	{
		if(numColumns != 1 && numColumns != 2)
			throw INVALIDNUMCOLUMNS;
		
		this.numColumns = numColumns;
		lineList = new ArrayList<String>();	
	}
	
	public void add(String completedLine)
	{
		lineList.add(completedLine);
	}
	
	public void setLineSpacing(String lineSpacing)
	{
		this.lineSpacing = lineSpacing;
	}
	
	private String merge(String line1, String line2)
	{
		return line1 + SPACING + line2;
	}
	
	public String merge()
	{
		int numLines = lineList.size();
		int numMergedLines = numLines / numColumns;
		
		String mergedLines = "";
		String line1, line2;
		for(int index = 0; index < numMergedLines; index++)
		{
			if(numColumns == 2)
			{
				line1 = lineList.get(index);
				line2 = lineList.get(numLines / 2 + index + numLines % 2);
				mergedLines += merge(line1, line2);
			}
			else
			{
				mergedLines += lineList.get(index);
			}
			mergedLines += lineSpacing;
		}
		if(numColumns == 2 && numLines % 2 == 1)
			mergedLines += lineList.get(numLines / 2);
		
		return mergedLines;
	}
}
