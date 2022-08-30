package main.Module.Character.Game.Variables;

import javafx.beans.property.SimpleStringProperty;
import main.Tools.LinkedMapProperty;

public class MentalState extends CharacterVariableBase
{

    private final LinkedMapProperty<Integer, MentalStateValue> values;
    private final SimpleStringProperty value;
    public MentalState(final String varName)
    {
        super(varName, CharacterVariableType.MENTAL);
        values = new LinkedMapProperty<>();
        value = new SimpleStringProperty();
    }
    public MentalState(final MentalState temp)
    {
        super(temp);
        values = temp.GetValues();
        value = new SimpleStringProperty();
    }

    public final void AddValue()
    {
        String a = "NewValue";
        int count=0;
        for (final MentalStateValue temp : values.values())
        {
            if (temp.GetValue().length() > a.length())
            {
                if (temp.GetValue().substring(0, a.length()).equals(a))
                {
                    count = Integer.parseInt(temp.GetValue().substring(a.length(), temp.GetValue().length()));
                    count++;
                }
            }
            else if (temp.GetValue().equals(a))
            {
                count++;
            }
        }
        if (count > 0)
        {
            a = a + count;
        }
        AddValue(a);
    }
    public final void AddValue(final String val)
    {
        MentalStateValue t = new MentalStateValue(val);
        AddValue(t);
    }
    public final void AddValue(final MentalStateValue m)
    {
        values.put(m.GetID(), m);
    }

    public final LinkedMapProperty<Integer, MentalStateValue> GetValues(){return values;}
    public final String GetValue(){return value.get();}
    public final SimpleStringProperty GetValueProp(){return value;}

    @Override
    public String toString()
    {
        return String.format("%sValue: %s\n", super.toString(), value.get());
    }
}
