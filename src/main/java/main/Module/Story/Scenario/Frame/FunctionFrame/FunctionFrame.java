package main.Module.Story.Scenario.Frame.FunctionFrame;

import javafx.beans.property.SimpleListProperty;
import javafx.geometry.Insets;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.Data.Frame.FunctionFrameData;
import main.Debug.Debug;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.FrameType;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.*;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameterArray;
import main.Module.Story.Scenario.Frame.Parameter.ParameterBase;
import main.Module.Story.Scenario.Scenario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class FunctionFrame extends BaseFrame
{
    private final BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame> function;
    private final FunctionType functionType;
    public FunctionFrame(final Scenario s, final FunctionType fType, final BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame> func, final String name)
    {
        super(s, FrameType.FUNCTION);

        functionType = fType;
        function = func;
        SetName(name);
        this.name.setDisable(true);
    }
    public FunctionFrame(final FunctionFrame other)
    {
        super(other.parent, FrameType.FUNCTION);
        functionType = other.functionType;
        function = other.function;
        SetName(other.GetName());
        for (InputParameter<?> i : other.inputParams)
        {
            AddInputParam(InputParameter.CREATE(this, i.GetType(), i.IsArray(), i.GetName()));
        }
        for (OutputParameter<?> o : other.outputParams)
        {
            AddOutputParam(new OutputParameter<>(this, o));
        }
        this.name.setDisable(true);
    }
    public FunctionFrame(final Scenario s, final FunctionFrameData data)
    {
        super(s, data.baseData());
        this.name.setDisable(true);
        functionType = data.functionType();
        function = Functions.GET_FUNCTION(functionType, GetName(), s);
        SetName(data.baseData().name());
    }

    public final ParameterBase<?> Cast(final ParameterBase<?> p)
    {
        System.out.println("CAST");
        ArrayList<InputParameter<?>> list = new ArrayList<InputParameter<?>>(inputParams);
        ParameterBase<?> rtn=null;
        for (int i=0; i<list.size(); i++)
        {
            if (list.get(i).IsGeneric())
            {
                boolean arr = p.IsArray();
                if (p.IsArray())
                {
                    rtn = new InputParameterArray(this, p.GetType());
                }
                else
                {
                    switch (p.GetType())
                    {
                        case BOOL:
                            rtn = new InputParameterBool(this);
                            break;
                        case NUMBER:
                            rtn = new InputParameterNumber(this);
                            break;
                        case TEXT:
                            rtn = new InputParameterText(this);
                            break;
                        default:
                            break;
                    }

                }
                    ReplaceParameter(list.get(i), rtn);
            }
        }

        return rtn;
    }

    public static ArrayList<InputParameter<?>> CREATE_INPUTS(final InputParameter<?> ...inputs)
    {
        return new ArrayList<InputParameter<?>>(Arrays.asList(inputs));
    }
    public static ArrayList<OutputParameter<?>> CREATE_OUTPUTS(final OutputParameter<?> ...outputs)
    {
        return new ArrayList<OutputParameter<?>>(Arrays.asList(outputs));
    }

    public final BaseFrame Apply()
    {
        return function.apply(inputParams, outputParams);
    }

    public final BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame> GetFunction(){return function;}

    @Override
    public void UpdateInputValues()
    {
        super.UpdateInputValues();
        Apply();
    }

    @Override
    protected void ContextMenuInit()
    {
        MenuItem check = new MenuItem("Func Check");
        check.setOnAction(evt->
        {
            System.out.println("Before: \n");
            PrintDebug();
            Apply();
            System.out.println("After: \n");
            PrintDebug();

        });
        contextMenu.getItems().add(check);

    }

    @Override
    protected void StyleInit()
    {
        setFocusTraversable(false);

        Background background = new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY));
        setBackground(background);
        Border b = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        setBorder(b);

        setPrefSize(150, 100);
    }

    public final FunctionFrameData AsData()
    {
        return new FunctionFrameData(functionType, super.AsBaseData());
    }
}
