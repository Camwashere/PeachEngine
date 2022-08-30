package main.Tools;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import main.Module.Story.Layout.StoryRightBar.ParamBox.ParamRowBase;

public class NamedValue<T>
{
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleObjectProperty<T> value = new SimpleObjectProperty<T>();

    public NamedValue()
    {
        SetName("");
    }
    public NamedValue(final String nameStr)
    {
        SetName(nameStr);
    }
    public NamedValue(final String nameStr, final T val)
    {
        SetName(nameStr);
        SetValue(val);
    }

    public final void SetName(final String str){name.set(str);}
    public final void SetValue(final T val){value.setValue(val);}

    public final String GetName(){return name.get();}
    public final T GetValue(){return value.getValue();}
    public final SimpleStringProperty GetNameProp(){return name;}
    public final SimpleObjectProperty<T> GetValueProp(){return value;}
}
