package main.Module.Play;

import main.Debug.Debug;
import main.Module.ModuleBase.BaseModule;
import main.Module.ModuleBase.ModuleID;
import main.Module.ModuleManager.ModuleManager;
import main.Module.Story.Scenario.Scenario;

public class PlayModule extends BaseModule
{
    private final PlayCenter center;
    public PlayModule(final ModuleManager parentManager)
    {
        super(ModuleID.PLAY, parentManager);
        center = new PlayCenter(this);
        setCenter(center);

    }

    public final void Test(final Scenario s)
    {
        Debug.println("Testing: " + s.GetName());
        center.LoadScenario(s);
    }
}
