package main.Module.Story.Layout;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import main.Debug.Debug;
import main.Module.ModuleBase.ModuleID;
import main.Module.Story.StoryModule;

public class StoryTopBar extends MenuBar
{
    private final StoryModule parent;
    public StoryTopBar(final StoryModule modParent)
    {
        parent = modParent;
        Menu play = new Menu("Play");
        MenuItem quickPlay = new MenuItem("Quick Play");
        quickPlay.setOnAction(evt->
        {
            parent.GetParent().SetModule(ModuleID.PLAY);
            parent.GetParent().GetPlayModule().Test(parent.GetCurrentScenario());
        });
        play.getItems().add(quickPlay);
        getMenus().add(play);
    }
}
