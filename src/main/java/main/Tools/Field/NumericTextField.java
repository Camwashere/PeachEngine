package main.Tools.Field;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.ContextMenuEvent;
import javafx.util.StringConverter;
import main.Debug.Debug;
import main.Maths.Vec.Vec2;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class NumericTextField extends TextField
{
    private final TextFormatter<Number> formatter;
    private final SimpleDoubleProperty min = new SimpleDoubleProperty(Double.MIN_VALUE);
    private final SimpleDoubleProperty max = new SimpleDoubleProperty(Double.MAX_VALUE);
    private final SimpleIntegerProperty precision = new SimpleIntegerProperty(2);

    public NumericTextField()
    {
        Pattern validEditingState = Pattern.compile("-?\\+?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c ->
        {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches())
            {
                return c;
            }
            else
            {
                if (c.getControlNewText().equals("MAX"))
                {
                    return c;
                }
                else if (c.getControlNewText().equals("MIN"))
                {
                    return c;
                }
                return null;
            }
        };
        StringConverter<Number> converter = new StringConverter<Number>()
        {
            @Override
            public Number fromString(String s)
            {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s))
                {
                    return 0.0;
                }
                else if ("None".equals(s))
                {
                    return 0;
                }
                else if ("MIN".equals(s))
                {
                    return Double.MIN_VALUE;
                }
                else if ("MAX".equals(s))
                {
                    return Double.MAX_VALUE;
                }
                else if ("Infinity".equals(s))
                {
                    return Double.POSITIVE_INFINITY;
                }
                else
                {
                    if (s.contains("."))
                    {
                        return Double.valueOf(s);
                    }
                    else
                    {
                        return Integer.valueOf(s);

                    }
                }
            }
            @Override
            public String toString(Number d)
            {
                if (d == null)
                {
                    return "None";
                }
                else if (d.doubleValue() == Double.MIN_VALUE)
                {
                    return "MIN";
                }
                else if (d.doubleValue() == Double.MAX_VALUE)
                {
                    return "MAX";
                }
                else if (d.doubleValue() == Double.POSITIVE_INFINITY)
                {
                    return "MAX";
                }

                if (d instanceof Integer i)
                {
                    return i.toString();
                }
                String format = "%." + precision.get() + "f";
                return String.format(format, d.doubleValue());
                //return d.toString();
            }
        };
        formatter = new TextFormatter<Number>(converter, 0, filter);
        setTextFormatter(formatter);
        //prefColumnCountProperty().bind(textProperty().length());
        EventInit();
        ListenerInit();
        RangeInit();
    }
    private void EventInit()
    {
        addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
    }
    private void ListenerInit()
    {

        focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (t1)
                        {
                            selectAll();
                        }
                    }
                });
            }
        });
    }

    private void RangeInit()
    {
        formatter.valueProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (IsLimited())
                {
                    if (t1.doubleValue()<GetMin())
                    {
                        SetValue(GetMin());
                    }
                    else if (t1.doubleValue()>GetMax())
                    {
                        SetValue(GetMax());
                    }
                }
            }
        });
        min.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (GetValue().doubleValue()<GetMin())
                {
                    SetValue(GetMin());
                }
            }
        });
        max.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (GetValue().doubleValue()>GetMax())
                {
                    SetValue(GetMax());
                }
            }
        });


    }


    public final void SetPrecision(final int p){precision.set(p);}

    public final void SetRange(double minimum, double maximum)
    {
        SetMin(minimum);
        SetMax(maximum);
    }
    public final void RemoveRange()
    {
        SetMin(Double.MIN_VALUE);
        SetMax(Double.MAX_VALUE);
    }
    public final void SetValue(final Number num)
    {
        GetValueProp().set(num);
    }
    public final void SetMin(final double d)
    {
        min.set(d);
        /*if (GetValue().doubleValue()<GetMin())
        {
            SetValue(GetMin());
        }*/
    }
    public final void SetMax(final double d)
    {
        max.set(d);
        /*if (GetValue().doubleValue()>GetMax())
        {
            SetValue(GetMax());
        }*/
    }


    public final boolean IsLimited(){return HasMin() || HasMax();}
    public final boolean HasMin(){return min.get() != Double.MIN_VALUE;}
    public final boolean HasMax(){return max.get() != Double.MAX_VALUE;}
    public final Number GetValue() {return formatter.getValueConverter().fromString(getText());}
    public final int GetPrecision(){return precision.get();}
    public final SimpleIntegerProperty GetPrecisionProp(){return precision;}
    public final double GetDouble(){return GetValue().doubleValue();}
    public final int GetInt(){return GetValue().intValue();}
    public final double GetMin(){return min.get();}
    public final double GetMax(){return max.get();}
    public final SimpleDoubleProperty GetMinProp(){return min;}
    public final SimpleDoubleProperty GetMaxProp(){return max;}
    public final Vec2 GetRange(){return new Vec2(GetMin(), GetMax());}
    public final ObjectProperty<Number> GetValueProp(){return formatter.valueProperty();}



}
