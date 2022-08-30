package main.Module.Story.Scenario.Frame.VariableFrame;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.FrameType;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Scenario;
import main.Tools.StringHelp;

import java.io.*;

public class VariableFrame<T> extends BaseFrame
{
    private final InputParameter<T> input;
    private final OutputParameter<T> output;
    public VariableFrame(final Scenario s, final ParamType t, final boolean array)
    {
        super(s, FrameType.VARIABLE);
        input = (InputParameter<T>) InputParameter.CREATE(this, t, array);
        output = new OutputParameter<T>(this, t, array);
        Init();

    }

    private void Init()
    {
        name.setDisable(true);
        SetBackground(output.GetColor().desaturate().darker());
        AddInputParam(input);
        AddOutputParam(output);
        name.prefColumnCountProperty().bind(name.textProperty().length());
        SetName(" " + StringHelp.Capitalize(output.GetType().name()) + " ");
        output.GetValueProp().bind(input.GetValueProp());

    }
    

    @Override
    protected void ContextMenuInit()
    {


    }

    @Override
    protected void StyleInit()
    {
        setFocusTraversable(false);
        Background background = new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY));
        setBackground(background);
        Border b = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        setBorder(b);
    }

    public final ParamType GetParamType(){return output.GetType();}
    public final InputParameter<T> GetInput(){return input;}
    public final OutputParameter<T> GetOutput(){return output;}
}
