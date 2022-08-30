package main.Module.Story.Scenario.Frame.StoryFrame;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.Data.Frame.StoryFrameData;
import main.Debug.Debug;
import main.Maths.Vec.Vec2;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.FrameType;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Frame.Parameter.ParameterBase;
import main.Module.Story.Scenario.Frame.StoryFrame.TextEditor.TextEditor;
import main.Module.Story.Scenario.Scenario;
import main.Module.Story.Scenario.ScenarioState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoryFrame extends BaseFrame
{
    private final TextEditor editor;
    private final SimpleBooleanProperty expanded = new SimpleBooleanProperty(false);

    public StoryFrame(final Scenario s)
    {
        super(s, FrameType.STORY);
        editor = new TextEditor(this);
        getChildren().add(editor);
        Init();
        InputAddHelp(ParamType.FLOW);
    }
    public StoryFrame(final Scenario s, final StoryFrameData data)
    {
        super(s, data.baseData());
        editor = new TextEditor(this);
        editor.SetText(data.text());
        Init();
        getChildren().add(1, editor);
    }
    private void Init()
    {
        EventInit();
        NameInit();
        EditorInit();
        editor.EditMode(false);
    }
    private void NameInit()
    {
        name.setOnKeyPressed(evt->
        {
            if (evt.getCode() == KeyCode.ENTER)
            {
                name.setDisable(true);
            }
        });
        name.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (! t1)
                {
                    name.setDisable(true);
                }
            }
        });


        name.setDisable(true);
    }
    private void EditorInit()
    {
        editor.GetEditor().focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (!t1)
                {
                    editor.EditMode(false);
                }
            }
        });
        editor.setMinHeight(getPrefHeight()-10);
        prefHeightProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                editor.setMinHeight(t1.doubleValue()-10);
            }
        });
    }
    private void EventInit()
    {
        addEventHandler(MouseEvent.MOUSE_RELEASED, evt->
        {
            if (parent.GetCurrentState().equals(ScenarioState.PARAM))
            {
                if (evt.getButton() == MouseButton.PRIMARY)
                {
                    //parent.GetActiveParameterConnector().GetType();
                    //AddInputParameter(parent.GetActiveParameterConnector().GetType());
                    //inputParams.get(inputParams.size()-1).Connect(parent.GetActiveParameterConnector());
                }
            }
            if (evt.getButton() == MouseButton.PRIMARY && evt.getClickCount() > 1)
            {
                if (name.getBoundsInParent().contains(evt.getX(), evt.getY()))
                {
                    System.out.println("NAME CONTAIN");
                    name.setDisable(false);
                    name.requestFocus();
                }
                else if (editor.getBoundsInParent().contains(evt.getX(), evt.getY()))
                {
                    editor.EditMode(true);
                }
            }
        });
    }
    @Override
    protected void ContextMenuInit()
    {
        MenuItem expand = new MenuItem("Expand");
        expand.setOnAction(evt->
        {
            Expand();
        });
        Menu edit = new Menu("Edit");
        MenuItem editName = new MenuItem("Name");
        MenuItem editBody = new MenuItem("Body");
        editName.setOnAction(evt->
        {
            name.setDisable(false);
            name.requestFocus();

        });
        editBody.setOnAction(evt->
        {
            editor.EditMode(true);
            editor.GetEditor().requestFocus();
        });

        MenuItem addChoice = new MenuItem("Add Choice");
        addChoice.setOnAction(evt->
        {
            AddChoice();
            evt.consume();
        });


        Menu addInputParam = new Menu("Add Input");

        MenuItem boolParam = new MenuItem("Bool");
        boolParam.setOnAction(evt->
        {
            InputAddHelp(ParamType.BOOL);
        });

        MenuItem numberParam = new MenuItem("Number");
        numberParam.setOnAction(evt->
        {
            InputAddHelp(ParamType.NUMBER);
        });
        MenuItem textParam = new MenuItem("Text");
        textParam.setOnAction(evt->
        {
            InputAddHelp(ParamType.TEXT);
        });
        addInputParam.getItems().addAll(boolParam, numberParam, textParam);

        edit.getItems().addAll(editName, editBody);

        contextMenu.getItems().addAll(expand, edit, addChoice, addInputParam, ContextMenuSizeInit());
        contextMenu.setAutoHide(true);
        contextMenu.setAutoFix(true);
    }

    public void AddChoice()
    {
        OutputParameter<BaseFrame> param = new OutputParameter<>(this, ParamType.FLOW, false, "Choice " + String.valueOf(outputParams.size()+1));
        ParamLabelInit(param);
        AddOutputParam(param);
    }

    public void InputAddHelp(final ParamType t)
    {
        InputParameter<?> param = InputParameter.CREATE(this, t, false);
        ParamLabelInit(param);
        if (t == ParamType.FLOW)
        {
            param.SetName("Input");
        }
        AddInputParam(param);
    }

    @Override
    protected void StyleInit()
    {
        setFocusTraversable(false);
        Background background = new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY));
        setBackground(background);
        Border b = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        setBorder(b);
        setPrefSize(200, 150);
    }

    private void ParamLabelInit(final ParameterBase<?> param)
    {
        ContextMenu menu = new ContextMenu();
        MenuItem edit = new MenuItem("Edit");

        MenuItem delete = new MenuItem("Delete");
        edit.setOnAction(evt->
        {
            EditParamName(param);
        });
        delete.setOnAction(evt->
        {
            editor.RemoveTextVariable(param);
            RemoveParameter(param);
        });
        if (param.GetType() == ParamType.FLOW && param.IsInput())
        {
            menu.getItems().add(edit);
        }
        else
        {
            menu.getItems().addAll(edit, delete);
        }
        param.GetLabel().setContextMenu(menu);
        param.GetLabel().setOnMouseReleased(event->
        {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1)
            {
                EditParamName(param);
                event.consume();
            }
            else if (event.getButton() == MouseButton.SECONDARY)
            {
                event.consume();
            }

        });
    }
    private void EditParamName(final ParameterBase<?> param)
    {
        TextField field = new TextField(param.GetName());
        field.setOnKeyReleased(evt->
        {
            if (evt.getCode().equals(KeyCode.ENTER))
            {
                StoryFrame.this.requestFocus();
            }

        });
        param.GetLabel().setGraphic(field);
        param.GetLabel().setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        field.requestFocus();
        field.selectAll();
        field.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (! t1)
                {
                    if (!field.getText().isEmpty())
                    {
                        param.GetLabel().setText(field.getText());
                    }
                    param.GetLabel().setGraphic(null);
                    param.GetLabel().setContentDisplay(ContentDisplay.TEXT_ONLY);
                    editor.GetEditor().selectRange(editor.GetEditor().getLength(), editor.GetEditor().getLength());
                }
            }
        });
    }

    public final void Expand()
    {
        Dialog<StoryFrame> dialog = new Dialog<>();
        dialog.setTitle(GetName());
        dialog.setResizable(true);
        dialog.setWidth(getScene().getWidth());
        dialog.setHeight(getScene().getHeight());
        Vec2 prevLayout = new Vec2(getLayoutX(), getLayoutY());
        Vec2 prevSize = new Vec2(getPrefWidth(), getPrefHeight());
        parent.RemoveFrame(this.GetID());
        dialog.getDialogPane().setContent(this);
        setPrefSize(900, 700);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        dialog.getDialogPane().lookupButton(ButtonType.CLOSE).setVisible(false);
        expanded.set(true);

        dialog.showAndWait();
        parent.AddFrame(this);
        expanded.set(false);
        setLayoutX(prevLayout.x);
        setLayoutY(prevLayout.y);
        setPrefSize(prevSize.x, prevSize.y);
    }

    public final String Read()
    {
        String text = editor.GetText();
        for (final InputParameter<?> in : inputParams)
        {
            if (in.GetType() != ParamType.FLOW)
            {
                if (in.GetValue() == null)
                {
                    Debug.println("Null input param on frame: " + GetID(), 2);
                }
                else
                {
                    text = text.replaceAll(in.GetName(), in.GetValue().toString());
                }
            }
        }
        return text;
    }



    public final SimpleBooleanProperty GetExpandedProp(){return expanded;}
    public final boolean IsExpanded(){return expanded.get();}
    public final StoryFrameData AsData(){return new StoryFrameData(AsBaseData(), editor.GetText());}
}
