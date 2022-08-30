package main.Module.Story;

import javafx.beans.property.SimpleObjectProperty;
import main.Data.ScenarioData;
import main.Data.StoryData;
import main.Debug.Debug;
import main.Module.ModuleBase.BaseModule;
import main.Module.ModuleBase.ModuleID;
import main.Module.ModuleManager.ModuleManager;
import main.Module.Story.Layout.StoryCenter;
import main.Module.Story.Layout.StoryLeftBar.ScenarioTree;
import main.Module.Story.Layout.StoryLeftBar.StoryLeftBar;
import main.Module.Story.Layout.StoryRightBar.StoryRightBar;
import main.Module.Story.Layout.StoryTopBar;
import main.Module.Story.Scenario.Scenario;
import main.Module.Story.Scenario.ScenarioType;
import main.Tools.TrackMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Primary module for creating stories. Handles the logic/scripting,
 * text, choices, and flow of the story while being capable of
 * implementing aspects of all other modules. This class serves
 * primarily as a bridge between the different scenarios the
 * user creates, and it's layout properties.
 */
public class StoryModule extends BaseModule
{
    private final StoryLeftBar left;
    private final StoryCenter center;
    private final StoryRightBar right;
    private final StoryTopBar top;
    private final Scenario main;
    public StoryModule(final ModuleManager modManager)
    {
        super(ModuleID.STORY, modManager);
        left = new StoryLeftBar(this);
        right = new StoryRightBar(this);
        center = new StoryCenter(this);
        top = new StoryTopBar(this);
        main = new Scenario(this, ScenarioType.MAIN);
        left.GetScenarioTree().AddScenario(main);
        setLeft(left);
        setCenter(center);
        setRight(right);
        setTop(top);
    }
    public StoryModule(final ModuleManager modManager, final StoryData data)
    {
        super(ModuleID.STORY, modManager);
        left = new StoryLeftBar(this);
        right = new StoryRightBar(this);
        center = new StoryCenter(this);
        top = new StoryTopBar(this);

        main = new Scenario(this, data.mainData());
        left.GetScenarioTree().AddScenario(main);
        setLeft(left);
        setCenter(center);
        setRight(right);
        setTop(top);
    }




    public final void SetCurrentScenario(final UUID s){left.GetScenarioTree().SetCurrentScenario(s);}
    public final Scenario GetCurrentScenario(){return left.GetScenarioTree().GetCurrentScenario();}
    public final Scenario GetMainScenario(){return main;}
    public final Scenario GetScenario(final UUID s){return left.GetScenarioTree().GetScenario(s);}
    public final TrackMap<UUID, Scenario> GetScenarios(){return left.GetScenarioTree().GetScenarios();}
    public final SimpleObjectProperty<UUID> GetCurrentScenarioProp(){return left.GetScenarioTree().GetCurrentScenarioProp();}
    public final ScenarioTree GetScenarioTree(){return left.GetScenarioTree();}
    public final StoryCenter GetCenter(){return center;}
    public final StoryRightBar GetRight(){return right;}

    public final StoryData AsData()
    {
        List<ScenarioData> scenarioData = new ArrayList<>();
        for (final Scenario s : GetScenarios().values())
        {
            scenarioData.add(s.AsData());
        }
        return new StoryData(main.AsData(), scenarioData);
    }
}
