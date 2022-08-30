package main.Module.Story.Scenario.Frame.ScenarioFrame;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.Module.Story.Layout.StoryRightBar.ParamBox.InputParamBox;
import main.Module.Story.Layout.StoryRightBar.ParamBox.OutputParamBox;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.FrameType;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Frame.StoryFrame.StoryFrame;
import main.Module.Story.Scenario.Scenario;

public class ScenarioInputFrame extends BaseFrame
{
    private final InputParamBox box;
    private final OutputParameter<BaseFrame> output;
    public ScenarioInputFrame(final Scenario s)
    {
        super(s, FrameType.SCENARIO_INPUT);
        box = s.GetParent().GetRight().GetInputBox();
        SetName(" Input ");
        output = new OutputParameter<BaseFrame>(this, ParamType.FLOW, false, "Input");
        name.setDisable(true);
        contextMenu.getItems().clear();
        AddOutputParam(output);
    }


    @Override
    protected void ContextMenuInit()
    {


    }

    @Override
    protected void StyleInit()
    {
        setFocusTraversable(false);
        Color c = Color.DARKKHAKI.saturate().darker();
        Color color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 0.5);
        Background background = new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
        setBackground(background);
        Border b = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        setBorder(b);

    }

    public final OutputParameter<BaseFrame> GetOutput(){return output;}
}
