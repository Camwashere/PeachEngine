package main.Module.Map.States.Default.Grid;

import javafx.scene.layout.HBox;
import main.Tools.Field.ConfirmationNumericField;

public class GridTopBar extends HBox
{
    private final GridView parent;
    private final ConfirmationNumericField gridSize = new ConfirmationNumericField("Grid Size");
    private final ConfirmationNumericField tileSize = new ConfirmationNumericField("Tile Size");
    private final ScaleSection scale;

    public GridTopBar(final GridView parentView)
    {
        parent = parentView;
        Init();
        scale = new ScaleSection(this);
        getChildren().add(scale);
    }
    private void Init()
    {
        GridSizeInit();
        TileSizeInit();
    }
    private void GridSizeInit()
    {
        gridSize.SetPrecision(0);
        gridSize.SetMin(1);
        gridSize.SetOnConfirm(evt->
        {
            parent.GetGrid().ResizeGrid(gridSize.GetValue().intValue());
            if (scale != null)
            {
                scale.Update();
            }
        });
        gridSize.SetValue(30);
        gridSize.Confirm();
        getChildren().add(gridSize);
    }

    private void TileSizeInit()
    {
        tileSize.SetPrecision(2);
        tileSize.SetMin(1);
        tileSize.SetOnConfirm(evt->
        {
            parent.GetGrid().SetTileSize(tileSize.GetValue().doubleValue());
        });
        tileSize.SetValue(50);
        tileSize.Confirm();
        getChildren().add(tileSize);

    }



    public final GridView GetParent(){return parent;}
}
