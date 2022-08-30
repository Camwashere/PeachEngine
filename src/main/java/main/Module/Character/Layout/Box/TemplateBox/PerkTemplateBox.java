package main.Module.Character.Layout.Box.TemplateBox;

import main.Module.Character.Game.CharacterTemplate.CharacterTemplate;
import main.Module.Character.Game.Variables.Perk;
import main.Module.Character.Layout.Box.VerticalColumn;
import main.Module.Character.Layout.ConditionField;

public class PerkTemplateBox extends VariableTemplateBox
{
    private final ConditionField effect;
    public PerkTemplateBox(final CharacterTemplate template,final Perk temp)
    {
        super(template, temp);
        effect = new ConditionField(template.GetManager().GetParentModule().GetParent().GetStoryModule().GetScenarioTree(), "Effect");
        AddContent(effect);
    }
}
