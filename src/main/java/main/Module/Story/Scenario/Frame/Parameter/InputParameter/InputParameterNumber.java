package main.Module.Story.Scenario.Frame.Parameter.InputParameter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import main.Data.Frame.ParameterBaseData;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Tools.InitHelp;
import main.Tools.Field.NumericTextField;

public class InputParameterNumber extends InputParameter<Number>
{
    private final NumericTextField field = new NumericTextField();
    public InputParameterNumber(final BaseFrame parentFrame)
    {
        super(parentFrame, ParamType.NUMBER, false);
        Init();
    }
    public InputParameterNumber(final BaseFrame parentFrame, final String paramName)
    {
        super(parentFrame, ParamType.NUMBER, paramName);
        Init();
    }

    public InputParameterNumber(final BaseFrame parentFrame, final ParameterBaseData<Number> data)
    {
        super(parentFrame, data);
        Init();
        SetValue(data.value());
        //field.SetValue(data.value());
    }

    private void Init()
    {
        /*field.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                SetValue(field.GetValue());

            }
        });*/
        InitHelp.NodeInit(field);
        field.setAlignment(Pos.CENTER_LEFT);
        if (! IsArray())
        {
            getChildren().add(field);
        }
        field.GetValueProp().bindBidirectional(GetValueProp());
        //SetValue(field.GetValue());
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
