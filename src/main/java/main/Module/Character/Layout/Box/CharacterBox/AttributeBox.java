package main.Module.Character.Layout.Box.CharacterBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import main.Module.Character.Layout.AttributeTextField;
import main.Module.Character.Game.Variables.Attribute;
import main.Module.Character.Layout.Box.VerticalColumn;

public class AttributeBox extends VariableCharacterBox
{
    private final AttributeTextField min;
    private final AttributeTextField max;
    private final AttributeTextField base;
    public AttributeBox(final Attribute temp)
    {
        super(temp);
        min = new AttributeTextField("Min");
        max = new AttributeTextField("Max");
        base = new AttributeTextField("Base");
        MinInit();
        MaxInit();
        BaseInit();
        base.Check(false);
        AddContent(min);
        AddContent(max);
        AddContent(base);
    }
    private boolean ListenerCheck()
    {
        if (character == null)
        {
            return false;
        }
        return character.GetAttribute(GetID()) != null;
    }
    private void SetCurrentMin(double d)
    {
        if (ListenerCheck())
        {
            character.GetAttribute(GetID()).SetMin(d);
        }
    }
    private void SetCurrentBase(Double d)
    {
        if (ListenerCheck())
        {
            if (d == null)
            {
                character.GetAttribute(GetID()).SetCurrent(character.GetAttribute(GetID()).GetMin());
            }
            else
            {
                character.GetAttribute(GetID()).SetCurrent(d);
            }
        }
    }
    private void SetCurrentMax(double d)
    {
        if (ListenerCheck())
        {
            character.GetAttribute(GetID()).SetMax(d);
        }
    }
    private void MinInit()
    {
        min.GetTextField().GetValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1 == null)
                {
                    SetCurrentMin(Double.MIN_VALUE);
                }
                else
                {
                    SetCurrentMin(t1.doubleValue());
                }
            }
        });


    }
    private void MaxInit()
    {
        max.GetTextField().GetValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1 == null)
                {
                    SetCurrentMax(Double.MAX_VALUE);
                }
                else
                {
                    SetCurrentMax(t1.doubleValue());
                }
            }
        });

    }
    private void BaseInit()
    {
        base.CanDisable(false);
        base.GetTextField().GetValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1 == null)
                {
                    SetCurrentBase(null);
                }
                else
                {
                    SetCurrentBase(t1.doubleValue());
                }
            }
        });

    }


    @Override
    protected void Load()
    {
        if (character != null)
        {
            Attribute a = character.GetAttribute(GetID());
            if (a != null)
            {
                min.SetValue(a.GetMin());
                base.SetValue(a.GetCurrent());
                max.SetValue(a.GetMax());
            }
        }
    }
}
