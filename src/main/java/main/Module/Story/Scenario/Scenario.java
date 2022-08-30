package main.Module.Story.Scenario;

import javafx.application.Platform;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;
import main.Data.DataBase;
import main.Data.SaveObject;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Frame.ScenarioFrame.ScenarioInputFrame;
import main.Module.Story.Scenario.Frame.ScenarioFrame.ScenarioOutputFrame;
import main.Module.Story.Scenario.ScenarioMenu.ScenarioMenu;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParameterConnector;
import main.Module.Story.StoryModule;
import main.Tools.StringHelp;
import main.Tools.TrackMap;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Contains the logic/scripting, text, variables, and general
 * story content needed for telling the visual story. After finishing
 * the logic and text, each scenario is loaded individually into play
 * mode for the player to enjoy. Scenarios can be linked together through
 * scripting, triggered through location/time events in map mode, or
 * through dynamic dialog events triggering. Scenario is also used for
 * creating custom functions such as determining how changes to
 * attributes are calculated.
 */
public class Scenario extends Pane implements EventHandler<InputEvent>, SaveObject
{
    private static final double PREF_SIZE = 9999999;



    private final SimpleStringProperty name;
    private final UUID id;
    private final StoryModule parent;
    private final TrackMap<ScenarioState, EventHandler<InputEvent>> states;
    private final SimpleMapProperty<UUID, BaseFrame> frames;
    private final ScenarioInputFrame input;
    private final ScenarioOutputFrame output;
    private final SimpleObjectProperty<ParameterConnector<?>> activeConnector;
    private final ScenarioMenu menu;
    private final ScenarioType type;

    public Scenario(final StoryModule storyMod)
    {
        parent = storyMod;
        id = UUID.randomUUID();
        name = new SimpleStringProperty(String.format("Scenario: %s", String.valueOf(id)));
        type = ScenarioType.STANDARD;
        states = new TrackMap<ScenarioState, EventHandler<InputEvent>>();
        frames = new SimpleMapProperty<>(FXCollections.observableHashMap());
        input = new ScenarioInputFrame(this);
        output = new ScenarioOutputFrame(this);
        activeConnector = new SimpleObjectProperty<ParameterConnector<?>>();
        menu = new ScenarioMenu(this);
        EventInit();
        ListenerInit();
        SelfInit();
        TypeInit();
    }

    public Scenario(final StoryModule storyMod, final ScenarioType scenarioType)
    {
        parent = storyMod;
        id = UUID.randomUUID();
        type = scenarioType;
        name = new SimpleStringProperty(StringHelp.EnumFormat(type));
        states = new TrackMap<ScenarioState, EventHandler<InputEvent>>();
        frames = new SimpleMapProperty<>(FXCollections.observableHashMap());
        input = new ScenarioInputFrame(this);
        output = new ScenarioOutputFrame(this);
        activeConnector = new SimpleObjectProperty<ParameterConnector<?>>();
        menu = new ScenarioMenu(this);
        EventInit();
        ListenerInit();
        SelfInit();
        TypeInit();
    }

    private void TypeInit()
    {
        switch (type)
        {
            case MAIN:
                RemoveFrame(output.GetID());
                break;
            case STANDARD:
                SetName(String.format("Scenario: %s", String.valueOf(id)));
                //GetInputFrame().AddOutputParameter(ParamType.FLOW);
                GetOutputFrame().AddInputParameter(ParamType.FLOW);
                break;
            case CHARACTER_EFFECT:
                //GetInputFrame().AddOutputParameter(ParamType.FLOW, "Input");
                GetInputFrame().AddOutputParameter(ParamType.CHARACTER, "Character");
                GetOutputFrame().AddInputParameter(ParamType.FLOW);
                break;
        }
    }

    private void SelfInit()
    {
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setFocusTraversable(false);
        setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        setMinSize(PREF_SIZE, PREF_SIZE);
        AddFrame(input, 20, 250);
        AddFrame(output, 500, 250);
    }
    private void ListenerInit()
    {
       menu.showingProperty().addListener(new ChangeListener<Boolean>()
       {
           @Override
           public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
           {
               if (t1)
               {

               }
               else
               {
                   if (GetCurrentState()==ScenarioState.CONNECT)
                   {
                        menu.ResetTree();
                   }
                   activeConnector.set(null);
               }
           }
       });
        activeConnector.addListener(new ChangeListener<ParameterConnector<?>>()
        {
            @Override
            public void changed(ObservableValue<? extends ParameterConnector<?>> observableValue, ParameterConnector<?> parameterConnector, ParameterConnector<?> t1)
            {
                if (t1==null)
                {
                    SetState(ScenarioState.DEFAULT);
                }
                else
                {
                    getChildren().add(t1);
                    SetState(ScenarioState.CONNECT);
                }
            }
        });
        states.GetCurrentProp().addListener(new ChangeListener<ScenarioState>()
        {
            @Override
            public void changed(ObservableValue<? extends ScenarioState> observableValue, ScenarioState scenarioState, ScenarioState t1)
            {
                if (t1 == ScenarioState.PAN)
                {
                    setCursor(Cursor.CLOSED_HAND);
                }
                else
                {
                    setCursor(Cursor.DEFAULT);
                }


            }
        });
        frames.addListener(new MapChangeListener<UUID, BaseFrame>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends BaseFrame> change)
            {
                if (change.wasAdded())
                {
                    getChildren().add(change.getValueAdded());
                }
                else if (change.wasRemoved())
                {
                    getChildren().remove(change.getValueRemoved());
                }
            }
        });
        name.addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                parent.GetRight().Update();
            }
        });
        layoutBoundsProperty().addListener(new ChangeListener<Bounds>()
        {
            @Override public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds bounds)
            {
                setClip(new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));
            }
        });
    }
    private void EventInit()
    {
        setOnMousePressed(this);
        setOnMouseReleased(this);
        setOnMouseClicked(this);
        setOnMouseMoved(this);
        setOnMouseDragged(this);
        StatesInit();
    }
    private void StatesInit()
    {
        DefaultStateInit();
        ConnectStateInit();
        PanStateInit();
        SetState(ScenarioState.DEFAULT);

    }
    private void DefaultStateInit()
    {
        states.put(ScenarioState.DEFAULT, inputEvent->
        {
            if (inputEvent instanceof MouseEvent)
            {
                MouseEvent evt = (MouseEvent)inputEvent;
                if (evt.getEventType() == MouseEvent.MOUSE_RELEASED)
                {
                    if (evt.getButton() == MouseButton.SECONDARY)
                    {
                        menu.show(getScene().getWindow(), evt.getScreenX(), evt.getScreenY());
                    }
                }
            }
        });
    }

    private void ConnectStateInit()
    {
        states.put(ScenarioState.CONNECT, inputEvent->
        {
            if (inputEvent instanceof MouseEvent evt)
            {
                if (evt.getEventType() == MouseEvent.MOUSE_RELEASED)
                {
                    if (evt.getButton() == MouseButton.PRIMARY)
                    {
                        if (! GetActiveConnector().IsConnected())
                        {
                            getChildren().remove(GetActiveConnector());
                        }
                        activeConnector.set(null);
                    }
                    else if (evt.getButton() == MouseButton.SECONDARY)
                    {
                        menu.show(getScene().getWindow(), evt.getScreenX(), evt.getScreenY());
                        menu.FilterByParameter(GetActiveConnector().GetStart());
                    }
                }
                else if (evt.getEventType() == MouseEvent.MOUSE_MOVED)
                {
                    GetActiveConnector().Update(evt.getX(), evt.getY());
                }
            }
        });
    }


    private void PanStateInit()
    {
        states.put(ScenarioState.PAN, inputEvent ->
        {
            if (inputEvent instanceof MouseEvent evt)
            {
                if (evt.getEventType() != MouseEvent.MOUSE_DRAGGED)
                {
                    SetState(ScenarioState.DEFAULT);
                }
            }

        });
    }

    public final void UpdateFrames()
    {
        for (final BaseFrame f : frames.values())
        {
            f.Update();
        }
    }

    public final void AbortConnection()
    {
        if (! activeConnector.get().IsConnected())
        {
            getChildren().remove(activeConnector.get());
            SetActiveConnector(null);
        }
    }


    public void AddFrame(final BaseFrame frame)
    {
        Point2D p = screenToLocal(menu.getX(), menu.getY());
        AddFrame(frame, p.getX(), p.getY());
        if (activeConnector.get() != null)
        {
            activeConnector.get().Connect(frame);

        }
    }

    public void AddFrame(final BaseFrame frame, double x, double y)
    {
        frame.setLayoutX(x);
        frame.setLayoutY(y);
        frames.put(frame.GetID(), frame);
        if (x > getWidth() / 2)
        {
            setPrefWidth(getWidth() + 200);
        }
        if (y > getHeight() / 2)
        {
            setPrefHeight(getHeight() + 200);
        }
    }

    public void RemoveFrame(final UUID frameID)
    {
        frames.remove(frameID);
    }

    @Override
    public void handle(InputEvent inputEvent)
    {
        states.GetCurrent().handle(inputEvent);
    }

    public final void SetActiveConnector(final ParameterConnector<?> c) {activeConnector.set(c);}
    public final void SetState(final ScenarioState state){states.SetCurrent(state);}
    public void SetName(final String str){name.setValue(str);}

    public final UUID GetID(){return id;}
    public final StoryModule GetParent(){return parent;}
    public final String GetName(){return name.getValue();}
    public final SimpleStringProperty GetNameProp(){return name;}
    public final ScenarioState GetCurrentState(){return states.GetCurrentKey();}
    public final ScenarioInputFrame GetInputFrame(){return input;}
    public final ScenarioOutputFrame GetOutputFrame(){return output;}
    public final ParameterConnector<?> GetActiveConnector(){return activeConnector.get();}
    public final boolean IsMain(){return type == ScenarioType.MAIN;}
    public final boolean IsEffect(){return type == ScenarioType.CHARACTER_EFFECT;}
    public final boolean IsStandard(){return type == ScenarioType.STANDARD;}
    public final int GetFrameCount(){return frames.size();}
    public final ScenarioType GetType(){return type;}

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Scenario other)
        {
            return id == other.GetID();
        }
        else
        {
            return false;
        }
    }

    @Override
    public DataBase ToData()
    {
        DataBase data = new DataBase(this);
        data.Add("ID", GetID());
        data.Add("NAME", GetName());
        data.Add("TYPE", GetType());
        // Add frames
        ArrayList<DataBase> frameData = new ArrayList<>();
        for (final BaseFrame f : frames.values())
        {
            frameData.add(f.ToData());
        }
        data.Add("FRAMES", frameData);
        return data;
    }

    @Override
    public void Load(DataBase data)
    {

    }
}
