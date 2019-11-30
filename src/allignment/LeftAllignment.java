package allignment;

public class LeftAllignment extends AllignmentType
{
	public String allign(String line, int maxChars)
	{
		return line + repeat(spacing, (maxChars - line.length()));
	}
}