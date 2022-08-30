package main.Module.Map.States.Default.Layout;

import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Module.Map.Game.Territory;
import main.Module.Map.States.Default.MapStateDefault;
import main.Tools.InitHelp;

import java.util.UUID;

public class MapDefaultRight extends VBox
{
    private final MapStateDefault parent;
    private final TerritoryListView territory;

    public MapDefaultRight(final MapStateDefault parentView)
    {
        parent = parentView;
        territory = new TerritoryListView(this);
        Label territoryLabel = new Label("Territories");
        InitHelp.LabelInit(territoryLabel);
        VBox.setVgrow(territoryLabel, Priority.NEVER);
        getChildren().add(territoryLabel);
        getChildren().add(territory);
    }

    public final void Refresh()
    {
        territory.refresh();
    }
    public final Territory GetTerritory(final UUID id){return parent.GetParent().GetTerritories().get(id);}

    public final MapStateDefault GetParent(){return parent;}
    public final TerritoryListView GetTerritoryList(){return territory;}


}
