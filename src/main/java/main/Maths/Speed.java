package main.Maths;

import javafx.geometry.Pos;
import main.Debug.Debug;
import main.Tools.StringHelp;

import java.util.concurrent.TimeUnit;

public class Speed
{
    private final Distance distance;
    private final TimeValue time;
    public Speed()
    {
        distance = new Distance(4, DistanceType.MILES);
        time = new TimeValue(TimeType.HOUR);

    }



    public final void SetDistanceAmount(final double amount){distance.SetLength(amount);}
    public final void SetDistanceType(final DistanceType type){distance.SetType(type);}
    public final void SetTimeType(final TimeType type){time.SetType(type);}

    public final Distance GetDistance(){return distance;}
    public final TimeValue GetTimeValue(){return time;}
    public final double GetDistanceAmount(){return distance.GetLength();}
    public final DistanceType GetDistanceType(){return distance.GetType();}
    public final TimeType GetTimeType(){return time.GetType();}

    @Override
    public String toString()
    {
        if (distance.GetLength() % 1 == 0)
        {
            return (int)distance.GetLength() + " " + StringHelp.EnumFormat(distance.GetType()) + " per " + time.toString();
        }
        return distance.GetLength() + " " + StringHelp.EnumFormat(distance.GetType()) + " per " + time.toString();
    }
}
