package main.Module.Story.Scenario.Frame.Parameter;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;

public class ParameterSlot extends HBox
{
    private InputParameter<?> input;
    private OutputParameter<?> output;
    public ParameterSlot()
    {



    }


    public void SetInput(final InputParameter<?> param)
    {
        if (HasInput())
        {
            getChildren().remove(input);
        }
        input = param;
        getChildren().add(0, input);
    }
    public void SetOutput(final OutputParameter<?> param)
    {
        if (HasOutput())
        {
            getChildren().remove(output);
        }
        output = param;
        if (HasInput())
        {
            getChildren().add(1, output);
        }
        else
        {
            getChildren().add(output);
        }
    }

    public boolean Replace(final ParameterBase<?> old, final ParameterBase<?> n)
    {
        if (output != null)
        {
            if (output.equals(old))
            {
                if (n instanceof OutputParameter<?>)
                {
                    SetOutput((OutputParameter<?>)n);
                    return true;
                }
            }
        }
        else if (input != null)
        {
            if (input.equals(old))
            {
                if (n instanceof InputParameter<?>)
                {
                    SetInput((InputParameter<?>)n);
                    return true;
                }
            }

        }
        return false;
    }

    public final void RemoveInput()
    {
        getChildren().remove(input);
        input.Delete();
        input = null;
    }
    public final void RemoveOutput()
    {
        getChildren().remove(output);
        output.Delete();
        output = null;
    }

    public final boolean ContainsInput(final InputParameter<?> in)
    {
        if (input == null || in == null)
        {
            return false;
        }
        return input.equals(in);
    }
    public final boolean ContainsOutput(final OutputParameter<?> out)
    {
        if (output == null || out == null)
        {
            return false;
        }
        return output.equals(out);
    }

    public final InputParameter<?> GetInput(){return input;}
    public final OutputParameter<?> GetOutput(){return output;}
    public final boolean IsFull(){return HasInput() && HasOutput();}
    public final boolean IsEmpty(){return !HasInput() && !HasOutput();}
    public final boolean HasInput(){return input != null;}
    public final boolean HasOutput(){return output != null;}
}
