package main.Tools.Field;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import main.Tools.InitHelp;

public class ConfirmationTextArea extends LabeledTextArea
{
    private final Button button = new Button("Confirm");
    private EventHandler<ActionEvent> onConfirm;
    public ConfirmationTextArea(final String nameStr)
    {
        super(nameStr);
        ButtonInit();
        getChildren().add(button);
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

    public void Confirm()
    {
        area.setDisable(true);
        button.setText("Edit");
        if (onConfirm != null)
        {
            onConfirm.handle(null);
        }
    }
    public void Edit()
    {
        area.setDisable(false);
        area.requestFocus();
        area.selectAll();
        button.setText("Confirm");
    }

    public final void SetOnConfirm(EventHandler<ActionEvent> evt){onConfirm = evt;}
    public final Button GetButton(){return button;}
}
