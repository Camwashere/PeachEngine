package main.Module.Character.Layout.Box.CharacterBox;

import main.Module.Character.Game.CharacterBase;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Module.Character.Layout.Box.VariableBoxBase;
import main.Module.Character.Game.Variables.CharacterVariableBase;
import main.Module.Character.Layout.Box.VerticalColumn;

public abstract class VariableCharacterBox extends VariableBoxBase
{
    protected CharacterBase character;
    public VariableCharacterBox(final CharacterVariableBase temp)
    {
        super(temp.GetID(), temp.GetName());
        //name.GetTextProperty().bind(temp.GetNameProp());

    }

    public final void LoadCharacter(final CharacterBase c)
    {
        character = c;
        Load();
    }

    protected abstract void Load();
    public void Unload(){character = null;}
}
