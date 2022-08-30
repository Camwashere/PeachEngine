package main.Module.Map.States.Default.Grid;


import javafx.scene.layout.VBox;
import main.Debug.Debug;
import main.Maths.DistanceType;
import main.Tools.Field.ConfirmationDistanceField;
import main.Tools.Field.DistanceConverterField;


public class ScaleSection extends VBox
{
    private final GridTopBar parent;
    private final ConfirmationDistanceField gridScale = new ConfirmationDistanceField("Grid Scale");
    private final DistanceConverterField tileScale = new DistanceConverterField("Tile Scale");
    public ScaleSection(final GridTopBar parentBar)
    {
        parent = parentBar;
        GridScaleInit();
        getChildren().add(gridScale);
        getChildren().add(tileScale);
        gridScale.Confirm();

    }
    private void GridScaleInit()
    {
        gridScale.GetAmountField().SetMin(0);
        gridScale.SetOnConfirm(evt->
        {
            Update();
        });
        gridScale.SetAmount(100);
        gridScale.SetType(DistanceType.MILES);
        tileScale.GetValueField().GetAmountField().setDisable(true);
        tileScale.GetValueField().GetTypeField().setDisable(true);

    }
    public void Update()
    {
        parent.GetParent().GetGrid().GetGridScale().SetType(gridScale.GetType());
        parent.GetParent().GetGrid().GetGridScale().SetLength(gridScale.GetAmount());
        tileScale.GetValueField().SetType(parent.GetParent().GetGrid().GetTileScale().GetType());
        tileScale.GetValueField().SetAmount(parent.GetParent().GetGrid().GetTileScale().GetLength());
    }

}
