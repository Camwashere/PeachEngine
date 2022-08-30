package main.Module.Map.States.Default.Grid;

import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Module.Map.States.Default.Grid.GridView;
import main.Module.Map.States.Default.Grid.Tool.GridToolBase;
import main.Module.Map.States.Default.Grid.Tool.GridToolType;
import main.Module.Map.States.Default.Layout.ToolMenu.GridToolBar;
import main.Tools.InitHelp;
import main.Tools.LinkedMapProperty;

public class GridLeftBar extends VBox
{
    private final GridView parent;
    private final GridToolBar toolBar;
    public GridLeftBar(final GridView parentView)
    {
        parent = parentView;
        LabelInit();
        toolBar = new GridToolBar(this);
        getChildren().add(toolBar);
    }
    private void LabelInit()
    {
        Label label = new Label("Tools");
        InitHelp.LabelInit(label);
        VBox.setVgrow(label, Priority.NEVER);
        getChildren().add(label);
    }


    public final void AddTool(final GridToolBase tool)
    {
        toolBar.AddTool(tool);
    }

    public final void SetTool(final GridToolType type)
    {
        toolBar.SetTool(type);
    }
    public final GridToolBase GetActiveTool(){return toolBar.GetActiveTool();}
    public final LinkedMapProperty<GridToolType, GridToolBase> GetTools(){return toolBar.GetTools();}
    public final GridToolBar GetToolBar(){return toolBar;}

    public final GridView GetParent(){return parent;}
}
