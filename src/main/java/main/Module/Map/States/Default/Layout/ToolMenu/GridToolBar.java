package main.Module.Map.States.Default.Layout.ToolMenu;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;
import main.Module.Map.States.Default.Grid.Tool.GridToolBase;
import main.Module.Map.States.Default.Grid.Tool.GridToolType;
import main.Module.Map.States.Default.Grid.Tool.IconTool;
import main.Module.Map.States.Default.Grid.GridLeftBar;
import main.Tools.LinkedMapProperty;

public class GridToolBar extends VBox
{
    private final GridLeftBar parent;
    private final LinkedMapProperty<GridToolType, GridToolBase> tools = new LinkedMapProperty<>();
    private final SimpleObjectProperty<GridToolType> active = new SimpleObjectProperty<>();

    public GridToolBar(final GridLeftBar parentBar)
    {
        parent = parentBar;
    }
    public final void AddTool(final GridToolBase tool)
    {
        tool.GetSelectedProp().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1)
                {
                    for (final GridToolBase t : tools.values())
                    {
                        if (t != tool)
                        {
                            t.Deselect();
                        }
                    }
                    active.set(tool.GetType());
                }
            }
        });
        if (tools.isEmpty())
        {
            tool.Select();
        }
        tools.put(tool.GetType(), tool);
        getChildren().add(tool);
    }

    public final void SetTool(final GridToolType type)
    {
        active.set(type);
    }
    public final GridToolBase GetActiveTool(){return tools.get(active.get());}
    public final LinkedMapProperty<GridToolType, GridToolBase> GetTools(){return tools;}
    public final IconTool GetIconTool(){return (IconTool)tools.get(GridToolType.ICON);}
    public final GridLeftBar GetParent(){return parent;}
}
