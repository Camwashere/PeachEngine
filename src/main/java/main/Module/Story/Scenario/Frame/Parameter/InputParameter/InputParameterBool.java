package main.Module.Story.Scenario.Frame.Parameter.InputParameter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Tools.InitHelp;

public class InputParameterBool extends InputParameter<Boolean>
{
    private final ComboBox<Boolean> box;
    public InputParameterBool(final BaseFrame parentFrame)
    {
        super(parentFrame, ParamType.BOOL, false);
        box = new ComboBox<Boolean>();
        Init();
    }
    public InputParameterBool(final BaseFrame parentFrame, final String paramName)
    {
        super(parentFrame, ParamType.BOOL, false);
        SetName(paramName);
        box = new ComboBox<Boolean>();
        Init();
    }

    private void Init()
    {
        box.getItems().add(true);
        box.getItems().add(false);
        box.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                SetValue(t1);
            }
        });
        box.getSelectionModel().selectFirst();
        InitHelp.NodeInit(box);
        if (! IsArray())
        {
            getChildren().add(box);
        }
    }

    @Override
    protected Node GetNode(){return box;}

    @Override
    protected Boolean GetNodeValue()
    {
        return box.getSelectionModel().getSelectedItem();
    }
}
