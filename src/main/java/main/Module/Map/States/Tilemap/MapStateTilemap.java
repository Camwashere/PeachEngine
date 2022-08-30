package main.Module.Map.States.Tilemap;

import main.Module.Map.MapModule;
import main.Module.Map.States.MapStateBase;
import main.Module.Map.States.MapStateType;

public class MapStateTilemap extends MapStateBase
{
    private final TilemapCenter center;
    public MapStateTilemap(final MapModule parentMod)
    {
        super(parentMod, MapStateType.TILEMAP);
        center = new TilemapCenter(this);
        setCenter(center);
    }
}
