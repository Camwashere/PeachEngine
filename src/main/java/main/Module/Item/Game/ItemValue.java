package main.Module.Item.Game;

import javafx.beans.property.SimpleStringProperty;
import main.Maths.LimitNumber;
import main.Tools.NamedValue;

import java.util.UUID;

public class ItemValue extends NamedValue<LimitNumber>
{
    private final UUID id = UUID.randomUUID();

    public ItemValue(final String nameStr)
    {
        super(nameStr);
        SetMin(Double.MIN_VALUE);
        SetMax(Double.MAX_VALUE);
        SetCurrent(0);

    }

    public final void SetCurrent(final double d){GetValue().SetCurrent(d);}
    public final void SetMin(final double d){GetValue().SetMin(d);}
    public final void SetMax(final double d){GetValue().SetMax(d);}

    public final UUID GetID(){return id;}
    public final double GetCurrent(){return GetValue().GetCurrent();}
    public final double GetMax(){return GetValue().GetMax();}
    public final double GetMin(){return GetValue().GetMin();}

    @Override
    public String toString()
    {
        return super.toString();
    }
}
