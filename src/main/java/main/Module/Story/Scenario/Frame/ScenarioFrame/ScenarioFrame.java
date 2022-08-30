package main.Module.Story.Scenario.Frame.ScenarioFrame;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.Data.Frame.ScenarioFrameData;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.FrameType;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;
import main.Module.Story.Scenario.Scenario;

import java.util.Objects;

public class ScenarioFrame extends BaseFrame
{
    private final Scenario ref;
    public ScenarioFrame(final Scenario s, final Scenario reference)
    {
        super(s, FrameType.SCENARIO);
        ref = reference;
        Init();

    }

    public ScenarioFrame(final ScenarioFrame other)
    {
        super(other.parent, FrameType.SCENARIO);
        ref = other.ref;
        Init();

    }

    public ScenarioFrame(final Scenario s, final ScenarioFrameData data)
    {
        super(s, data.baseData());
        ref = parent.GetParent().GetScenario(data.ref());
        Init();
    }
    private void Init()
    {
        name.textProperty().bind(ref.GetNameProp());
        Construct();
        ListenerInit();
    }

    private void Construct()
    {
        ClearParameters();
        InputConstruct();
        OutputConstruct();
    }
    private void ListenerInit()
    {
        ref.GetInputFrame().GetOutputParams().sizeProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                Construct();
            }
        });
        ref.GetOutputFrame().GetInputParams().sizeProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                Construct();
            }
        });
    }
    private void InputConstruct()
    {
        for (OutputParameter<?> in : ref.GetInputFrame().GetOutputParams())
        {
            AddInputParameter(in.GetType(), in.IsArray(), in.GetName());
        }

    }
    private void OutputConstruct()
    {
        for (InputParameter<?> out : ref.GetOutputFrame().GetInputParams())
        {
            AddOutputParameter(out.GetType(), out.IsArray(), out.GetName());
        }
    }

    public final void RemoveInputParameter(final OutputParameter<?> out)
    {
        InputParameter<?> choice = null;
        for (InputParameter<?> p : inputParams)
        {
            if (p.GetType() == out.GetType() && p.IsArray() == out.IsArray() && Objects.equals(p.GetName(), out.GetName()))
            {
                choice = p;
            }
        }
        if (choice != null)
        {
            RemoveInputParameter(choice);

        }
    }

    public final Scenario GetReference(){return ref;}

    @Override
    protected void ContextMenuInit()
    {
        MenuItem goTo = new MenuItem("Go To");
        goTo.setOnAction(evt->
        {
            parent.GetParent().GetScenarioTree().SetCurrentScenario(ref.GetID());
        });
        contextMenu.getItems().add(goTo);

    }

    @Override
    protected void StyleInit()
    {
        setFocusTraversable(false);
        Background background = new Background(new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY));
        setBackground(background);
        Border b = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        setBorder(b);
    }

    public final ScenarioFrameData AsData(){return new ScenarioFrameData(ref.GetID(), AsBaseData());}
}
