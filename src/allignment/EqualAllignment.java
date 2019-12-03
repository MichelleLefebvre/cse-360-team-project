package allignment;

public class EqualAllignment extends AllignmentType
{
	public String allign(String line, int maxChars)
	{
		String[] words = line.split(" ");
		int numCharacter = 0;
		String newLine = "";

		while(numCharacter < maxChars) {

			for (int i = 0; i < words.length; i++)
			{
				if(numCharacter < maxChars) {
					words[i] += " ";
					numCharacter += 1;

				}
			}

			numCharacter = 0;
			for (int i = 0; i < words.length; i++)
			{

				numCharacter += words[i].length();

			}



		}

		for(int i = 0; i < words.length; i++)
		{
			newLine += words[i];
		}

		return newLine;
	}

	public static void main(String[] args)
	{

		EqualAllignment e = new EqualAllignment();

		e.allign("Michelle is the best. keep going everything will be fine.", 80);
	}
}