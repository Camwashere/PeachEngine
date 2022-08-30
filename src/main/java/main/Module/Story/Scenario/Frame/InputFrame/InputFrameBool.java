package main.Module.Story.Scenario.Frame.InputFrame;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import main.Data.Frame.BaseFrameData;
import main.Data.Frame.InputFrameData;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Scenario;
import main.Tools.InitHelp;

public class InputFrameBool extends InputFrameBase<Boolean>
{
    private final CheckBox check = new CheckBox();
    public InputFrameBool(final Scenario s)
    {
        super(s, ParamType.BOOL);
        Init();
    }
    public InputFrameBool(final Scenario s, final InputFrameData data)
    {
        super(s, data);
        Init();
    }
    private void Init()
    {
        InitHelp.NodeInit(check);
        check.setText("False");
        SetOutputValue(false);
        check.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                SetOutputValue(t1);
                if (t1)
                {
                    check.setText("True");

                }
                else
                {
                    check.setText("False");
                }
            }
        });
    }

    @Override
    public Control GetField()
    {
        return check;
    }

    @Override
    public boolean IsReady()
    {
        return true;
    }
}
