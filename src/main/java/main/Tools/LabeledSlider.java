package main.Tools;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class LabeledSlider extends HBox
{
    private final Label label;
    private final Slider slider;

    public LabeledSlider(String name, double min, double max, double current)
    {
        slider = new Slider(min, max, current);
        label = new Label(name + ": " + Double.toString(slider.getValue()));
        slider.valueProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1.doubleValue() < 1000 && t1.doubleValue() >= 100)
                {
                    label.setText(String.format("%s %.0f", name, t1.doubleValue()));
                } else if (t1.doubleValue() < 100 && t1.doubleValue() >= 10)
                {
                    label.setText(String.format("%s %.1f", name, t1.doubleValue()));
                } else if (t1.doubleValue() < 10)
                {
                    label.setText(String.format("%s %.2f", name, t1.doubleValue()));
                }
            }
        });
        getChildren().addAll(label, slider);
    }

    public final Label GetLabel()
    {
        return label;
    }

    public final Slider GetSlider()
    {
        return slider;
    }
}
