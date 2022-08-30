package main.Module.Map.States.Territory;

import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Tools.InitHelp;

public class TerritoryLeftBar extends VBox
{
    private final MapStateTerritory parent;
    private final TerritoryTree tree;

    public TerritoryLeftBar(final MapStateTerritory parentState)
    {
        parent = parentState;
        tree = new TerritoryTree(parent);
        Button add = new Button("New +");
        InitHelp.ButtonInit(add);
        VBox.setVgrow(add, Priority.SOMETIMES);
        add.setOnAction(evt->
        {
            parent.AddTerritory();
        });
        getChildren().add(add);
        getChildren().add(tree);
    }

    public final TerritoryTree GetTree(){return tree;}
}
