package main.Module.Story.Layout.StoryLeftBar;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import main.Module.Story.Scenario.Scenario;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class ScenarioItem implements Serializable
{
    private String name;
    private final UUID id;

    public ScenarioItem(final String nameStr)
    {
        name = nameStr;

        id = null;
    }
    public ScenarioItem(final String nameStr, final UUID s)
    {
        name = nameStr;
        id = s;
    }

    public static TreeItem<ScenarioItem> CREATE(final String nameStr){return new TreeItem<ScenarioItem>(new ScenarioItem(nameStr));}
    public static TreeItem<ScenarioItem> CREATE(final String nameStr, final UUID s){return new TreeItem<ScenarioItem>(new ScenarioItem(nameStr, s));}

    public final void SetName(final String nameStr){name = nameStr;}

    public final String GetName(){return name;}
    public final UUID GetID(){return id;}
    //public final Scenario GetScenario(){return scenario;}

    public final boolean IsDirectory(){return id == null;}
    public final boolean HasScenario(){return id != null;}


    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ScenarioItem other)
        {
            return Objects.equals(id, other.id) && Objects.equals(name, other.name);
        }
        return false;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
