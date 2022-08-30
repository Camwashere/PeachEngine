package main.Module.ModuleManager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import main.Debug.Debug;
import main.Main;
import main.Module.ModuleBase.ModuleID;
import main.Project.Project;
import main.Tools.StringHelp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleManagerTopBar extends MenuBar
{
    private final ModuleManager parent;
    private final Menu file;
    private final Menu edit;
    private final Menu mod;
    private final Menu view;
    private final Menu help;
    public ModuleManagerTopBar(final ModuleManager manager)
    {
        parent = manager;
        file = new Menu("File");
        edit = new Menu("Edit");
        mod = new Menu("Module");
        view = new Menu("View");
        help = new Menu("Help");
        getMenus().addAll(file, edit, mod, view, help);
        FileInit();
        ModInit();
    }
    private void FileInit()
    {
        MenuItem save = new MenuItem("Save");
        save.setOnAction(evt->
        {
            try
            {
                FileChooser chooser = Project.FILE_CHOOSER();
                chooser.setInitialFileName(parent.GetParent().GetName());
                File file = chooser.showSaveDialog(null);

                if (file != null)
                {
                    boolean fileGo = false;
                    if (! file.exists())
                    {
                        fileGo = file.createNewFile();
                    }
                    if (file.exists())
                    {
                        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                        out.writeObject(parent.GetParent().AsData());
                        out.close();
                    }
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        });
        MenuItem load = new MenuItem("Load");
        load.setOnAction(evt->
        {
            FileChooser chooser = Project.FILE_CHOOSER();
            File file = chooser.showOpenDialog(null);
            Debug.println(file);
        });
        file.getItems().add(save);
        file.getItems().add(load);
    }
    private void ModInit()
    {
        List<CheckMenuItem> list = new ArrayList<CheckMenuItem>();
        for (final ModuleID name : parent.GetModuleKeys())
        {
            CheckMenuItem item = new CheckMenuItem(StringHelp.EnumFormat(name));
            item.setOnAction(evt->
            {
                parent.SetModule(name);
            });
            list.add(item);
        }
        parent.GetCurrentProp().addListener(new ChangeListener<ModuleID>()
        {
            @Override
            public void changed(ObservableValue<? extends ModuleID> observableValue, ModuleID moduleID, ModuleID t1)
            {
                for (CheckMenuItem item : list)
                {
                    item.setSelected(item.getText().equals(StringHelp.EnumFormat(t1)));
                }
            }
        });
        mod.getItems().addAll(list);
    }
}
