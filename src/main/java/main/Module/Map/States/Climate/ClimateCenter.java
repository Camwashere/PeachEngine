package main.Module.Map.States.Climate;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Tools.Field.ConfirmationColorPicker;
import main.Tools.Field.ConfirmationTextArea;
import main.Tools.Field.ConfirmationTextField;

public class ClimateCenter extends VBox
{
    private final MapStateClimate parent;
    private final ConfirmationTextField name = new ConfirmationTextField("Name");
    private final ConfirmationTextArea description = new ConfirmationTextArea("Description");
    private final ConfirmationColorPicker displayPaint = new ConfirmationColorPicker("Display Color");

    public ClimateCenter(final MapStateClimate parentState)
    {
        parent = parentState;
        NameInit();
        DescriptionInit();
        PickerInit();
    }
    private void NameInit()
    {
        name.Confirm();
        name.SetOnConfirm(evt->
        {
            parent.GetCurrent().SetName(name.GetText());
            parent.GetLeft().GetTree().refresh();
        });
        HBox.setHgrow(name.GetLabel(), Priority.NEVER);
        getChildren().add(name);
    }
    private void DescriptionInit()
    {
        description.Confirm();
        description.SetOnConfirm(evt->
        {
            parent.GetCurrent().SetDescription(description.GetText());
            parent.GetLeft().GetTree().refresh();
        });
        getChildren().add(description);
    }
    private void PickerInit()
    {
        displayPaint.Confirm();
        displayPaint.SetOnConfirm(evt->
        {
            parent.GetCurrent().SetDisplayPaint(displayPaint.GetValue());
            parent.GetLeft().GetTree().refresh();
        });
        getChildren().add(displayPaint);
    }

    public final void LoadCurrent()
    {
        name.SetText(parent.GetCurrent().GetName());
        description.SetText(parent.GetCurrent().GetDescription());
        displayPaint.SetValue(parent.GetCurrent().GetDisplayPaint());
        ConfirmAll();
    }
    private void ConfirmAll()
    {
        name.Confirm();
        description.Confirm();
        displayPaint.Confirm();
    }

    public final MapStateClimate GetParent(){return parent;}
}
