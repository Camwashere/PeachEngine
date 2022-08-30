package main.Module.Story.Scenario.Frame.Parameter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import main.Data.DataBase;
import main.Data.SaveObject;
import main.Maths.Vec.Vec2;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.FunctionFrame.FunctionFrame;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;

import java.util.UUID;

public class ParameterConnector<T> extends Path implements SaveObject
{
    private final UUID id;
    private InputParameter<T> input;
    private OutputParameter<T> output;
    private final Vec2 startPos;
    private final Vec2 endPos;
    private final SimpleBooleanProperty connectedProp;
    private final boolean outputStart;

    public ParameterConnector(final ParameterBase<T> param)
    {
        id = UUID.randomUUID();
        if (param.IsOutput())
        {
            output = (OutputParameter<T>)param;
            outputStart = true;
        }
        else
        {
            input = (InputParameter<T>)param;
            outputStart = false;
        }
        startPos = new Vec2();
        endPos = new Vec2();
        connectedProp = new SimpleBooleanProperty(false);
        setStroke(GetStart().GetColor());
        Init();
        setDisabled(true);
        setDisable(true);
    }

    private void Init()
    {
        connectedProp.addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1)
                {
                    GetStart().AddConnector(ParameterConnector.this);
                }
                else
                {
                    output.GetValueProp().unbind();
                    input = null;
                    output = null;
                }
            }
        });
    }

    public void Update()
    {
        Bounds b = GetEnd().GetParent().localToParent(GetEnd().getParent().localToParent(GetEnd().localToParent(GetEnd().GetShape().getBoundsInParent())));
        Update(b.getCenterX(), b.getCenterY());
    }

    public void Update(double x, double y)
    {
        Bounds b = GetStart().GetParent().localToParent(GetStart().getParent().localToParent(GetStart().localToParent(GetStart().GetShape().getBoundsInParent())));

        startPos.x = b.getCenterX();
        startPos.y = b.getCenterY();

        endPos.x = x;
        endPos.y = y;
        UpdatePoints();
    }

    private void UpdatePoints()
    {
        getElements().clear();
        MoveTo startMove = new MoveTo(startPos.x, startPos.y);
        Vec2 midPos = Vec2.FindMidpoint(startPos, endPos);
        Vec2 midLeft = Vec2.FindMidpoint(startPos, midPos);
        Vec2 midRight = Vec2.FindMidpoint(midPos, endPos);
        midLeft.y = startPos.y;

        QuadCurveTo quadCurve = new QuadCurveTo(midLeft.x, midLeft.y, midPos.x, midPos.y);
        midRight.y = endPos.y;
        QuadCurveTo quad2 = new QuadCurveTo(midRight.x, midRight.y, endPos.x, endPos.y);

        LineTo endLine = new LineTo(endPos.x, endPos.y);
        getElements().add(startMove);
        getElements().add(quadCurve);
        getElements().add(quad2);
        getElements().add(endLine);
    }

    public final void Disconnect()
    {
        if (IsConnected())
        {
            input.GetConnectors().remove(this);
            output.GetConnectors().remove(this);
            input.GetParent().GetParent().getChildren().remove(this);
        }
        connectedProp.set(false);

    }
    public final void Connect(final BaseFrame frame)
    {
        if (IsStartOutput())
        {
            for (InputParameter<?> p : frame.GetInputParams())
            {
                if (p.GetType()==GetStart().GetType())
                {
                    Connect(p);
                    break;
                }
            }
        }
        else
        {
            for (OutputParameter<?> p : frame.GetOutputParams())
            {
                if (p.GetType()==GetStart().GetType())
                {
                    Connect(p);
                    break;
                }
            }
        }
    }
    public final void Connect(final ParameterBase<?> param)
    {
        if (CanConnect(param))
        {
            connectedProp.set(SetEnd(param));
        }
    }
    public final boolean CanConnect(final ParameterBase<?> param)
    {
        if (param.IsConnected())
        {
            if (param.GetConnectors().IsSingle() && !param.GetConnectors().IsReplace())
            {
                return false;
            }
        }
        if (IsStartOutput())
        {
            if (param.IsInput())
            {
                return param.GetType() == GetStart().GetType() || param.GetType()==ParamType.GENERIC;
            }
        }
        else
        {
            if (param.IsOutput())
            {
                return param.GetType() == GetStart().GetType() || param.GetType()==ParamType.GENERIC;
            }
        }
        return false;
    }

    public final boolean SetEnd(final ParameterBase<?> param)
    {
        if (IsStartOutput())
        {
            if (param.IsInput())
            {
                input = (InputParameter<T>)param;
                if (input.GetType() == ParamType.GENERIC)
                {
                    if (input.GetParent() instanceof FunctionFrame f)
                    {
                        input = (InputParameter<T>) f.Cast(GetStart());
                    }
                }
                return input.AddConnector(this);
            }
        }
        else
        {
            if (param.IsOutput())
            {
                output = (OutputParameter<T>)param;
                return output.AddConnector(this);
            }
        }
        return false;
    }

    public final boolean IsStartOutput(){return outputStart;}
    public final boolean IsStartInput(){return !outputStart;}
    public final boolean IsConnected(){return connectedProp.get();}
    public final boolean HasInput(){return input!=null;}
    public final boolean HasOutput(){return output!=null;}

    public final InputParameter<T> GetInput(){return input;}
    public final OutputParameter<T> GetOutput(){return output;}
    public final ParameterBase<T> GetStart() {return outputStart ? output : input;}
    public final ParameterBase<T> GetEnd() {return outputStart ? input : output;}
    public final UUID GetID(){return id;}

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ParameterConnector<?> other)
        {
            return id==other.id && super.equals(obj);
        }
        return false;
    }

    @Override
    public DataBase ToData()
    {
        DataBase data = new DataBase(this);
        data.Add("ID", GetID());
        data.Add("IS_OUTPUT_START", IsStartOutput());
        if (input != null)
        {
            data.Add("INPUT_ID", input.GetID());
        }
        else
        {
            data.Add("INPUT_ID", null);
        }
        if (output != null)
        {
            data.Add("OUTPUT_ID", output.GetID());
        }
        else
        {
            data.Add("OUTPUT_ID", null);
        }
        return data;
    }

    @Override
    public void Load(DataBase data)
    {


    }
}
