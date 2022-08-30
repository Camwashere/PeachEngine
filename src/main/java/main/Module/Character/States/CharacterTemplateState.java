package main.Module.Character.States;

import javafx.collections.MapChangeListener;
import main.Module.Character.Game.CharacterTemplate.CharacterTemplate;
import main.Module.Character.Game.Variables.*;
import main.Module.Character.Layout.Box.TemplateBox.*;
import main.Module.Character.Layout.LeftBar.CharacterTree;

import java.util.UUID;

public class CharacterTemplateState extends CharacterStateBase
{
    public CharacterTemplateState(final CharacterTree tree)
    {
        super(tree);
        ListenerInit(tree);
        VariableInit(tree);
    }

    @Override
    public UUID GetID()
    {
        return GetTemplate().GetID();
    }

    private void ListenerInit(final CharacterTree tree)
    {
        tree.GetTemplate().GetAttributes().addListener(new MapChangeListener<UUID, Attribute>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends Attribute> change)
            {
                if (change.wasAdded())
                {
                    attributes.AddContent(new AttributeTemplateBox(tree.GetTemplate(), change.getValueAdded()));
                }
                else if (change.wasRemoved())
                {
                    attributes.RemoveContent(change.getKey());
                }
            }
        });
        tree.GetTemplate().GetPerks().addListener(new MapChangeListener<UUID, Perk>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends Perk> change)
            {
                if (change.wasAdded())
                {
                    perks.AddContent(new PerkTemplateBox(tree.GetTemplate(), change.getValueAdded()));
                }
                else if (change.wasRemoved())
                {
                    perks.RemoveContent(change.getKey());
                }
            }
        });
        tree.GetTemplate().GetTraits().addListener(new MapChangeListener<UUID, Trait>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends Trait> change)
            {
                if (change.wasAdded())
                {
                    traits.AddContent(new TraitTemplateBox(tree.GetTemplate(), change.getValueAdded()));
                }
                else if (change.wasRemoved())
                {
                    traits.RemoveContent(change.getKey());
                }
            }
        });
        tree.GetTemplate().GetDescriptors().addListener(new MapChangeListener<UUID, Descriptor>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends Descriptor> change)
            {
                if (change.wasAdded())
                {
                    descriptors.AddContent(new DescriptorTemplateBox(tree.GetTemplate(), change.getValueAdded()));
                }
                else if (change.wasRemoved())
                {
                    descriptors.RemoveContent(change.getKey());
                }
            }
        });
        tree.GetTemplate().GetMentalStates().addListener(new MapChangeListener<UUID, MentalState>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends MentalState> change)
            {
                if (change.wasAdded())
                {
                    mentalStates.AddContent(new MentalStateTemplateBox(tree.GetTemplate(), change.getValueAdded()));
                }
                else if (change.wasRemoved())
                {
                    mentalStates.RemoveContent(change.getKey());
                }
            }
        });

    }

    private void VariableInit(final CharacterTree tree)
    {
        AddButton(attributes, evt->
        {
            tree.GetTemplate().AddAttribute();
        });
        AddButton(perks, evt->
        {
            tree.GetTemplate().AddPerk();
        });
        AddButton(traits, evt->
        {
            tree.GetTemplate().AddTrait();
        });
        AddButton(descriptors, evt->
        {
            tree.GetTemplate().AddDescriptor();
        });
        AddButton(mentalStates, evt->
        {
            tree.GetTemplate().AddMentalState();
        });
    }


}
