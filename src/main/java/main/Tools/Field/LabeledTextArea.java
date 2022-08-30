package main.Tools.Field;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Tools.InitHelp;

public class LabeledTextArea extends HBox
{
    protected final Label name;
    protected final TextArea area;


    public LabeledTextArea(final String str)
    {
        name = new Label(str);
        area = new TextArea();
        setAlignment(Pos.TOP_LEFT);
        InitHelp.LabelInit(name);
        InitHelp.NodeInit(area);
        name.setAlignment(Pos.TOP_LEFT);
        getChildren().add(name);
        getChildren().add(area);
        area.setWrapText(true);
        HBox.setHgrow(name, Priority.SOMETIMES);
        HBox.setHgrow(area, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.NEVER);
        //name.setPrefWidth(20);
        area.setPrefWidth(40);
        setSpacing(5);

    }

    public final void SetText(final String txt){area.setText(txt);}
    public final Label GetLabel(){return name;}
    public final TextArea GetTextArea(){return area;}
    public final String GetText(){return area.getText();}

}
