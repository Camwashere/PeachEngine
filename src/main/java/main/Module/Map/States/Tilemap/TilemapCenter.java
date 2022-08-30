package main.Module.Map.States.Tilemap;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import main.Debug.Debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TilemapCenter extends StackPane
{
    private final MapStateTilemap parent;
    private final ArrayList<TilemapLayer> layers = new ArrayList<>();

    public TilemapCenter(final MapStateTilemap parentState)
    {
        parent = parentState;
        DefaultStyle();
        setOnDragOver(evt->
        {
            final Dragboard db = evt.getDragboard();

            final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                    || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpeg")
                    || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpg");

            if (db.hasFiles())
            {
                if (isAccepted)
                {
                    setStyle("-fx-border-color: green;"
                            + "-fx-border-width: 5;"
                            + "-fx-background-color: #C6C6C6;"
                            + "-fx-border-style: solid;");
                    evt.acceptTransferModes(TransferMode.COPY);
                }
                else
                {
                    setStyle("-fx-border-color: red;"
                            + "-fx-border-width: 5;"
                            + "-fx-background-color: #C6C6C6;"
                            + "-fx-border-style: solid;");
                    evt.acceptTransferModes(TransferMode.NONE);
                }
            }
            else
            {
                evt.consume();
            }
        });
        setOnDragDropped(evt->
        {
            final Dragboard db = evt.getDragboard();
            boolean success = false;
            if (db.hasFiles())
            {
                success = true;
                // Only get the first file from the list
                for (final File file : db.getFiles())
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Tilemap map = new Tilemap(new Image(new FileInputStream(file.getAbsolutePath())));
                                TilemapLayer layer = new TilemapLayer(map);
                                layers.add(layer);
                                getChildren().add(layer);
                            }
                            catch (FileNotFoundException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
            evt.setDropCompleted(success);
            evt.consume();
        });
        setOnDragExited(evt->
        {
            DefaultStyle();
        });
    }
    private void DefaultStyle()
    {
        setStyle("-fx-border-color: black;"
                + "-fx-border-width: 1;"
                + "-fx-background-color: #C6C6C6;"
                + "-fx-border-style: solid;");
    }


    public final MapStateTilemap GetParent(){return parent;}
}
