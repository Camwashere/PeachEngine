package main.Module.Map.States.Tilemap;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;


public class Tilemap extends WritableImage
{
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleIntegerProperty tileSize = new SimpleIntegerProperty(10);



    public Tilemap(final Image img)
    {
        super(img.getPixelReader(), (int)img.getWidth(), (int)img.getHeight());
    }

    public final void SetName(final String str){name.set(str);}
    public final void SetTileSize(final int s){tileSize.set(s);}


    public final String GetName(){return name.get();}
    public final SimpleStringProperty GetNameProp(){return name;}
    public final int GetTileSize(){return tileSize.get();}
    public final SimpleIntegerProperty GetTileSizeProp(){return tileSize;}

}
