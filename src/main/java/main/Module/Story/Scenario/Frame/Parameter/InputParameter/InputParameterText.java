package main.Module.Story.Scenario.Frame.Parameter.InputParameter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import main.Data.Frame.ParameterBaseData;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Tools.InitHelp;

import java.util.function.UnaryOperator;

public class InputParameterText extends InputParameter<String>
{
    private final TextField field = new TextField();
    public InputParameterText(final BaseFrame parentFrame)
    {
        super(parentFrame, ParamType.TEXT, false);
        Init();
    }
    public InputParameterText(final BaseFrame parentFrame, final String paramName)
    {
        super(parentFrame, ParamType.TEXT, paramName);
        Init();
    }
    public InputParameterText(final BaseFrame parentFrame, final ParameterBaseData<String> data)
    {
        super(parentFrame, data);
        Init();
        SetValue(data.value());
    }
    private void Init()
    {
        ListenerInit();
        InitHelp.NodeInit(field);
        if (! IsArray())
        {
            getChildren().add(field);
        }
    }

    private void ListenerInit()
    {
        /*field.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                SetValue(t1);
            }
        });*/
        field.textProperty().bindBidirectional(GetValueProp());
    }

    @Override
    protected Node GetNode()
    {
        return field;
    }

    @Override
    protected String GetNodeValue()
    {
        return field.getText();
    }
}
