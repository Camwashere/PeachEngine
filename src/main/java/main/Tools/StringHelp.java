package main.Tools;

import javafx.scene.paint.Color;

import java.util.List;

/**
 * Helper class for quick {@link String} formatting
 */
public class StringHelp
{
    /**
     * Converts the string to lower case and capitalizes the first letter.
     * @param s The original {@link String} to capitalize
     * @return The newly capitalized {@link String}
     */
    public static String Capitalize(String s)
    {
        if (s == null)
        {
            return "";
        }
        if (s.length()==1)
        {
            return s.toUpperCase();
        }
        if (s.isEmpty())
        {
            return "";
        }
        return s.substring(0, 1).toUpperCase() + s.toLowerCase().substring(1);
    }

    public static String ColorToStyle(final Color c)
    {
        String r = String.valueOf(c.getRed() * 255) + ",";
        String g = String.valueOf(c.getGreen()*255) + ",";
        String b = String.valueOf(c.getBlue()*255) + ",";
        String a = String.valueOf(c.getOpacity());

        return "-fx-fill:rgba(" + r + g + b + a + ");";
    }

    /**
     * Converts an enum value into a nicer looking formatted string.
     * <p>
     *     Converts the enum to lower case and capitalizes the first letter
     *     while also replacing '_' characters with spaces to make the name
     *     appear more readable and look nicer for when an enum name needs to
     *     be displayed as a string.
     * </p>
     * @param e The enum to format
     * @return The formatted {@link String}
     */
    public static String EnumFormat(Enum<?> e)
    {
        String[] arr = e.name().split("_");
        String rtn = "";
        for (String str : arr)
        {
            if (!rtn.isEmpty())
            {
                rtn += " ";
            }
            rtn += Capitalize(str);
        }
        return rtn;
    }

    /**
     * Convert absolute file path format to format that can be opened in code
     * @param s The absolute file path
     * @return Reformatted file path
     */
    public static String AbsoluteFile(final String s)
    {
        return s.replace("\\", "/");
    }
}
