package main.Maths;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.SizeUnits;
import main.Tools.StringHelp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Distance
{
    private static final double INCH_TO_METER = 0.0254;
    private static final double FOOT_TO_METER = 0.3048;
    private static final double MILE_TO_METER = 1609.344;
    private static final double MM_TO_METER = 0.001;
    private static final double CM_TO_METER = 0.01;
    private static final double KM_TO_METER = 1000;
    private static final double YD_TO_METER = 0.9144;



    private final SimpleDoubleProperty length;
    private final SimpleObjectProperty<DistanceType> type;

    public Distance()
    {
        length = new SimpleDoubleProperty(0);
        type = new SimpleObjectProperty<DistanceType>(DistanceType.MILES);
    }

    public Distance(double len, DistanceType dt)
    {
        length = new SimpleDoubleProperty(len);
        type = new SimpleObjectProperty<DistanceType>(dt);
    }

    @Override
    public String toString()
    {
        DecimalFormat format = new DecimalFormat("0.000");
        return format.format(length.get()) + " " + type.get().toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Distance))
        {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Distance d = (Distance)o;

        // Compare the data members and return accordingly
        return length.get() == d.length.get() && type.get().equals(d.type.get());


    }

    @Override
    public int hashCode()
    {
        return Objects.hash(length.get(), type.get());
    }

    public void SetLength(double len){length.set(len);}
    public void SetType(DistanceType dType) {type.set(dType);}
    public void Convert(DistanceType dType)
    {
        CONVERT(this, dType);
        SetType(dType);
    }

    public final double GetLength(){return length.get();}
    public final DistanceType GetType(){return type.get();}

    public final SimpleDoubleProperty GetLengthProp(){return length;}
    public final SimpleObjectProperty<DistanceType> GetTypeProp(){return type;}


    public static double Convert(double val, DistanceType prev, DistanceType dt)
    {
        Distance d = new Distance(val, prev);
        CONVERT(d, dt);
        return d.GetLength();
    }
    private static void CONVERT(Distance d, final DistanceType dt)
    {
        if (d.GetType().equals(dt))
        {
            return;
        }

        double factor=1;

        switch(d.GetType())
        {
            case MILES:
                factor = MILE_TO_METER;
                break;
            case KILOMETERS:
                factor = KM_TO_METER;
                break;
            case FEET:
                factor = FOOT_TO_METER;
                break;
            case YARDS:
                factor = YD_TO_METER;
                break;
            case CENTIMETERS:
                factor = CM_TO_METER;
                break;
            case INCHES:
                factor = INCH_TO_METER;
                break;
            default:
                break;
        }
        double meters = ToMeters(d.GetLength(), factor);
        factor = 1;

        switch (dt)
        {
            case MILES:
                factor = MILE_TO_METER;
                break;
            case KILOMETERS:
                factor = KM_TO_METER;
                break;
            case FEET:
                factor = FOOT_TO_METER;
                break;
            case YARDS:
                factor = YD_TO_METER;
                break;
            case CENTIMETERS:
                factor = CM_TO_METER;
                break;
            case INCHES:
                factor = INCH_TO_METER;
                break;
            default:
                break;
        }
        d.SetLength(FromMeters(meters, factor));
    }

    private static double ToMeters(double val, double factor){return val * factor;}
    private static double FromMeters(double val, double factor){return val / factor;}
}
