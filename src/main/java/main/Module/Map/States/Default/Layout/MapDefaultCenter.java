package main.Module.Map.States.Default.Layout;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.Debug.Debug;
import main.Module.Map.States.Default.Grid.GridView;
import main.Module.Map.MapModule;
import main.Module.Map.States.Default.MapStateDefault;

import java.util.ArrayList;
import java.util.Stack;

public class MapDefaultCenter extends TabPane
{
    private static final int maxSize = 10;
    private final MapStateDefault parent;
    private final Tab global;

    public MapDefaultCenter(final MapStateDefault stateParent)
    {
        parent = stateParent;
        global = new Tab(parent.GetGlobal().GetName());
        global.setClosable(false);
        global.setContent(parent.GetGlobal());
        global.textProperty().bind(parent.GetGlobal().GetNameProp());
        getTabs().add(global);
        widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                CalculateTabSize();
            }
        });
        getTabs().addListener(new ListChangeListener<Tab>()
        {
            @Override
            public void onChanged(Change<? extends Tab> change)
            {
                if (change.next())
                {
                    CalculateTabSize();
                }
            }
        });


    }
    private void CalculateTabSize()
    {
        int count = getTabs().size();
        double size = getWidth()/(count);
        setTabMinWidth(size-count);
        setTabMaxWidth(size-count);
    }
    public final void RemoveTab(final GridView grid)
    {
        Tab check = null;
        for (final Tab t : getTabs())
        {
            if (grid == t.getContent())
            {
                check = t;
                break;
            }
        }
        if (check != null)
        {
            getTabs().remove(check);
        }
    }
    public final void SetMostRecent(final GridView grid)
    {
        Tab check = null;
        for (final Tab t : getTabs())
        {
            if (grid == t.getContent())
            {
                check = t;
                break;
            }
        }
        if (check == null)
        {
            final Tab first = new Tab(grid.GetName());
            first.setClosable(true);
            first.setOnClosed(evt->
            {
                getTabs().remove(first);
            });
            first.setContent(grid);
            first.textProperty().bind(grid.GetNameProp());
            getTabs().add(1, first);
        }
        else
        {
            getTabs().add(1, check);
        }
        if (getTabs().size() >= maxSize)
        {
            getTabs().remove(getTabs().size()-1);
        }
    }
    public final void Load(final GridView grid)
    {
        SetMostRecent(grid);
        if (grid.IsGlobal())
        {
            getSelectionModel().selectFirst();
        }
        else
        {
            getSelectionModel().select(1);
        }
    }



    public final MapStateDefault GetParent(){return parent;}
}
