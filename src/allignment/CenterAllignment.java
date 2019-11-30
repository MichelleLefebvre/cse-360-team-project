package allignment;

public class CenterAllignment extends AllignmentType
{
	public String allign(String line, int maxChars)
	{
		int diff = maxChars - line.length();
		String leftSpacing = repeat(spacing, (diff / 2));
		String rightSpacing = leftSpacing;
		if(diff % 2 == 1)
			leftSpacing += spacing;
		return leftSpacing + line + rightSpacing;
	}
}
