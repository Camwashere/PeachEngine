package main.Tools.Field;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import main.Tools.InitHelp;

public class ConfirmationTextField extends LabeledTextField
{
    private final Button button = new Button("Confirm");
    private EventHandler<ActionEvent> onConfirm;
    public ConfirmationTextField(final String fieldName)
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
        field.setDisable(true);
        button.setText("Edit");
        if (onConfirm != null)
        {
            onConfirm.handle(null);
        }
    }
    public void Edit()
    {
        field.setDisable(false);
        field.requestFocus();
        field.selectAll();
        button.setText("Confirm");
    }

    public final void SetOnConfirm(EventHandler<ActionEvent> evt){onConfirm = evt;}
    public final Button GetButton(){return button;}

}
