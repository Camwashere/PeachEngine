package main.Module.Story.Scenario.Frame.StoryFrame.TextEditor;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Frame.Parameter.ParameterBase;
import main.Tools.InitHelp;

public class TextEditorUtilityBar extends HBox
{
    private final TextEditor parent;
    private final Button insertVariable;
    private final Button addInput;
    private final Button addChoice;
    private InputParameter<?> prevChosen = null;

    public TextEditorUtilityBar(final TextEditor editor)
    {
        parent = editor;
        insertVariable = new Button("Insert Variable");
        addInput = new Button("Add Input");
        addChoice = new Button("Add Choice");
        InitHelp.ButtonsInit(addInput, addChoice);
        InitHelp.ButtonInit(insertVariable);
        insertVariable.setOnMouseReleased(evt->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                VariableMenu(evt);
            }

        });
        ContextMenu input = new ContextMenu();
        MenuItem addBool = new MenuItem("Bool");
        addBool.setOnAction(evt->
        {
            parent.GetParent().InputAddHelp(ParamType.BOOL);
        });
        MenuItem addNumber = new MenuItem("Number");
        addNumber.setOnAction(evt->
        {
            parent.GetParent().InputAddHelp(ParamType.NUMBER);
        });
        MenuItem addText = new MenuItem("Text");
        addText.setOnAction(evt->
        {
            parent.GetParent().InputAddHelp(ParamType.TEXT);
        });
        input.getItems().addAll(addBool, addNumber, addText);
        addInput.setOnMouseReleased(evt->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                input.show(addInput, evt.getScreenX(), evt.getScreenY());
            }
        });

        addChoice.setOnMouseReleased(evt->
        {
            parent.GetParent().AddChoice();
        });


        getChildren().add(addInput);
        getChildren().add(insertVariable);
        getChildren().add(addChoice);
    }

    public final void VariableMenu(MouseEvent event)
    {
        ContextMenu menu = new ContextMenu();
        for (final InputParameter<?> v : parent.GetParent().GetInputParams())
        {
            if (v.GetType() != ParamType.FLOW)
            {
                MenuItem item = new MenuItem(v.GetName());
                item.setOnAction(evt->
                {
                    parent.AddTextVariable(v);
                });
                item.setGraphic(new Circle(ParameterBase.SHAPE_SIZE, v.GetColor()));
                menu.getItems().add(item);
            }
        }
        menu.show(this, event.getScreenX(), event.getScreenY());
        parent.EditMode(true);
    }


}
