package main.Module.Story.Scenario.Frame.Parameter.InputParameter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Tools.InitHelp;
import main.Tools.Field.NumericTextField;

public class InputParameterNumber extends InputParameter<Number>
{
    private final NumericTextField field;
    public InputParameterNumber(final BaseFrame parentFrame)
    {
        super(parentFrame, ParamType.NUMBER, false);
        field = new NumericTextField();
        Init();
    }
    public InputParameterNumber(final BaseFrame parentFrame, final String paramName)
    {
        super(parentFrame, ParamType.NUMBER, paramName);
        field = new NumericTextField();
        Init();
        //SetName(paramName);
    }

    private void Init()
    {
        field.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                SetValue(field.GetValue());

            }
        });
        InitHelp.NodeInit(field);
        field.setAlignment(Pos.CENTER_LEFT);
        if (! IsArray())
        {
            getChildren().add(field);
        }
        SetValue(field.GetValue());
    }

    @Override
    protected Node GetNode()
    {
        return field;
    }

    @Override
    protected Number GetNodeValue()
    {
        return field.GetValue();
    }
}
