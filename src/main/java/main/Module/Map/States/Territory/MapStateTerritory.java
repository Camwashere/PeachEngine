package main.Module.Map.States.Territory;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import main.Debug.Debug;
import main.Module.Map.Game.Territory;
import main.Module.Map.MapModule;
import main.Module.Map.States.MapStateBase;
import main.Module.Map.States.MapStateType;
import main.Tools.LinkedMapProperty;

import java.util.UUID;

public class MapStateTerritory extends MapStateBase
{
    private final TerritoryEditor center;
    private final TerritoryLeftBar left;
    private final LinkedMapProperty<UUID, Territory> territories = new LinkedMapProperty<>();
    private final SimpleObjectProperty<UUID> current = new SimpleObjectProperty<>();
    public MapStateTerritory(final MapModule parentMod)
    {
        super(parentMod, MapStateType.TERRITORY);
        left = new TerritoryLeftBar(this);
        center = new TerritoryEditor(this);
        setLeft(left);
        setCenter(center);
        ListenerInit();
    }
    private void ListenerInit()
    {
        territories.addListener(new MapChangeListener<UUID, Territory>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends Territory> change)
            {
                if (change.wasRemoved())
                {
                    left.GetTree().RemoveTerritory(change.getKey());
                    parent.GetDefaultState().GetRight().GetTerritoryList().RemoveTerritory(change.getKey());
                }
                if (change.wasAdded())
                {
                    left.GetTree().AddTerritory(change.getKey());
                    parent.GetDefaultState().GetRight().GetTerritoryList().AddTerritory(change.getKey());
                    current.set(change.getKey());
                }
            }
        });
        current.addListener(new ChangeListener<UUID>()
        {
            @Override
            public void changed(ObservableValue<? extends UUID> observableValue, UUID uuid, UUID t1)
            {
                center.LoadCurrent();
                left.GetTree().getSelectionModel().select(t1);
            }
        });
    }

    public final void AddTerritory()
    {
        Territory t = new Territory("New Territory");
        territories.put(t.GetID(), t);
    }

    public final void SetCurrent(final UUID id){current.set(id);}

    public final Territory GetTerritory(final UUID id){return territories.get(id);}
    public final Territory GetCurrent(){return territories.get(current.get());}
    public final LinkedMapProperty<UUID, Territory> GetTerritories(){return territories;}
    public final TerritoryLeftBar GetLeft(){return left;}

}
