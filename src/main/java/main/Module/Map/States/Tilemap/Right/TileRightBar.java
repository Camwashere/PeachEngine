package main.Module.Map.States.Tilemap.Right;

import javafx.scene.layout.VBox;
import main.Module.Map.States.Tilemap.MapStateTilemap;

public class TileRightBar extends VBox
{
    private final MapStateTilemap parent;
    public TileRightBar(final MapStateTilemap parentState)
    {
        parent = parentState;
    }

    public final MapStateTilemap GetParent(){return parent;}

}
