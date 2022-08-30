package main.Module.Map.States.Default.Grid.Tool;


import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import javafx.util.StringConverter;
import main.Module.Map.States.Default.Grid.Grid;
import main.Module.Map.States.Default.Layout.Icon;

import org.controlsfx.control.SearchableComboBox;

import java.io.File;

public class IconTool extends GridToolBase
{
    private final SearchableComboBox<Icon> icons = new SearchableComboBox<>();

    public IconTool(final Grid parentGrid)
    {
        super(parentGrid, GridToolType.ICON);
        SetNode(icons);
        Init();
        HBox.setHgrow(icons, Priority.NEVER);
    }
    private void Init()
    {
        icons.setCellFactory(new Callback<ListView<Icon>, ListCell<Icon>>()
        {
            @Override
            public ListCell<Icon> call(ListView<Icon> iconListView)
            {
                return CreateCell();
            }
        });
        icons.setButtonCell(CreateCell());
        File folder = new File("src/main/resources/Map/Icons");
        File[] files = folder.listFiles();
        if (files != null)
        {
            for (final File file : files)
            {
                if (file.exists())
                {
                    icons.getItems().add(new Icon(file.toURI().toString()));
                }

            }
        }
        icons.setConverter(new StringConverter<Icon>()
        {
            @Override
            public String toString(Icon icon)
            {
                if (icon == null)
                {
                    return null;
                }
                return icon.GetName();
            }

            @Override
            public Icon fromString(String s)
            {
                for (final Icon i : icons.getItems())
                {
                    if (s.equals(i.GetName()))
                    {
                        return i;
                    }
                }
                return icons.getValue();
            }
        });

        icons.getSelectionModel().selectFirst();
    }
    private ListCell<Icon> CreateCell()
    {
        return new ListCell<Icon>()
        {
            @Override
            protected void updateItem(Icon item, boolean empty)
            {
                super.updateItem(item, empty);
                if (item == null || empty)
                {
                    setText(null);
                    setGraphic(null);
                }
                else
                {
                    setText(item.GetName());
                    ImageView view = new ImageView(item);
                    view.setFitWidth(20);
                    view.setFitHeight(20);
                    setGraphic(view);
                }
            }
        };
    }

    public final Icon GetValue(){return icons.getValue();}
}
