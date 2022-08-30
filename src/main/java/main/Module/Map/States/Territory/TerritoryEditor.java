package main.Module.Map.States.Territory;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Tools.Field.*;

public class TerritoryEditor extends VBox
{
    private final MapStateTerritory parent;
    private final ConfirmationTextField name = new ConfirmationTextField("Name");
    private final ConfirmationTextArea description = new ConfirmationTextArea("Description");
    private final ConfirmationColorPicker picker = new ConfirmationColorPicker("Color");

    public TerritoryEditor(final MapStateTerritory parentState)
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
            parent.GetParent().GetDefaultState().GetRight().Refresh();
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
            parent.GetParent().GetDefaultState().GetRight().Refresh();
        });
        getChildren().add(description);
    }
    private void PickerInit()
    {
        picker.Confirm();
        picker.SetOnConfirm(evt->
        {
            parent.GetCurrent().SetColor(picker.GetValue());
            parent.GetLeft().GetTree().refresh();
            parent.GetParent().GetDefaultState().GetRight().Refresh();
        });
        getChildren().add(picker);
    }

    public final void LoadCurrent()
    {
        name.SetText(parent.GetCurrent().GetName());
        description.SetText(parent.GetCurrent().GetDescription());
        picker.SetValue(parent.GetCurrent().GetColor());
        ConfirmAll();
    }
    private void ConfirmAll()
    {
        name.Confirm();
        description.Confirm();
        picker.Confirm();
    }

}
