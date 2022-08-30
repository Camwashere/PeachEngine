package main.Module.Character.Game.Variables;

import javafx.beans.property.SimpleStringProperty;

public class MentalStateValue
{
    private static int ID = Integer.MIN_VALUE;
    private static int ASSIGN_ID(){return ID++;}
    private final int id;
    private final SimpleStringProperty value;

    public MentalStateValue()
    {
        id = ASSIGN_ID();
        value = new SimpleStringProperty();
    }
    public MentalStateValue(final String str)
    {
        id = ASSIGN_ID();
        value = new SimpleStringProperty(str);
    }
    public final void SetValue(final String str){value.set(str);}
    public final String GetValue(){return value.get();}
    public final SimpleStringProperty GetValueProp(){return value;}
    public final int GetID(){return id;}

}
