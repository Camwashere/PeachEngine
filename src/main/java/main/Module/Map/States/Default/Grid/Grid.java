package main.Module.Map.States.Default.Grid;

import eu.hansolo.tilesfx.Tile;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import main.Debug.Debug;
import main.Maths.Distance;
import main.Maths.DistanceType;
import main.Maths.Vec.Vec2;
import main.Module.Map.Game.Territory;
import main.Module.Map.States.Default.Grid.Tool.*;
import main.Tools.LinkedMapProperty;
import main.Tools.StringHelp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Grid extends GridPane implements EventHandler<InputEvent>
{
    private final GridView parent;
    private final UUID id;
    private final LinkedMapProperty<Vec2, GridTile> tiles = new LinkedMapProperty<>();
    private final SimpleIntegerProperty gridSize = new SimpleIntegerProperty(0);
    private final SimpleDoubleProperty tileSize = new SimpleDoubleProperty(0);
    private final Distance gridScale = new Distance();
    private final Distance tileScale = new Distance();

    public Grid(final GridView parentView)
    {
        parent = parentView;
        id = UUID.randomUUID();
        EventInit();
        ToolInit();
        ListenerInit();
    }
    private void ListenerInit()
    {
        gridSize.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                tileScale.SetLength(gridScale.GetLength()/tiles.size());
            }
        });
        tileSize.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                ResizeTiles(t1.doubleValue());
            }
        });
        gridScale.GetLengthProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                tileScale.SetLength(t1.doubleValue()/tiles.size());
            }
        });
        gridScale.GetTypeProp().addListener(new ChangeListener<DistanceType>()
        {
            @Override
            public void changed(ObservableValue<? extends DistanceType> observableValue, DistanceType distanceType, DistanceType t1)
            {
                tileScale.SetType(t1);
            }
        });


    }
    private void EventInit()
    {
        setOnKeyPressed(this);
        setOnKeyReleased(this);
        setOnKeyTyped(this);

        setOnMouseDragged(this);
        setOnMouseClicked(this);
        setOnMouseMoved(this);
        setOnMousePressed(this);
        setOnMouseReleased(this);
        setOnMouseEntered(this);
        setOnMouseExited(this);
    }
    private void ToolInit()
    {
        SelectToolInit();
        DrawToolInit();
        FillToolInit();
        EraseToolInit();
        IconToolInit();
    }
    private void SelectToolInit()
    {
        GridTool select = new GridTool(this, GridToolType.SELECT);
        final ContextMenu menu = new ContextMenu();
        menu.setAutoHide(true);
        menu.setAutoFix(true);
        final Vec2 prev = new Vec2();
        select.AddMode(new GridToolMode("Default", evt->
        {
            GridTile tile = CheckTile(evt);
            if (tile != null)
            {
                if (evt.getEventType() == MouseEvent.MOUSE_RELEASED || evt.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {
                    if (evt.getButton() == MouseButton.PRIMARY)
                    {
                        if (!prev.equals(tile.GetPos()))
                        {
                            tile.ToggleSelected();
                            prev.MakeEqual(tile.GetPos());
                        }
                        if (evt.getEventType() == MouseEvent.MOUSE_RELEASED)
                        {
                            prev.MakeDefault();
                        }
                    }
                    else if (evt.getButton() == MouseButton.SECONDARY)
                    {
                        SelectModeContextMenu(evt, menu, tile);
                    }
                }
            }
        }));
        select.AddMode(new GridToolMode("Precise", evt->
        {
            GridTile tile = CheckTile(evt);
            if (tile != null)
            {
                if (evt.getEventType() == MouseEvent.MOUSE_RELEASED)
                {
                    if (evt.getButton() == MouseButton.PRIMARY)
                    {
                        tile.ToggleSelected();
                    }
                    else if (evt.getButton() == MouseButton.SECONDARY)
                    {
                        SelectModeContextMenu(evt, menu, tile);
                    }
                }
            }
        }));
        select.AddMode(new GridToolMode("All", evt->
        {
            if (evt.getEventType() == MouseEvent.MOUSE_RELEASED)
            {
                if (evt.getButton() == MouseButton.PRIMARY)
                {
                    SelectAllTiles();
                }
                else if (evt.getButton() == MouseButton.SECONDARY)
                {
                    DeselectAllTiles();
                }
            }

        }));
        select.GetPicker().SetIgnore(true);
        AddTool(select);
    }

    private void DrawToolInit()
    {
        GridTool draw = new GridTool(this, GridToolType.DRAW);
        draw.AddMode(new GridToolMode("Default", evt->
        {
            if (draw.IsColorIgnored())
            {
                return;
            }
            GridTile tile = CheckTile(evt);
            if (tile != null)
            {
                if (evt.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    if(evt.getButton() == MouseButton.PRIMARY)
                    {
                        tile.SetTexture(draw.GetTexture());
                    }
                    else if (evt.getButton() == MouseButton.SECONDARY)
                    {
                        if (draw.GetTexture().equals(tile.GetTexture()))
                        {
                            tile.SetTexture(Color.WHITE);
                        }
                    }
                }
                else if (evt.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {
                    if(evt.getButton() == MouseButton.PRIMARY)
                    {
                        tile.SetTexture(draw.GetTexture());
                    }
                    else if (evt.getButton() == MouseButton.SECONDARY)
                    {
                        if (draw.GetTexture().equals(tile.GetTexture()))
                        {
                            tile.SetTexture(Color.WHITE);
                        }
                    }
                }
            }
        }));
        draw.AddMode(new GridToolMode("Precise", evt->
        {
            if (draw.IsColorIgnored())
            {
                return;
            }
            GridTile tile = CheckTile(evt);
            if (tile != null)
            {
                if (evt.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    if(evt.getButton() == MouseButton.PRIMARY)
                    {
                        tile.SetTexture(draw.GetTexture());
                    }
                    else if (evt.getButton() == MouseButton.SECONDARY)
                    {
                        if (draw.GetTexture().equals(tile.GetTexture()))
                        {
                            tile.SetTexture(Color.WHITE);
                        }
                    }
                }
            }
        }));

        AddTool(draw);
    }
    private void EraseToolInit()
    {
        GridTool erase = new GridTool(this, GridToolType.ERASE);
        erase.AddMode(new GridToolMode("Default", evt->
        {
            GridTile tile = CheckTile(evt);
            if (tile != null)
            {
                if (evt.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    if (erase.IsColorIgnored())
                    {
                        tile.SetTexture(Color.WHITE);
                    }
                    else if (erase.GetTexture().equals(tile.GetTexture()))
                    {
                        tile.SetTexture(Color.WHITE);
                    }
                }
                else if (evt.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {
                    if (erase.IsColorIgnored())
                    {
                        tile.SetTexture(Color.WHITE);
                    }
                    else if (erase.GetTexture().equals(tile.GetTexture()))
                    {
                        tile.SetTexture(Color.WHITE);
                    }
                }
            }
        }));
        erase.AddMode(new GridToolMode("Precise", evt->
        {
            GridTile tile = CheckTile(evt);
            if (tile != null)
            {
                if (evt.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    if (erase.IsColorIgnored())
                    {
                        tile.SetTexture(Color.WHITE);
                    }
                    else if (erase.GetTexture().equals(tile.GetTexture()))
                    {
                        tile.SetTexture(Color.WHITE);
                    }
                }
            }
        }));
        erase.GetPicker().SetIgnore(true);
        AddTool(erase);
    }
    private void FillToolInit()
    {
        GridTool fill = new GridTool(this, GridToolType.FILL);
        fill.AddMode(new GridToolMode("Default", evt->
        {
            if (evt.getEventType() == MouseEvent.MOUSE_RELEASED)
            {
                GridTile tile = CheckTile(evt);
                if (tile != null)
                {
                    if (! fill.GetTexture().equals(tile.GetTexture()))
                    {
                        BoundaryFill(tile.GetPos(), tile.GetTexture(), fill.GetTexture());
                    }
                }
            }
        }));
        fill.AddMode(new GridToolMode("All", evt->
        {
            if (evt.getEventType() == MouseEvent.MOUSE_RELEASED)
            {
                FillAllTiles(fill.GetTexture());
            }
        }));
        fill.AddMode(new GridToolMode("Replace", evt->
        {
            if (evt.getEventType() == MouseEvent.MOUSE_RELEASED)
            {
                GridTile tile = CheckTile(evt);
                if (tile != null)
                {
                    for (final GridTile t : tiles.values())
                    {
                        if (tile.GetTexture().equals(t.GetTexture()))
                        {
                            t.SetTexture(fill.GetTexture());

                        }
                    }
                }
            }
        }));
        fill.AddMode(new GridToolMode("Clear", evt->
        {
            if (evt.getEventType() == MouseEvent.MOUSE_RELEASED)
            {
                ClearAllTiles();
            }
        }));
        fill.SetTexture(Color.RED);
        AddTool(fill);
    }
    private void IconToolInit()
    {
        IconTool icon = new IconTool(this);
        icon.AddMode(new GridToolMode("Add", evt->
        {
            GridTile tile = CheckTile(evt);
            if (tile != null)
            {
                if (evt.getEventType() == MouseEvent.MOUSE_CLICKED)
                {
                    if (evt.getButton() == MouseButton.PRIMARY)
                    {
                        tile.SetIcon(icon.GetValue());
                    }
                }
            }
        }));
        icon.AddMode(new GridToolMode("Remove", evt->
        {
            GridTile tile = CheckTile(evt);
            if (tile != null)
            {
                if (evt.getEventType() == MouseEvent.MOUSE_CLICKED)
                {
                    if (evt.getButton() == MouseButton.PRIMARY)
                    {
                        tile.SetIcon(null);
                    }
                }
            }
        }));
        AddTool(icon);
    }

    private void SelectModeContextMenu(MouseEvent event, final ContextMenu menu, final GridTile tile)
    {
        menu.getItems().clear();
        if (HasSelectedTiles())
        {
            Menu assignTex = new Menu("Assign Texture");
            for (final GridToolBase tool : parent.GetTools().values())
            {
                if (tool instanceof GridTool gridTool)
                {
                    MenuItem item = new MenuItem(StringHelp.EnumFormat(tool.GetType()));
                    item.setOnAction(evt->
                    {
                        for (final GridTile t : tiles.values())
                        {
                            if (t.IsSelected())
                            {
                                t.SetTexture(gridTool.GetTexture());
                                t.SetSelected(false);
                            }
                        }
                    });
                    assignTex.getItems().add(item);
                }
            }

            MenuItem assignTerritory = new MenuItem("Assign Territory");
            assignTerritory.setOnAction(evt->
            {
                UUID selected = parent.GetParent().GetParent().GetParent().GetRight().GetTerritoryList().getSelectionModel().getSelectedItem();
                if (selected == null)
                {
                    for (final Vec2 vec : GetSelectedTiles())
                    {
                        GetTile(vec).ClearTerritory();
                    }
                    DeselectAllTiles();
                }
                else
                {
                    Territory t = parent.GetParent().GetParent().GetParent().GetRight().GetTerritory(selected);
                    if (t != null)
                    {
                        for (final Vec2 vec : GetSelectedTiles())
                        {
                            GetTile(vec).SetTerritory(t);
                        }
                    }
                }
            });

            MenuItem assignIcon = new MenuItem("Assign Icon");
            assignIcon.setOnAction(evt->
            {
                for (final Vec2 vec : GetSelectedTiles())
                {
                    tiles.get(vec).SetIcon(parent.GetIconTool().GetValue());
                    tiles.get(vec).SetSelected(false);
                }
            });

            MenuItem unSelect = new MenuItem("Unselect");
            unSelect.setOnAction(evt->
            {
                for (final GridTile t : tiles.values())
                {
                    t.SetSelected(false);
                }
            });
            MenuItem clear = new MenuItem("Clear");
            clear.setOnAction(evt->
            {
                List<Vec2> selected = GetSelectedTiles();
                for (final Vec2 vec : selected)
                {
                    tiles.get(vec).Clear();
                }
            });
            menu.getItems().addAll(assignTex, assignTerritory, assignIcon, unSelect, clear);
        }
        else
        {
            Menu autoSelect = new Menu("Auto Select");
            MenuItem byTexture = new MenuItem("By Texture");
            MenuItem byTerritory = new MenuItem("By Territory");
            byTexture.setOnAction(evt->
            {
                for (final GridTile t : tiles.values())
                {
                    if (tile.GetTexture().equals(t.GetTexture()))
                    {
                        t.SetSelected(true);
                    }
                }
            });
            byTerritory.setOnAction(evt->
            {
                if (tile.HasTerritory())
                {
                    for (final GridTile t : tiles.values())
                    {
                        if (tile.GetTerritory().equals(t.GetTerritory()))
                        {
                            t.SetSelected(true);
                        }

                    }
                }
                else
                {
                    for (final GridTile t : tiles.values())
                    {
                        if (! t.HasTerritory())
                        {
                            t.SetSelected(true);
                        }
                    }
                }
            });
            autoSelect.getItems().addAll(byTexture, byTerritory);
            menu.getItems().add(autoSelect);
        }
        MenuItem selectAll = new MenuItem("Select All");
        selectAll.setOnAction(evt->
        {
            SelectAllTiles();
        });
        MenuItem unSelectAll = new MenuItem("Unselect All");
        unSelectAll.setOnAction(evt->
        {
            DeselectAllTiles();
        });
        menu.getItems().addAll(selectAll, unSelectAll);
        menu.show(this, event.getScreenX(), event.getScreenY());
    }


    private void ClearAllTiles()
    {
        FillAllTiles(Color.WHITE);
    }
    private void FillAllTiles(final Paint tex)
    {
        for (final GridTile t : tiles.values())
        {
            t.SetTexture(tex);
        }
    }
    private void SelectAllTiles()
    {
        SetSelectAllTiles(true);
    }
    private void DeselectAllTiles()
    {
        SetSelectAllTiles(false);
    }

    private void SetSelectAllTiles(boolean bool)
    {
        for (final GridTile t : tiles.values())
        {
            t.SetSelected(bool);
        }
    }

    private void BoundaryFill(final Vec2 key, final Paint fillTex, final Paint replaceTex)
    {
        if (tiles.containsKey(key))
        {
            if (fillTex.equals(tiles.get(key).GetTexture()))
            {
                tiles.get(key).SetTexture(replaceTex);
                BoundaryFill(new Vec2(key.x + 1, key.y), fillTex, replaceTex);
                BoundaryFill(new Vec2(key.x, key.y+1), fillTex, replaceTex);
                BoundaryFill(new Vec2(key.x-1, key.y), fillTex, replaceTex);
                BoundaryFill(new Vec2(key.x, key.y-1), fillTex, replaceTex);
            }
        }
    }
    private void AddTool(final GridToolBase tool)
    {
        parent.AddTool(tool);
    }
    private GridTile CheckTile(MouseEvent evt)
    {
        for (final GridTile tile : tiles.values())
        {
            if (tile.contains(tile.parentToLocal(evt.getX(), evt.getY())))
            {
                return tile;
            }
        }
        return null;
    }
    public void ResizeGrid(int sizeVal)
    {
        if (sizeVal > GetGridSize())
        {
            ExpandGrid(sizeVal - GetGridSize());
        }
        else if (sizeVal < GetGridSize())
        {
            ReduceGrid(GetGridSize() - sizeVal);
        }
    }
    public void RebuildGrid(int sizeVal)
    {
        tiles.clear();
        getChildren().clear();
        for (int i=0; i<sizeVal; i++)
        {
            for (int a=0; a<sizeVal; a++)
            {
                GridTile t = new GridTile(this, new Vec2(i, a), tileSize.get());
                getChildren().add(t);
                setColumnIndex(t, i);
                setRowIndex(t, a);
                tiles.put(new Vec2(i, a), t);
            }
        }
        gridSize.set(sizeVal);
    }
    public void ExpandGrid(int amount)
    {
        int newGridSize = GetGridSize() + amount;
        // Right Side
        for (int i=GetGridSize(); i<newGridSize; i++)
        {
            for (int a=0; a<GetGridSize(); a++)
            {
                GridTile t = new GridTile(this, new Vec2(i, a), GetTileSize());
                getChildren().add(t);
                setColumnIndex(t, i);
                setRowIndex(t, a);
                tiles.put(new Vec2(i, a), t);
            }
        }

        // Bottom Side
        for (int a=GetGridSize(); a<newGridSize; a++)
        {
            for (int i=0; i<newGridSize; i++)
            {
                GridTile t = new GridTile(this, new Vec2(i, a), GetTileSize());
                getChildren().add(t);
                setColumnIndex(t, i);
                setRowIndex(t, a);
                tiles.put(new Vec2(i, a), t);
            }
        }
        gridSize.set(newGridSize);
    }
    public void ReduceGrid(int amount)
    {
        int newGridSize = GetGridSize() - amount;
        // Right Side
        for (int c = GetGridSize()-1; c>=newGridSize; c--)
        {
            for (int r = GetGridSize()-1; r>=0; r--)
            {
                GridTile tile = GetTile(c, r);
                getChildren().remove(tile);
                tiles.remove(tile.GetPos());
            }
        }

        for (int r=GetGridSize()-1; r>=newGridSize; r--)
        {
            for (int c = newGridSize-1; c>=0; c--)
            {
                GridTile tile = GetTile(c, r);
                getChildren().remove(tile);
                tiles.remove(tile.GetPos());
            }
        }
        gridSize.set(newGridSize);
    }
    private void ResizeTilesToFit()
    {
        double tileWidth = getWidth() / gridSize.getValue();
        if (GetTileSize() != tileWidth)
        {
            ResizeTiles(tileWidth);
        }

    }
    private void ResizeTiles(double tSize)
    {
        for (int i=0; i<gridSize.getValue(); i++)
        {
            for (int a=0; a<gridSize.getValue(); a++)
            {
                GetTile(i, a).SetSize(tSize);
            }
        }
    }
    public final List<Vec2> GetSelectedTiles()
    {
        List<Vec2> list = new ArrayList<>();
        for (final GridTile tile : tiles.values())
        {
            if (tile.IsSelected())
            {
                list.add(tile.GetPos());
            }
        }
        return list;
    }
    public final boolean HasSelectedTiles()
    {
        for (final GridTile tile : tiles.values())
        {
            if (tile.IsSelected())
            {
                return true;
            }
        }
        return false;
    }
    public final boolean UsesTerritory(final Territory t)
    {
        for (final GridTile tile : tiles.values())
        {
            if (tile.HasTerritory(t))
            {
                return true;
            }
        }
        return false;
    }


    @Override
    public void handle(InputEvent inputEvent)
    {
        parent.GetActiveTool().handle(inputEvent);
    }


    public final void SetTileSize(double tSize) {tileSize.set(tSize);}
    public final void SetTool(final GridToolType type){parent.SetTool(type);}

    public final int GetGridSize(){return gridSize.get();}
    public final double GetTileSize() {return tileSize.get();}
    public final GridTile GetTile(final Vec2 pos){return tiles.get(pos);}
    public final GridTile GetTile(final double x, final double y){return tiles.get(new Vec2(x, y));}
    public final SimpleIntegerProperty GetGridSizeProp(){return gridSize;}
    public final GridView GetParent(){return parent;}
    public final UUID GetID(){return id;}
    public final String GetName(){return parent.GetName();}
    public final boolean IsGlobal(){return parent.IsGlobal();}
    public final Distance GetGridScale(){return gridScale;}
    public final Distance GetTileScale(){return tileScale;}
}
