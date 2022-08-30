package main.Module.Map.States.Tilemap.Right;

import javafx.scene.control.ListView;
import main.Module.Map.States.Tilemap.TilemapLayer;

public class TileLayerList extends ListView<TilemapLayer>
{
    private final TileRightBar parent;

    public TileLayerList(final TileRightBar parentBar)
    {
        parent = parentBar;
    }

    private void Init()
    {

    }

    public final TileRightBar GetParent(){return parent;}
}
