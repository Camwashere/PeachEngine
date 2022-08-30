package main.Module.Map.States.Default.Layout;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import main.Tools.InitHelp;

import java.util.UUID;

public class TerritoryListView extends ListView<UUID>
{
    private final MapDefaultRight parent;
    public TerritoryListView(final MapDefaultRight parentBar)
    {
        parent = parentBar;
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
                    HBox box = new HBox();
                    Rectangle rec = new Rectangle(25, 25, parent.GetTerritory(item).GetColor());
                    CheckBox check = new CheckBox("In Use");
                    check.setAlignment(Pos.CENTER);
                    check.setTextAlignment(TextAlignment.CENTER);
                    InitHelp.NodeInit(check);
                    check.setSelected(parent.GetParent().GetCurrent().GetGrid().UsesTerritory(parent.GetTerritory(item)));
                    check.setDisable(true);
                    box.getChildren().addAll(check, rec);
                    box.setSpacing(3);
                    setGraphic(box);
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

    public final void RemoveTerritory(final UUID id){getItems().remove(id);}
    public final void AddTerritory(final UUID id){getItems().add(id);}

    public static TreeItem<UUID> CREATE(final UUID i){return new TreeItem<UUID>(i);}
    public final MapDefaultRight GetParent(){return parent;}
}
