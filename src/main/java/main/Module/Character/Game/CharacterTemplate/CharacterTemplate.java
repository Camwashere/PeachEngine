package main.Module.Character.Game.CharacterTemplate;

import javafx.beans.property.SimpleMapProperty;
import javafx.collections.MapChangeListener;
import main.Module.Character.Game.CharacterClass.CharacterClassBase;
import main.Module.Character.Game.CharacterManager.CharacterManager;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Module.Character.Game.Variables.*;

public class CharacterTemplate extends CharacterClassBase
{
    public CharacterTemplate(final CharacterManager managerParent)
    {
        super(managerParent);
    }





    public final void TestFill()
    {
        AddAttribute(new Attribute("Health"));
        AddAttribute(new Attribute("Stamina"));
        AddAttribute(new Attribute("Magic"));
        AddPerk(new Perk("Lockpicking"));
        AddPerk(new Perk("Crafter"));
        AddPerk(new Perk("Longshot"));
        AddPerk(new Perk("Rapid fire"));
        AddTrait(new Trait("Race"));
        AddTrait(new Trait("Gender"));
        AddTrait(new Trait("Height"));
        AddDescriptor(new Descriptor("Backstory"));
        AddMentalState(new MentalState("Angry"));
        AddMentalState(new MentalState("Happy"));
        AddMentalState(new MentalState("Sad"));
        AddMentalState(new MentalState("Silly"));
    }

    public final void AddAttribute()
    {
        Attribute a = new Attribute("New Attribute");
        int count=0;
        for (final Attribute temp : attributes.values())
        {
            if (temp.GetName().length() > a.GetName().length())
            {
                if (temp.GetName().substring(0, a.GetName().length()).equals(a.GetName()))
                {
                    count = Integer.parseInt(temp.GetName().substring(a.GetName().length(), temp.GetName().length()));
                    count++;
                }
            }
            else if (temp.GetName().equals(a.GetName()))
            {
                count++;
            }
        }
        if (count > 0)
        {
            a.SetName(a.GetName() + count);
        }
        AddAttribute(a);
    }
    public final void AddPerk()
    {
        Perk a = new Perk( "New Perk");
        int count=0;
        for (final Perk temp : perks.values())
        {
            if (temp.GetName().length() > a.GetName().length())
            {
                if (temp.GetName().substring(0, a.GetName().length()).equals(a.GetName()))
                {
                    count = Integer.parseInt(temp.GetName().substring(a.GetName().length(), temp.GetName().length()));
                    count++;
                }
            }
            else if (temp.GetName().equals(a.GetName()))
            {
                count++;
            }
        }
        if (count > 0)
        {
            a.SetName(a.GetName() + count);
        }
        AddPerk(a);
    }
    public final void AddTrait()
    {
        Trait a = new Trait( "New Trait");
        int count=0;
        for (final Trait temp : traits.values())
        {
            if (temp.GetName().length() > a.GetName().length())
            {
                if (temp.GetName().substring(0, a.GetName().length()).equals(a.GetName()))
                {
                    count = Integer.parseInt(temp.GetName().substring(a.GetName().length(), temp.GetName().length()));
                    count++;
                }
            }
            else if (temp.GetName().equals(a.GetName()))
            {
                count++;
            }
        }
        if (count > 0)
        {
            a.SetName(a.GetName() + count);
        }
        AddTrait(a);
    }
    public final void AddDescriptor()
    {
        Descriptor a = new Descriptor( "New Descriptor");
        int count=0;
        for (final Descriptor temp : descriptors.values())
        {
            if (temp.GetName().length() > a.GetName().length())
            {
                if (temp.GetName().substring(0, a.GetName().length()).equals(a.GetName()))
                {
                    count = Integer.parseInt(temp.GetName().substring(a.GetName().length(), temp.GetName().length()));
                    count++;
                }
            }
            else if (temp.GetName().equals(a.GetName()))
            {
                count++;
            }
        }
        if (count > 0)
        {
            a.SetName(a.GetName() + count);
        }
        AddDescriptor(a);
    }
    public final void AddMentalState()
    {
        MentalState a = new MentalState( "New MentalState");
        int count=0;
        for (final MentalState temp : mentalStates.values())
        {
            if (temp.GetName().length() > a.GetName().length())
            {
                if (temp.GetName().substring(0, a.GetName().length()).equals(a.GetName()))
                {
                    count = Integer.parseInt(temp.GetName().substring(a.GetName().length(), temp.GetName().length()));
                    count++;
                }
            }
            else if (temp.GetName().equals(a.GetName()))
            {
                count++;
            }
        }
        if (count > 0)
        {
            a.SetName(a.GetName() + count);
        }
        AddMentalState(a);
    }


    @Override
    public String toString()
    {
        return String.format("Character Template: %s", super.toString());
    }

    @Override
    public String GetDisplayName()
    {
        return "Template";
    }
}
