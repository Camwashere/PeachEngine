package main.Module.Map.States.Climate;

import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Module.Map.Game.Climate;
import main.Tools.InitHelp;

import java.util.UUID;

public class ClimateLeftBar extends VBox
{
    private final MapStateClimate parent;
    private final ClimateTree tree;

    public ClimateLeftBar(final MapStateClimate parentState)
    {
        parent = parentState;
        tree = new ClimateTree(this);
        TreeInit();
    }
    private void TreeInit()
    {
        Button add = new Button("New +");
        InitHelp.ButtonInit(add);
        VBox.setVgrow(add, Priority.NEVER);
        add.setOnAction(evt->
        {
            AddClimate();
        });
        getChildren().add(add);
        getChildren().add(tree);
    }

    public final void AddClimate(){parent.AddClimate();}
    public final MapStateClimate GetParent(){return parent;}
    public final Climate GetClimate(final UUID id){return parent.GetClimate(id);}
    public final ClimateTree GetTree(){return tree;}
}
