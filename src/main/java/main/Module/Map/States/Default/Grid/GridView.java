package main.Module.Map.States.Default.Grid;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import main.Debug.Debug;
import main.Module.Map.States.Default.Grid.Tool.GridToolBase;
import main.Module.Map.States.Default.Grid.Tool.GridToolType;
import main.Module.Map.States.Default.Grid.Tool.IconTool;
import main.Module.Map.States.Default.Layout.GridTree;
import main.Tools.LinkedMapProperty;

import java.util.UUID;

public class GridView extends BorderPane
{
    private final GridTree parent;
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final Grid grid;
    private final SimpleObjectProperty<GridView> gridParent = new SimpleObjectProperty<>();
    private final GridTopBar top;
    private final GridLeftBar left;
    private final ScrollPane center = new ScrollPane();


    public GridView(final GridTree treeParent, final String viewName)
    {
        parent = treeParent;
        name.set(viewName);
        left = new GridLeftBar(this);
        grid = new Grid(this);
        top = new GridTopBar(this);
        Init();
    }
    public GridView(final GridTree treeParent, final GridView parentGrid, final String viewName)
    {
        parent = treeParent;
        gridParent.set(parentGrid);
        name.set(viewName);
        grid = new Grid(this);
        left = new GridLeftBar(this);
        top = new GridTopBar(this);
        Init();
    }
    private void Init()
    {
        center.setContent(grid);
        setTop(top);
        setCenter(center);
        setLeft(left);
        ListenerInit();
    }
    private void ListenerInit()
    {
        name.addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                Debug.println("Changed from: " + s + " to: " + t1);
            }
        });
    }

    public final void SetTool(final GridToolType type){left.SetTool(type);}
    public final void SetName(final String str){name.set(str);}
    public final void AddTool(final GridToolBase tool)
    {
        left.AddTool(tool);
    }
    public final GridToolBase GetActiveTool(){return left.GetActiveTool();}
    public final LinkedMapProperty<GridToolType, GridToolBase> GetTools(){return left.GetTools();}
    public final IconTool GetIconTool(){return left.GetToolBar().GetIconTool();}

    public final String GetName(){return name.get();}
    public final SimpleStringProperty GetNameProp(){return name;}
    public final GridView GetGridParent(){return gridParent.get();}
    public final SimpleObjectProperty<GridView> GetGridParentProp(){return gridParent;}
    public final Grid GetGrid(){return grid;}
    public final UUID GetID(){return grid.GetID();}
    public final boolean IsGlobal(){return gridParent.get() == null;}
    public final GridTree GetParent(){return parent;}
}
