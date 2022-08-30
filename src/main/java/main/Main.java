package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Debug.Debug;
import main.Module.Map.Game.Time.GameTime;
import main.Module.ModuleBase.ModuleID;
import main.Module.ModuleManager.ModuleManager;
import main.Settings.GlobalSettings;
import main.Tools.StringHelp;

import java.io.*;

public class Main extends Application
{
    public static GlobalSettings SETTINGS;
    @Override
    public void start(Stage stage) throws IOException
    {
        SETTINGS = new GlobalSettings();
        Debug.SET_DEBUG_LEVEL(1);
        stage.setTitle("Peach Engine");
        ModuleManager moduleManager = new ModuleManager();
        moduleManager.GetCurrentProp().addListener(new ChangeListener<ModuleID>()
        {
            @Override
            public void changed(ObservableValue<? extends ModuleID> observableValue, ModuleID moduleID, ModuleID t1)
            {
                stage.setTitle("Peach Engine - " + StringHelp.EnumFormat(t1));
            }
        });

        moduleManager.SetModule(ModuleID.MAP);
        Scene scene = new Scene(moduleManager, SETTINGS.GetResolution().x, SETTINGS.GetResolution().y);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}