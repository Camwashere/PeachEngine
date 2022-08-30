package main.Tools;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class ValueButton<T> extends ToggleButton
{
    private final SimpleObjectProperty<T> value = new SimpleObjectProperty<T>();

    public ValueButton()
    {
        setMinWidth(50);
    }
    public ValueButton(String name)
    {
        super(name);
        setMinWidth(50);
    }

    public final void SetValue(final T val){value.set(val);}
    public final T GetValue(){return value.get();}
    public final SimpleObjectProperty<T> GetValueProp(){return value;}
}
