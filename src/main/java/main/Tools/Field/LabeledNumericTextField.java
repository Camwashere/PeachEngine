package main.Tools.Field;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.Debug.Debug;
import main.Maths.Vec.Vec2;
import main.Tools.InitHelp;

public class LabeledNumericTextField extends HBox
{
    private final Label label;
    protected final NumericTextField field;
    private final Vec2 prevPos = new Vec2();
    //private int precision = 2;

    public LabeledNumericTextField(final String name)
    {
        label = new Label(name);
        field = new NumericTextField();
        Init();
    }
    private void Init()
    {
        InitHelp.LabelInit(label);
        InitHelp.NodeInit(field);
        HBox.setHgrow(label, Priority.NEVER);
        EventInit();
        getChildren().addAll(label, field);
    }
    private void EventInit()
    {
        label.setOnMouseEntered(evt->
        {
            setCursor(Cursor.E_RESIZE);
        });
        label.setOnMousePressed(evt->
        {
            prevPos.MakeEqual(evt.getX(), evt.getY());
        });
        label.setOnMouseDragged(evt->
        {
            // If dragging to the right
            if (evt.getX() > prevPos.x)
            {
                double dif = evt.getX() - prevPos.x;
                if (field.GetPrecision() == 0)
                {
                    dif = Math.ceil(dif);
                }
                String format = "%." + field.GetPrecision() + "f";
                double val = Double.parseDouble(String.format(format, GetValue().doubleValue() + dif));
                //SetValue(val);
                SetValue(GetValue().doubleValue() + dif);
            }
            // If dragging to the left
            else if (evt.getX() < prevPos.x)
            {
                double dif = prevPos.x - evt.getX();
                String format = "%." + field.GetPrecision() + "f";
                double val = Double.parseDouble(String.format(format, GetValue().doubleValue() - dif));
                //SetValue(val);
                SetValue(GetValue().doubleValue() - dif);
            }
            prevPos.MakeEqual(evt.getX(), evt.getY());

        });
        label.setOnMouseExited(evt->
        {
            setCursor(Cursor.DEFAULT);
        });
    }
    public final void SetName(final String str){label.setText(str);}
    public final void SetValue(final Number n){field.SetValue(n);}
    public final void SetRange(double min, double max){field.SetRange(min, max);}
    public final void SetMin(double d){field.SetMin(d);}
    public final void SetMax(double d){field.SetMax(d);}
    public final void RemoveRange(){field.RemoveRange();}
    public final void SetSlidePrecision(int p){field.SetPrecision(p);}
    public final void SetPrecision(int p){field.SetPrecision(p);}



    public final String GetName(){return label.getText();}
    public final int GetPrecision(){return field.GetPrecision();}
    public final Vec2 GetRange(){return field.GetRange();}
    public final double GetMin(){return field.GetMin();}
    public final double GetMax(){return field.GetMax();}
    public final SimpleDoubleProperty GetMinProp(){return field.GetMinProp();}
    public final SimpleDoubleProperty GetMaxProp(){return field.GetMaxProp();}
    public final boolean IsLimited(){return field.IsLimited();}
    public final Label GetLabel(){return label;}
    public final NumericTextField GetField(){return field;}
    public final Number GetValue(){return field.GetValue();}
    public final double GetDouble(){return field.GetDouble();}
    public final int GetInt(){return field.GetInt();}
    public final ObjectProperty<Number> GetValueProp(){return field.GetValueProp();}
    public final StringProperty GetLabelTextProperty(){return label.textProperty();}
}
