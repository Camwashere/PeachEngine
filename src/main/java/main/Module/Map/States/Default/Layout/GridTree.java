package main.Module.Map.States.Default.Layout;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import main.Debug.Debug;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Module.Map.States.Default.Grid.GridView;
import main.Module.Story.Layout.StoryLeftBar.ScenarioItem;
import main.Tools.LinkedMapProperty;

import java.util.UUID;

public class GridTree extends TreeView<UUID>
{
    private final MapDefaultLeft parent;
    private final LinkedMapProperty<UUID, GridView> grids = new LinkedMapProperty<>();
    private final SimpleObjectProperty<UUID> current = new SimpleObjectProperty<>();
    private final UUID global;
    public GridTree(final MapDefaultLeft parentBar)
    {
        parent = parentBar;
        GridView grid = new GridView(this, "Global");
        global = grid.GetID();
        grids.put(global, grid);
        SetCurrent(global);
        SelfInit();
        EventInit();
        CellFactoryInit();
    }
    private void SelfInit()
    {
        setRoot(CREATE(global));
        setShowRoot(true);
        setFocusTraversable(false);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setEditable(true);
        VBox.setVgrow(this, Priority.ALWAYS);
    }


    private void EventInit()
    {
        setOnMouseClicked(evt->
        {
            getSelectionModel().clearSelection();
        });

    }


    private void CellFactoryInit()
    {
        setCellFactory(new Callback<TreeView<UUID>, TreeCell<UUID>>()
        {
            @Override
            public TreeCell<UUID> call(TreeView<UUID> uuidTreeView)
            {
                return CreateCell();
            }
        });
    }
    private TextFieldTreeCell<UUID> CreateCell()
    {
        TextFieldTreeCell<UUID> cell = new TextFieldTreeCell<UUID>()
        {
            @Override
            public void updateItem(UUID item, boolean empty)
            {
                super.updateItem(item, empty);
                if (item == null || empty)
                {
                    setText(null);
                    setGraphic(null);
                }
                else
                {
                    setText(parent.GetGridView(item).GetName());
                }
            }
            @Override
            public void commitEdit(UUID item)
            {
                if (item != null)
                {
                    super.commitEdit(item);
                    grids.get(item).SetName(getText());
                    setEditable(false);
                }
            }
        };
        cell.setConverter(new StringConverter<UUID>()
        {
            @Override
            public String toString(UUID uuid)
            {
                return grids.get(uuid).GetName();
            }

            @Override
            public UUID fromString(String s)
            {
                grids.get(cell.getTreeItem().getValue()).SetName(s);
                return cell.getTreeItem().getValue();
            }
        });
        cell.setEditable(false);
        cell.setOnMouseClicked(evt->
        {
            if (cell.getTreeItem() == null)
            {
                return;
            }
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                if (evt.getClickCount() > 1)
                {
                    cell.getTreeItem().setExpanded(!cell.getTreeItem().isExpanded());
                    SetCurrent(cell.getTreeItem().getValue());
                }
            }
            evt.consume();
        });
        ContextMenu menu = new ContextMenu();
        MenuItem load = new MenuItem("Load");
        MenuItem rename = new MenuItem("Rename");
        MenuItem add = new MenuItem("New +");
        MenuItem delete = new MenuItem("Delete");
        MenuItem collapse = new MenuItem("Collapse All");
        MenuItem expand = new MenuItem("Expand All");
        load.setOnAction(evt->
        {
            parent.SetCurrent(GetSelected());
        });
        rename.setOnAction(evt->
        {
            cell.setEditable(true);
            cell.startEdit();
        });
        add.setOnAction(evt->
        {
            AddGrid();
        });
        delete.setOnAction(evt->
        {
            RemoveCurrent();
        });
        collapse.setOnAction(evt->
        {
            Debug.println("COLLAPSE ALL");
        });
        expand.setOnAction(evt->
        {
            Debug.println("EXPAND ALL");
        });
        menu.getItems().addAll(load, rename, add, delete, collapse, expand);
        menu.setOnShowing(evt->
        {
            menu.getItems().clear();
            if (getSelectionModel().getSelectedItem() != null)
            {
                if (! GetSelected().GetID().equals(GetCurrent().GetID()))
                {
                    menu.getItems().add(load);
                }
                menu.getItems().add(rename);
                menu.getItems().add(add);
                if (! GetSelected().IsGlobal())
                {
                    menu.getItems().add(delete);
                }
            }
            menu.getItems().add(collapse);
            menu.getItems().add(expand);
        });
        cell.setContextMenu(menu);

        return cell;
    }

    private TreeItem<UUID> CREATE(final UUID id){return new TreeItem<UUID>(id);}

    public final void AddRootGrid()
    {
        AddRootGrid("New Grid");
    }
    public final void AddRootGrid(final String name)
    {
        int counter = 0;
        String last = "";
        GridView grid = new GridView(this, GetGlobal(), name);
        NameCheck(counter, last, grid);
        grids.put(grid.GetID(), grid);
        TreeItem<UUID> item = CREATE(grid.GetID());
        getRoot().getChildren().add(item);
        getSelectionModel().select(item);
        SetCurrent(grid.GetID());
    }
    public final void TestFill()
    {
        for (int i=0; i<500; i++)
        {
            AddRootGrid();
        }
    }
    public final void AddGrid(){AddGrid("New Grid");}
    public final void AddGrid(final String name)
    {
        int counter = 0;
        String last = "";

        if (getSelectionModel().getSelectedItem() == null)
        {
            GridView grid = new GridView(this, GetGlobal(), name);
            NameCheck(counter, last, grid);
            grids.put(grid.GetID(), grid);
            TreeItem<UUID> item = CREATE(grid.GetID());
            getRoot().getChildren().add(item);
            getSelectionModel().select(item);
            SetCurrent(grid.GetID());
        }
        else
        {
            GridView grid = new GridView(this, GetSelected(), name);
            NameCheck(counter, last, grid);
            grids.put(grid.GetID(), grid);
            TreeItem<UUID> item = CREATE(grid.GetID());
            getSelectionModel().getSelectedItem().getChildren().add(item);
            getSelectionModel().select(item);
            SetCurrent(grid.GetID());

        }
    }
    public final void RemoveCurrent()
    {
        TreeItem<UUID> item = getSelectionModel().getSelectedItem();
        if (item != null)
        {
            GridView grid = grids.get(item.getValue());
            if (! grid.IsGlobal())
            {
                grids.remove(grid.GetID());
                item.getParent().getChildren().remove(item);
            }
        }
    }


    private void NameCheck(int counter, String last, final GridView gridView)
    {
        boolean bet = true;
        for (final GridView grid : grids.values())
        {
            if ((gridView.GetName() + last).equals(grid.GetName()))
            {
                counter++;
                last = String.valueOf(counter);
                bet = false;
            }
        }
        if (bet)
        {
            gridView.SetName(gridView.GetName() + last);
        }
        else
        {
            NameCheck(counter, last, gridView);
        }
    }


    public final void SetCurrent(final UUID id){current.set(id);}
    public final void SetCurrent(final GridView gridView){current.set(gridView.GetID());}
    public final GridView GetSelected()
    {
        if (getSelectionModel().getSelectedItem() == null)
        {
            return GetGlobal();
        }
        return grids.get(getSelectionModel().getSelectedItem().getValue());
    }
    public final GridView GetCurrent(){return grids.get(current.get());}
    public final GridView GetGlobal(){return grids.get(global);}
    public final GridView GetGridView(final UUID id){return grids.get(id);}
    public final LinkedMapProperty<UUID, GridView> GetGrids(){return grids;}
    public final SimpleObjectProperty<UUID> GetCurrentProp(){return current;}

    public final MapDefaultLeft GetParent(){return parent;}
}
