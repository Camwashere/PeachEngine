package main.Module.Story.Scenario.Frame.FunctionFrame;

import javafx.beans.property.SimpleListProperty;
import javafx.css.Size;
import main.Debug.Debug;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.*;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Scenario;
import main.Tools.StringHelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class Functions
{
    // OPERATORS
    @SuppressWarnings("unchecked")
    public static List<FunctionFrame> OPERATORS(final Scenario s)
    {
        ArrayList<FunctionFrame> list = new ArrayList<FunctionFrame>();
        
        //ADD
        FunctionFrame add = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterNumber left = (InputParameterNumber)ins.get(0);
                InputParameterNumber right = (InputParameterNumber)ins.get(1);
                OutputParameter<Number> result = (OutputParameter<Number>)outs.get(0);
                result.SetValue(left.GetValue().doubleValue() + right.GetValue().doubleValue());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Add");
        add.AddInputParameter(ParamType.NUMBER);
        add.AddInputParameter(ParamType.NUMBER);
        add.AddOutputParameter(ParamType.NUMBER);
        list.add(add);

        //SUBTRACT
        FunctionFrame subtract = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterNumber left = (InputParameterNumber)ins.get(0);
                InputParameterNumber right = (InputParameterNumber)ins.get(1);
                OutputParameter<Number> result = (OutputParameter<Number>)outs.get(0);
                result.SetValue(left.GetValue().doubleValue() - right.GetValue().doubleValue());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Subtract");
        subtract.AddInputParameter(ParamType.NUMBER);
        subtract.AddInputParameter(ParamType.NUMBER);
        subtract.AddOutputParameter(ParamType.NUMBER);
        list.add(subtract);

        //MULTIPLY
        FunctionFrame Multiply = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterNumber left = (InputParameterNumber)ins.get(0);
                InputParameterNumber right = (InputParameterNumber)ins.get(1);
                OutputParameter<Number> result = (OutputParameter<Number>)outs.get(0);
                result.SetValue(left.GetValue().doubleValue() * right.GetValue().doubleValue());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Multiply");
        Multiply.AddInputParameter(ParamType.NUMBER);
        Multiply.AddInputParameter(ParamType.NUMBER);
        Multiply.AddOutputParameter(ParamType.NUMBER);
        list.add(Multiply);

        //DIVIDE
        FunctionFrame Divide = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterNumber left = (InputParameterNumber)ins.get(0);
                InputParameterNumber right = (InputParameterNumber)ins.get(1);
                OutputParameter<Number> result = (OutputParameter<Number>)outs.get(0);
                result.SetValue(left.GetValue().doubleValue() / right.GetValue().doubleValue());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Divide");
        Divide.AddInputParameter(ParamType.NUMBER);
        Divide.AddInputParameter(ParamType.NUMBER);
        Divide.AddOutputParameter(ParamType.NUMBER);
        list.add(Divide);

        //Remainder
        FunctionFrame Remainder = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterNumber left = (InputParameterNumber)ins.get(0);
                InputParameterNumber right = (InputParameterNumber)ins.get(1);
                OutputParameter<Number> result = (OutputParameter<Number>)outs.get(0);
                result.SetValue(left.GetValue().doubleValue() % right.GetValue().doubleValue());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Remainder");
        Remainder.AddInputParameter(ParamType.NUMBER);
        Remainder.AddInputParameter(ParamType.NUMBER);
        Remainder.AddOutputParameter(ParamType.NUMBER);
        list.add(Remainder);

        //EQUALS
        FunctionFrame equals = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterGeneric left = (InputParameterGeneric)ins.get(0);
                InputParameterGeneric right = (InputParameterGeneric)ins.get(1);
                OutputParameter<Boolean> result = (OutputParameter<Boolean>)outs.get(0);
                result.SetValue(left.GetValue().equals(right.GetValue()));
                return null;
            }
        }, "Equals");
        equals.AddInputParameter(ParamType.GENERIC);
        equals.AddInputParameter(ParamType.GENERIC);
        equals.AddOutputParameter(ParamType.BOOL);
        list.add(equals);

        //NOT EQUALS
        FunctionFrame notEquals = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterGeneric left = (InputParameterGeneric)ins.get(0);
                InputParameterGeneric right = (InputParameterGeneric)ins.get(1);
                OutputParameter<Boolean> result = (OutputParameter<Boolean>)outs.get(0);
                result.SetValue(! left.GetValue().equals(right.GetValue()));
                return null;
            }
        }, "Not Equals");
        notEquals.AddInputParameter(ParamType.GENERIC);
        notEquals.AddInputParameter(ParamType.GENERIC);
        notEquals.AddOutputParameter(ParamType.BOOL);
        list.add(notEquals);

        //LESS THAN
        FunctionFrame lessThan = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterNumber left = (InputParameterNumber)ins.get(0);
                InputParameterNumber right = (InputParameterNumber)ins.get(1);
                OutputParameter<Boolean> result = (OutputParameter<Boolean>)outs.get(0);
                result.SetValue(left.GetValue().doubleValue() < right.GetValue().doubleValue());
                return null;
            }
        }, "Less Than");
        lessThan.AddInputParameter(ParamType.NUMBER);
        lessThan.AddInputParameter(ParamType.NUMBER);
        lessThan.AddOutputParameter(ParamType.BOOL);
        list.add(lessThan);

        //GREATER THAN
        FunctionFrame greaterThan = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterNumber left = (InputParameterNumber)ins.get(0);
                InputParameterNumber right = (InputParameterNumber)ins.get(1);
                OutputParameter<Boolean> result = (OutputParameter<Boolean>)outs.get(0);
                result.SetValue(left.GetValue().doubleValue() > right.GetValue().doubleValue());
                return null;
            }
        }, "Greater Than");
        greaterThan.AddInputParameter(ParamType.NUMBER);
        greaterThan.AddInputParameter(ParamType.NUMBER);
        greaterThan.AddOutputParameter(ParamType.BOOL);
        list.add(greaterThan);

        //LESS EQUAL
        FunctionFrame lessThanEqual = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterNumber left = (InputParameterNumber)ins.get(0);
                InputParameterNumber right = (InputParameterNumber)ins.get(1);
                OutputParameter<Boolean> result = (OutputParameter<Boolean>)outs.get(0);
                result.SetValue(left.GetValue().doubleValue() <= right.GetValue().doubleValue());
                return null;
            }
        }, "Less Equal");
        lessThanEqual.AddInputParameter(ParamType.NUMBER);
        lessThanEqual.AddInputParameter(ParamType.NUMBER);
        lessThanEqual.AddOutputParameter(ParamType.BOOL);
        list.add(lessThanEqual);

        //GREATER EQUAL
        FunctionFrame greaterThanEqual = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterNumber left = (InputParameterNumber)ins.get(0);
                InputParameterNumber right = (InputParameterNumber)ins.get(1);
                OutputParameter<Boolean> result = (OutputParameter<Boolean>)outs.get(0);
                result.SetValue(left.GetValue().doubleValue() >= right.GetValue().doubleValue());
                return null;
            }
        }, "Greater Equal");
        greaterThanEqual.AddInputParameter(ParamType.NUMBER);
        greaterThanEqual.AddInputParameter(ParamType.NUMBER);
        greaterThanEqual.AddOutputParameter(ParamType.BOOL);
        list.add(greaterThanEqual);

        
        return list;

    }

    // LOGIC
    @SuppressWarnings("unchecked")
    public static List<FunctionFrame> LOGIC(final Scenario s)
    {
        List<FunctionFrame> list = new ArrayList<FunctionFrame>();
        
        //IF
        FunctionFrame branch = new FunctionFrame(s, FunctionType.LOGIC, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterBool val = (InputParameterBool)ins.get(1);
                OutputParameter<BaseFrame> outTrue = (OutputParameter<BaseFrame>)outs.get(0);
                OutputParameter<BaseFrame> outFalse = (OutputParameter<BaseFrame>)outs.get(1);
                if (val.GetValue())
                {
                    return outTrue.GetValue();
                }
                else
                {
                    return outFalse.GetValue();
                }
            }
        }, "If");
        branch.AddInputParameter(ParamType.FLOW);
        branch.AddInputParameter(ParamType.BOOL);
        branch.AddOutputParameter(ParamType.FLOW, "True");
        branch.AddOutputParameter(ParamType.FLOW, "False");
        list.add(branch);

        //NOT
        FunctionFrame not = new FunctionFrame(s, FunctionType.LOGIC, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterBool val = (InputParameterBool)ins.get(0);
                OutputParameter<Boolean> out = (OutputParameter<Boolean>)outs.get(0);
                out.SetValue(! val.GetValue());
                return null;
            }
        }, "Not");
        not.AddInputParameter(ParamType.BOOL);
        not.AddOutputParameter(ParamType.BOOL);
        list.add(not);

        //AND
        FunctionFrame and = new FunctionFrame(s, FunctionType.LOGIC, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterBool left = (InputParameterBool)ins.get(0);
                InputParameterBool right = (InputParameterBool)ins.get(1);
                OutputParameter<Boolean> out = (OutputParameter<Boolean>)outs.get(0);
                out.SetValue(left.GetValue() && right.GetValue());
                return null;
            }
        }, "And");
        and.AddInputParameter(ParamType.BOOL);
        and.AddInputParameter(ParamType.BOOL);
        and.AddOutputParameter(ParamType.BOOL);
        list.add(and);

        //OR
        FunctionFrame or = new FunctionFrame(s, FunctionType.LOGIC, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterBool left = (InputParameterBool)ins.get(0);
                InputParameterBool right = (InputParameterBool)ins.get(1);
                OutputParameter<Boolean> out = (OutputParameter<Boolean>)outs.get(0);
                out.SetValue(left.GetValue() || right.GetValue());
                return null;
            }
        }, "Or");
        or.AddInputParameter(ParamType.BOOL);
        or.AddInputParameter(ParamType.BOOL);
        or.AddOutputParameter(ParamType.BOOL);
        list.add(or);

        //XOR
        FunctionFrame xor = new FunctionFrame(s, FunctionType.LOGIC, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterBool left = (InputParameterBool)ins.get(0);
                InputParameterBool right = (InputParameterBool)ins.get(1);
                OutputParameter<Boolean> out = (OutputParameter<Boolean>)outs.get(0);
                out.SetValue(left.GetValue() != right.GetValue());
                return null;
            }
        }, "Xor");
        xor.AddInputParameter(ParamType.BOOL);
        xor.AddInputParameter(ParamType.BOOL);
        xor.AddOutputParameter(ParamType.BOOL);
        list.add(xor);

        //NAND
        FunctionFrame nand = new FunctionFrame(s, FunctionType.LOGIC, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterBool left = (InputParameterBool)ins.get(0);
                InputParameterBool right = (InputParameterBool)ins.get(1);
                OutputParameter<Boolean> out = (OutputParameter<Boolean>)outs.get(0);
                out.SetValue(! (left.GetValue() && right.GetValue()));
                return null;
            }
        }, "Nand");
        nand.AddInputParameter(ParamType.BOOL);
        nand.AddInputParameter(ParamType.BOOL);
        nand.AddOutputParameter(ParamType.BOOL);
        list.add(nand);

        //NOR
        FunctionFrame nor = new FunctionFrame(s, FunctionType.LOGIC, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterBool left = (InputParameterBool)ins.get(0);
                InputParameterBool right = (InputParameterBool)ins.get(1);
                OutputParameter<Boolean> out = (OutputParameter<Boolean>)outs.get(0);
                out.SetValue(! (left.GetValue() || right.GetValue()));
                return null;
            }
        }, "Nor");
        nor.AddInputParameter(ParamType.BOOL);
        nor.AddInputParameter(ParamType.BOOL);
        nor.AddOutputParameter(ParamType.BOOL);
        list.add(nor);

        


        return list;
    }

    // MATH
    public static List<FunctionFrame> MATH(final Scenario s)
    {
        List<FunctionFrame> list = new ArrayList<FunctionFrame>();


        //POWER
        FunctionFrame Power = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterNumber left = (InputParameterNumber)ins.get(0);
                InputParameterNumber right = (InputParameterNumber)ins.get(1);
                OutputParameter<Number> result = (OutputParameter<Number>)outs.get(0);
                result.SetValue(Math.pow(left.GetValue().doubleValue(), right.GetValue().doubleValue()));
                return null;
            }
        }, "Power");
        Power.AddInputParameter(ParamType.NUMBER);
        Power.AddInputParameter(ParamType.NUMBER);
        Power.AddOutputParameter(ParamType.NUMBER);
        list.add(Power);

        //SQUARE ROOT
        FunctionFrame sqrt = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterNumber val = (InputParameterNumber)ins.get(0);
                OutputParameter<Number> result = (OutputParameter<Number>)outs.get(0);
                result.SetValue(Math.sqrt(val.GetValue().doubleValue()));
                return null;
            }
        }, "Square Root");
        sqrt.AddInputParameter(ParamType.NUMBER);
        sqrt.AddOutputParameter(ParamType.NUMBER);
        list.add(sqrt);


        // RANDOM
        FunctionFrame rand = new FunctionFrame(s, FunctionType.OPERATOR, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterNumber min = (InputParameterNumber)ins.get(0);
                InputParameterNumber max = (InputParameterNumber)ins.get(1);
                OutputParameter<Number> result = (OutputParameter<Number>)outs.get(0);
                // If Decimal
                if ((min.GetValue().doubleValue() % 1)!=0 || (max.GetValue().doubleValue() % 1)!=0)
                {
                    result.SetValue(Math.random() * (max.GetValue().doubleValue() - min.GetValue().doubleValue()) + min.GetValue().doubleValue());
                }
                // If Integer
                else
                {
                    result.SetValue((int) ((Math.random() * (max.GetValue().intValue() - min.GetValue().intValue())) + min.GetValue().intValue()));
                }
                return null;
            }
        }, "Random");
        rand.AddInputParameter(ParamType.NUMBER, "Min");
        rand.AddInputParameter(ParamType.NUMBER, "Max");
        rand.AddOutputParameter(ParamType.NUMBER);
        list.add(rand);



        return list;
    }

    // ARRAY
    @SuppressWarnings("unchecked")
    public static List<FunctionFrame> ARRAY(final Scenario s)
    {
        List<FunctionFrame> list = new ArrayList<FunctionFrame>();
        //GET
        FunctionFrame get = new FunctionFrame(s, FunctionType.ARRAY, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterArray<Object> array = (InputParameterArray<Object>)ins.get(0);
                InputParameterNumber index = (InputParameterNumber)ins.get(1);
                OutputParameter<Object> result = (OutputParameter<Object>)outs.get(0);
                result.SetValue(array.GetValue().get(index.GetValue().intValue()));
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Get");
        get.AddInputParameter(ParamType.GENERIC, true);
        get.AddInputParameter(ParamType.NUMBER);
        get.AddOutputParameter(ParamType.GENERIC);
        list.add(get);

        //ADD
        FunctionFrame add = new FunctionFrame(s, FunctionType.ARRAY, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterArray<Object> array = (InputParameterArray<Object>)ins.get(1);
                InputParameterGeneric addVal = (InputParameterGeneric)ins.get(2);
                OutputParameter<BaseFrame> next = (OutputParameter<BaseFrame>)outs.get(0);
                //OutputParameterArray<Object> result = (InputParameterArray<Object>)ins.get(1);
                array.GetValue().add(addVal.GetValue());
                if (next.IsConnected())
                {
                    return next.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Add");
        add.AddInputParameter(ParamType.FLOW);
        add.AddInputParameter(ParamType.GENERIC, true);
        add.AddInputParameter(ParamType.GENERIC);
        add.AddOutputParameter(ParamType.FLOW);
        list.add(add);

        //SIZE
        FunctionFrame size = new FunctionFrame(s, FunctionType.ARRAY, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterArray<Object> array = (InputParameterArray<Object>)ins.get(0);
                OutputParameter<Number> result = (OutputParameter<Number>)outs.get(0);
                result.SetValue(array.GetValue().size());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Size");
        size.AddInputParameter(ParamType.GENERIC, true);
        size.AddOutputParameter(ParamType.NUMBER);
        list.add(size);
        
        return list;
    }
    
    // TEXT
    @SuppressWarnings("unchecked")
    public static List<FunctionFrame> TEXT(final Scenario s)
    {
        List<FunctionFrame> list = new ArrayList<FunctionFrame>();
        
        FunctionFrame capital = new FunctionFrame(s, FunctionType.TEXT, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterText original = (InputParameterText)ins.get(0);
                OutputParameter<String> result = (OutputParameter<String>)outs.get(0);
                result.SetValue(StringHelp.Capitalize(original.GetValue()));
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Capitalize");
        capital.AddInputParameter(ParamType.TEXT);
        capital.AddOutputParameter(ParamType.TEXT);
        list.add(capital);

        FunctionFrame lower = new FunctionFrame(s, FunctionType.TEXT, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterText original = (InputParameterText)ins.get(0);
                OutputParameter<String> result = (OutputParameter<String>)outs.get(0);
                result.SetValue(original.GetValue().toLowerCase());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Lower Case");
        lower.AddInputParameter(ParamType.TEXT);
        lower.AddOutputParameter(ParamType.TEXT);
        list.add(lower);

        FunctionFrame upper = new FunctionFrame(s, FunctionType.TEXT, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameterText original = (InputParameterText)ins.get(0);
                OutputParameter<String> result = (OutputParameter<String>)outs.get(0);
                result.SetValue(original.GetValue().toUpperCase());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Upper Case");
        upper.AddInputParameter(ParamType.TEXT);
        upper.AddOutputParameter(ParamType.TEXT);
        list.add(upper);

        FunctionFrame asString = new FunctionFrame(s, FunctionType.TEXT, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> ins, SimpleListProperty<OutputParameter<?>> outs)
            {
                InputParameter<?>original = ins.get(0);
                OutputParameter<String> result = (OutputParameter<String>)outs.get(0);
                result.SetValue(original.GetValue().toString());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "As String");
        asString.AddInputParameter(ParamType.GENERIC);
        asString.AddOutputParameter(ParamType.TEXT);
        list.add(asString);
        

        return list;
    }

    // CHARACTER
    @SuppressWarnings("unchecked")
    public static List<FunctionFrame> CHARACTER_NAME(final Scenario s)
    {
        List<FunctionFrame> list = new ArrayList<FunctionFrame>();
        FunctionFrame getFirst = new FunctionFrame(s, FunctionType.CHARACTER, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> inputParameters, SimpleListProperty<OutputParameter<?>> outputParameters)
            {
                InputParameterCharacter input = (InputParameterCharacter)inputParameters.get(0);
                OutputParameter<String> result = (OutputParameter<String>)outputParameters.get(0);
                result.SetValue(input.GetValue().GetFirstName());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Get First Name");
        getFirst.AddInputParameter(ParamType.CHARACTER);
        getFirst.AddOutputParameter(ParamType.TEXT);
        list.add(getFirst);


        FunctionFrame getMiddle = new FunctionFrame(s, FunctionType.CHARACTER, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> inputParameters, SimpleListProperty<OutputParameter<?>> outputParameters)
            {
                InputParameterCharacter input = (InputParameterCharacter)inputParameters.get(0);
                OutputParameter<String> result = (OutputParameter<String>)outputParameters.get(0);
                result.SetValue(input.GetValue().GetMiddleName());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Get Middle Name");
        getMiddle.AddInputParameter(ParamType.CHARACTER);
        getMiddle.AddOutputParameter(ParamType.TEXT);
        list.add(getMiddle);

        FunctionFrame getLast = new FunctionFrame(s, FunctionType.CHARACTER, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> inputParameters, SimpleListProperty<OutputParameter<?>> outputParameters)
            {
                InputParameterCharacter input = (InputParameterCharacter)inputParameters.get(0);
                OutputParameter<String> result = (OutputParameter<String>)outputParameters.get(0);
                result.SetValue(input.GetValue().GetLastName());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Get Last Name");
        getLast.AddInputParameter(ParamType.CHARACTER);
        getLast.AddOutputParameter(ParamType.TEXT);
        list.add(getLast);

        FunctionFrame getFull = new FunctionFrame(s, FunctionType.CHARACTER, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> inputParameters, SimpleListProperty<OutputParameter<?>> outputParameters)
            {
                InputParameterCharacter input = (InputParameterCharacter)inputParameters.get(0);
                OutputParameter<String> result = (OutputParameter<String>)outputParameters.get(0);
                result.SetValue(input.GetValue().GetFullName());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Get Full Name");
        getFull.AddInputParameter(ParamType.CHARACTER);
        getFull.AddOutputParameter(ParamType.TEXT);
        list.add(getFull);

        FunctionFrame getLegal = new FunctionFrame(s, FunctionType.CHARACTER, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> inputParameters, SimpleListProperty<OutputParameter<?>> outputParameters)
            {
                InputParameterCharacter input = (InputParameterCharacter)inputParameters.get(0);
                OutputParameter<String> result = (OutputParameter<String>)outputParameters.get(0);
                result.SetValue(input.GetValue().GetLegalName());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Get Legal Name");
        getLegal.AddInputParameter(ParamType.CHARACTER);
        getLegal.AddOutputParameter(ParamType.TEXT);
        list.add(getLegal);

        FunctionFrame getLegalAbr = new FunctionFrame(s, FunctionType.CHARACTER, new BiFunction<SimpleListProperty<InputParameter<?>>, SimpleListProperty<OutputParameter<?>>, BaseFrame>()
        {
            @Override
            public BaseFrame apply(SimpleListProperty<InputParameter<?>> inputParameters, SimpleListProperty<OutputParameter<?>> outputParameters)
            {
                InputParameterCharacter input = (InputParameterCharacter)inputParameters.get(0);
                OutputParameter<String> result = (OutputParameter<String>)outputParameters.get(0);
                result.SetValue(input.GetValue().GetLegalNameAbr());
                if (result.IsConnected())
                {
                    return result.GetFirstConnector().GetEnd().GetParent();
                }
                else
                {
                    return null;
                }
            }
        }, "Get Legal Name (Abr)");
        getLegalAbr.AddInputParameter(ParamType.CHARACTER);
        getLegalAbr.AddOutputParameter(ParamType.TEXT);
        list.add(getLegalAbr);



        return list;
    }
    
    

    

    
}
