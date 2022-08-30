package main.Tools;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.UUID;

public class InfoRow extends HBox
{
    private final Label left;
    private final Label right;

    public InfoRow(String name)
    {
        left = new Label(name);
        right = new Label();
        InitHelp.LabelsInit(left, right);
        //setSpacing(25);
        getChildren().addAll(left, right);
        setAlignment(Pos.CENTER);
        setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

    }

    public final String GetName(){return left.getText();}
    public final String GetValue(){return right.getText();}

    public void SetValue(String val){right.setText(val);}
    public void SetValue(int val){right.setText(String.valueOf(val));}
    public void SetValue(double val){right.setText(String.valueOf(val));}
    public void SetValue(boolean val){right.setText(String.valueOf(val));}
    public void SetValue(UUID val){right.setText(val.toString());}
}
