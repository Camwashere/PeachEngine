package main.Module.Story.Scenario.Frame.Parameter.OutputParameter;

import javafx.geometry.Pos;
import main.Data.Frame.ParameterBaseData;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Frame.Parameter.ParameterBase;

public class OutputParameter<T> extends ParameterBase<T>
{

    public OutputParameter(final BaseFrame parentFrame, final ParamType paramType, final boolean array)
    {
        super(parentFrame, paramType, array, false);
        Init();
    }
    public OutputParameter(final BaseFrame parentFrame, final ParamType paramType,final boolean array, final String nameStr)
    {
        super(parentFrame, paramType, array, false);
        SetName(nameStr);
        Init();
    }
    public OutputParameter(final BaseFrame parentFrame, final OutputParameter<T> other)
    {
        super(parentFrame, other);
        SetName(other.GetName());
        Init();
    }

    public OutputParameter(final BaseFrame parentFrame, final ParameterBaseData<T> data)
    {
        super(parentFrame, data);
        Init();
    }

    private void Init()
    {
        name.setAlignment(Pos.CENTER_RIGHT);
        getChildren().clear();
        getChildren().add(name);
        getChildren().add(shape);
        if (GetType() == ParamType.FLOW)
        {
            connectors.Single(true);
            connectors.Replace(true);
        }
    }

    @Override
    protected void OnConnect()
    {

    }

    @Override
    protected void OnDisconnect()
    {


    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }
}
