package allignment;

public class RightAllignment extends AllignmentType
{
	public String allign(String line, int maxChars)
	{
		return repeat(spacing, (maxChars - line.length())) + line;
	}
}
