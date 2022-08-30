package main.Module.Story.Scenario.Frame.Parameter.InputParameter;

import javafx.scene.Node;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;

public class InputParameterGeneric extends InputParameter<Object>
{
    public InputParameterGeneric(final BaseFrame parentFrame)
    {
        super(parentFrame, ParamType.GENERIC, false);

    }

    public void Cast(final ParamType t, final boolean array)
    {

    }

    @Override
    protected void OnConnect()
    {
        super.OnConnect();
        System.out.println("NEEDS CAST");

    }



    @Override
    protected Node GetNode()
    {
        return null;
    }

    @Override
    protected Object GetNodeValue()
    {
        return null;
    }
}
