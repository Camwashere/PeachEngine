package main.Module.Character.Game;

import javafx.beans.property.SimpleMapProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import main.Module.Character.Game.CharacterClass.CharacterClassBase;
import main.Module.Character.Game.CharacterManager.CharacterManager;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Module.Character.Game.Variables.*;
import main.Tools.LinkedMapProperty;

import java.util.ArrayList;
import java.util.UUID;

public abstract class CharacterBase
{

    private final UUID id;
    protected CharacterClassBase parent;
    protected final CharacterManager manager;

    protected final LinkedMapProperty<Integer, CharacterVariableBase> variables;
    protected final LinkedMapProperty<Integer, Attribute> attributes;
    protected final LinkedMapProperty<Integer, Perk> perks;
    protected final LinkedMapProperty<Integer, Trait> traits;
    protected final LinkedMapProperty<Integer, Descriptor> descriptors;
    protected final LinkedMapProperty<Integer, MentalState> mentalStates;

    protected Image icon=null;

    public CharacterBase(final CharacterClassBase parentClass)
    {
        id = UUID.randomUUID();
        parent = parentClass;
        manager = parent.GetManager();

        attributes = new LinkedMapProperty<>();
        perks = new LinkedMapProperty<>();
        traits = new LinkedMapProperty<>();
        descriptors = new LinkedMapProperty<>();
        mentalStates = new LinkedMapProperty<>();
        variables = new LinkedMapProperty<Integer, CharacterVariableBase>(GetAttributes(), GetPerks(), GetTraits(), GetDescriptors(), GetMentalStates());
        for (Attribute a : parent.GetAttributes().values())
        {
            AddNewAttribute(a);
        }
        if (! parent.IsTemplate())
        {
            for (final Perk p : parent.GetPerks().values())
            {
                AddNewPerk(p);
            }
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
        parent.AddChild(this);
    }
    public CharacterBase(final CharacterManager charManager)
    {
        id = UUID.randomUUID();
        parent = null;
        manager = charManager;

        attributes = new LinkedMapProperty<>();
        perks = new LinkedMapProperty<>();
        traits = new LinkedMapProperty<>();
        descriptors = new LinkedMapProperty<>();
        mentalStates = new LinkedMapProperty<>();
        variables = new LinkedMapProperty<Integer, CharacterVariableBase>(GetAttributes(), GetPerks(), GetTraits(), GetDescriptors(), GetMentalStates());
    }
    


    

    public final void AddNewVariable(final CharacterVariableBase var)
    {
        switch (var.GetType())
        {
            case ATTRIBUTE:
                AddNewAttribute((Attribute)var);
                break;
            case PERK:
                AddNewPerk((Perk)var);
                break;
            case TRAIT:
                AddNewTrait((Trait)var);
                break;
            case DESCRIPTOR:
                AddNewDescriptor((Descriptor)var);
                break;
            case MENTAL:
                AddNewMentalState((MentalState)var);
                break;
            default:
                break;
        }
    }
    public final void RemoveVariable(final CharacterVariableBase var)
    {
        attributes.remove(var.GetID());
        perks.remove(var.GetID());
        traits.remove(var.GetID());
        descriptors.remove(var.GetID());
        mentalStates.remove(var.GetID());
    }

    // ADD VARIABLE MULTIPLE
    public final void AddPerks(final Perk...ps)
    {
        for (final Perk p : ps)
        {
            AddPerk(p);
        }
    }
    public final void AddPerks(final ArrayList<Perk> ps)
    {
        for (final Perk p : ps)
        {
            AddPerk(p);
        }
    }
    
    public final void AddNewAttribute(final Attribute a) {AddAttribute(new Attribute(a));}
    public final void AddNewPerk(final Perk a) {AddPerk(new Perk(a));}
    public final void AddNewTrait(final Trait a) {AddTrait(new Trait(a));}
    public final void AddNewDescriptor(final Descriptor a) {AddDescriptor(new Descriptor(a));}
    public final void AddNewMentalState(final MentalState a) {AddMentalState(new MentalState(a));}

    public final void AddAttribute(final Attribute a){attributes.put(a.GetID(), a);}
    public final void AddPerk(final Perk a){perks.put(a.GetID(), a);}
    public final void AddTrait(final Trait a){traits.put(a.GetID(), a);}
    public final void AddDescriptor(final Descriptor a){descriptors.put(a.GetID(), a);}
    public final void AddMentalState(final MentalState a){mentalStates.put(a.GetID(), a);}

    public final boolean HasIcon(){return icon!=null;}
    public abstract boolean IsClass();

    public final void SetIcon(final Image p){icon = p;}
    public final void SetParent(final CharacterClassBase base)
    {
        if (parent != null)
        {
            parent.RemoveChild(this);
        }
        parent = base;
        if (parent == null)
        {
            parent = manager.GetTemplate();
        }
        PerkAssign();
        parent.AddChild(this);
    }
    private void PerkAssign()
    {
        if (! parent.IsTemplate())
        {
            for (final Perk p : parent.GetPerks().values())
            {
                if (!perks.containsKey(p.GetID()))
                {
                    AddNewPerk(p);
                }
            }
        }
    }
    
    public final UUID GetID(){return id;}
    public final CharacterClassBase GetParent(){return parent;}
    public final CharacterManager GetManager(){return manager;}

    // GET SINGLE
    public final Attribute GetAttribute(final int i){return attributes.get(i);}
    public final Perk GetPerk(final int i){return perks.get(i);}
    public final Trait GetTrait(final int i){return traits.get(i);}
    public final Descriptor GetDescriptor(final int i){return descriptors.get(i);}
    public final MentalState GetMentalState(final int i){return mentalStates.get(i);}

    public final LinkedMapProperty<Integer, Attribute> GetAttributes(){return attributes;}
    public final LinkedMapProperty<Integer, Perk> GetPerks(){return perks;}
    public final LinkedMapProperty<Integer, Trait> GetTraits(){return traits;}
    public final LinkedMapProperty<Integer, Descriptor> GetDescriptors(){return descriptors;}
    public final LinkedMapProperty<Integer, MentalState> GetMentalStates(){return mentalStates;}
    public final LinkedMapProperty<Integer, CharacterVariableBase> GetVariables(){return variables;}
    public final Image GetIcon(){return icon;}
    public abstract String GetDisplayName();

}
