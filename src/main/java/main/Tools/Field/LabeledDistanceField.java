package main.Tools.Field;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.Tools.InitHelp;

public class LabeledDistanceField extends DistanceField
{
    private final Label name;
    public LabeledDistanceField(final String fieldName)
    {
        name = new Label(fieldName);
        InitHelp.LabelInit(name);
        HBox.setHgrow(name, Priority.NEVER);
        getChildren().add(0, name);
        amount.setPadding(new Insets(0, 0, 0, 10));
    }

    public final void SetName(final String str){name.setText(str);}
}
