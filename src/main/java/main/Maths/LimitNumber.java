package main.Maths;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import main.Data.DataBase;
import main.Data.SaveObject;

import java.util.Objects;

/**
 * A number that is bound to a minimum and maximum value.
 * If the number is bigger or smaller than the minimum or maximum values respectively,
 * it will be corrected back within the range.
 */
public class LimitNumber implements Comparable<LimitNumber>, SaveObject
{
    private final SimpleDoubleProperty current = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty min = new SimpleDoubleProperty(Double.MIN_VALUE);
    private final SimpleDoubleProperty max = new SimpleDoubleProperty(Double.MAX_VALUE);
    private final SimpleBooleanProperty active = new SimpleBooleanProperty(false);
    private RangeHit onHit;

    public LimitNumber()
    {
        Init();
        SetMin(Double.MIN_VALUE);
        SetMax(Double.MAX_VALUE);
    }
    public LimitNumber(final LimitNumber other)
    {
        Init();
        SetMin(other.GetMin());
        SetMax(other.GetMax());
        SetCurrent(other.GetCurrent());
        SetActive(other.IsActive());
        onHit = other.onHit;
    }
    public LimitNumber(final double baseValue)
    {
        Init();
        SetCurrent(baseValue);
    }
    public LimitNumber(final double baseValue, final double minValue, final double maxValue)
    {
        Init();
        SetMin(minValue);
        SetMax(maxValue);
        SetCurrent(baseValue);
    }
    public LimitNumber(final double baseValue, final double minValue, final double maxValue, final boolean isActive)
    {
        Init();
        SetActive(isActive);
        SetMin(minValue);
        SetMax(maxValue);
        SetCurrent(baseValue);
    }
    private void Init()
    {
        HitInit();
        ListenerInit();
    }
    private void HitInit()
    {
        onHit = new RangeHit()
        {
            @Override
            public void HitMin()
            {

            }

            @Override
            public void HitMax()
            {

            }
        };
    }
    private void ListenerInit()
    {
        current.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1.doubleValue() > GetMax())
                {
                    SetCurrent(GetMax());
                }
                else if (t1.doubleValue() < GetMin())
                {
                    SetCurrent(GetMin());
                }
                else if (t1.doubleValue() == GetMax())
                {
                    HitMax();
                }
                else if (t1.doubleValue() == GetMin())
                {
                    HitMin();
                }
            }
        });
        min.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1.doubleValue() > GetMax())
                {
                    SetMin(GetMax());

                }
                if (t1.doubleValue() > GetCurrent())
                {
                    SetCurrent(GetMin());
                }
            }
        });
        max.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1.doubleValue() < GetMin())
                {
                    SetMax(GetMin());
                }
                if (t1.doubleValue() < GetCurrent())
                {
                    SetCurrent(GetMax());
                }
            }
        });
    }
    private void HitMax()
    {
        if (IsActive())
        {
            if (onHit != null)
            {
                onHit.HitMax();
            }
        }
    }
    private void HitMin()
    {
        if (IsActive())
        {
            if (onHit != null)
            {
                onHit.HitMin();
            }
        }
    }

    /**
     * Sets what course of action will be taken when the number
     * reaches the minimum/maximum limits.
     * @param hit Code to execute when the limit has been reached
     */
    public final void SetOnHit(final RangeHit hit){onHit = hit;}
    public final void SetCurrent(final double d){current.set(d);}
    public final void SetMin(final double d){min.set(d);}
    public final void SetMax(final double d){max.set(d);}

    /**
     * Sets if hitting the limits triggers the onHit code
     * @param bool true to engage the on onHit code when a limit is hit, false to ignore
     */
    public final void SetActive(final boolean bool){active.set(bool);}

    public final void Increment(){SetCurrent(GetCurrent()+1);}
    public final void IncrementMin(){SetMin(GetMin()+1);}
    public final void IncrementMax(){SetMax(GetMax()+1);}
    public final void Increment(final double amount){SetCurrent(GetCurrent()+amount);}
    public final void IncrementMin(final double amount){SetMin(GetMin()+amount);}
    public final void IncrementMax(final double amount){SetMax(GetMax()+amount);}

    public final void Decrement(){SetCurrent(GetCurrent()-1);}
    public final void DecrementMin(){SetMin(GetMin()-1);}
    public final void DecrementMax(){SetMax(GetMax()-1);}
    public final void Decrement(final double amount){SetCurrent(GetCurrent()-amount);}
    public final void DecrementMin(final double amount){SetMin(GetMin()-amount);}
    public final void DecrementMax(final double amount){SetMax(GetMax()-amount);}

    /**
     * Restores all values to their defaults and clears the onHit code
     */
    public final void Reset()
    {
        SetActive(false);
        SetMin(Double.MIN_VALUE);
        SetMax(Double.MAX_VALUE);
        SetCurrent(0);
        ClearHit();
    }
    public final void ClearHit(){onHit = null;}

    public final boolean Contains(double value)
    {
        return value >= GetMin() && value <= GetMax();
    }
    public final double GetSize(){return Math.abs(GetMax() - GetMin());}


    public final SimpleDoubleProperty GetMinProp(){return min;}
    public final SimpleDoubleProperty GetMaxProp(){return max;}
    public final SimpleDoubleProperty GetCurrentProp(){return current;}
    public final SimpleBooleanProperty GetActiveProp(){return active;}
    public final boolean IsActive(){return active.get();}
    public final double GetCurrent(){return current.get();}
    public final double GetMin(){return min.get();}
    public final double GetMax(){return max.get();}

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (obj instanceof LimitNumber other)
        {
            return Objects.equals(GetCurrent(), other.GetCurrent()) && Objects.equals(GetMin(), other.GetMin()) && Objects.equals(GetMax(),other.GetMax())
                    && Objects.equals(IsActive(), other.IsActive());
        }
        return false;
    }

    @Override
    public String toString()
    {
        return String.format("Current: %s, Min: %s, Max: %s, Active: %s",GetCurrent(), GetMin(), GetMax(), IsActive());
    }



    @Override
    public int compareTo(LimitNumber other)
    {
        return Double.compare(GetCurrent(), other.GetCurrent());
    }

    @Override
    public DataBase ToData()
    {
        DataBase data = new DataBase(getClass().getName());
        data.Add("MIN", min.getValue());
        data.Add("MAX", max.getValue());
        data.Add("CURRENT", current.getValue());
        data.Add("ACTIVE", active.getValue());
        return data;
    }

    @Override
    public void Load(DataBase data)
    {
        if (Objects.equals(data.GetClassName(), getClass().getName()))
        {
            min.setValue(data.GetNumber("MIN"));
            max.setValue(data.GetNumber("MAX"));
            current.setValue(data.GetNumber("CURRENT"));
            active.setValue(data.GetBool("ACTIVE"));
        }
    }
}
