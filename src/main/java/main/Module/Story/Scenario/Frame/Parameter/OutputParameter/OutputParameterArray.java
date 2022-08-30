package main.Module.Story.Scenario.Frame.Parameter.OutputParameter;

import main.Data.Frame.ParameterBaseData;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;

import java.util.ArrayList;

public class OutputParameterArray<T> extends OutputParameter<ArrayList<T>>
{
    public OutputParameterArray(final BaseFrame parentFrame, final ParamType paramType)
    {
        super(parentFrame, paramType, true);
        //SetValue(new ArrayList<T>());
    }

    public OutputParameterArray(final BaseFrame parentFrame, final ParamType paramType, final String paramName)
    {
        super(parentFrame, paramType, true, paramName);
        //SetValue(new ArrayList<T>());
    }


}
