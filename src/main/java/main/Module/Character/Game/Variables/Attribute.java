package main.Module.Character.Game.Variables;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import main.Maths.LimitNumber;
import main.Module.Story.Layout.StoryLeftBar.ScenarioItem;

import java.util.Objects;

public class Attribute extends CharacterVariableBase
{
    private final LimitNumber constraint;
    private final SimpleObjectProperty<ScenarioItem> onMin = new SimpleObjectProperty<ScenarioItem>();
    private final SimpleObjectProperty<ScenarioItem> onMax = new SimpleObjectProperty<ScenarioItem>();
    private final SimpleObjectProperty<ScenarioItem> onCurrentChanged = new SimpleObjectProperty<ScenarioItem>();


    public Attribute(final String varName)
    {
        super(varName, CharacterVariableType.ATTRIBUTE);
        constraint = new LimitNumber();
        SetOnMin(null);
        SetOnMax(null);
        SetOnCurrentChanged(null);
    }
    public Attribute(final Attribute temp)
    {
        super(temp);
        constraint = new LimitNumber(temp.constraint);
        SetOnMin(temp.GetOnMin());
        SetOnMax(temp.GetOnMax());
        SetOnCurrentChanged(temp.GetOnCurrentChanged());
    }




    public final void SetMin(Double val){constraint.SetMin(val);}
    public final void SetMax(Double val){constraint.SetMax(val);}
    public final void SetCurrent(Double val){constraint.SetCurrent(val);}

    public final void SetOnMin(final ScenarioItem l){onMin.setValue(l);}
    public final void SetOnMax(final ScenarioItem l){onMax.setValue(l);}
    public final void SetOnCurrentChanged(final ScenarioItem l){onCurrentChanged.setValue(l);}


    public final double GetMin(){return constraint.GetMin();}
    public final double GetMax(){return constraint.GetMax();}
    public final double GetCurrent(){return constraint.GetCurrent();}
    public final SimpleDoubleProperty GetMinProp(){return constraint.GetMinProp();}
    public final SimpleDoubleProperty GetMaxProp(){return constraint.GetMaxProp();}
    public final SimpleDoubleProperty GetCurrentProp(){return constraint.GetCurrentProp();}
    public final ScenarioItem GetOnMin(){return onMin.getValue();}
    public final ScenarioItem GetOnMax(){return onMax.getValue();}
    public final ScenarioItem GetOnCurrentChanged(){return onCurrentChanged.getValue();}
    public final LimitNumber GetConstraint(){return constraint;}


    @Override
    public String toString()
    {
        return String.format("%s%s", super.toString(), constraint.toString());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (obj instanceof Attribute other)
        {
            return Objects.equals(GetID(), other.GetID()) && Objects.equals(constraint, other.constraint);

        }
        return false;
    }
}
