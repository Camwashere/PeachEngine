package main.Module.Map.States.Tilemap;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TilemapLayer extends Canvas
{
    private final SimpleStringProperty name = new SimpleStringProperty();
    public final GraphicsContext gc;
    public TilemapLayer(final Image img)
    {
        super(img.getWidth(), img.getHeight());
        gc = getGraphicsContext2D();
        gc.drawImage(img, 0, 0);
    }

    public final void SetName(final String str){name.set(str);}
    public final String GetName(){return name.get();}
}
