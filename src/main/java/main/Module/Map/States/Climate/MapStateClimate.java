package main.Module.Map.States.Climate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import main.Module.Map.Game.Climate;
import main.Module.Map.MapModule;
import main.Module.Map.States.MapStateBase;
import main.Module.Map.States.MapStateType;
import main.Tools.LinkedMapProperty;

import java.util.UUID;

public class MapStateClimate extends MapStateBase
{
    private final LinkedMapProperty<UUID, Climate> climates = new LinkedMapProperty<UUID, Climate>();
    private final SimpleObjectProperty<UUID> current = new SimpleObjectProperty<>();
    private final ClimateLeftBar left;
    private final ClimateCenter center;
    public MapStateClimate(final MapModule parentMod)
    {
        super(parentMod, MapStateType.CLIMATE);
        left = new ClimateLeftBar(this);
        center = new ClimateCenter(this);
        setLeft(left);
        setCenter(center);
        ListenerInit();
    }
    private void ListenerInit()
    {
        climates.addListener(new MapChangeListener<UUID, Climate>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends Climate> change)
            {
                if (change.wasRemoved())
                {
                    left.GetTree().RemoveClimate(change.getKey());
                }
                if (change.wasAdded())
                {
                    left.GetTree().AddClimate(change.getKey());
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

    public final void AddClimate()
    {
        Climate c = new Climate("New Climate");
        climates.put(c.GetID(), c);
    }
    public final Climate GetClimate(final UUID id){return climates.get(id);}
    public final Climate GetCurrent(){return climates.get(current.get());}
    public final LinkedMapProperty<UUID, Climate> GetClimates(){return climates;}

    public final ClimateLeftBar GetLeft(){return left;}
}
