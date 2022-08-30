package main.Module.Map.States.Climate;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeView;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import main.Tools.InitHelp;

import java.util.UUID;

public class ClimateTree extends ListView<UUID>
{
    private final ClimateLeftBar parent;

    public ClimateTree(final ClimateLeftBar left)
    {
        parent = left;
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
                    setText(parent.GetClimate(item).GetName());
                    setGraphic(new Rectangle(25, 25, parent.GetClimate(item).GetDisplayPaint()));
                }
            }
        };

        cell.setOnMouseClicked(evt->
        {
            if (evt.getClickCount() > 1)
            {
                //parent.SetCurrent(cell.getItem());

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

    public final void AddClimate(final UUID id){getItems().add(id);}
    public final void RemoveClimate(final UUID id){getItems().remove(id);}

    public final ClimateLeftBar GetParent(){return parent;}
}
