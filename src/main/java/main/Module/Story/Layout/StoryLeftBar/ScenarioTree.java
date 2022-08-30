package main.Module.Story.Layout.StoryLeftBar;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.scene.control.*;
import main.Module.Story.Scenario.Scenario;
import main.Module.Story.Scenario.ScenarioType;
import main.Module.Story.StoryModule;
import main.Tools.TrackMap;

import java.util.UUID;

public class ScenarioTree extends TreeView<ScenarioItem>
{
    private final StoryModule parent;
    protected final TrackMap<UUID, Scenario> scenarios;
    public ScenarioTree(final StoryModule storyMod)
    {
        parent = storyMod;
        scenarios = new TrackMap<UUID, Scenario>();
        SelfInit();
        CellFactoryInit();
        SelectionModelInit();
        ListenerInit();
    }

    private void ListenerInit()
    {
        scenarios.GetCurrentProp().addListener(new ChangeListener<UUID>()
        {
            @Override
            public void changed(ObservableValue<? extends UUID> observableValue, UUID UUID, UUID t1)
            {
                parent.GetCenter().LoadScenario(GetCurrentScenario());
                parent.GetRight().Update();
            }
        });
        
    }
    private void SelectionModelInit()
    {
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    private void SelfInit()
    {
        setRoot(ScenarioItem.CREATE("root"));
        setShowRoot(false);
        setFocusTraversable(false);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setEditable(true);
    }

    private void CellFactoryInit()
    {
        setCellFactory(new LeftCellFactory(this));
    }

    public ScenarioItem SearchTreeItem(final String find)
    {
        return SearchTreeItemHelp(getRoot(), find);
    }
    private ScenarioItem SearchTreeItemHelp(TreeItem<ScenarioItem> item, final String find)
    {
        if (item.getValue()==null)
        {
            return null;
        }
        if (item.getValue().GetName() == null)
        {
            return null;
        }
        //if(item.getValue().AsSearchString().equals(find)) return item.getValue(); // hit!
        // continue on the children:
        ScenarioItem result = null;
        for(TreeItem<ScenarioItem> child : item.getChildren())
        {
            result = SearchTreeItemHelp(child, find);
            if(result != null) return result; // hit!
        }
        //no hit:
        return null;
    }


    public void AddScenario()
    {
        AddScenario(new Scenario(parent));
    }
    public void AddEffect()
    {
        AddScenario(new Scenario(parent, ScenarioType.CHARACTER_EFFECT));
    }

    public final void AddScenario(final Scenario s)
    {
        if (!AddScenarioCheck(s))
        {
            return;
        }
        scenarios.put(s.GetID(), s);
        TreeItem<ScenarioItem> item = ScenarioItem.CREATE(s.GetName(), s.GetID());
        if (getSelectionModel().getSelectedItem() == null)
        {
            getRoot().getChildren().add(item);
        }
        else
        {
            if (getSelectionModel().getSelectedItem().getValue().IsDirectory())
            {
                getSelectionModel().getSelectedItem().getChildren().add(item);
            }
            else
            {
                getSelectionModel().getSelectedItem().getParent().getChildren().add(item);
            }
        }
        getSelectionModel().select(item);
        SetCurrentScenario(s.GetID());

    }
    protected boolean AddScenarioCheck(final Scenario s){return true;}
    public final void AddFolder()
    {
        TreeItem<ScenarioItem> item = ScenarioItem.CREATE("Folder");
        if (getSelectionModel().getSelectedItem() == null)
        {
            getRoot().getChildren().add(item);
        }
        else if (getSelectionModel().getSelectedItem().getValue().IsDirectory())
        {
            getSelectionModel().getSelectedItem().getChildren().add(item);
        }
        else
        {
            getSelectionModel().getSelectedItem().getParent().getChildren().add(item);
        }
        getSelectionModel().select(item);
    }
    public final void Rename()
    {

        getSelectionModel().getSelectedItem();
    }
    public final void LoadCurrentScenario()
    {
        if (getSelectionModel().getSelectedItem() == null)
        {
            return;
        }
        if (getSelectionModel().getSelectedItem().getValue() == null)
        {
            return;
        }
        if (getSelectionModel().getSelectedItem().getValue().IsDirectory())
        {
            return;
        }
        SetCurrentScenario(getSelectionModel().getSelectedItem().getValue().GetID());
    }
    public final void DeleteSelectedScenario()
    {
        TreeItem<ScenarioItem> i = getSelectionModel().getSelectedItem();
        if (! i.getValue().IsDirectory())
        {
            RemoveScenario(i.getValue().GetID());
        }
        if (getRoot().getChildren().size() > 1)
        {
            i.getParent().getChildren().remove(i);
        }
    }

    /**
     * Removes the scenario with a matching id.
     * If there is only one scenario left, this function does nothing.
     * If the currently selected scenario is being removed, finds the
     * next best option to select before removal.
     * @param s ID of the scenario to remove
     */
    public final void RemoveScenario(final UUID s)
    {
        if (parent.GetMainScenario().GetID() == s)
        {
            System.out.println("Cannot delete main scenario");
        }
        else if (GetCurrentScenario().GetID() == s)
        {
            for(final UUID i : scenarios.keySet())
            {
                if (i != s)
                {
                    SetCurrentScenario(i);
                    break;
                }
            }
            scenarios.remove(s);
        }
        else
        {
            scenarios.remove(s);
        }
    }

    public final void SetCurrentScenario(final UUID s){scenarios.SetCurrent(s);}
    public final Scenario GetCurrentScenario(){return scenarios.GetCurrent();}
    public final Scenario GetScenario(final UUID s){return scenarios.get(s);}
    public final SimpleObjectProperty<UUID> GetCurrentScenarioProp(){return scenarios.GetCurrentProp();}
    public final TrackMap<UUID, Scenario> GetScenarios(){return scenarios;}


    public final StoryModule GetParent(){return parent;}
}
