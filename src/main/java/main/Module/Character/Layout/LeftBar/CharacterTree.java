package main.Module.Character.Layout.LeftBar;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import main.Module.Character.Game.CharacterClass.CharacterClass;
import main.Module.Character.Game.CharacterManager.CharacterManager;
import main.Module.Character.CharacterModule;
import main.Module.Character.Game.CharacterTemplate.CharacterTemplate;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Module.Character.States.CharacterClassState;
import main.Module.Character.States.CharacterSpecialState;
import main.Module.Character.States.CharacterTemplateState;
import main.Tools.InitHelp;

import java.util.UUID;

public class CharacterTree extends TreeView<UUID>
{
    private final CharacterModule parent;
    private final CharacterLeftBar left;
    private final CharacterManager manager;
    private final SimpleObjectProperty<UUID> current;
    private final CharacterTemplateState templateState;
    private final CharacterSpecialState specialState;
    private final CharacterClassState classState;

    public CharacterTree(final CharacterLeftBar leftBar)
    {
        left = leftBar;
        parent = left.GetParent();
        manager = new CharacterManager(parent);
        current = new SimpleObjectProperty<UUID>();
        templateState = new CharacterTemplateState(this);
        specialState = new CharacterSpecialState(this);
        classState = new CharacterClassState(this);

        SelfInit();
        CellFactoryInit();
        ListenerInit();
        LoadTemplate();
    }
    private void SelfInit()
    {
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setRoot(CREATE(null));
        setShowRoot(false);
        setFocusTraversable(false);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setEditable(true);
        InitHelp.GrowInit(this);
    }
    private void CellFactoryInit() {setCellFactory(new CharacterTreeCellFactory(this));}
    private void ListenerInit()
    {
        current.addListener(new ChangeListener<UUID>()
        {
            @Override
            public void changed(ObservableValue<? extends UUID> observableValue, UUID number, UUID t1)
            {
                left.GetTemplateButton().setSelected(manager.IsTemplate(t1));

            }
        });
    }
    public final CharacterLeftBar GetLeft(){return left;}
    public final void AddCharacter()
    {
        StoryCharacter c = new StoryCharacter(GetTemplate());
        if (getSelectionModel().getSelectedItem() == null)
        {

        }
        else if (left.GetManager().IsClass(getSelectionModel().getSelectedItem().getValue()))
        {
            c.SetParent(left.GetManager().GetClasses().get(getSelectionModel().getSelectedItem().getValue()));
        }
        else
        {
            if (getSelectionModel().getSelectedItem().getParent()!=null)
            {
                c.SetParent(left.GetManager().GetClasses().get(getSelectionModel().getSelectedItem().getParent().getValue()));
            }
        }
        c.SetFirstName("New");
        c.SetLastName("Character");
        AddCharacter(c);
    }
    public final void AddCharacter(final StoryCharacter base)
    {
        manager.AddCharacter(base);
        TreeItem<UUID> item = CREATE(base.GetID());

        if (getSelectionModel().getSelectedItem() == null)
        {
            getRoot().getChildren().add(item);
        }
        else
        {
            if (left.GetManager().IsClass(getSelectionModel().getSelectedItem().getValue()))
            {
                getSelectionModel().getSelectedItem().getChildren().add(item);
            }
            else
            {
                if (getSelectionModel().getSelectedItem().getParent()!=null)
                {
                    getSelectionModel().getSelectedItem().getParent().getChildren().add(item);
                }
                else
                {
                    getRoot().getChildren().add(item);
                }

            }
        }
        getSelectionModel().select(item);
        Load(base.GetID());
    }
    public final void AddClass()
    {
        CharacterClass c = new CharacterClass(GetTemplate());
        if (getSelectionModel().getSelectedItem() == null)
        {
            c.SetParent(manager.GetTemplate());
        }
        else
        {
            if (left.GetManager().IsClass(getSelectionModel().getSelectedItem().getValue()))
            {
                c.SetParent(left.GetManager().GetClasses().get(getSelectionModel().getSelectedItem().getValue()));
            }
            else
            {
                if (getSelectionModel().getSelectedItem().getParent()!=null)
                {
                    c.SetParent(left.GetManager().GetClasses().get(getSelectionModel().getSelectedItem().getParent().getValue()));
                }
            }
        }
        c.SetName("New Class");
        AddClass(c);
    }
    public final void AddClass(final CharacterClass c)
    {
        manager.AddClass(c);
        TreeItem<UUID> item = CREATE(c.GetID());
        if (getSelectionModel().getSelectedItem() == null)
        {
            getRoot().getChildren().add(item);
        }
        else
        {
            if (left.GetManager().IsClass(getSelectionModel().getSelectedItem().getValue()))
            {
                getSelectionModel().getSelectedItem().getChildren().add(item);
            }
            else
            {
                if (getSelectionModel().getSelectedItem().getParent()!=null)
                {
                    getSelectionModel().getSelectedItem().getParent().getChildren().add(item);
                }
                else
                {
                    getRoot().getChildren().add(item);
                }

            }
        }
        getSelectionModel().select(item);
        Load(c.GetID());
    }
    public final void Delete(final UUID id)
    {
        manager.GetCharacters().remove(id);
        manager.GetClasses().remove(id);
    }
    public final void Load(final UUID id)
    {
        if (manager.IsCharacter(id))
        {
            specialState.Load(manager.GetCharacter(id));
            parent.setCenter(specialState);
            current.set(id);
        }
        else if (manager.IsTemplate(id))
        {
            LoadTemplate();
        }
        else if (manager.IsClass(id))
        {
            classState.Load(manager.GetClass(id));
            parent.setCenter(classState);
            current.set(id);
        }
        else
        {
            LoadTemplate();
        }

    }
    public final void LoadTemplate()
    {
        parent.setCenter(templateState);
        getSelectionModel().clearSelection();
        current.set(templateState.GetID());
    }

    public final CharacterModule GetParent(){return parent;}
    public final CharacterManager GetManager(){return manager;}
    public final CharacterTemplate GetTemplate(){return manager.GetTemplate();}
    public final UUID GetCurrent(){return current.get();}


    public static TreeItem<UUID> CREATE(final UUID l){return new TreeItem<UUID>(l);}



}
