package main.Tools.Field;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import main.Tools.InitHelp;

public class LabeledTextField extends HBox
{
    protected final Label label;
    protected final TextField field;

    public LabeledTextField(final String name)
    {
        label = new Label(name);
        InitHelp.LabelInit(label);
        field = new TextField();
        InitHelp.NodeInit(field);
        getChildren().addAll(label, field);
    }
    public final void SetName(final String str){label.setText(str);}
    public final void SetPrompt(final String str){field.setPromptText(str);}
    public final void SetText(final String str){field.setText(str);}

    public final String GetName(){return label.getText();}
    public final Label GetLabel(){return label;}
    public final TextField GetField(){return field;}
    public final String GetText(){return field.getText();}
    public final StringProperty GetFieldTextProperty(){return field.textProperty();}
    public final StringProperty GetLabelTextProperty(){return label.textProperty();}
}
