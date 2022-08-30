package main.Module.Map.States.Default.Grid.Tool;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import main.Module.Map.States.Default.Grid.Grid;
import main.Tools.InitHelp;
import main.Tools.StringHelp;

public abstract class GridToolBase extends HBox implements EventHandler<InputEvent>
{
    protected final Grid parent;
    protected final GridToolType type;
    protected final Label name;
    protected Node node;
    protected final ComboBox<GridToolMode> modes;
    protected final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);
    protected GridToolBase(final Grid parentGrid, final GridToolType toolType)
    {
        parent = parentGrid;
        type = toolType;
        name = new Label(StringHelp.EnumFormat(type));
        modes = new ComboBox<GridToolMode>();
        ModesInit();
        SelfInit();
        ListenerInit();
        EventInit();
    }
    private void EventInit()
    {
        modes.setOnMouseClicked(evt->
        {
            Select();
        });
        setOnMouseClicked(evt->
        {
            Select();
        });
    }
    private void SelfInit()
    {
        setBorder(InitHelp.Border(Color.WHITE, 1));
        InitHelp.LabelInit(name);
        InitHelp.NodeInit(modes);
        getChildren().addAll(name, modes);
    }
    private void ListenerInit()
    {
        selected.addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1)
                {
                    setBorder(InitHelp.Border(Color.LIMEGREEN, 1));
                }
                else
                {
                    setBorder(InitHelp.Border(Color.WHITE, 1));
                }
            }
        });
    }
    private void ModesInit()
    {
        modes.setCellFactory(new Callback<ListView<GridToolMode>, ListCell<GridToolMode>>()
        {
            @Override
            public ListCell<GridToolMode> call(ListView<GridToolMode> gridToolModeListView)
            {
                return CreateModesCell();
            }
        });
        modes.setButtonCell(CreateModesCell());
    }
    private ListCell<GridToolMode> CreateModesCell()
    {
        return new ListCell<GridToolMode>()
        {
            @Override
            protected void updateItem(GridToolMode item, boolean empty)
            {
                super.updateItem(item, empty);
                if (item == null || empty)
                {
                    setText(null);
                }
                else
                {
                    setText(item.GetName());
                }
            }
        };
    }
    public final void AddMode(final GridToolMode mode)
    {
        boolean empty = modes.getItems().isEmpty();
        modes.getItems().add(mode);
        if (empty)
        {
            modes.setValue(mode);
        }
    }

    protected final void SetNode(final Node n)
    {
        getChildren().add(1, n);
    }


    public final void Select(){selected.set(true);}
    public final void Select(boolean bool){selected.set(bool);}
    public final void Deselect(){selected.set(false);}

    public final boolean IsSelected(){return selected.get();}
    public final GridToolMode GetCurrentMode(){return modes.getValue();}
    public final GridToolType GetType(){return type;}
    public final String GetName(){return StringHelp.EnumFormat(type);}
    public final Label GetLabel(){return name;}
    public final ComboBox<GridToolMode> GetComboBox(){return modes;}
    public final Grid GetParent(){return parent;}
    public final Node GetNode(){return node;}
    public final ObjectProperty<GridToolMode> GetCurrentModeProp(){return modes.valueProperty();}
    public final SimpleBooleanProperty GetSelectedProp(){return selected;}

    @Override
    public void handle(InputEvent inputEvent)
    {
        GetCurrentMode().Handle(inputEvent);
    }
}
