package main.Tools.Field;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import main.Maths.Distance;
import main.Maths.DistanceType;

public class DistanceField extends HBox
{
    protected final LabeledNumericTextField amount = new LabeledNumericTextField("Amount");
    protected final ComboBox<DistanceType> type = new ComboBox<>();

    public DistanceField()
    {
        type.getItems().addAll(DistanceType.values());
        getChildren().addAll(amount, type);
    }


    public final void SetAmount(final double a){amount.SetValue(a);}
    public final void SetType(final DistanceType t){type.setValue(t);}

    public final LabeledNumericTextField GetAmountField(){return amount;}
    public final double GetAmount(){return amount.GetDouble();}
    public final DistanceType GetType(){return type.getValue();}
    public final ComboBox<DistanceType> GetTypeField(){return type;}
    public final Distance GetDistance(){return new Distance(amount.GetValue().doubleValue(), type.getValue());}


}
