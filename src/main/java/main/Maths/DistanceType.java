package main.Maths;

import main.Tools.StringHelp;

import java.util.ArrayList;
import java.util.List;

public enum DistanceType
{
    MILES,
    KILOMETERS,
    METERS,
    FEET,
    YARDS,
    CENTIMETERS,
    INCHES,
    ;



    public static List<String> AsNames()
    {
        List<String> list = new ArrayList<String>();
        for (DistanceType d : values())
        {
            list.add(StringHelp.Capitalize(d.name()));

        }
        return list;
    }

    @Override
    public String toString()
    {
        return StringHelp.Capitalize(name());
    }
}
