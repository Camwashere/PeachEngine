package main.Project;

import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import main.Data.ProjectData;
import main.Data.StoryData;
import main.Debug.Debug;
import main.Main;
import main.Module.ModuleBase.ModuleID;
import main.Module.ModuleManager.ModuleManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Project extends BorderPane
{
    private final String name;
    private final ProjectTime time;
    private final ModuleManager manager;

    public static FileChooser FILE_CHOOSER()
    {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(Main.SETTINGS.GetSavePath());
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PEACH Files (*.peach)", "*.peach");
        chooser.getExtensionFilters().add(filter);
        chooser.setSelectedExtensionFilter(filter);
        return chooser;
    }


    public Project(final String projectName)
    {
        name = projectName;
        time = new ProjectTime();
        manager = new ModuleManager(this);
        setCenter(manager);
        manager.SetModule(ModuleID.STORY);
    }

    public Project(final File projectFile)
    {
        ProjectData data = null;
        try
        {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(projectFile));
            data = (ProjectData)in.readObject();
            in.close();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        if (data != null)
        {
            name = data.name();
            time = data.time();
            manager = new ModuleManager(this, data.moduleManagerData());
            setCenter(manager);
            manager.SetModule(ModuleID.STORY);
        }
        else
        {
            name = null;
            time = null;
            manager = null;
            Debug.println("FAILED TO OPEN PROJECT FILE: " + projectFile.getName());
        }

    }


    public final String GetName(){return name;}
    public final ProjectTime GetTime(){return time;}
    public final ModuleManager GetManager(){return manager;}
    public final boolean IsFucked(){return manager==null;}

    public final ProjectData AsData(){return new ProjectData(name, time, manager.AsData());}


}
