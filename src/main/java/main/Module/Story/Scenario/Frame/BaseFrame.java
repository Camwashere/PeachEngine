package main.Module.Story.Scenario.Frame;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.Data.DataBase;
import main.Data.SaveObject;
import main.Maths.Vec.Vec2;
import main.Module.Story.Scenario.Frame.Parameter.*;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameterArray;
import main.Module.Story.Scenario.Frame.StoryFrame.StoryFrame;
import main.Module.Story.Scenario.Scenario;
import main.Tools.InitHelp;
import main.Tools.StringHelp;


import java.util.ArrayList;
import java.util.UUID;

public abstract class BaseFrame extends VBox implements SaveObject
{
    protected final UUID id;

    protected static final double largerFactor = 2;
    protected static final double smallerFactor = 0.5;

    protected final Vec2 delta;
    protected final ContextMenu contextMenu;
    protected final Scenario parent;
    protected final SimpleListProperty<InputParameter<?>> inputParams;
    protected final SimpleListProperty<OutputParameter<?>> outputParams;
    protected final ArrayList<ParameterSlot> paramSlots;

    protected boolean isGlowing;
    protected final TextField name;
    private final FrameType type;
    protected boolean isUpdated = false;



    public BaseFrame(final Scenario scenario, final FrameType frameType)
    {
        inputParams = new SimpleListProperty<>(FXCollections.observableArrayList());
        outputParams = new SimpleListProperty<>(FXCollections.observableArrayList());
        paramSlots = new ArrayList<ParameterSlot>();
        id = UUID.randomUUID();
        type = frameType;
        parent = scenario;
        delta = new Vec2();
        contextMenu = new ContextMenu();
        isGlowing = false;
        name = new TextField("");
        name.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                Text t = new Text(t1);
                t.setFont(name.getFont());
                name.setPrefWidth(t.getLayoutBounds().getWidth() * 1.5);
            }
        });
        boundsInParentProperty().addListener(new ChangeListener<Bounds>()
        {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1)
            {
                Update();
            }
        });
        name.setText("Frame: " + id);
        Init();

    }

    private void Init()
    {
        SimpleListProperty<?> list = new SimpleListProperty<>(FXCollections.observableArrayList());
        setLayoutX(0);
        setLayoutY(0);
        InitHelp.NodeInit(name);
        name.setAlignment(Pos.CENTER);
        getChildren().add(name);
        ContextMenuInit();
        StyleInit();
        EventInit();
        Insets i = new Insets(5, 5, 5, 5);
        setPadding(i);
        MenuItem debug = new MenuItem("Debug");
        debug.setOnAction(evt ->
        {
            PrintDebug();
        });
        contextMenu.getItems().add(debug);
        MenuItem delete = new MenuItem("Delete");
        contextMenu.getItems().add(delete);
        delete.setOnAction(evt ->
        {
            for (InputParameter<?> p : inputParams)
            {
                p.Delete();
            }
            for (OutputParameter<?> p : outputParams)
            {
                p.Delete();
            }
            parent.RemoveFrame(GetID());
        });

        name.setDisable(true);

    }

    protected abstract void ContextMenuInit();

    protected Menu ContextMenuSizeInit() {
        Menu size = new Menu("Size");
        Menu set = new Menu("Set");
        Menu modify = new Menu("Modify");
        MenuItem custom = new MenuItem("Custom");
        MenuItem larger = new MenuItem("Larger");
        MenuItem smaller = new MenuItem("Smaller");
        MenuItem wider = new MenuItem("Wider");
        MenuItem taller = new MenuItem("Taller");
        MenuItem small = new MenuItem("Small");
        MenuItem medium = new MenuItem("Medium");
        MenuItem large = new MenuItem("Large");
        MenuItem wide = new MenuItem("Wide");
        MenuItem tall = new MenuItem("Tall");

        set.getItems().addAll(custom, small, medium, large, wide, tall);
        modify.getItems().addAll(larger, smaller, wider, taller);
        size.getItems().addAll(modify, set);

        larger.setOnAction(evt ->
        {
            setPrefSize(getPrefWidth() * largerFactor, getPrefHeight() * largerFactor);
            SizeChecker();
        });
        smaller.setOnAction(evt ->
        {
            setPrefSize(getPrefWidth() * smallerFactor, getPrefHeight() * smallerFactor);
            SizeChecker();

        });
        wider.setOnAction(evt ->
        {
            setPrefSize(getPrefWidth() * largerFactor, getPrefHeight());
            SizeChecker();
        });
        taller.setOnAction(evt ->
        {
            setPrefSize(getPrefWidth(), getPrefHeight() * largerFactor);
            SizeChecker();
        });


        custom.setOnAction(evt ->
        {
            //TODO implement custom size dialog here
            System.out.println("CUSTOM SIZE DIALOG");
        });
        small.setOnAction(evt ->
        {
            setPrefSize(50, 50);
        });
        medium.setOnAction(evt ->
        {
            setPrefSize(100, 100);

        });
        large.setOnAction(evt ->
        {
            setPrefSize(200, 200);

        });
        wide.setOnAction(evt ->
        {
            setPrefSize(200, 100);

        });
        tall.setOnAction(evt ->
        {
            setPrefSize(100, 200);
        });


        return size;
    }

    public void SetSize(double val)
    {
        setPrefSize(val, val);
    }

    public void PrintDebug()
    {
        System.out.println("ID: " + id);
        System.out.println("Name: " + GetName());
        System.out.println("Type: " + type.name());
        System.out.println("Inputs: ");
        for (InputParameter<?> i : inputParams)
        {
            i.UpdateValue();
            System.out.println(i.GetValue());
        }
        System.out.println("Outputs: ");
        for (OutputParameter<?> o : outputParams)
        {
            System.out.println(o.GetValue());
        }
        System.out.println();
    }


    protected void SizeChecker() {
        if (getPrefWidth() < 25) {
            setPrefWidth(25);
        }
        if (getPrefHeight() < 25) {
            setPrefHeight(25);
        }
    }

    public void SetDelta(MouseEvent evt)
    {
        delta.x = getLayoutX() - evt.getSceneX();
        delta.y = getLayoutY() - evt.getSceneY();
        //Update();
        //evt.consume();
    }

    public void Drag(MouseEvent evt)
    {
        setLayoutX(evt.getSceneX() + delta.x);
        setLayoutY(evt.getSceneY() + delta.y);
        //Update();
    }

    private void EventInit()
    {
        setOnMousePressed(evt ->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                SetDelta(evt);
            }
            else if (evt.getButton() == MouseButton.SECONDARY)
            {
                evt.consume();
            }
        });
        setOnMouseReleased(evt ->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                if (contextMenu.isShowing())
                {
                    contextMenu.hide();
                }
            }
            else if (evt.getButton() == MouseButton.SECONDARY)
            {
                contextMenu.show(this, evt.getScreenX(), evt.getScreenY());
                evt.consume();
            }

        });

        setOnMouseDragged(evt ->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                Drag(evt);
                /*for (BaseFrame f : parent.GetGlowingFrames())
                {
                    f.Drag(evt);
                }*/
                //evt.consume();
            }
        });
    }

    public void Update()
    {
        for (InputParameter<?> i : inputParams)
        {
            i.Update();
        }
        for (OutputParameter<?> i : outputParams)
        {
            i.Update();
        }
    }


    protected abstract void StyleInit();

    public void Glow(boolean bool) {
        isGlowing = bool;
        if (isGlowing) {
            setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        } else {
            setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }

    }

    public final ParameterSlot AddParamSlot()
    {
        ParameterSlot p = new ParameterSlot();
        paramSlots.add(p);
        getChildren().add(p);
        return p;
    }

    public final ParameterSlot GetLastParamSlot() {
        return paramSlots.get(paramSlots.size() - 1);
    }

    public final ParameterSlot GetAvailableInputParamSlot()
    {
        for (final ParameterSlot p : paramSlots)
        {
            if (!p.HasInput())
            {
                return p;
            }
        }
        return AddParamSlot();
    }

    public final ParameterSlot GetAvailableOutputParamSlot() {
        for (final ParameterSlot p : paramSlots) {
            if (!p.HasOutput()) {
                return p;
            }
        }
        return AddParamSlot();
    }
    public final void RemoveParameterSlot(final ParameterSlot slot)
    {
        getChildren().remove(slot);
        paramSlots.remove(slot);
    }


    public final boolean HasSpaceInParamSlotInput()
    {
        if (paramSlots.isEmpty()) {
            return false;
        }
        return !GetLastParamSlot().HasInput();
    }

    public final boolean HasSpaceInParamSlotOutput()
    {
        if (paramSlots.isEmpty()) {
            return false;
        }
        return !GetLastParamSlot().HasOutput();
    }


    public void AddInputParameter(final ParamType paramType)
    {
        AddInputParam(InputParameter.CREATE(this, paramType, false));
    }
    public void AddInputParameter(final ParamType paramType, final boolean array)
    {
        AddInputParam(InputParameter.CREATE(this, paramType, array));
    }
    public void AddInputParameter(final ParamType paramType, final String paramName)
    {
        AddInputParam(InputParameter.CREATE(this, paramType, paramName));
    }
    public void AddInputParameter(final ParamType paramType, final boolean array, final String paramName)
    {
        AddInputParam(InputParameter.CREATE(this, paramType, array, paramName));
    }

    public final boolean ReplaceInputParameter(final InputParameter<?> old, final InputParameter<?> n)
    {
        for (ParameterSlot slot : paramSlots)
        {
            if (slot.Replace(old, n))
            {
                inputParams.remove(old);
                inputParams.add(n);
                System.out.println("REPLACED");
                return true;
            }
        }
        System.out.println("Replace fail");
        return false;
    }
    public final boolean ReplaceOutputParameter(final OutputParameter<?> old, final OutputParameter<?> n)
    {
        for (ParameterSlot slot : paramSlots)
        {
            if (slot.Replace(old, n))
            {
                outputParams.remove(old);
                outputParams.add(n);
                return true;

            }
        }
        return false;
    }
    public final boolean ReplaceParameter(final ParameterBase<?> old, final ParameterBase<?> n)
    {
        if (old instanceof InputParameter<?>)
        {
            if (n instanceof InputParameter<?>)
            {
                return ReplaceInputParameter((InputParameter<?>)old, (InputParameter<?>)n);
            }
        }
        else if (old instanceof OutputParameter<?>)
        {
            if (n instanceof OutputParameter<?>)
            {
                return ReplaceOutputParameter((OutputParameter<?>)old, (OutputParameter<?>)n);
            }
        }
        System.out.println("Replace fail");
        return false;
    }


    public void AddOutputParameter(final ParamType paramType)
    {
        AddOutputParam(new OutputParameter<>(this, paramType, false));
    }
    public void AddOutputParameter(final ParamType paramType, final String paramName)
    {
        AddOutputParam(new OutputParameter<>(this, paramType, false, paramName));
    }
    public void AddOutputParameter(final ParamType paramType, final boolean array)
    {
        if (array)
        {
            AddOutputParam(new OutputParameterArray<>(this, paramType));
        }
        else
        {
            AddOutputParam(new OutputParameter<>(this, paramType, false));
        }
    }
    public void AddOutputParameter(final ParamType paramType, final boolean array, final String paramName)
    {
        if (array)
        {
            AddOutputParam(new OutputParameterArray<>(this, paramType, paramName));
        }
        else
        {
            AddOutputParam(new OutputParameter<>(this, paramType, false, paramName));
        }
    }
    public void AddInputParam(final InputParameter<?> b)
    {
        inputParams.add(b);
        if (this instanceof StoryFrame)
        {
            if (b.GetName().isEmpty())
            {
                b.SetName(StringHelp.EnumFormat(b.GetType()) + "Param" + String.valueOf(inputParams.size()));
            }
        }
        GetAvailableInputParamSlot().SetInput(b);
    }
    public void AddOutputParam(final OutputParameter<?> b)
    {
        outputParams.add(b);
        GetAvailableOutputParamSlot().SetOutput(b);
    }

    public final void RemoveInputParameter(final InputParameter<?> p)
    {
        ArrayList<ParameterSlot> list = new ArrayList<ParameterSlot>(paramSlots);
        for (ParameterSlot slot : list)
        {
            if (slot.ContainsInput(p))
            {
                inputParams.remove(slot.GetInput());
                slot.RemoveInput();
                if (slot.IsEmpty())
                {
                    RemoveParameterSlot(slot);
                }
                break;
            }
        }
    }
    public final void RemoveOutputParameter(final OutputParameter<?> p)
    {
        ArrayList<ParameterSlot> list = new ArrayList<ParameterSlot>(paramSlots);
        for (ParameterSlot slot : list)
        {
            if (slot.ContainsOutput(p))
            {
                outputParams.remove(slot.GetOutput());
                slot.RemoveOutput();
                if (slot.IsEmpty())
                {
                    RemoveParameterSlot(slot);
                }
                break;
            }
        }
    }
    public final void RemoveParameter(final ParameterBase<?> p)
    {
        if (p instanceof InputParameter<?> in)
        {
            RemoveInputParameter(in);
        }
        else if (p instanceof OutputParameter<?> out)
        {
            RemoveOutputParameter(out);
        }
    }

    public final void ClearParameters()
    {
        ArrayList<InputParameter<?>> list = new ArrayList<>(inputParams);
        for (InputParameter<?> in : list)
        {
            in.Delete();
            inputParams.remove(in);
        }
        ArrayList<OutputParameter<?>> outList = new ArrayList<>(outputParams);
        for (OutputParameter<?> out : outList)
        {
            out.Delete();
            outputParams.remove(out);
        }
        ArrayList<ParameterSlot> slots = new ArrayList<>(paramSlots);
        for (ParameterSlot slot : slots)
        {
            RemoveParameterSlot(slot);
        }
    }


    public void Taller() {
        setPrefSize(getPrefWidth(), getPrefHeight() * largerFactor);
    }

    protected void SetBackground(Color c) {
        Background background = new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY));
        setBackground(background);
    }

    protected void SetBorder(Color c) {
        Border border = new Border(new BorderStroke(c, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        setBorder(border);
    }

    public final boolean HasInputs() {
        return !inputParams.isEmpty();
    }

    public void UpdateInputValues()
    {
        if (! isUpdated)
        {
            for (InputParameter<?> p : inputParams)
            {
                for (final ParameterConnector<?> c : p.GetConnectors())
                {
                    c.GetStart().GetParent().UpdateInputValues();
                }
                p.UpdateValue();
            }
        }
        isUpdated = true;
    }


    public final UUID GetID() {
        return id;
    }

    public final FrameType GetFrameType() {
        return type;
    }

    public final SimpleListProperty<InputParameter<?>> GetInputParams() {
        return inputParams;
    }

    public final SimpleListProperty<OutputParameter<?>> GetOutputParams() {
        return outputParams;
    }

    public final Scenario GetParent() {
        return parent;
    }

    public final String GetName() {
        return name.getText();
    }

    public void SetName(final String newName) {
        name.setText(newName);
    }

    public boolean IsGlowing() {
        return isGlowing;
    }

    @Override
    public DataBase ToData()
    {
        DataBase data = new DataBase(this);
        data.Add("ID", GetID());
        data.Add("NAME", GetName());
        data.Add("TYPE", GetFrameType());
        data.Add("POS", new Vec2(getLayoutX(), getLayoutY()));
        data.Add("SIZE", new Vec2(getPrefWidth(), getPrefHeight()));
        // Save input params
        ArrayList<DataBase> inputData = new ArrayList<DataBase>();
        for (final InputParameter<?> i : inputParams)
        {
            inputData.add(i.ToData());
        }
        data.Add("INPUTS", inputData);
        // Save output params
        ArrayList<DataBase> outputData = new ArrayList<DataBase>();
        for (final OutputParameter<?> i : outputParams)
        {
            outputData.add(i.ToData());
        }
        data.Add("OUTPUTS", outputData);
        return data;
    }

    @Override
    public void Load(DataBase data)
    {

    }
}