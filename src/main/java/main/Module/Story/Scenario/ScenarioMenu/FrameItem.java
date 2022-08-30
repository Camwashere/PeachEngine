package main.Module.Story.Scenario.ScenarioMenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.FunctionFrame.FunctionFrame;
import main.Module.Story.Scenario.Frame.InputFrame.InputFrameBase;
import main.Module.Story.Scenario.Frame.InputFrame.InputFrameBool;
import main.Module.Story.Scenario.Frame.InputFrame.InputFrameNumber;
import main.Module.Story.Scenario.Frame.InputFrame.InputFrameText;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Frame.Parameter.ParameterBase;
import main.Module.Story.Scenario.Frame.ScenarioFrame.ScenarioFrame;
import main.Module.Story.Scenario.Frame.StoryFrame.StoryFrame;
import main.Module.Story.Scenario.Frame.VariableFrame.VariableFrame;
import main.Module.Story.Scenario.Scenario;

import java.util.Objects;

public class FrameItem implements EventHandler<ActionEvent>
{
    private final BaseFrame frame;
    private String name;
    private final String parent;
    public FrameItem(final String p, final String str)
    {
        parent = p;
        name = str;
        frame = null;
    }
    public FrameItem(final String p, final String str, final BaseFrame baseFrame)
    {
        parent = p;
        name = str;
        frame = baseFrame;
    }

    public static TreeItem<FrameItem> CREATE(final String p, final String str)
    {
        return new TreeItem<FrameItem>(new FrameItem(p, str));
    }
    public static TreeItem<FrameItem> CREATE(final String p, final String str, final BaseFrame baseFrame)
    {
        return new TreeItem<FrameItem>(new FrameItem(p, str, baseFrame));
    }
    private void DetermineVariableFrame()
    {
        VariableFrame<?> guess = (VariableFrame<?>)frame;
        switch(Objects.requireNonNull(guess).GetOutput().GetType())
        {
            case BOOL:
                frame.GetParent().AddFrame(new VariableFrame<Boolean>(frame.GetParent(), ParamType.BOOL, guess.GetOutput().IsArray()));
                break;
            case NUMBER:
                frame.GetParent().AddFrame(new VariableFrame<Number>(frame.GetParent(), ParamType.NUMBER, guess.GetOutput().IsArray()));
                break;
            case TEXT:
                frame.GetParent().AddFrame(new VariableFrame<Number>(frame.GetParent(), ParamType.TEXT, guess.GetOutput().IsArray()));
                break;
            case CHARACTER:
                frame.GetParent().AddFrame(new VariableFrame<Character>(frame.GetParent(), ParamType.CHARACTER, guess.GetOutput().IsArray()));
                break;
            default:
                frame.GetParent().AddFrame(new VariableFrame<BaseFrame>(frame.GetParent(), ParamType.FLOW, guess.GetOutput().IsArray()));
                break;
        }
    }

    private void DetermineInputFrame()
    {
        InputFrameBase<?> guess = (InputFrameBase<?>)frame;
        switch (Objects.requireNonNull(guess).GetInputType())
        {
            case TEXT:
                frame.GetParent().AddFrame(new InputFrameText(frame.GetParent()));
                break;
            case NUMBER:
                frame.GetParent().AddFrame(new InputFrameNumber(frame.GetParent()));
                break;
            case BOOL:
                frame.GetParent().AddFrame(new InputFrameBool(frame.GetParent()));
                break;
            default:
                break;
        }
    }


    @Override
    public void handle(ActionEvent actionEvent)
    {
        if (! IsDirectory())
        {
            switch(frame.GetFrameType())
            {
                case STORY:
                    frame.GetParent().AddFrame(new StoryFrame(frame.GetParent()));
                    break;
                case VARIABLE:
                    DetermineVariableFrame();
                            break;
                case FUNCTION:
                    frame.GetParent().AddFrame(new FunctionFrame((FunctionFrame)frame));
                    break;
                case SCENARIO:
                    frame.GetParent().AddFrame(new ScenarioFrame((ScenarioFrame)frame));
                    break;
                case INPUT:
                    DetermineInputFrame();
                    break;
                default:
                    break;
            }
        }
    }

    public final boolean ParamMatch(final ParameterBase<?> param)
    {
        if (frame == null)
        {
            return false;
        }
        if (param.IsOutput())
        {
            for (final InputParameter<?> in : frame.GetInputParams())
            {
                if (in.GetType() == param.GetType() && in.IsArray() == param.IsArray())
                {
                    return true;
                }
            }
        }
        else
        {
            for (final OutputParameter<?> out : frame.GetOutputParams())
            {
                if (out.GetType() == param.GetType() && out.IsArray() == param.IsArray())
                {
                    return true;
                }
            }
        }
        return false;
    }
    public final void SetName(final String n){name = n;}
    public final String GetName(){return name;}
    public final String GetParent(){return parent;}
    public final boolean IsDirectory(){return frame == null;}
    public final boolean HasFrame(){return frame != null;}
    public final boolean HasParent(){return parent != null;}
}
