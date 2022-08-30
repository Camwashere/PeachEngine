package main.Module.Story.Scenario.Frame.Parameter.InputParameter;

import javafx.scene.Node;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;

import java.util.ArrayList;

public class InputParameterArray<T> extends InputParameter<ArrayList<T>>
{
    public InputParameterArray(final BaseFrame parentFrame, final ParamType paramType)
    {
        super(parentFrame, paramType, true);
        SetValue(new ArrayList<T>());
    }
    @Override
    protected Node GetNode()
    {
        return null;
    }

    @Override
    protected ArrayList<T> GetNodeValue()
    {
        return null;
    }
}
