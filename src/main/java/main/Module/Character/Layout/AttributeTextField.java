package main.Module.Character.Layout;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;
import main.Tools.Field.NumericTextField;

public class AttributeTextField extends HBox
{
    private final Label label;
    private final NumericTextField field;
    private final CheckBox box;
    public AttributeTextField(final String name)
    {
        label = new Label(name);
        field = new NumericTextField();
        box = new CheckBox("Disable");

        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(label, field, box);
        HBox.setHgrow(field, Priority.ALWAYS);

        box.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                field.setDisable(t1);
            }
        });


        box.setSelected(true);
    }
    public final void CanDisable(boolean bool)
    {
        if (bool)
        {
            getChildren().add(box);
        }
        else
        {
            getChildren().remove(box);
        }
    }

    public final void SetValue(final Number n)
    {
        field.SetValue(n);
    }
    public final Number GetValue(){return field.GetValue();}

    public final void Check(boolean bool){box.setSelected(bool);}
    public final NumericTextField GetTextField(){return field;}
    public final CheckBox GetCheckBox(){return box;}

}
