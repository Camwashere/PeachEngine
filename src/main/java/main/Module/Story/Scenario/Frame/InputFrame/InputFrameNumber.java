package main.Module.Story.Scenario.Frame.InputFrame;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import main.Data.Frame.BaseFrameData;
import main.Data.Frame.InputFrameData;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Scenario;
import main.Tools.Field.NumericTextField;

public class InputFrameNumber extends InputFrameBase<Number>
{
    private final NumericTextField field = new NumericTextField();
    public InputFrameNumber(final Scenario s)
    {
        super(s, ParamType.NUMBER);
        Init();

    }
    public InputFrameNumber(final Scenario s, final InputFrameData data)
    {
        super(s, data);
        Init();
    }
    private void Init()
    {
        field.GetValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
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
