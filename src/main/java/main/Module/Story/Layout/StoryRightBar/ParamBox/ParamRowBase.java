package main.Module.Story.Layout.StoryRightBar.ParamBox;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Tools.InitHelp;

public abstract class ParamRowBase extends HBox
{
    protected final ParamBoxBase parent;
    protected final TextField name;
    protected final ComboBox<ParamType> type;
    protected final CheckBox array;
    protected final Button remove;

    public ParamRowBase(final ParamBoxBase parentBox)
    {
        parent = parentBox;
        name = new TextField();
        name.setPromptText("Parameter Name");
        type = new ComboBox<ParamType>();
        array = new CheckBox("Is Array");
        remove = new Button("X");

        InitHelp.NodeInit(name);
        InitHelp.NodeInit(type);
        InitHelp.NodeInit(array);
        InitHelp.ButtonInit(remove);
    }
}
