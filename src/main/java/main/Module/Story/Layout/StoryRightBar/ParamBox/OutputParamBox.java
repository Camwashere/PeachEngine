package main.Module.Story.Layout.StoryRightBar.ParamBox;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import main.Module.Story.Layout.StoryRightBar.StoryRightBar;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameterBool;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Frame.ScenarioFrame.ScenarioInputFrame;
import main.Module.Story.Scenario.Frame.ScenarioFrame.ScenarioOutputFrame;
import main.Module.Story.Scenario.ScenarioType;
import main.Module.Story.StoryModule;
import main.Tools.InitHelp;

import java.util.*;

public class OutputParamBox extends ParamBoxBase
{
    public OutputParamBox(final StoryRightBar rightBar)
    {
        super(rightBar, "Outputs");
    }

    @Override
    public void AddParameter()
    {
        InputParameterBool param = new InputParameterBool(GetOutput());
        GetOutput().AddInputParam(param);
        OutputParamRow row = new OutputParamRow(this, param);
        rows.add(row);
        content.getChildren().add(row);
    }

    @Override
    public void AddParameter(final ParamType paramType)
    {
        InputParameter<?> param = InputParameter.CREATE(GetOutput(), paramType, false);
        GetOutput().AddInputParam(param);
        OutputParamRow row = new OutputParamRow(this, param);
        rows.add(row);
        content.getChildren().add(row);
    }

    public void Update()
    {
        this.setDisable(!GetParent().GetParent().GetCurrentScenario().IsStandard());
        Clear();
        for (InputParameter<?> in : parent.GetParent().GetCurrentScenario().GetOutputFrame().GetInputParams())
        {
            LoadParameter(in);
        }
    }

    public void LoadParameter(final InputParameter<?> in)
    {
        OutputParamRow row = new OutputParamRow(this, in);
        rows.add(row);
        content.getChildren().add(row);
    }
}
