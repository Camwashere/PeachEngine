package main.Module.Character.Layout.Box.CharacterBox;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import main.Module.Character.Game.Variables.Perk;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Module.Character.Layout.Box.VerticalColumn;

public class PerkBox extends VariableCharacterBox
{
    public PerkBox(final Perk temp)
    {
        super(temp);
        ContextMenu menu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(evt->
        {
            if (character != null)
            {
                character.RemoveVariable(temp);
            }
        });
        menu.getItems().add(delete);
        GetNameButton().setContextMenu(menu);
    }

    @Override
    protected void Load()
    {
        if (character != null)
        {

        }
    }
}
