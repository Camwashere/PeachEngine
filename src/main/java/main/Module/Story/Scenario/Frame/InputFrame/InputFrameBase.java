package main.Module.Story.Scenario.Frame.InputFrame;

import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.Data.Frame.BaseFrameData;
import main.Data.Frame.InputFrameData;
import main.Data.Frame.ParameterBaseData;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.FrameType;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameterText;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Frame.Parameter.ParameterBase;
import main.Module.Story.Scenario.Scenario;
import main.Tools.StringHelp;

public abstract class InputFrameBase<T> extends BaseFrame
{
    protected final InputParameterText prompt;
    protected final OutputParameter<T> output;
    public InputFrameBase(final Scenario s, final ParamType outputType)
    {
        super(s, FrameType.INPUT);
        prompt = new InputParameterText(this, "Prompt");
        output = new OutputParameter<T>(this, outputType, false);
        AddInputParameter(ParamType.FLOW);
        AddInputParam(prompt);
        AddOutputParameter(ParamType.FLOW);
        AddOutputParam(output);
        Init();
    }
    public InputFrameBase(final Scenario s, final InputFrameData data)
    {
        super(s, data.baseData());
        prompt = (InputParameterText)GetInputParams().get(1);
        output = (OutputParameter<T>)GetOutputParams().get(1);
        Init();
    }


    private void Init()
    {
        Background background = new Background(new BackgroundFill(output.GetColor().desaturate().darker(), CornerRadii.EMPTY, Insets.EMPTY));
        setBackground(background);
        SetName("Player " + StringHelp.EnumFormat(GetInputType()) + " Input");
    }
    @Override
    protected void ContextMenuInit()
    {

    }

    @Override
    protected void StyleInit()
    {
        setFocusTraversable(false);
        Background background = new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY));
        setBackground(background);
        Border b = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        setBorder(b);
    }

    public abstract Control GetField();
    public abstract boolean IsReady();


    public final BaseFrame GetNext()
    {
        if (outputParams.get(0).IsConnected())
        {
            return outputParams.get(0).GetFirstConnector().GetInput().GetParent();
        }
        return null;
    }
    public final void SetOutputValue(final T val){output.SetValue(val);}
    public final boolean HasPrompt(){return prompt.GetValue() != null;}
    public final ParamType GetInputType(){return output.GetType();}
    public final InputParameterText GetPrompt(){return prompt;}
    public final OutputParameter<T> GetOutput(){return output;}
    public final String GetPromptText(){return prompt.GetValue();}

    public final InputFrameData AsData()
    {
        return new InputFrameData(output.GetType(), AsBaseData());
    }


}
