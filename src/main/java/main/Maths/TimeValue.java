package main.Maths;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import main.Tools.StringHelp;

import java.util.concurrent.TimeUnit;

public class TimeValue
{
    private final static double MillisecondsToSeconds = 0.001;
    private final static double SecondsToMinutes = 0.60;


    private final SimpleObjectProperty<TimeType> type;
    private final SimpleDoubleProperty value;

    public TimeValue(final TimeType timeType)
    {
        type = new SimpleObjectProperty<TimeType>(timeType);
        value = new SimpleDoubleProperty(1);

    }


    public double ValueToMilliseconds()
    {
        return GetValue() * MillisecondsToSeconds;
    }
    /*private double MillisecondsToSeconds(final double m)
    {
        return m/1000;
    }
    private double SecondsToMinutes(final double s)
    {
        return s/60;
    }
    private double MinutesToHours(final double m)
    {
        return m/60;
    }
    private double HoursToDays(final double h)
    {
        return h/24;
    }
    private double DaysToWeeks(final double d)
    {
        return d/7;
    }
    private double WeeksToYears(final double w)
    {
        return w/52;
    }
    private double YearsToDecades(final double y)
    {
        return y/10;
    }
    private double DecadesToCenturies(final double d)
    {
        return d/10;
    }*/

    public final void SetType(final TimeType t){type.set(t);}
    public final void SetValue(final double v){value.set(v);}

    public final double Convert(final TimeType t)
    {
        if (GetType() == t)
        {
            return GetValue();
        }
        return 0;
    }

    public final TimeType GetType(){return type.get();}
    public final double GetValue(){return value.doubleValue();}
    public final SimpleObjectProperty<TimeType> GetTypeProp(){return type;}
    public final SimpleDoubleProperty GetValueProp(){return value;}

    @Override
    public String toString()
    {
        if (value.get() == 1)
        {
            return StringHelp.EnumFormat(type.get());
        }
        return value.get() + " " + StringHelp.EnumFormat(type.get()) + "s";
    }
}
