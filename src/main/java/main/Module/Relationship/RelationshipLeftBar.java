package main.Module.Relationship;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Tools.*;
import main.Tools.Field.LabeledNumericTextField;
import main.Tools.Field.LabeledTextField;
import main.Tools.Field.Vec2NumericTextField;

public class RelationshipLeftBar extends VBox
{
    private final RelationshipModule parent;
    private final CheckBox displayLines;
    private final CheckBox displayStart;
    private final LabeledTextField xName;
    private final LabeledTextField yName;
    private final LabeledNumericTextField tickUnit;
    private final LabeledNumericTextField lowerBound;
    private final LabeledNumericTextField upperBound;
    private final Vec2NumericTextField startPos;
    public RelationshipLeftBar(final RelationshipModule parentMod)
    {
        parent = parentMod;
        displayLines = new CheckBox("Display Lines");
        displayStart = new CheckBox("Display Start");
        xName = new LabeledTextField("X Axis");
        yName = new LabeledTextField("Y Axis");
        tickUnit = new LabeledNumericTextField("Tick Unit");
        lowerBound = new LabeledNumericTextField("Min Value");
        upperBound = new LabeledNumericTextField("Max Value");
        startPos = new Vec2NumericTextField("Start Point");
        DisplayInit();
        AxisNameInit();
        ValueInit();
        DefaultStartInit();
    }
    private void DisplayInit()
    {
        displayLines.setSelected(true);
        displayStart.setSelected(true);
        //InitHelp.NodeInit(displayLines);
        //InitHelp.NodeInit(displayStart);

        GetGrid().GetShowLinesProp().bind(displayLines.selectedProperty());
        GetGrid().GetShowStartProp().bind(displayStart.selectedProperty());
        getChildren().addAll(displayLines, displayStart);
    }
    
    private void AxisNameInit()
    {
        Button confirmX = new Button("Edit");
        Button confirmY = new Button("Edit");
        InitHelp.ButtonsInit(confirmX, confirmY);
        xName.setDisable(true);
        yName.setDisable(true);
        confirmX.setOnMouseReleased(evt->
        {
            if (confirmX.getText().equals("Edit"))
            {
                xName.setDisable(false);
                xName.requestFocus();
                xName.GetField().selectAll();
                confirmX.setText("Confirm");
            }
            else
            {
                parent.GetCenter().GetGrid().SetXName(xName.GetText());
                xName.setDisable(true);
                confirmX.setText("Edit");

            }
        });
        confirmY.setOnMouseReleased(evt->
        {
            if (confirmY.getText().equals("Edit"))
            {
                yName.setDisable(false);
                yName.requestFocus();
                yName.GetField().selectAll();
                confirmY.setText("Confirm");
            }
            else
            {
                parent.GetCenter().GetGrid().SetYName(yName.GetText());
                yName.setDisable(true);
                confirmY.setText("Edit");

            }
        });
        HBox xBox = new HBox();
        HBox yBox = new HBox();
        xBox.getChildren().addAll(xName, confirmX);
        yBox.getChildren().addAll(yName, confirmY);
        getChildren().addAll(xBox, yBox);
        xName.SetText("X");
        yName.SetText("Y");
    }

    private void ValueInit()
    {
        tickUnit.GetMaxProp().bind(upperBound.GetValueProp());
        tickUnit.SetMin(0);
        startPos.GetXField().GetMinProp().bind(lowerBound.GetValueProp());
        startPos.GetYField().GetMinProp().bind(lowerBound.GetValueProp());
        startPos.GetXField().GetMaxProp().bind(upperBound.GetValueProp());
        startPos.GetYField().GetMaxProp().bind(upperBound.GetValueProp());

        lowerBound.GetValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1.doubleValue()>upperBound.GetDouble())
                {
                    lowerBound.SetValue(upperBound.GetValue());
                }
            }
        });
        upperBound.GetValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1.doubleValue() < lowerBound.GetDouble())
                {
                    upperBound.SetValue(lowerBound.GetValue());
                }
            }
        });

        GetGrid().GetTickUnitProp().bind(tickUnit.GetValueProp());
        GetGrid().GetLowerBoundProp().bind(lowerBound.GetValueProp());
        GetGrid().GetUpperBoundProp().bind(upperBound.GetValueProp());
        lowerBound.SetValue(-100);
        upperBound.SetValue(100);
        tickUnit.SetValue(20);
        lowerBound.SetSlidePrecision(0);
        upperBound.SetSlidePrecision(0);
        tickUnit.SetSlidePrecision(0);

        getChildren().addAll(tickUnit, lowerBound, upperBound);
    }
    private void DefaultStartInit()
    {

        startPos.GetXValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                GetGrid().SetStartLocationX(t1.doubleValue());
            }
        });
        startPos.GetYValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                GetGrid().SetStartLocationY(t1.doubleValue() * -1);
            }
        });
        GetGrid().GetStartPoint().GetOffsetProp().GetXProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                startPos.SetXValue(t1);
            }
        });
        GetGrid().GetStartPoint().GetOffsetProp().GetYProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                startPos.SetYValue(t1.doubleValue() * -1);
            }
        });
        getChildren().add(startPos);
    }


    public final RelationshipModule GetParent(){return parent;}
    public final RelationshipGrid GetGrid(){return parent.GetCenter().GetGrid();}
}
