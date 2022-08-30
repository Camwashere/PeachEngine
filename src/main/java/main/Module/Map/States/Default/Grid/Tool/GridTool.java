package main.Module.Map.States.Default.Grid.Tool;

import javafx.scene.paint.Paint;
import main.Module.Map.States.Default.Grid.Grid;
import main.Module.Map.States.Default.Layout.ToolMenu.TexturePicker;

public class GridTool extends GridToolBase
{
    private final TexturePicker picker = new TexturePicker();
    public GridTool(final Grid parentGrid, final GridToolType toolType)
    {
        super(parentGrid, toolType);
        SetNode(picker);
    }



    public final TexturePicker GetPicker(){return picker;}
    public final boolean IsColorIgnored(){return picker.IsIgnore();}
    public final Paint GetTexture(){return picker.GetTexture();}
    public final void SetTexture(final Paint p){picker.SetTexture(p);}

}
