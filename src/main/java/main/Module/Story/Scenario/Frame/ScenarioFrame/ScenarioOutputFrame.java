package main.Module.Story.Scenario.Frame.ScenarioFrame;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.Data.Frame.BaseFrameData;
import main.Module.Story.Layout.StoryRightBar.ParamBox.OutputParamBox;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.FrameType;
import main.Module.Story.Scenario.Scenario;

public class ScenarioOutputFrame extends BaseFrame
{
    private final OutputParamBox box;
    public ScenarioOutputFrame(final Scenario s)
    {
        super(s, FrameType.SCENARIO_INPUT);
        box = s.GetParent().GetRight().GetOutputBox();
        Init();
    }
    public ScenarioOutputFrame(final Scenario s, final BaseFrameData data)
    {
        super(s, data);
        box = s.GetParent().GetRight().GetOutputBox();
        Init();
    }
    private void Init()
    {
        SetName(" Output ");
        name.setDisable(true);
        contextMenu.getItems().clear();
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
}
