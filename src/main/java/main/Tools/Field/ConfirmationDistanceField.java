package main.Tools.Field;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import main.Tools.InitHelp;

public class ConfirmationDistanceField extends LabeledDistanceField
{
    private final Button button = new Button("Confirm");
    private EventHandler<ActionEvent> onConfirm;

    public ConfirmationDistanceField(final String fieldName)
    {
        super(fieldName);
        ButtonInit();
        Init();
        getChildren().add(button);

    }

    private void Init()
    {
        setOnKeyPressed(evt->
        {
            if (evt.getCode() == KeyCode.ENTER)
            {
                if ("Confirm".equals(button.getText()))
                {
                    Confirm();
                }
            }
        });
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
        amount.setDisable(true);
        type.setDisable(true);
        button.setText("Edit");
        if (onConfirm != null)
        {
            onConfirm.handle(null);
        }
    }
    public void Edit()
    {
        amount.setDisable(false);
        type.setDisable(false);
        amount.requestFocus();
        amount.GetField().selectAll();
        button.setText("Confirm");
    }

    public final void SetOnConfirm(EventHandler<ActionEvent> evt){onConfirm = evt;}
    public final Button GetButton(){return button;}

}
