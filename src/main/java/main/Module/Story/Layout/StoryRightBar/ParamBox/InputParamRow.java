package main.Module.Story.Layout.StoryRightBar.ParamBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;

public class InputParamRow extends ParamRowBase
{
    private OutputParameter<?> parameter;
    public InputParamRow(final InputParamBox inputBox, final OutputParameter<?> param)
    {
        super(inputBox);
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
                OutputParameter<?> temp = new OutputParameter<>(parent.GetInput(), t1, array.isSelected());
                if (parent.GetInput().ReplaceOutputParameter(parameter, temp))
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
                OutputParameter<?> temp = new OutputParameter<>(parent.GetInput(), type.getValue(), t1);
                if (parent.GetInput().ReplaceOutputParameter(parameter, temp))
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
            parent.GetInput().RemoveOutputParameter(parameter);
            parent.RemoveRow(this);
        });
    }
}
