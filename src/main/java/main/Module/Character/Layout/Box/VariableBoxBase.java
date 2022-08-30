package main.Module.Character.Layout.Box;

import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Tools.EditableButton;
import main.Tools.InitHelp;

import java.util.Objects;

public class VariableBoxBase extends TitledPane
{

    private final int id;
    protected final EditableButton name;
    private final ScrollPane pane;
    private final VBox content;

    public VariableBoxBase(final int ID, final String tempName)
    {
        id = ID;
        name = new EditableButton(tempName);
        pane = new ScrollPane();
        content = new VBox();
        pane.setFitToWidth(true);
        pane.setContent(content);
        setContent(pane);
        InitHelp.ButtonInit(name);
        HBox.setHgrow(name, Priority.ALWAYS);
        setGraphic(name);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        name.minWidthProperty().bind(widthProperty().subtract(25));
    }
    public void Delete()
    {
        //GetParent().RemoveContent(id);
    }
    protected void AddContent(final Node node){content.getChildren().add(node);}
    public final EditableButton GetNameButton(){return name;}
    public final int GetID(){return id;}

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof VariableBoxBase other)
        {
            return id==other.GetID();
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, pane, content);
    }
}
