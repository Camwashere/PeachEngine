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
    private Splash splash;
    @Override
    public void start(Stage stage) throws IOException
    {
        SETTINGS = new GlobalSettings();
        splash = new Splash(stage);
        Debug.SET_DEBUG_LEVEL(1);
        stage.setTitle("Peach Engine");
        Scene scene = new Scene(splash, SETTINGS.GetResolution().x, SETTINGS.GetResolution().y);
        stage.setScene(scene);
        stage.show();


    }



    public static void main(String[] args)
    {
        launch();
    }
}