package main.Module.Character.Layout.Box;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Debug.Debug;
import main.Module.Character.Game.CharacterBase;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Module.Character.Game.Variables.CharacterVariableBase;
import main.Module.Character.Game.Variables.Perk;
import main.Module.Character.Layout.Box.CharacterBox.VariableCharacterBox;
import main.Module.Character.Layout.Box.TemplateBox.VariableTemplateBox;
import main.Module.Character.Layout.Box.VariableBoxBase;
import main.Module.Character.States.CharacterStateBase;
import main.Tools.InitHelp;
import main.Tools.LinkedMapProperty;

import java.util.ArrayList;

public class VerticalColumn extends VBox
{
    private final CharacterStateBase parent;
    private final Label name;
    private final ScrollPane pane;
    private final VBox content;
    private final LinkedMapProperty<Integer, VariableBoxBase> variables;

    public VerticalColumn(final CharacterStateBase base, final String str)
    {
        parent = base;
        name = new Label(str);
        pane = new ScrollPane();
        content = new VBox();
        variables = new LinkedMapProperty<Integer, VariableBoxBase>();
        InitHelp.LabelInit(name);
        setVgrow(name, Priority.SOMETIMES);
        setVgrow(pane, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
        InitHelp.GrowInit(content);
        HBox.setHgrow(content, Priority.SOMETIMES);
        pane.setContent(content);
        getChildren().add(name);
        getChildren().add(pane);
        HBox.setHgrow(this, Priority.SOMETIMES);
        pane.setFitToWidth(true);

        variables.addListener(new MapChangeListener<Integer, VariableBoxBase>()
        {
            @Override
            public void onChanged(Change<? extends Integer, ? extends VariableBoxBase> change)
            {
                if (change.wasRemoved())
                {
                    content.getChildren().remove(change.getValueRemoved());
                }
                if (change.wasAdded())
                {
                    content.getChildren().add(change.getValueAdded());
                }

            }
        });


    }
    private void PreLoad(final CharacterBase character)
    {
        for (final Perk p : character.GetManager().GetTemplate().GetPerks().values())
        {
            if (!character.GetPerks().containsKey(p.GetID()))
            {
                content.getChildren().remove(variables.get(p.GetID()));
            }
            else
            {
                if (!content.getChildren().contains(variables.get(p.GetID())))
                {
                    if (variables.get(p.GetID())!= null)
                    {
                        content.getChildren().add(variables.get(p.GetID()));
                    }
                }
            }
        }
    }
    public final void Load(final CharacterBase base)
    {
        PreLoad(base);
        for (final VariableBoxBase box : variables.values())
        {
            if (box instanceof VariableCharacterBox b)
            {
                b.LoadCharacter(base);
            }
        }
    }
    /*public final void Load(final StoryCharacter character)
    {
        PreLoad(character);
        for (final VariableBoxBase base : variables.values())
        {
            if (base instanceof VariableCharacterBox box)
            {
                box.LoadCharacter(character);
            }
        }
    }*/
    public final void Unload()
    {
        for (final VariableBoxBase base : variables.values())
        {
            if (base instanceof VariableCharacterBox box)
            {
                box.Unload();
            }
        }
    }


    public final void AddChild(final Node node)
    {
        int index = getChildren().indexOf(pane);
        getChildren().add(index, node);
    }
    public final void CollapseAll()
    {
        for (Node n : content.getChildren())
        {
            if (n instanceof TitledPane p)
            {
                p.setExpanded(false);
            }
        }
    }
    public final void ExpandAll()
    {
        for (Node n : content.getChildren())
        {
            if (n instanceof TitledPane p)
            {
                p.setExpanded(true);
            }
        }
    }
    public final void AddContent(final VariableBoxBase variable)
    {
        variables.put(variable.GetID(), variable);
    }
    public final void ClearContent()
    {
        variables.clear();
    }
    public final void RemoveContent(final int id)
    {
        variables.remove(id);
    }
}
