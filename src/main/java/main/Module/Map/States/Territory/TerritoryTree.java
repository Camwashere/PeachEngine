package main.Module.Map.States.Territory;

import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import main.Module.Map.Game.Territory;
import main.Tools.InitHelp;

import java.util.UUID;

public class TerritoryTree extends ListView<UUID>
{
    private final MapStateTerritory parent;
    public TerritoryTree(final MapStateTerritory parentState)
    {
        parent = parentState;
        SelfInit();
        CellFactoryInit();
    }
    private void CellFactoryInit()
    {
        setCellFactory(new Callback<ListView<UUID>, ListCell<UUID>>()
        {
            @Override
            public ListCell<UUID> call(ListView<UUID> uuidListView)
            {
                return CreateCell();
            }
        });
    }
    private ListCell<UUID> CreateCell()
    {
        ListCell<UUID> cell = new ListCell<>()
        {
            @Override
            protected void updateItem(UUID item, boolean empty)
            {
                super.updateItem(item, empty);
                if (item == null || empty)
                {
                    setText(null);
                    setGraphic(null);
                }
                else
                {
                    setText(parent.GetTerritory(item).GetName());
                    setGraphic(new Rectangle(25, 25, parent.GetTerritory(item).GetColor()));
                }
            }
        };

        cell.setOnMouseClicked(evt->
        {
            if (evt.getClickCount() > 1)
            {
                parent.SetCurrent(cell.getItem());

            }
        });

        return cell;
    }

    private void SelfInit()
    {
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setFocusTraversable(false);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setEditable(false);
        InitHelp.GrowInit(this);
    }

    public final void RemoveTerritory(final UUID id){getItems().remove(id);}
    public final void AddTerritory(final UUID id){getItems().add(id);}

    public static TreeItem<UUID> CREATE(final UUID i){return new TreeItem<UUID>(i);}
}
