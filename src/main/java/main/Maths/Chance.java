package main.Maths;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Chance
{
    private final SimpleDoubleProperty percent;

    public static int RandInt(final int range)
    {
        return (int)(Math.random() * range);
    }
    public static double Rand(final double range){return Math.random()*range;}
    public static double RandRange(final double lowerBound, final double upperBound){return (Math.random()*upperBound) + lowerBound;}

    public Chance()
    {
        percent = new SimpleDoubleProperty(100);
        ListenerInit();
    }
    public Chance(final double p)
    {
        percent = new SimpleDoubleProperty(100);
        ListenerInit();
        SetPercent(p);
    }

    private void ListenerInit()
    {
        percent.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1.doubleValue() > 100)
                {
                    percent.set(100);
                }
                else if (t1.doubleValue() < 0)
                {
                    percent.set(0);
                }
            }
        });
    }


    public int getRandomNumber(int min, int max)
    {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public final boolean Roll()
    {
        double val = Math.random() * 100;
        return val <= percent.doubleValue();
    }


    public final void SetPercent(final double p){percent.set(p);}
    public final double GetPercent(){return percent.get();}
    public final SimpleDoubleProperty GetPercentProp(){return percent;}
}
