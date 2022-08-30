package main.Module.Map;


import main.Module.Map.Game.Climate;
import main.Module.Map.Game.Territory;
import main.Module.Map.States.Climate.MapStateClimate;
import main.Module.Map.States.Default.MapStateDefault;
import main.Module.Map.States.MapStateBase;
import main.Module.Map.States.MapStateType;
import main.Module.Map.States.Territory.MapStateTerritory;
import main.Module.Map.States.Texture.MapStateTexture;
import main.Module.Map.States.Tilemap.MapStateTilemap;
import main.Module.ModuleBase.BaseModule;
import main.Module.ModuleBase.ModuleID;
import main.Module.ModuleManager.ModuleManager;
import main.Tools.LinkedMapProperty;

import java.util.UUID;


public class MapModule extends BaseModule
{
    private final MapModuleTopBar top;
    private final MapStateDefault defaultState;
    private final MapStateTerritory territoryState;
    private final MapStateClimate climateState;
    private final MapStateTexture textureState;
    private final MapStateTilemap tilemapState;


    public MapModule(final ModuleManager parentManager)
    {
        super(ModuleID.MAP, parentManager);
        top = new MapModuleTopBar(this);
        setTop(top);
        defaultState = new MapStateDefault(this);
        territoryState = new MapStateTerritory(this);
        climateState = new MapStateClimate(this);
        textureState = new MapStateTexture(this);
        tilemapState = new MapStateTilemap(this);
        AddState(defaultState);
        AddState(territoryState);
        AddState(climateState);
        AddState(textureState);
        AddState(tilemapState);
        SetState(MapStateType.DEFAULT);
    }


    private void AddState(final MapStateBase state)
    {
        top.AddState(state);
    }
    public final void SetState(final MapStateType type)
    {
        top.SetState(type);
    }

    public final MapStateDefault GetDefaultState(){return defaultState;}
    public final MapStateTerritory GetTerritoryState(){return territoryState;}
    public final MapStateClimate GetClimateState(){return climateState;}
    public final MapStateTexture GetTextureState(){return textureState;}
    public final MapStateTilemap GetTilemapState(){return tilemapState;}

    public final LinkedMapProperty<UUID, Territory> GetTerritories(){return territoryState.GetTerritories();}
    public final LinkedMapProperty<UUID, Climate> GetClimates(){return climateState.GetClimates();}

}
