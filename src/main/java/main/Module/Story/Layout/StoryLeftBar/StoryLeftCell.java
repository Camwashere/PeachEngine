package main.Module.Story.Layout.StoryLeftBar;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.util.StringConverter;
import main.Module.Story.Scenario.ScenarioType;

public class StoryLeftCell extends TextFieldTreeCell<ScenarioItem>
{
    private static final Image FOLDER_IMAGE = new Image("C://Users//perso//IdeaProjects//PeachEngine//src//main//resources//Story//folderIcon.png");
    private static final Image EFFECT_IMAGE = new Image("C://Users//perso//IdeaProjects//PeachEngine//src//main//resources//Story//effectIcon.png");
    private static final Image SCENARIO_IMAGE = new Image("C://Users//perso//IdeaProjects//PeachEngine//src//main//resources//Story//scenarioIcon.png");
    private static final Image MAIN_IMAGE = new Image("C://Users//perso//IdeaProjects//PeachEngine//src//main//resources//Story//mainIcon.png");
    private final ContextMenu menu;
    private final ScenarioTree parent;
    private final MenuItem loadScenario;
    private final MenuItem newScenario;
    private final MenuItem newEffect;
    private final MenuItem newFolder;
    private final MenuItem rename;
    private final MenuItem delete;

    public StoryLeftCell(final ScenarioTree leftBar)
    {
        menu = new ContextMenu();
        parent = leftBar;
        loadScenario = new MenuItem("Load");
        newScenario = new MenuItem("New Scenario");
        newEffect = new MenuItem("New Effect");
        newFolder = new MenuItem("New Folder");
        rename = new MenuItem("Rename");
        delete = new MenuItem("Delete");
        MenuInit();
        EventInit();
        setConverter(new StringConverter<ScenarioItem>()
        {

            @Override
            public String toString(ScenarioItem scenarioItem)
            {
                return scenarioItem.GetName();
            }

            @Override
            public ScenarioItem fromString(String s)
            {
                return new ScenarioItem(s, getTreeItem().getValue().GetID());
            }
        });

    }

    private void MenuInit()
    {
        loadScenario.setOnAction(evt->
        {
            parent.LoadCurrentScenario();
        });
        newScenario.setOnAction(evt->
        {
            parent.AddScenario();
        });
        newEffect.setOnAction(evt->
        {
            parent.AddEffect();
        });
        newFolder.setOnAction(evt->
        {
            parent.AddFolder();

        });
        rename.setOnAction(evt->
        {
            startEdit();

        });
        delete.setOnAction(evt->
        {
            parent.DeleteSelectedScenario();
        });
        menu.getItems().addAll(loadScenario, newScenario, newFolder, rename, delete);
    }

    private void EventInit()
    {
        setOnMouseClicked(evt->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                menu.hide();
                getTreeView().getSelectionModel().select(getTreeItem());
            }
            else if (evt.getButton() == MouseButton.SECONDARY)
            {
                getTreeView().getSelectionModel().select(getTreeItem());
                menu.getItems().clear();
                if (getTreeItem()==null)
                {
                    menu.getItems().addAll(newScenario, newEffect, newFolder);
                }
                else if (getTreeItem().getValue().IsDirectory())
                {
                    menu.getItems().addAll(newScenario, newEffect, newFolder, rename, delete);
                }
                else if (parent.GetScenario(getTreeItem().getValue().GetID()).IsMain())
                {
                    if (getTreeItem().getValue().GetID().equals(parent.GetCurrentScenario().GetID()))
                    {
                        menu.getItems().addAll(newScenario, newEffect, newFolder, rename);
                    }
                    else
                    {
                        menu.getItems().addAll(loadScenario, newScenario, newEffect, newFolder, rename);
                    }
                }
                else if (getTreeItem().getValue().GetID().equals(parent.GetCurrentScenario().GetID()))
                {
                    menu.getItems().addAll(newScenario, newEffect, newFolder, rename, delete);
                }
                else
                {
                    menu.getItems().addAll(loadScenario, newScenario, newEffect, newFolder, rename, delete);
                }
                menu.show(this, evt.getScreenX(), evt.getScreenY());
            }
        });
    }

    @Override
    public void commitEdit(ScenarioItem scenarioItem)
    {
        if (scenarioItem != null)
        {
            super.commitEdit(scenarioItem);
            if (scenarioItem.GetID() != null && scenarioItem.GetName() != null)
            {
                parent.GetScenario(scenarioItem.GetID()).SetName(scenarioItem.GetName());
            }
        }
    }

    @Override
    public void updateItem(ScenarioItem item, boolean empty)
    {
        super.updateItem(item, empty);
        if (item == null || empty)
        {
            setText(null);
            setGraphic(null);
        }
        else
        {
            if (!isEditing())
            {
                setText(item.GetName());
            }
            ImageView imageView = new ImageView();
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            if (item.IsDirectory())
            {
                imageView.setImage(FOLDER_IMAGE);
                setGraphic(imageView);
            }
            else if (parent.GetScenario(item.GetID()).GetType() == ScenarioType.CHARACTER_EFFECT)
            {

                imageView.setImage(EFFECT_IMAGE);
                setGraphic(imageView);
            }
            else if (parent.GetScenario(item.GetID()).GetType() == ScenarioType.STANDARD)
            {
                imageView.setImage(SCENARIO_IMAGE);
                setGraphic(imageView);
            }
            else if (parent.GetScenario(item.GetID()).GetType() == ScenarioType.MAIN)
            {
                imageView.setImage(MAIN_IMAGE);
                setGraphic(imageView);
            }
        }
    }
}
