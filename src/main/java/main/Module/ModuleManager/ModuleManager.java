package main.Module.ModuleManager;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.BorderPane;
import main.Module.Item.ItemModule;
import main.Module.Map.MapModule;
import main.Module.ModuleBase.BaseModule;
import main.Module.Character.CharacterModule;
import main.Module.ModuleBase.ModuleID;
import main.Module.Play.PlayModule;
import main.Module.Relationship.RelationshipModule;
import main.Module.Story.StoryModule;
import main.Tools.TrackMap;

import java.util.Set;

/**
 * Handles loading/unloading of the various modules
 * and aids in communication between them when needed.
 */
public class ModuleManager extends BorderPane
{
    private final TrackMap<ModuleID, BaseModule> modules;
    private final StoryModule story;
    private final CharacterModule character;
    private final RelationshipModule relationship;
    private final ItemModule item;
    private final MapModule map;
    private final PlayModule play;
    private final ModuleManagerTopBar top;

    public ModuleManager()
    {
        modules = new TrackMap<>();
        story = new StoryModule(this);
        character = new CharacterModule(this);
        relationship = new RelationshipModule(this);
        item = new ItemModule(this);
        map = new MapModule(this);
        play = new PlayModule(this);
        AddModule(story);
        AddModule(character);
        AddModule(relationship);
        AddModule(item);
        AddModule(map);
        AddModule(play);
        ListenerInit();
        top = new ModuleManagerTopBar(this);
        setTop(top);
    }

    private void ListenerInit()
    {
        modules.GetCurrentProp().addListener(new ChangeListener<ModuleID>()
        {
            @Override
            public void changed(ObservableValue<? extends ModuleID> observableValue, ModuleID moduleID, ModuleID t1)
            {
                setCenter(modules.get(t1));

            }
        });
    }

    private void AddModule(final BaseModule mod) {modules.put(mod.GetID(), mod);}
    public final void SetModule(final ModuleID mod){modules.SetCurrent(mod);}
    public final Set<ModuleID>  GetModuleKeys(){return modules.keySet();}
    public final SimpleObjectProperty<ModuleID> GetCurrentProp(){return modules.GetCurrentProp();}
    public final StoryModule GetStoryModule(){return story;}
    public final CharacterModule GetCharacterModule(){return character;}
    public final RelationshipModule GetRelationshipModule(){return relationship;}
    public final ItemModule GetItemModule(){return item;}
    public final PlayModule GetPlayModule(){return play;}


}
