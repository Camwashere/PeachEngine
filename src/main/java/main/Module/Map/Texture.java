package main.Module.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

public class Texture extends WritableImage
{
    public static final Texture BLANK = new Texture(Color.WHITE);
    public static final Texture BLUE = new Texture(Color.BLUE);
    public static final Texture RED = new Texture(Color.RED);
    private final SimpleStringProperty name = new SimpleStringProperty();
    public Texture()
    {
        super(50, 50);
        name.set("Texture");
    }
    public Texture(final String tName, int w, int h)
    {
        super(w, h);
        name.set(tName);
    }

    public Texture(final Image img)
    {
        super(img.getPixelReader(), (int)img.getWidth(), (int)img.getHeight());
        name.set("Texture");
    }

    public Texture(final Color c)
    {
        super(50, 50);
        for (int i=0; i<50; i++)
        {
            for (int a=0; a<50; a++)
            {
                getPixelWriter().setColor(i, a, c);
            }
        }
    }
    public Texture(final Color c, int w, int h)
    {
        super(w, h);
        for (int i=0; i<w; i++)
        {
            for (int a=0; a<h; a++)
            {
                getPixelWriter().setColor(i, a, c);
            }
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Texture))
        {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Texture t = (Texture) o;

        // Compare the data members and return accordingly
        return getPixelReader().equals(t.getPixelReader()) && name.get().equals(t.GetName());
    }



    public final void SetName(final String tName){name.set(tName);}

    public final String GetName(){return name.get();}
    public final SimpleStringProperty GetNameProp(){return name;}


    public final ImagePattern AsImagePattern() {return new ImagePattern(this);}
}
