package main.Module.Story.Scenario.Frame.Parameter.InputParameter;

import javafx.scene.Node;
import main.Data.Frame.ParameterBaseData;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;

public class InputParameterFlow extends InputParameter<BaseFrame>
{
    public InputParameterFlow(final BaseFrame parentFrame)
    {
        super(parentFrame, ParamType.FLOW, false);
    }
    public InputParameterFlow(final BaseFrame parentFrame, final ParameterBaseData<BaseFrame> data)
    {
        super(parentFrame, data);
    }

    @Override
    protected Node GetNode()
    {
        return null;
    }

    @Override
    protected BaseFrame GetNodeValue()
    {
        return null;
    }
}
