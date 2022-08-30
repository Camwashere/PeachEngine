package main.Module.Story.Scenario.Frame.Parameter.InputParameter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Tools.InitHelp;

import java.util.function.UnaryOperator;

public class InputParameterText extends InputParameter<String>
{
    private final TextField field;
    public InputParameterText(final BaseFrame parentFrame)
    {
        super(parentFrame, ParamType.TEXT, false);
        field = new TextField();
        field.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                SetValue(t1);
            }
        });
        InitHelp.NodeInit(field);
        if (! IsArray())
        {
            getChildren().add(field);
        }
    }
    public InputParameterText(final BaseFrame parentFrame, final String paramName)
    {
        super(parentFrame, ParamType.TEXT, paramName);
        field = new TextField();
        field.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                SetValue(t1);
            }
        });
        InitHelp.NodeInit(field);
        if (! IsArray())
        {
            getChildren().add(field);
        }
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
