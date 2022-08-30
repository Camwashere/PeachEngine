package main.Module.Character.Game.CharacterClass;

import javafx.collections.MapChangeListener;
import main.Module.Character.Game.CharacterBase;
import main.Module.Character.Game.CharacterManager.CharacterManager;
import main.Module.Character.Game.Variables.*;
import main.Tools.LinkedMapProperty;

import java.util.UUID;

public abstract class CharacterClassBase extends CharacterBase
{
    protected final LinkedMapProperty<UUID, CharacterBase> children;
    private final boolean isTemplate;
    
    public CharacterClassBase(final CharacterClassBase parentClass)
    {
        super(parentClass);
        isTemplate = false;
        children = new LinkedMapProperty<UUID, CharacterBase>();
        ListenerInit();
    }
    public CharacterClassBase(final CharacterManager charManager)
    {
        super(charManager);
        isTemplate = true;
        children = new LinkedMapProperty<UUID, CharacterBase>();
        ListenerInit();
    }
    private void ListenerInit()
    {
        attributes.addListener(new MapChangeListener<UUID, Attribute>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends Attribute> change)
            {
                if (change.wasAdded())
                {
                    for (final CharacterBase b : children.values())
                    {
                        b.AddNewVariable(change.getValueAdded());
                    }
                }
                else if (change.wasRemoved())
                {
                    for (final CharacterBase b : children.values())
                    {
                        b.RemoveVariable(change.getValueRemoved());
                    }
                }
            }
        });

        perks.addListener(new MapChangeListener<UUID, Perk>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends Perk> change)
            {
                if (change.wasAdded())
                {
                    if (!IsTemplate())
                    {
                        for (final CharacterBase b : children.values())
                        {
                            b.AddPerk(change.getValueAdded());
                        }
                    }

                }
                else
                {
                    for (final CharacterBase b : children.values())
                    {
                        b.RemoveVariable(change.getValueRemoved());
                    }
                }
            }
        });

        traits.addListener(new MapChangeListener<UUID, Trait>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends Trait> change)
            {
                if (change.wasAdded())
                {
                    for (final CharacterBase b : children.values())
                    {
                        b.AddNewVariable(change.getValueAdded());
                    }
                }
                else
                {
                    for (final CharacterBase b : children.values())
                    {
                        b.RemoveVariable(change.getValueRemoved());
                    }
                }
            }
        });

        descriptors.addListener(new MapChangeListener<UUID, Descriptor>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends Descriptor> change)
            {
                if (change.wasAdded())
                {
                    for (final CharacterBase b : children.values())
                    {
                        b.AddNewVariable(change.getValueAdded());
                    }
                }
                else
                {
                    for (final CharacterBase b : children.values())
                    {
                        b.RemoveVariable(change.getValueRemoved());
                    }
                }
            }
        });
        mentalStates.addListener(new MapChangeListener<UUID, MentalState>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends MentalState> change)
            {
                if (change.wasAdded())
                {
                    for (final CharacterBase b : children.values())
                    {
                        b.AddNewVariable(change.getValueAdded());
                    }
                }
                else
                {
                    for (final CharacterBase b : children.values())
                    {
                        b.RemoveVariable(change.getValueRemoved());
                    }
                }
            }
        });
    }

    public final void RemoveChild(final CharacterBase base){children.remove(base.GetID());}
    public final void AddChild(final CharacterBase base) {children.put(base.GetID(), base);}


    public final boolean IsTemplate(){return isTemplate;}

    public final LinkedMapProperty<UUID, CharacterBase> GetChildren(){return children;}



    @Override
    public boolean IsClass() {return true;}

    @Override
    public String GetDisplayName()
    {
        return "Template";
    }

    @Override
    public String toString()
    {
        String attributeString = "Attributes: \n";
        for (Attribute a : attributes.values())
        {
            attributeString += a.toString() + "\n";
        }
        String perkString = "Perks: \n";
        for (Perk p : perks.values())
        {
            perkString += p.toString() + "\n";
        }
        String traitString = "Traits: \n";
        for (Trait t : traits.values())
        {
            traitString += t.toString() + "\n";
        }
        String descriptorString = "Descriptors: \n";
        for (Descriptor d : descriptors.values())
        {
            descriptorString += d.toString() + "\n";
        }
        String mentalString = "Mental States: \n";
        for (MentalState m : mentalStates.values())
        {
            mentalString += m.toString() + "\n";
        }

        return String.format("\n%s\n%s\n%s\n%s\n%s\n", attributeString, perkString, traitString, descriptorString, mentalString);
    }
}
