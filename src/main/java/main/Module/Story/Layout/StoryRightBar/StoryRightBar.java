package main.Module.Story.Layout.StoryRightBar;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Module.Story.Layout.StoryRightBar.ParamBox.InputParamBox;
import main.Module.Story.Layout.StoryRightBar.ParamBox.OutputParamBox;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.StoryModule;
import main.Tools.InfoRow;
import main.Tools.InitHelp;
import main.Tools.StringHelp;

public class StoryRightBar extends VBox
{
    private final StoryModule parent;
    private final InfoRow id;
    private final InfoRow name;
    private final InfoRow type;
    private final InfoRow frames;
    private final InputParamBox inputBox;
    private final OutputParamBox outputBox;

    public StoryRightBar(final StoryModule storyMod)
    {
        parent = storyMod;
        id = new InfoRow("ID");
        name = new InfoRow("Name");
        type = new InfoRow("Type");
        frames = new InfoRow("Frames");
        inputBox = new InputParamBox(this);
        outputBox = new OutputParamBox(this);
        getChildren().addAll(id, name, type, frames);
        Separator s = new Separator(Orientation.HORIZONTAL);
        s.setBackground(InitHelp.Background(Color.BLACK, 0));
        getChildren().add(s);
        getChildren().add(inputBox);
        Separator s2 = new Separator(Orientation.HORIZONTAL);
        s2.setBackground(InitHelp.Background(Color.BLACK, 0));
        getChildren().add(s2);
        getChildren().add(outputBox);
        setBorder(InitHelp.Border(Color.BLACK, 2));
    }



    public void Update()
    {
        id.SetValue(parent.GetCurrentScenario().GetID());
        name.SetValue(parent.GetCurrentScenario().GetName());
        type.SetValue(StringHelp.EnumFormat(parent.GetCurrentScenario().GetType()));
        frames.SetValue(parent.GetCurrentScenario().GetFrameCount());
        inputBox.Update();
        outputBox.Update();
    }

    public final StoryModule GetParent(){return parent;}
    public final InputParamBox GetInputBox(){return inputBox;}
    public final OutputParamBox GetOutputBox(){return outputBox;}
}
