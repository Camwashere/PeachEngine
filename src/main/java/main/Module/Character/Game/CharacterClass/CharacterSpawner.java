package main.Module.Character.Game.CharacterClass;

import javafx.beans.property.SimpleMapProperty;
import main.Maths.Chance;
import main.Module.Character.Game.CharacterTemplate.CharacterTemplate;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;

public class CharacterSpawner
{
    private final CharacterClassBase parent;
    private final CharacterTemplate template;

    private final SimpleMapProperty<Integer, Chance> perks;



    public CharacterSpawner(final CharacterClassBase parentClass)
    {
        parent = parentClass;
        template = parent.GetManager().GetTemplate();

        perks = new SimpleMapProperty<Integer, Chance>();
    }
    public final StoryCharacter Spawn()
    {
        StoryCharacter c = new StoryCharacter(template);
        c.SetFirstName(RandomBoyName());
        c.SetMiddleName(RandomBoyName());
        c.SetLastName(RandomLastName());
        return c;
    }
    private String RandomBoyName()
    {
        int index = Chance.RandInt(parent.GetManager().GetBoyNames().size());
        return parent.GetManager().GetBoyNames().get(index);
    }
    private String RandomLastName()
    {
        int index = Chance.RandInt(parent.GetManager().GetLastNames().size());
        return parent.GetManager().GetLastNames().get(index);
    }
    private String RandomGirlName()
    {
        int index = Chance.RandInt(parent.GetManager().GetGirlNames().size());
        return parent.GetManager().GetGirlNames().get(index);
    }



}
