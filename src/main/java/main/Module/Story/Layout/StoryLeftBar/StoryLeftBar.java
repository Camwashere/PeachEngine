package main.Module.Story.Layout.StoryLeftBar;

import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Module.Story.StoryModule;
import main.Tools.InitHelp;

public class StoryLeftBar extends VBox
{
    private final StoryModule parent;
    private final ScenarioTree scenarioTree;


    public StoryLeftBar(final StoryModule modParent)
    {
        parent = modParent;
        Label label = new Label("Scenarios");
        InitHelp.LabelInit(label);
        VBox.setVgrow(label, Priority.NEVER);
        scenarioTree = new ScenarioTree(parent);
        getChildren().add(label);
        getChildren().add(scenarioTree);


    }

    public final ScenarioTree GetScenarioTree(){return scenarioTree;}

}
