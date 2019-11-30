package allignment;

public abstract class AllignmentType
{
	protected static String spacing = " ";
	
	public abstract String allign(String line, int maxChars);
	
	protected static String repeat(String toRepeat, int numTimes)
	{
		String repeat = "";
		for(int counter = 0; counter < numTimes; counter++)
			repeat += toRepeat;
		return repeat;
	}
}
