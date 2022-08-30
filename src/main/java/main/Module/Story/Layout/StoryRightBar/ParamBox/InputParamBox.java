package main.Module.Story.Layout.StoryRightBar.ParamBox;

import main.Module.Story.Layout.StoryRightBar.StoryRightBar;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameterBool;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Frame.Parameter.ParameterBase;

public class InputParamBox extends ParamBoxBase
{
    public InputParamBox(final StoryRightBar rightBar)
    {
        super(rightBar, "Inputs");
    }

    @Override
    public void AddParameter()
    {
        OutputParameter<Boolean> param = new OutputParameter<Boolean>(GetInput(), ParamType.BOOL, false);
        GetInput().AddOutputParam(param);
        InputParamRow row = new InputParamRow(this, param);
        rows.add(row);
        content.getChildren().add(row);

    }

    @Override
    public void AddParameter(final ParamType paramType)
    {
        OutputParameter<?> param = new OutputParameter<>(GetInput(), paramType, false);
        GetInput().AddOutputParam(param);
        InputParamRow row = new InputParamRow(this, param);
        rows.add(row);
        content.getChildren().add(row);

    }

    public final void Update()
    {
        this.setDisable(!GetParent().GetParent().GetCurrentScenario().IsStandard());
        Clear();
        for (OutputParameter<?> out : parent.GetParent().GetCurrentScenario().GetInputFrame().GetOutputParams())
        {
            LoadParameter(out);
        }
    }

    public void LoadParameter(OutputParameter<?> p)
    {
        InputParamRow row = new InputParamRow(this, p);
        rows.add(row);
        content.getChildren().add(row);
    }
}
