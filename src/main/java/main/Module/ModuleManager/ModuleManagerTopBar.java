package main.Module.ModuleManager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import main.Module.ModuleBase.ModuleID;
import main.Tools.StringHelp;

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
        ModInit();
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
