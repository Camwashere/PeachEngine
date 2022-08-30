package main.Module.Item.Layout;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import main.Module.Item.ItemModule;
import main.Tools.InitHelp;

public class ItemTopBar extends HBox
{
    private final ItemModule parent;

    public ItemTopBar(final ItemModule moduleParent)
    {
        parent = moduleParent;
        Init();
    }

    private void Init()
    {
        Button items = new Button("Items");
        Button spawnTables = new Button("Spawns");
        InitHelp.ButtonsInit(items, spawnTables);
        getChildren().addAll(items, spawnTables);
    }

    public final ItemModule GetParent(){return parent;}
}
