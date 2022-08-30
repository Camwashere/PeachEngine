package main.Module.Character.Layout.Box.TemplateBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import main.Module.Character.Game.CharacterTemplate.CharacterTemplate;
import main.Module.Character.Game.Variables.Attribute;
import main.Module.Character.Layout.AttributeTextField;
import main.Module.Character.Layout.Box.VerticalColumn;
import main.Module.Character.Layout.ConditionField;
import main.Module.Story.Layout.StoryLeftBar.ScenarioItem;

public class AttributeTemplateBox extends VariableTemplateBox
{
    private final AttributeTextField min;
    private final AttributeTextField max;
    private final AttributeTextField base;
    private final ConditionField onMin;
    private final ConditionField onMax;
    private final ConditionField onCurrentChanged;
    public AttributeTemplateBox(final CharacterTemplate template, final Attribute temp)
    {
        super(template, temp);
        min = new AttributeTextField("Min");
        max = new AttributeTextField("Max");
        base = new AttributeTextField("Base");
        onMin = new ConditionField(template.GetManager().GetParentModule().GetParent().GetStoryModule().GetScenarioTree(),"On Minimum Hit");
        onMax = new ConditionField(template.GetManager().GetParentModule().GetParent().GetStoryModule().GetScenarioTree(),"On Maximum Hit");
        onCurrentChanged = new ConditionField(template.GetManager().GetParentModule().GetParent().GetStoryModule().GetScenarioTree(),"On Current Changed");
        MinInit(temp);
        MaxInit(temp);
        BaseInit(temp);
        ConditionInit(temp);
        base.Check(false);
        ConstraintInit();
        AddContent(min);
        AddContent(max);
        AddContent(base);
        AddContent(onMin);
        AddContent(onMax);
        AddContent(onCurrentChanged);
    }
    private void ConditionInit(final Attribute temp)
    {
        onMin.SetCurrent(temp.GetOnMin());
        onMax.SetCurrent(temp.GetOnMax());
        onCurrentChanged.SetCurrent(temp.GetOnCurrentChanged());
        onMin.GetCurrentProp().addListener(new ChangeListener<ScenarioItem>()
        {
            @Override
            public void changed(ObservableValue<? extends ScenarioItem> observableValue, ScenarioItem scenarioItem, ScenarioItem t1)
            {
                temp.SetOnMin(t1);
            }
        });
        onMax.GetCurrentProp().addListener(new ChangeListener<ScenarioItem>()
        {
            @Override
            public void changed(ObservableValue<? extends ScenarioItem> observableValue, ScenarioItem scenarioItem, ScenarioItem t1)
            {
                temp.SetOnMax(t1);
            }
        });

        onCurrentChanged.GetCurrentProp().addListener(new ChangeListener<ScenarioItem>()
        {
            @Override
            public void changed(ObservableValue<? extends ScenarioItem> observableValue, ScenarioItem scenarioItem, ScenarioItem t1)
            {
                temp.SetOnCurrentChanged(t1);
            }
        });
    }
    private void MinInit(final Attribute temp)
    {
        min.SetValue(temp.GetMin());
        min.GetTextField().disabledProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1)
                {
                    min.SetValue(Double.MIN_VALUE);
                }
            }
        });

        min.GetTextField().GetValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1 == null)
                {
                    temp.SetMin(Double.MIN_VALUE);
                }
                else
                {
                    temp.SetMin(t1.doubleValue());
                }
            }
        });
        temp.GetMinProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                min.SetValue(t1);
                UpdateTooltip();
            }
        });

    }
    private void MaxInit(final Attribute temp)
    {
        max.SetValue(temp.GetMax());
        max.GetTextField().disabledProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1)
                {
                    max.SetValue(Double.MAX_VALUE);
                }
            }
        });
        max.GetTextField().GetValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1 == null)
                {
                    temp.SetMax(Double.MAX_VALUE);
                }
                else
                {
                    temp.SetMax(t1.doubleValue());
                }
            }
        });
        temp.GetMaxProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                max.SetValue(t1);
                UpdateTooltip();
            }
        });

    }
    private void BaseInit(final Attribute temp)
    {
        base.SetValue(temp.GetCurrent());
        base.CanDisable(false);
        base.GetTextField().GetValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1 == null)
                {
                    temp.SetCurrent(temp.GetMin());
                }
                else
                {
                    temp.SetCurrent(t1.doubleValue());
                }
            }
        });
        temp.GetCurrentProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                base.SetValue(t1);
                UpdateTooltip();
            }
        });
    }

    private void ConstraintInit()
    {
    }


}
