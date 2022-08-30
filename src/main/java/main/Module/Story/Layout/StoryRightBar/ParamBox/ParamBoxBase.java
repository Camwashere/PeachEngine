package main.Module.Story.Layout.StoryRightBar.ParamBox;

import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.Module.Story.Layout.StoryRightBar.StoryRightBar;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Frame.Parameter.ParameterBase;
import main.Module.Story.Scenario.Frame.ScenarioFrame.ScenarioInputFrame;
import main.Module.Story.Scenario.Frame.ScenarioFrame.ScenarioOutputFrame;
import main.Module.Story.StoryModule;
import main.Tools.InitHelp;
import main.Tools.StringHelp;

import java.util.ArrayList;

public abstract class ParamBoxBase extends VBox
{
    protected final StoryRightBar parent;
    protected final ArrayList<ParamRowBase> rows;
    protected final Label name;
    protected final MenuButton add;
    protected final ScrollPane pane;
    protected final VBox content;

    public ParamBoxBase(final StoryRightBar rightBar, final String boxName)
    {
        parent = rightBar;
        rows = new ArrayList<ParamRowBase>();
        name = new Label(boxName);
        InitHelp.LabelInit(name);
        add = new MenuButton("Add +");
        for (ParamType p : ParamType.values())
        {
            MenuItem item = new MenuItem(StringHelp.EnumFormat(p));
            item.setOnAction(evt->
            {
                AddParameter(p);
            });
            add.getItems().add(item);
        }
        //add.setContentDisplay(ContentDisplay.RIGHT);
        add.setPopupSide(Side.BOTTOM);
        pane = new ScrollPane();
        content = new VBox();
        InitHelp.GrowInit(content);
        InitHelp.GrowInit(pane);
        InitHelp.ButtonInit(add);

        HBox box = new HBox();
        box.getChildren().add(name);
        box.getChildren().add(add);
        getChildren().add(box);
        pane.setContent(content);
        getChildren().add(pane);
        pane.setFitToWidth(true);
        pane.setFitToHeight(true);
        InitHelp.GrowInit(this);

    }

    public abstract void AddParameter();
    public abstract void AddParameter(final ParamType p);

    public final void RemoveRow(final ParamRowBase row)
    {
        content.getChildren().remove(row);
        rows.remove(row);
    }
    public final void Clear()
    {
        ArrayList<ParamRowBase> list = new ArrayList<>(rows);
        for (ParamRowBase row : list)
        {
            RemoveRow(row);
        }
    }


    public final StoryRightBar GetParent(){return parent;}
    public final StoryModule GetStory(){return parent.GetParent();}
    public final ScenarioInputFrame GetInput(){return parent.GetParent().GetCurrentScenario().GetInputFrame();}
    public final ScenarioOutputFrame GetOutput(){return parent.GetParent().GetCurrentScenario().GetOutputFrame();}
}
