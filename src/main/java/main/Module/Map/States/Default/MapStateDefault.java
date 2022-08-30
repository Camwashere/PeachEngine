package main.Module.Map.States.Default;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import main.Module.Map.MapModule;
import main.Module.Map.States.Default.Grid.Grid;
import main.Module.Map.States.Default.Grid.GridView;
import main.Module.Map.States.Default.Layout.MapDefaultCenter;
import main.Module.Map.States.Default.Layout.MapDefaultLeft;
import main.Module.Map.States.Default.Layout.MapDefaultRight;
import main.Module.Map.States.MapStateBase;
import main.Module.Map.States.MapStateType;
import main.Tools.LinkedMapProperty;

import java.util.UUID;

public class MapStateDefault extends MapStateBase
{
    private final MapDefaultCenter center;
    private final MapDefaultRight right;
    private final MapDefaultLeft left;

    public MapStateDefault(final MapModule parentMod)
    {
        super(parentMod, MapStateType.DEFAULT);
        left = new MapDefaultLeft(this);
        ListenerInit();
        center = new MapDefaultCenter(this);
        right = new MapDefaultRight(this);
        setCenter(center);
        setRight(right);
        setLeft(left);

    }
    private void ListenerInit()
    {
        left.GetGrids().addListener(new MapChangeListener<UUID, GridView>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends GridView> change)
            {
                if (change.wasRemoved())
                {
                    center.RemoveTab(change.getValueRemoved());
                }

            }
        });
        left.GetCurrentProp().addListener(new ChangeListener<UUID>()
        {
            @Override
            public void changed(ObservableValue<? extends UUID> observableValue, UUID uuid, UUID t1)
            {
                center.Load(left.GetGridView(t1));
            }
        });
    }

    public final void AddGrid(final String name)
    {
        left.AddGrid(name);
    }


    public final void SetCurrent(final UUID id){left.SetCurrent(id);}
    public final void SetCurrent(final GridView gridView){left.SetCurrent(gridView);}
    public final GridView GetCurrent(){return left.GetCurrent();}
    public final GridView GetGlobal(){return left.GetGlobal();}
    public final GridView GetGridView(final UUID id){return left.GetGridView(id);}
    public final MapDefaultRight GetRight(){return right;}
}
