package main.Module.Character.Layout;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import main.Module.Character.Game.CharacterBase;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Module.Character.Layout.LeftBar.CharacterTree;
import main.Tools.InitHelp;
import main.Tools.ValueButton;

import java.util.UUID;

public class DynamicCharacterMenu extends PopupControl
{
    private final CharacterTree parent;
    private final TreeView<UUID> tree;
    private final ValueButton<StoryCharacter> box;
    public DynamicCharacterMenu(final CharacterTree parentTree, final ValueButton<StoryCharacter> parentBox)
    {
        parent = parentTree;
        box = parentBox;
        tree = new TreeView<UUID>();
        BorderPane pane = new BorderPane();
        pane.setCenter(tree);
        getScene().setRoot(pane);
        tree.setRoot(parent.getRoot());
        SelfInit();
        CellFactoryInit();
        TreeInit();
    }
    private void SelfInit()
    {
        setAutoFix(true);
        setAutoHide(true);
        tree.setShowRoot(false);
        tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tree.setFocusTraversable(false);
        tree.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tree.setEditable(false);
        InitHelp.GrowInit(tree);
    }
    private void CellFactoryInit()
    {
        tree.setCellFactory(new Callback<TreeView<UUID>, TreeCell<UUID>>()
        {
            @Override
            public TreeCell<UUID> call(TreeView<UUID> UUIDTreeView)
            {
                return new TreeCell<UUID>()
                {
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
                            CharacterBase b = parent.GetManager().GetCharacterBase(item);
                            if (b != null)
                            {
                                setText(b.GetDisplayName());
                                ImageView view = new ImageView(b.GetIcon());
                                view.setFitWidth(20);
                                view.setFitHeight(20);
                                setGraphic(view);
                            }
                            else
                            {
                                setText(null);
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        });
    }
    private void TreeInit()
    {
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<UUID>>()
        {
            @Override
            public void changed(ObservableValue<? extends TreeItem<UUID>> observableValue, TreeItem<UUID> UUIDTreeItem, TreeItem<UUID> t1)
            {
                if (parent.GetManager().IsCharacter(t1.getValue()))
                {
                    box.SetValue(parent.GetManager().GetCharacter(t1.getValue()));
                    hide();
                }
            }
        });
    }
}
