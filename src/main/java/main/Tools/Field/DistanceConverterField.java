package main.Tools.Field;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;
import main.Maths.Distance;
import main.Maths.DistanceType;

public class DistanceConverterField extends VBox
{
    private final LabeledDistanceField value;
    private final LabeledDistanceField converted;

    public DistanceConverterField(final String name)
    {
        value = new LabeledDistanceField(name);
        converted = new LabeledDistanceField("Conversion");
        getChildren().addAll(value, converted);
        converted.GetAmountField().setDisable(true);
        Init();
    }
    private void Init()
    {
        value.SetType(DistanceType.MILES);
        converted.SetType(DistanceType.KILOMETERS);
        value.GetAmountField().GetValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                Convert();
            }
        });
        value.GetTypeField().valueProperty().addListener(new ChangeListener<DistanceType>()
        {
            @Override
            public void changed(ObservableValue<? extends DistanceType> observableValue, DistanceType distanceType, DistanceType t1)
            {
                Convert();
            }
        });
        converted.GetTypeField().valueProperty().addListener(new ChangeListener<DistanceType>()
        {
            @Override
            public void changed(ObservableValue<? extends DistanceType> observableValue, DistanceType distanceType, DistanceType t1)
            {
                Convert();
            }
        });

    }
    private void Convert()
    {
        converted.SetAmount(Distance.Convert(value.GetAmount(), value.GetType(), converted.GetType()));
    }

    public final LabeledDistanceField GetValueField(){return value;}
    public final LabeledDistanceField GetConvertedField(){return converted;}
}
