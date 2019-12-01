package builders;

public class TitleBuilder {

    private LineBuilder linebuilder;
    private String stringUnderline = "";
    private String stringtitle = "";
    private String title = linebuilder.getLine();

    int numberOfSpaces = (linebuilder.getMaxChars() - title.length())/2; //determines how many spaces on each side of title

    public String makeTitle()
    {
        for(int i = 0; i < numberOfSpaces; i++)
        {
            stringUnderline = stringUnderline.concat(" ");
            stringtitle = stringtitle.concat(" ");
        }

        for(int i = 0; i < title.length(); i++) //underlines characters in title
        {
            stringUnderline = stringUnderline.concat("_");
        }

        stringtitle.concat(title); //centers title

        return stringtitle + "\n" + stringUnderline; //returns the centered title and underline

    }
}
