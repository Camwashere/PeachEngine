package main.Module.Character.Game.Variables;

import javafx.beans.property.SimpleStringProperty;
import main.Tools.LinkedMapProperty;

public class Trait extends CharacterVariableBase
{
    private final LinkedMapProperty<Integer, TraitValue> values;
    private final SimpleStringProperty value;

    public Trait(final String varName)
    {
        super(varName, CharacterVariableType.TRAIT);
        values = new LinkedMapProperty<>();
        value = new SimpleStringProperty();
    }
    public Trait(final Trait t)
    {
        super(t);
        values = t.GetValues();
        value = new SimpleStringProperty();
    }

    public final void AddValue()
    {
        String a = "NewValue";
        int count=0;
        for (final TraitValue temp : values.values())
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
        AddValue(new TraitValue(val));
    }
    public final void AddValue(final TraitValue t)
    {
        values.put(t.GetID(), t);
    }

    public final String GetValue(){return value.get();}
    public final SimpleStringProperty GetValueProp(){return value;}
    public final LinkedMapProperty<Integer, TraitValue> GetValues(){return values;}

    @Override
    public String toString()
    {
        return String.format("%sValue: %s\n", super.toString(), value.get());
    }
}
