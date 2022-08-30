package main.Module.Character.Layout.LeftBar;

import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import main.Debug.Debug;
import main.Tools.InitHelp;

import java.util.UUID;

public class CharacterTreeCell extends TreeCell<UUID>
{
    private static final Image FOLDER_IMAGE = new Image("C://Users//perso//IdeaProjects//PeachEngine//src//main//resources//Story//folderIcon.png");

    private final CharacterTree parent;
    private final ContextMenu menu;
    private final MenuItem load;
    private final MenuItem addCharacter;
    private final MenuItem addClass;
    private final MenuItem delete;
    private final MenuItem debug;


    public CharacterTreeCell(final CharacterTree tree)
    {
        parent = tree;
        menu = new ContextMenu();
        load = new MenuItem("Load");
        addCharacter = new MenuItem("Add Character");
        addClass = new MenuItem("Add Class");
        delete = new MenuItem("Delete");
        debug = new MenuItem("Debug");
        load.setOnAction(evt->
        {
            parent.Load(getTreeItem().getValue());
        });
        addCharacter.setOnAction(evt->
        {
            parent.AddCharacter();

        });
        addClass.setOnAction(evt->
        {
            parent.AddClass();
        });
        delete.setOnAction(evt->
        {
            parent.Delete(getTreeItem().getValue());
            parent.getSelectionModel().select(getTreeItem().getParent());
            parent.Load(getTreeItem().getParent().getValue());
            getTreeItem().getParent().getChildren().remove(getTreeItem());
        });
        debug.setOnAction(evt->
        {
            Debug.println(parent.GetManager().GetCharacterBase(getTreeItem().getValue()).toString());
        });
        menu.getItems().addAll(load, addCharacter, addClass);
        setContextMenu(menu);
        setOnMouseClicked(evt->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                tree.getSelectionModel().select(getTreeItem());
                if (evt.getClickCount()>1)
                {
                    parent.Load(getTreeItem().getValue());

                }
            }
            else if (evt.getButton() == MouseButton.SECONDARY)
            {
                tree.getSelectionModel().select(getTreeItem());
                menu.getItems().clear();
                if (getTreeItem()==null)
                {
                    menu.getItems().addAll(addCharacter, addClass);
                }
                else if (parent.GetLeft().GetManager().IsClass(getTreeItem().getValue()))
                {
                    menu.getItems().addAll(load, addCharacter, addClass, delete);
                }
                else if (getTreeItem().getValue().equals(parent.GetCurrent()))
                {
                    menu.getItems().addAll(addCharacter, addClass, delete);
                }
                else
                {
                    menu.getItems().addAll(load, addCharacter, addClass, delete);
                }
                menu.getItems().add(debug);
            }
        });


    }
    @Override
    protected void updateItem(UUID item, boolean empty)
    {
        super.updateItem(item, empty);
        if (item == null || empty)
        {
            setText(null);
            setGraphic(null);
        }
        else
        {
            setText(parent.GetManager().GetCharacterBase(item).GetDisplayName());
            if (parent.GetManager().IsClass(item))
            {
                ImageView imageView = new ImageView();
                imageView.setImage(FOLDER_IMAGE);
                imageView.setFitHeight(20);
                imageView.setFitWidth(20);
                setGraphic(imageView);
            }
        }
    }
}
