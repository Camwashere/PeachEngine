package main.Module.Story.Layout.StoryRightBar.ParamBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Tools.InitHelp;

public class OutputParamRow extends ParamRowBase
{
    //private final OutputParamBox parent;
    private InputParameter<?> parameter;
    public OutputParamRow(final OutputParamBox paramBox, final InputParameter<?> param)
    {
        super(paramBox);
        //parent = paramBox;
        parameter = param;
        Init();

    }

    private void Init()
    {
        ValuesInit();
        ListenersInit();
        EventInit();
        getChildren().addAll(name, type, array, remove);
    }
    private void ValuesInit()
    {
        name.setText(parameter.GetName());
        type.getItems().addAll(ParamType.values());
        type.getSelectionModel().select(parameter.GetType());
        array.setSelected(parameter.IsArray());
    }
    private void ListenersInit()
    {
        name.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                parameter.SetName(t1);
            }
        });
        type.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ParamType>()
        {
            @Override
            public void changed(ObservableValue<? extends ParamType> observableValue, ParamType paramType, ParamType t1)
            {
                InputParameter<?> temp = InputParameter.CREATE(parent.GetOutput(), t1, array.isSelected());
                if (parent.GetOutput().ReplaceInputParameter(parameter, temp))
                {
                    parameter = temp;
                }
            }
        });
        array.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                InputParameter<?> temp = InputParameter.CREATE(parent.GetOutput(), type.getSelectionModel().getSelectedItem(), t1);
                if (parent.GetOutput().ReplaceInputParameter(parameter, temp))
                {
                    parameter = temp;
                }
            }
        });
    }
    private void EventInit()
    {
        remove.setOnMouseReleased(evt->
        {
            parent.GetOutput().RemoveInputParameter(parameter);
            parent.RemoveRow(this);
        });
    }

    public final InputParameter<?> GetParameter()
    {
        return parameter;
    }
}
