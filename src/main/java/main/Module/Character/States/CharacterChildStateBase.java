package main.Module.Character.States;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.MapChangeListener;
import main.Module.Character.Game.CharacterBase;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Module.Character.Game.Variables.*;
import main.Module.Character.Layout.Box.CharacterBox.*;
import main.Module.Character.Layout.LeftBar.CharacterTree;
import main.Module.Character.Layout.PerkSelectionDialog;
import main.Module.Character.Layout.RightBar.CharacterRightBar;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public abstract class CharacterChildStateBase<T extends CharacterBase> extends CharacterStateBase
{
    protected final SimpleObjectProperty<T> current;
    protected final CharacterRightBar right;
    public CharacterChildStateBase(final CharacterTree tree)
    {
        super(tree);
        current = new SimpleObjectProperty<>();
        right = new CharacterRightBar(this);
        Init();
        setRight(right);
    }
    protected abstract void OnLoad();
    protected abstract void OnUnload();
    public final void Load(final T c)
    {
        current.set(c);
        if (current.get() == null)
        {
            OnUnload();
            UnloadVariables();
        }
        else
        {
            OnLoad();
            LoadVariables();
        }
    }
    public final void Unload(){Load(null);}
    private void Init()
    {
        InitCenter();
        ListenerInit();
        InitAddButton();
    }
    private void InitAddButton()
    {
        AddButton(perks, evt->
        {
            if (current.get() != null)
            {
                PerkSelectionDialog d = new PerkSelectionDialog(current.get(), GetTemplate());
                Optional<ArrayList<Perk>> option = d.showAndWait();
                if (option.isPresent())
                {
                    current.get().AddPerks(option.get());
                    Load(current.get());
                }
            }
        });
    }
    private void ListenerInit()
    {
        GetTemplate().GetVariables().addListener(new MapChangeListener<UUID, CharacterVariableBase>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends CharacterVariableBase> change)
            {
                if (change.wasRemoved())
                {
                    RemoveContent(change.getKey());
                }
                if (change.wasAdded())
                {
                    AddVariable(change.getValueAdded());
                }
            }
        });
    }
    private void InitAttributes()
    {
        for (final Attribute v : GetTemplate().GetAttributes().values())
        {
            attributes.AddContent(new AttributeBox(v));
        }
    }
    private void InitCenter()
    {
        InitAttributes();
        for (final Trait v : GetTemplate().GetTraits().values())
        {
            traits.AddContent(new TraitBox(v));
        }
        for (final Descriptor v : GetTemplate().GetDescriptors().values())
        {
            descriptors.AddContent(new DescriptorBox(v));
        }
        for (final MentalState v : GetTemplate().GetMentalStates().values())
        {
            mentalStates.AddContent(new MentalStateBox(v));
        }
    }

    private void AddVariable(final CharacterVariableBase var)
    {
        if (var instanceof Attribute temp)
        {
            attributes.AddContent(new AttributeBox(temp));
        }
        else if (var instanceof Perk temp)
        {
            perks.AddContent(new PerkBox(temp));
        }
        else if (var instanceof Trait temp)
        {
            traits.AddContent(new TraitBox(temp));
        }
        else if (var instanceof Descriptor temp)
        {
            descriptors.AddContent(new DescriptorBox(temp));
        }
        else if (var instanceof MentalState temp)
        {
            mentalStates.AddContent(new MentalStateBox(temp));
        }
    }

    private void LoadVariables()
    {
        attributes.Load(current.get());
        perks.Load(current.get());
        traits.Load(current.get());
        descriptors.Load(current.get());
        mentalStates.Load(current.get());
    }
    private void UnloadVariables()
    {
        attributes.Unload();
        perks.Unload();
        traits.Unload();
        descriptors.Unload();
        mentalStates.Unload();
    }

    @Override
    public final UUID GetID()
    {
        if (current.get() == null)
        {
            return GetTemplate().GetID();
        }
        else
        {
            return current.get().GetID();
        }
    }

    public final T GetCurrent() {return current.get();}
    public final SimpleObjectProperty<T> GetCurrentProp(){return current;}
    public final CharacterRightBar GetRight(){return right;}
}


