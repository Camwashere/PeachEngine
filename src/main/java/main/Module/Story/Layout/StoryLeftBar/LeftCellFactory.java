package main.Module.Story.Layout.StoryLeftBar;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.*;
import javafx.util.Callback;

import java.util.Objects;

public class LeftCellFactory implements Callback<TreeView<ScenarioItem>, TreeCell<ScenarioItem>>
{
    private static final DataFormat JAVA_FORMAT = new DataFormat("application/x-java-serialized-object");
    private static final String DROP_HINT_STYLE = "-fx-border-color: #eea82f; -fx-border-width: 0 0 2 0; -fx-padding: 3 3 1 3";
    private static final String FOLDER_DROP_STYLE = "-fx-border-color: #eea82f; -fx-border-width: 3 3 2 3; -fx-padding: 3 3 3 3";
    private TreeCell<ScenarioItem> dropZone;
    private TreeItem<ScenarioItem> draggedItem;
    private final ScenarioTree parent;

    public LeftCellFactory(final ScenarioTree leftBar)
    {
        parent = leftBar;
    }

    @Override
    public TreeCell<ScenarioItem> call(TreeView<ScenarioItem> treeView)
    {
        StoryLeftCell cell = new StoryLeftCell(parent);
        cell.setOnDragDetected((MouseEvent event) -> dragDetected(event, cell, treeView));
        cell.setOnDragOver((DragEvent event) -> dragOver(event, cell, treeView));
        cell.setOnDragDropped((DragEvent event) -> drop(event, cell, treeView));
        cell.setOnDragDone((DragEvent event) -> clearDropLocation());

        return cell;
    }

    private void dragDetected(MouseEvent event, TreeCell<ScenarioItem> treeCell, TreeView<ScenarioItem> treeView)
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

    private void dragOver(DragEvent event, TreeCell<ScenarioItem> treeCell, TreeView<ScenarioItem> treeView)
    {
        if (!event.getDragboard().hasContent(JAVA_FORMAT)) return;
        TreeItem<ScenarioItem> thisItem = treeCell.getTreeItem();

        // can't drop on itself
        if (draggedItem == null || thisItem == null || thisItem == draggedItem) return;
        // ignore if this is the root
        if (draggedItem.getParent() == null) {
            clearDropLocation();
            return;
        }

        event.acceptTransferModes(TransferMode.MOVE);
        if (!Objects.equals(dropZone, treeCell))
        {
            clearDropLocation();
            this.dropZone = treeCell;
            if (dropZone.getTreeItem().getValue().IsDirectory())
            {
                dropZone.setStyle(FOLDER_DROP_STYLE);
            }
            else
            {
                dropZone.setStyle(DROP_HINT_STYLE);
            }
        }
    }

    private void drop(DragEvent event, TreeCell<ScenarioItem> treeCell, TreeView<ScenarioItem> treeView)
    {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (!db.hasContent(JAVA_FORMAT)) return;

        TreeItem<ScenarioItem> thisItem = treeCell.getTreeItem();
        TreeItem<ScenarioItem> droppedItemParent = draggedItem.getParent();

        // remove from previous location
        droppedItemParent.getChildren().remove(draggedItem);

        // dropping on parent node makes it the first child
        if (Objects.equals(droppedItemParent, thisItem))
        {
            thisItem.getChildren().add(0, draggedItem);
            treeView.getSelectionModel().select(draggedItem);
        }
        else if (thisItem.getValue().IsDirectory())
        {
            thisItem.getChildren().add(draggedItem);
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

    private void clearDropLocation()
    {
        if (dropZone != null) dropZone.setStyle("");
    }
}
