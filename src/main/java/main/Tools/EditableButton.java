package main.Tools;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class EditableButton extends Button
{
    private final TextField tf = new TextField();

    public EditableButton(String text)
    {
        setText(text);
        tf.setOnContextMenuRequested(Event::consume);
        tf.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (tf.isFocused())
                        {
                            tf.selectAll();
                        }
                        else
                        {
                            CancelEdit();
                        }
                    }
                });
            }
        });
        tf.setOnAction(evt->
        {
            CommitEdit();
        });


    }

    public final StringProperty GetTextProperty(){return textProperty();}

    public void StartEdit()
    {
        tf.setText(getText());
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setGraphic(tf);
        tf.requestFocus();
    }
    public void CommitEdit()
    {
        setText(tf.getText());
        setContentDisplay(ContentDisplay.TEXT_ONLY);
        tf.deselect();
    }
    public void CancelEdit()
    {
        setContentDisplay(ContentDisplay.TEXT_ONLY);
        tf.deselect();
    }
}

