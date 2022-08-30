package main.Module.Character.Game.Variables;

import javafx.beans.property.SimpleStringProperty;


public class Descriptor extends CharacterVariableBase
{
    private final SimpleStringProperty value;
    public Descriptor(final String varName)
    {
        super(varName, CharacterVariableType.DESCRIPTOR);
        value = new SimpleStringProperty();


    }
    public Descriptor(final Descriptor temp)
    {
        super(temp);
        value = new SimpleStringProperty();

    }

    public final String GetValue(){return value.get();}
    public final SimpleStringProperty GetValueProp(){return value;}

    @Override
    public String toString()
    {
        return String.format("%sValue: %s\n", super.toString(), value.get());
    }
}
