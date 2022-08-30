package main.Module.Story.Scenario.Frame.StoryFrame.TextEditor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import main.Debug.Debug;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Tools.StringHelp;

import java.util.Objects;

public class TextVariable
{
    private final SimpleStringProperty style;
    private final SimpleStringProperty name;

    public TextVariable(final InputParameter<?> param)
    {
        name = new SimpleStringProperty(param.GetName());
        name.bind(param.GetLabel().textProperty());
        style = new SimpleStringProperty(StringHelp.ColorToStyle(param.GetColor()));
    }

    public final void SetStyle(final Color c){style.set(StringHelp.ColorToStyle(c));}
    public final String GetName(){return name.get();}
    public final SimpleStringProperty GetNameProp(){return name;}
    public final String GetStyle(){return style.get();}



    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        if (obj instanceof TextVariable other)
        {
            return Objects.equals(style.get(), other.style.get()) && Objects.equals(name.get(), other.name.get());
        }
        return false;
    }
}
