package main.Module.Story.Scenario.Frame.InputFrame;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Scenario;

public class InputFrameText extends InputFrameBase<String>
{
    private final TextField field;
    public InputFrameText(final Scenario s)
    {
        super(s, ParamType.TEXT);
        field = new TextField();
        field.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                SetOutputValue(t1);
            }
        });
    }

    @Override
    public Control GetField()
    {
        return field;
    }

    @Override
    public boolean IsReady()
    {
        return !field.getText().isEmpty();
    }
}
