package main.Module.Character.States;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Module.Character.Game.CharacterBase;
import main.Module.Character.Game.CharacterTemplate.CharacterTemplate;
import main.Module.Character.Game.Variables.Attribute;
import main.Module.Character.Game.Variables.CharacterVariableBase;
import main.Module.Character.Layout.Box.CharacterBox.AttributeBox;
import main.Module.Character.Layout.LeftBar.CharacterTree;
import main.Tools.InitHelp;
import main.Module.Character.Layout.Box.VerticalColumn;

import java.util.UUID;

public abstract class CharacterStateBase extends BorderPane
{
    private final CharacterTree tree;
    private final CharacterTemplate template;
    protected final VerticalColumn attributes;
    protected final VerticalColumn perks;
    protected final VerticalColumn traits;
    protected final VerticalColumn descriptors;
    protected final VerticalColumn mentalStates;

    public CharacterStateBase(final CharacterTree temp)
    {
        tree = temp;
        template = tree.GetTemplate();
        HBox center = new HBox();
        attributes = new VerticalColumn(this,"Attributes");
        perks = new VerticalColumn(this,"Perks");
        traits = new VerticalColumn(this,"Traits");
        descriptors = new VerticalColumn(this,"Descriptors");
        mentalStates = new VerticalColumn(this,"Mental States");
        center.getChildren().addAll(attributes, perks, traits, descriptors, mentalStates);
        setCenter(center);
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                CollapseAll();
            }
        });
        temp.GetParent().layoutBoundsProperty().addListener(new ChangeListener<Bounds>()
        {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1)
            {
                double var = 4;
                attributes.setPrefWidth(t1.getWidth()/var);
                perks.setPrefWidth(t1.getWidth()/var);
                traits.setPrefWidth(t1.getWidth()/var);
                descriptors.setPrefWidth(t1.getWidth()/var);
                mentalStates.setPrefWidth(t1.getWidth()/var);

            }
        });
    }



    public final void RemoveContent(final int id)
    {
        attributes.RemoveContent(id);
        perks.RemoveContent(id);
        traits.RemoveContent(id);
        descriptors.RemoveContent(id);
        mentalStates.RemoveContent(id);
    }
    public final void CollapseAll()
    {
        attributes.CollapseAll();
        perks.CollapseAll();
        traits.CollapseAll();
        descriptors.CollapseAll();
        mentalStates.CollapseAll();
    }

    public final void ClearContent()
    {
        attributes.ClearContent();
        perks.ClearContent();
        traits.ClearContent();
        descriptors.ClearContent();
        mentalStates.ClearContent();
    }

    protected final void AddButton(final VerticalColumn column, EventHandler<? super MouseEvent> event)
    {
        Button b = new Button("+");
        InitHelp.ButtonInit(b);
        VBox.setVgrow(b, Priority.SOMETIMES);
        ContextMenu menu = new ContextMenu();
        MenuItem collapse = new MenuItem("Collapse All");
        collapse.setOnAction(evt->
        {
            column.CollapseAll();
        });
        MenuItem expand = new MenuItem("Expand All");
        expand.setOnAction(evt->
        {
            column.ExpandAll();
        });
        menu.getItems().addAll(collapse, expand);
        b.setContextMenu(menu);
        b.setOnMouseReleased(evt->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                event.handle(evt);
            }
        });
        column.AddChild(b);
    }

    public final CharacterTemplate GetTemplate(){return template;}
    public final CharacterTree GetTree(){return tree;}
    public abstract UUID GetID();

}
