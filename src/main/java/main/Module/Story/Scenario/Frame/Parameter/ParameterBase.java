package main.Module.Story.Scenario.Frame.Parameter;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import main.Data.DataBase;
import main.Data.Frame.ConnectionData;
import main.Data.Frame.ParameterBaseData;
import main.Data.SaveObject;
import main.Maths.Vec.Vec4;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.ScenarioState;
import main.Tools.DynamicArrayList;
import main.Tools.InitHelp;
import org.kordamp.jsilhouette.javafx.RegularPolygon;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class ParameterBase<T> extends HBox
{
    private final UUID id;
    protected Color color;
    protected final ParamType type;
    protected final Label name;
    protected Shape shape;
    protected final SimpleBooleanProperty active;
    protected final BaseFrame parent;
    protected final SimpleObjectProperty<T> value;
    protected final boolean isArray;
    protected final DynamicArrayList<ParameterConnector<T>> connectors;
    private final boolean isInput;

    public static double SHAPE_SIZE = 10;

    public ParameterBase(final BaseFrame parentFrame, final ParamType paramType, final boolean array, final boolean isIn)
    {
        id = UUID.randomUUID();
        parent = parentFrame;
        value = new SimpleObjectProperty<T>();
        type = paramType;
        ColorInit();
        isArray = array;
        connectors = new DynamicArrayList<ParameterConnector<T>>();
        isInput = isIn;
        ShapeInit();
        name = new Label();
        active = new SimpleBooleanProperty(false);
        ListenerInit();
        SelfInit();
        EventInit();
    }

    public ParameterBase(final BaseFrame parentFrame, final ParameterBase<T> other)
    {
        id = UUID.randomUUID();
        parent = parentFrame;
        value = new SimpleObjectProperty<T>();
        type = other.type;
        ColorInit();
        isArray = other.isArray;
        connectors = new DynamicArrayList<ParameterConnector<T>>();
        isInput = other.IsInput();
        ShapeInit();
        name = new Label();
        active = new SimpleBooleanProperty(false);
        ListenerInit();
        SetValue(other.value.get());
        SelfInit();
        EventInit();
    }
    public ParameterBase(final BaseFrame parentFrame, final ParameterBaseData<T> data)
    {
        id = data.id();
        parent = parentFrame;
        value = new SimpleObjectProperty<T>();
        type = data.type();
        ColorInit();
        isArray = data.isArray();
        connectors = new DynamicArrayList<ParameterConnector<T>>();
        isInput = data.isInput();
        ShapeInit();
        name = new Label(data.name());
        active = new SimpleBooleanProperty(false);
        ListenerInit();
        SetValue(data.value());
        SelfInit();
        EventInit();


    }

    private void ColorInit()
    {
        switch (type)
        {
            case GENERIC:
                color = Color.DARKGRAY;
                shape = new RegularPolygon(0, 0, SHAPE_SIZE, 6).getShape();
                break;
            case FLOW:
                color = Color.WHITE;
                break;
            case BOOL:
                color = Color.RED;
                break;
            case NUMBER:
                color = Color.GREEN;
                break;
            case TEXT:
                color = Color.YELLOW;
                break;
            default:
                color = Color.LIGHTBLUE;
                break;
        }
    }
    private void ShapeInit()
    {
        if (IsArray())
        {
            shape = new Rectangle(SHAPE_SIZE, SHAPE_SIZE);
        }
        else
        {
            shape = new Circle(SHAPE_SIZE);
        }
    }

    private void SelfInit()
    {
        InitHelp.GrowInit(this);
        InitHelp.GrowInit(shape);
        InitHelp.NodeInit(name);
        setAlignment(Pos.CENTER);
        shape.setStroke(color);
        shape.setFill(Color.TRANSPARENT);
    }

    private void EventInit()
    {
        shape.setOnMouseReleased(evt->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                if (parent.GetParent().GetCurrentState()==ScenarioState.CONNECT)
                {
                    parent.GetParent().GetActiveConnector().Connect(ParameterBase.this);
                }
                else if (parent.GetParent().GetCurrentState()==ScenarioState.DEFAULT)
                {
                    if (IsConnected() && connectors.IsSingle())
                    {
                        ClearConnectors();
                    }
                    else
                    {
                        parent.GetParent().SetActiveConnector(new ParameterConnector<T>(this));
                        //System.out.println(parent.GetParent().GetCurrentState());
                        evt.consume();
                    }
                }
            }
        });
        shape.setOnMouseEntered(evt->
        {
            if (parent.GetParent().GetCurrentState()==ScenarioState.CONNECT)
            {
                if (parent.GetParent().GetActiveConnector().CanConnect(this))
                {
                    shape.setStroke(Color.LIGHTGREEN);
                }
                else
                {
                    shape.setStroke(Color.GRAY);
                }
            }
            else if (parent.GetParent().GetCurrentState()==ScenarioState.DEFAULT)
            {
                shape.setStroke(Color.LIGHTGREEN);
            }
        });
        shape.setOnMouseExited(evt->
        {
            shape.setStroke(color);

        });


    }
    private void ListenerInit()
    {
        active.addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1)
                {
                    shape.setFill(color);
                }
                else
                {
                    shape.setFill(Color.TRANSPARENT);
                }
            }
        });
    }

    public final void Update()
    {
        if (IsConnected())
        {
            for (final ParameterConnector<T> c : connectors)
            {
                c.Update();
            }
        }
    }

    public final void Delete()
    {
        ClearConnectors();
    }

    public final Shape CopyShape()
    {
        if (IsArray())
        {
            Rectangle r = new Rectangle(SHAPE_SIZE, SHAPE_SIZE);
            r.setStroke(Color.TRANSPARENT);
            r.setFill(Color.TRANSPARENT);
            r.setDisable(true);
            return r;
        }
        else
        {
            Circle c = new Circle(SHAPE_SIZE);
            c.setStroke(Color.BLACK);
            c.setFill(Color.TRANSPARENT);
            c.setDisable(true);
            return c;
        }
    }

    public final boolean AddConnector(final ParameterConnector<T> c)
    {
        if (connectors.IsSingle() && !connectors.IsReplace() && !connectors.isEmpty())
        {
            return false;
        }
        if (connectors.IsReplace())
        {
            ClearConnectors();
        }
        if(connectors.isEmpty())
        {
            OnConnect();
        }
        connectors.add(c);
        active.set(true);
        return true;
    }
    public final boolean HasConnector(final ParameterConnector<T> c)
    {
        for (final ParameterConnector<T> con : connectors)
        {
            if (con.equals(c))
            {
                return true;
            }
        }
        return false;
    }
    public final void RemoveConnector(final ParameterConnector<T> c)
    {
        connectors.remove(c);
        if (connectors.isEmpty())
        {
            OnDisconnect();
            active.set(false);
        }
    }
    public final void ClearConnectors()
    {
        ArrayList<ParameterConnector<T>> list = new ArrayList<ParameterConnector<T>>(connectors);
        for (ParameterConnector<T> p : list)
        {
            parent.GetParent().getChildren().remove(p);
            p.GetInput().RemoveConnector(p);
            p.GetOutput().RemoveConnector(p);
        }
    }

    protected abstract void OnConnect();
    protected abstract void OnDisconnect();
    public final void SetValue(final T val){value.setValue(val);}
    public final void SetName(final String str){name.setText(str);}

    public final boolean IsArray(){return isArray;}
    public final boolean IsConnected(){return !connectors.isEmpty();}
    public final boolean IsInput(){return isInput;}
    public final boolean IsOutput(){return !isInput;}
    public final boolean IsActive(){return active.get();}
    public final boolean IsGeneric(){return type==ParamType.GENERIC;}

    public final Shape GetShape(){return shape;}
    public final DynamicArrayList<ParameterConnector<T>> GetConnectors(){return connectors;}
    public final ParameterConnector<T> GetFirstConnector(){return connectors.GetFirst();}
    public final ParameterConnector<T> GetMostRecentConnector(){return connectors.GetLast();}
    public final ParamType GetType(){return type;}
    public final Color GetColor(){return color;}
    public final BaseFrame GetParent(){return parent;}
    public final String GetName(){return name.getText();}
    public final Label GetLabel(){return name;}
    public final SimpleObjectProperty<T> GetValueProp(){return value;}
    public T GetValue(){return value.getValue();}
    public final UUID GetID(){return id;}

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ParameterBase<?> other)
        {
            return type == other.GetType() && isArray==other.IsArray()
                    && isInput==other.isInput && color.equals(other.color) &&
                    shape.equals(other.shape) &&
                    id==other.id;
        }
        return false;
    }

    public ParameterBaseData<T> AsData()
    {
        List<ConnectionData> connectedParams = new ArrayList<>();
        if (isInput)
        {
            for (final ParameterConnector<T> c : connectors)
            {
                connectedParams.add(new ConnectionData(c.GetOutput().GetParent().GetID(), c.GetOutput().GetID()));
            }
        }
        else
        {
            for (final ParameterConnector<T> c : connectors)
            {
                connectedParams.add(new ConnectionData(c.GetInput().GetParent().GetID(), c.GetInput().GetID()));
            }
        }
        return new ParameterBaseData<T>(id, type, name.getText(), value.getValue(), isArray, isInput, connectedParams);
    }
}
