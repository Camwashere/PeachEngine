package main.Module.Character.Game.CharacterClass;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.MapChangeListener;
import main.Debug.Debug;
import main.Module.Character.Game.CharacterBase;
import main.Module.Character.Game.Variables.*;
import main.Tools.LinkedMapProperty;

public class CharacterClass extends CharacterClassBase
{
    private final SimpleStringProperty name;


    public CharacterClass(final CharacterClassBase parentClass)
    {
        super(parentClass);
        name = new SimpleStringProperty();
        Init();

    }
    private void Init()
    {
        perks.addListener(new MapChangeListener<Integer, Perk>()
        {
            @Override
            public void onChanged(Change<? extends Integer, ? extends Perk> change)
            {
                if (change.wasAdded())
                {
                    Debug.println("Was Added to: " + GetName(), 1);
                }
            }
        });
        for (Attribute a : parent.GetAttributes().values())
        {
            AddNewAttribute(a);
        }
        for (Trait t : parent.GetTraits().values())
        {
            AddNewTrait(t);
        }
        for (Descriptor d : parent.GetDescriptors().values())
        {
            AddNewDescriptor(d);
        }
        for (MentalState m : parent.GetMentalStates().values())
        {
            AddNewMentalState(m);
        }
    }

    public final void SetName(final String str){name.set(str);}
    public final String GetName(){return name.get();}

    @Override
    public String GetDisplayName()
    {
        return name.get();
    }

    @Override
    public String toString()
    {
        return String.format("Character Class: %s\n%s", name.get(), super.toString());
    }
}
