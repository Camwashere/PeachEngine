package main.Module.Story.Scenario.Frame.StoryFrame.TextEditor;

import javafx.scene.layout.VBox;

public class TextEditorTopBar extends VBox
{
    private final TextEditor parent;
    private final TextEditorUtilityBar utilityBar;
    private final TextEditorFontBar fontBar;

    public TextEditorTopBar(final TextEditor parentEditor)
    {
        parent = parentEditor;
        utilityBar = new TextEditorUtilityBar(parent);
        fontBar = new TextEditorFontBar(parent);
        //getChildren().add(fontBar);
        getChildren().add(utilityBar);
    }


}
