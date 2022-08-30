package main.Module.Story.Scenario.Frame.Parameter.InputParameter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.text.TextAlignment;
import main.Data.Frame.ParameterBaseData;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Frame.Parameter.ParameterBase;

public abstract class InputParameter<T> extends ParameterBase<T>
{
    public static InputParameter<?> CREATE(final BaseFrame parentFrame, final ParamType paramType, final String paramName)
    {
        switch (paramType)
        {
            case BOOL:
                return new InputParameterBool(parentFrame, paramName);
            case NUMBER:
                return new InputParameterNumber(parentFrame, paramName);
            case TEXT:
                return new InputParameterText(parentFrame);
            case GENERIC:
                return new InputParameterGeneric(parentFrame);
            case CHARACTER:
                return new InputParameterCharacter(parentFrame);
            default:
                return new InputParameterFlow(parentFrame);
        }
    }
    public static InputParameter<?> CREATE(final BaseFrame parentFrame, final ParamType paramType, final boolean array)
    {
        if (array)
        {
            return new InputParameterArray<>(parentFrame, paramType);
        }
        else
        {
            switch (paramType)
            {
                case BOOL:
                    return new InputParameterBool(parentFrame);
                case NUMBER:
                    return new InputParameterNumber(parentFrame);
                case TEXT:
                    return new InputParameterText(parentFrame);
                case GENERIC:
                    return new InputParameterGeneric(parentFrame);
                case CHARACTER:
                    return new InputParameterCharacter(parentFrame);
                default:
                    return new InputParameterFlow(parentFrame);
            }

        }
    }
    public static InputParameter<?> CREATE(final BaseFrame parentFrame, final ParamType paramType, final boolean array, final String paramName)
    {
        if (array)
        {
            return new InputParameterArray<>(parentFrame, paramType);
        }
        else
        {
            switch (paramType)
            {
                case BOOL:
                    return new InputParameterBool(parentFrame, paramName);
                case NUMBER:
                    return new InputParameterNumber(parentFrame, paramName);
                case TEXT:
                    return new InputParameterText(parentFrame);
                case GENERIC:
                    return new InputParameterGeneric(parentFrame);
                case CHARACTER:
                    return new InputParameterCharacter(parentFrame);
                default:
                    return new InputParameterFlow(parentFrame);
            }

        }
    }

    public static InputParameter<?> CREATE(final BaseFrame parentFrame, final ParameterBaseData<?> data)
    {
        switch (data.type())
        {
            case BOOL:
                return new InputParameterBool(parentFrame, (ParameterBaseData<Boolean>) data);
            case NUMBER:
                return new InputParameterNumber(parentFrame, (ParameterBaseData<Number>) data);
            case TEXT:
                return new InputParameterText(parentFrame, (ParameterBaseData<String>) data);
            default:
                return new InputParameterFlow(parentFrame, (ParameterBaseData<BaseFrame>) data);
        }
    }


    public InputParameter(final BaseFrame parentFrame, final ParamType paramType, final boolean array)
    {
        super(parentFrame, paramType, array, true);
        Init();
    }
    public InputParameter(final BaseFrame parentFrame, final ParamType paramType, final String paramName)
    {
        super(parentFrame, paramType, false, true);
        SetName(paramName);
        Init();
    }
    public InputParameter(final BaseFrame parentFrame, final InputParameter<T> other)
    {
        super(parentFrame, other);
        Init();
    }

    public InputParameter(final BaseFrame parentFrame, final ParameterBaseData<T> data)
    {
        super(parentFrame, data);
        Init();
    }

    private void Init()
    {
        name.setAlignment(Pos.CENTER_LEFT);
        getChildren().clear();
        getChildren().add(shape);
        getChildren().add(name);
        if (GetType() != ParamType.FLOW)
        {
            connectors.Single(true);
            connectors.Replace(true);
        }
    }

    @Override
    protected void OnConnect()
    {
        getChildren().remove(GetNode());
    }

    @Override
    protected void OnDisconnect()
    {
        if (!IsArray())
        {
            if (HasNode())
            {
                getChildren().add(getChildren().size()-1, GetNode());

            }
        }
    }

    protected abstract Node GetNode();
    protected abstract T GetNodeValue();

    public final boolean HasNode(){return GetNode()!=null;}

    public void UpdateValue()
    {
        if (IsConnected())
        {
            SetValue(GetFirstConnector().GetOutput().GetValue());
        }
        else
        {
            SetValue(GetNodeValue());
        }
    }





    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }
}
