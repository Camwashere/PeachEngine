package main.Module.Character.Layout.LeftBar;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.util.Callback;
import main.Module.Character.Game.CharacterBase;

import java.util.Objects;
import java.util.UUID;

public class CharacterTreeCellFactory implements Callback<TreeView<UUID>, TreeCell<UUID>>
{
    private static final DataFormat JAVA_FORMAT = DataFormat.PLAIN_TEXT;
    private static final String DROP_HINT_STYLE = "-fx-border-color: #eea82f; -fx-border-width: 0 0 2 0; -fx-padding: 3 3 1 3";
    private static final String FOLDER_DROP_STYLE = "-fx-border-color: #eea82f; -fx-border-width: 3 3 2 3; -fx-padding: 3 3 3 3";
    private final CharacterTree parent;
    private TreeCell<UUID> dropZone;
    private TreeItem<UUID> draggedItem;

    public CharacterTreeCellFactory(final CharacterTree tree)
    {
        parent = tree;
    }

    @Override
    public TreeCell<UUID> call(TreeView<UUID> treeView)
    {
        CharacterTreeCell cell = new CharacterTreeCell(parent);


        cell.setOnDragDetected((MouseEvent event) -> dragDetected(event, cell, treeView));
        cell.setOnDragOver((DragEvent event) -> dragOver(event, cell, treeView));
        cell.setOnDragDropped((DragEvent event) -> drop(event, cell, treeView));
        cell.setOnDragDone((DragEvent event) -> clearDropLocation());

        return cell;
    }

    private void dragDetected(MouseEvent event, TreeCell<UUID> treeCell, TreeView<UUID> treeView)
    {
        draggedItem = treeCell.getTreeItem();
        if (draggedItem==null) return;

        // root can't be dragged
        if (draggedItem.getParent() == null) return;
        Dragboard db = treeCell.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        content.put(JAVA_FORMAT, draggedItem.getValue());
        db.setContent(content);
        db.setDragView(treeCell.snapshot(null, null));
        event.consume();
    }

    private void dragOver(DragEvent event, TreeCell<UUID> treeCell, TreeView<UUID> treeView)
    {
        if (!event.getDragboard().hasContent(JAVA_FORMAT)) return;
        TreeItem<UUID> thisItem = treeCell.getTreeItem();

        // can't drop on itself
        if (draggedItem == null || thisItem == null || thisItem == draggedItem) return;
        // ignore if this is the root
        if (draggedItem.getParent() == null)
        {
            clearDropLocation();
            return;
        }

        event.acceptTransferModes(TransferMode.MOVE);
        if (!Objects.equals(dropZone, treeCell))
        {
            clearDropLocation();
            this.dropZone = treeCell;
            if (parent.GetLeft().GetManager().IsClass(dropZone.getTreeItem().getValue()))
            {
                dropZone.setStyle(FOLDER_DROP_STYLE);
            }
            else
            {
                dropZone.setStyle(DROP_HINT_STYLE);
            }
        }
    }

    private void drop(DragEvent event, TreeCell<UUID> treeCell, TreeView<UUID> treeView)
    {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (!db.hasContent(JAVA_FORMAT)) return;

        TreeItem<UUID> thisItem = treeCell.getTreeItem();
        TreeItem<UUID> droppedItemParent = draggedItem.getParent();

        // remove from previous location
        droppedItemParent.getChildren().remove(draggedItem);

        // dropping on parent node makes it the first child
        if (Objects.equals(droppedItemParent, thisItem))
        {
            if (IsClass(draggedItem.getValue()))
            {
                thisItem.getChildren().add(0, draggedItem);
            }
            else
            {
                thisItem.getChildren().add(draggedItem);
            }
            treeView.getSelectionModel().select(draggedItem);
        }
        // if this item is a class, add it to the top
        else if (parent.GetLeft().GetManager().IsClass(thisItem.getValue()))
        {
            thisItem.getChildren().add(0, draggedItem);
            treeView.getSelectionModel().select(draggedItem);
        }
        else
        {
            // add to new location
            int indexInParent = thisItem.getParent().getChildren().indexOf(thisItem);
            thisItem.getParent().getChildren().add(indexInParent + 1, draggedItem);
        }
        treeView.getSelectionModel().select(draggedItem);
        event.setDropCompleted(success);
    }
    private boolean IsClass(UUID val){return parent.GetLeft().GetManager().IsClass(val);}

    private void clearDropLocation()
    {
        if (dropZone != null) dropZone.setStyle("");
    }
}
