package main.Module.Map;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import main.Module.Map.States.MapStateBase;
import main.Module.Map.States.MapStateType;
import main.Tools.InitHelp;
import main.Tools.LinkedMapProperty;
import main.Tools.StringHelp;
import main.Tools.ValueButton;

public class MapModuleTopBar extends HBox
{
    private final MapModule parent;
    private final LinkedMapProperty<MapStateType, ValueButton<MapStateBase>> buttons = new LinkedMapProperty<>();
    private final SimpleObjectProperty<MapStateType> current = new SimpleObjectProperty<>();
    private final ToggleGroup toggleGroup;

    public MapModuleTopBar(final MapModule parentMod)
    {
        parent = parentMod;
        toggleGroup = new ToggleGroup();
        current.addListener(new ChangeListener<MapStateType>()
        {
            @Override
            public void changed(ObservableValue<? extends MapStateType> observableValue, MapStateType mapStateType, MapStateType t1)
            {
                ValueButton<MapStateBase> button = buttons.get(t1);
                button.setSelected(true);
                parent.setCenter(button.GetValue());
            }
        });
    }



    public final void AddState(final MapStateBase state)
    {
        ValueButton<MapStateBase> button = new ValueButton<>(StringHelp.EnumFormat(state.GetType()));
        InitHelp.ButtonInit(button);
        button.setOnAction(evt->
        {
            SetState(state.GetType());
        });
        button.SetValue(state);
        buttons.put(state.GetType(), button);
        toggleGroup.getToggles().add(button);
        getChildren().add(button);
    }

    public final void SetState(final MapStateType type)
    {
        current.set(type);

    }
}
