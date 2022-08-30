package main.Module.Story.Scenario.Frame.InputFrame;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Scenario;
import main.Tools.InitHelp;

public class InputFrameBool extends InputFrameBase<Boolean>
{
    private final CheckBox check;
    public InputFrameBool(final Scenario s)
    {
        super(s, ParamType.BOOL);
        check = new CheckBox();
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
