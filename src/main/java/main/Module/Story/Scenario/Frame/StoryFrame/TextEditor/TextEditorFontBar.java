package main.Module.Story.Scenario.Frame.StoryFrame.TextEditor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import main.Tools.Field.NumericTextField;

public class TextEditorFontBar extends HBox
{
    private final TextEditor parent;
    private final ComboBox<String> fonts;
    private final NumericTextField fontSize = new NumericTextField();
    private final ToggleButton bold = new ToggleButton("Bold");
    private final ToggleButton italic = new ToggleButton("Italic");
    private final ToggleButton underLine = new ToggleButton("Underline");

    public TextEditorFontBar(final TextEditor editor)
    {
        parent = editor;
        fonts = new ComboBox<>();
        FontsInit();
        getChildren().add(fonts);



    }
    private void FontsInit()
    {
        for (final String s : Font.getFamilies())
        {
            fonts.getItems().add(s);
        }
        fonts.setCellFactory(new Callback<ListView<String>, ListCell<String>>()
        {
            @Override
            public ListCell<String> call(ListView<String> stringListView)
            {
                return new ListCell<String>()
                {
                    @Override
                    protected void updateItem(String item, boolean empty)
                    {
                        super.updateItem(item, empty);

                        if (item == null || empty)
                        {
                            setText(null);
                            setGraphic(null);
                        }
                        else
                        {
                            setText(item);
                            setFont(Font.font(item));
                        }
                    }
                };
            }
        });
        fonts.setButtonCell(new ListCell<String>()
        {
            @Override
            protected void updateItem(String item, boolean empty)
            {
                super.updateItem(item, empty);

                if (item == null || empty)
                {
                    setText(null);
                    setGraphic(null);
                }
                else
                {
                    setText(item);
                    setFont(Font.font(item));
                }
            }
        });
        fonts.setValue(Font.getDefault().getFamily());
        fonts.valueProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                parent.GetCurrentStyle().SetAll(t1);
            }
        });

    }

}
