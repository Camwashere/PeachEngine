package main.Tools.Field;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import main.Tools.InitHelp;

public class ConfirmationColorPicker extends HBox
{
    private final Label name;
    private final ColorPicker picker = new ColorPicker();
    private final Button button = new Button("Confirm");
    private EventHandler<ActionEvent> onConfirm;

    public ConfirmationColorPicker(final String nameStr)
    {
        name = new Label(nameStr);
        InitHelp.LabelInit(name);
        InitHelp.NodeInit(picker);
        InitHelp.ButtonInit(button);
        HBox.setHgrow(name, Priority.SOMETIMES);
        ButtonInit();
        getChildren().addAll(name, picker, button);

    }
    private void ButtonInit()
    {
        InitHelp.ButtonInit(button);
        button.setOnAction(evt->
        {
            if ("Confirm".equals(button.getText()))
            {
                Confirm();
            }
            else
            {
                Edit();
            }
        });
    }


    public final void Confirm()
    {
        picker.setDisable(true);
        button.setText("Edit");
        if (onConfirm != null)
        {
            onConfirm.handle(null);
        }
    }
    public final void Edit()
    {
        picker.setDisable(false);
        picker.requestFocus();
        button.setText("Confirm");
    }

    public final void SetValue(final Color c){picker.setValue(c);}
    public final void SetOnConfirm(EventHandler<ActionEvent> evt){onConfirm = evt;}

    public final Button GetButton(){return button;}
    public final Color GetValue(){return picker.getValue();}
}
