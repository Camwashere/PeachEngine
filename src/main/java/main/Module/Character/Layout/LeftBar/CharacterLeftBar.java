package main.Module.Character.Layout.LeftBar;

import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.Module.Character.Game.CharacterManager.CharacterManager;
import main.Module.Character.CharacterModule;
import main.Tools.InitHelp;

public class CharacterLeftBar extends VBox
{
    private final CharacterModule parent;
    private final Label label;
    private final ToggleButton template;
    private final CharacterTree tree;
    public CharacterLeftBar(final CharacterModule charModule)
    {

        parent = charModule;
        label = new Label("Characters");
        template = new ToggleButton("Template");
        tree = new CharacterTree(this);
        InitHelp.ButtonInit(template);
        InitHelp.LabelInit(label);
        setVgrow(label, Priority.SOMETIMES);
        setVgrow(template, Priority.SOMETIMES);
        getChildren().add(label);
        getChildren().add(template);
        getChildren().add(tree);
        template.setTooltip(new Tooltip(tree.GetTemplate().toString()));
        template.getTooltip().setShowDuration(Duration.INDEFINITE);
        template.getTooltip().setShowDelay(Duration.seconds(0.4));
        template.setOnMouseEntered(evt->
        {
            template.getTooltip().setText(tree.GetTemplate().toString());
        });
        template.setOnMouseReleased(evt->
        {
            tree.LoadTemplate();
        });
    }

    public final ToggleButton GetTemplateButton(){return template;}
    public final CharacterModule GetParent(){return parent;}
    public final CharacterManager GetManager(){return tree.GetManager();}
    public final CharacterTree GetTree(){return tree;}

}
