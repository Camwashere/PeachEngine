package main.Module.Map.States.Default.Layout;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Module.Map.States.Default.Grid.GridView;
import main.Module.Map.States.Default.MapStateDefault;
import main.Tools.InitHelp;
import main.Tools.LinkedMapProperty;

import java.util.UUID;

public class MapDefaultLeft extends VBox
{
    private final MapStateDefault parent;
    private final GridTree tree;
    private final Button add = new Button("New +");

    public MapDefaultLeft(final MapStateDefault parentState)
    {
        parent = parentState;
        tree = new GridTree(this);
        LabelInit();
        ButtonInit();
        getChildren().add(tree);
    }
    private void ButtonInit()
    {
        InitHelp.ButtonInit(add);
        VBox.setVgrow(add, Priority.NEVER);
        add.setOnAction(evt->
        {
            tree.AddRootGrid();
        });
        getChildren().add(add);

    }
    private void LabelInit()
    {
        Label label = new Label("Grids");
        InitHelp.LabelInit(label);
        VBox.setVgrow(label, Priority.NEVER);
        getChildren().add(label);
    }

    public final void AddGrid()
    {
        tree.AddGrid();
    }
    public final void AddGrid(final String name)
    {
        tree.AddGrid(name);
    }


    public final MapStateDefault GetParent(){return parent;}
    public final void SetCurrent(final UUID id){tree.SetCurrent(id);}
    public final void SetCurrent(final GridView gridView){tree.SetCurrent(gridView);}
    public final GridView GetCurrent(){return tree.GetCurrent();}
    public final GridView GetGlobal(){return tree.GetGlobal();}
    public final GridView GetGridView(final UUID id){return tree.GetGridView(id);}
    public final GridTree GetTree(){return tree;}

    public final LinkedMapProperty<UUID, GridView> GetGrids(){return tree.GetGrids();}
    public final SimpleObjectProperty<UUID> GetCurrentProp(){return tree.GetCurrentProp();}
}
