package main.Tools.Field;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import main.Tools.InitHelp;

public class Vec2NumericTextField extends HBox
{
    private final Label label;
    private final LabeledNumericTextField x;
    private final LabeledNumericTextField y;

    public Vec2NumericTextField(final String name)
    {
        label = new Label(name);
        x = new LabeledNumericTextField("X");
        y = new LabeledNumericTextField("Y");
        InitHelp.LabelInit(label);
        HBox.setHgrow(label, Priority.NEVER);
        InitHelp.GrowInit(x);
        InitHelp.GrowInit(y);
        Region spacer = new Region();
        spacer.setMinWidth(5);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        getChildren().addAll(label, spacer, x, y);



    }

    public final void SetXValue(final Number val){x.SetValue(val);}
    public final void SetYValue(final Number val){y.SetValue(val);}
    public final LabeledNumericTextField GetXField(){return x;}
    public final LabeledNumericTextField GetYField(){return y;}
    public final String GetName(){return label.getText();}
    public final Label GetLabel(){return label;}
    public final StringProperty GetNameProp(){return label.textProperty();}
    public final ObjectProperty<Number> GetXValueProp(){return x.GetValueProp();}
    public final ObjectProperty<Number> GetYValueProp(){return y.GetValueProp();}

}
