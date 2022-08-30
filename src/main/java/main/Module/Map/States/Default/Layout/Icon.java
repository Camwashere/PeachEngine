package main.Module.Map.States.Default.Layout;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import main.Debug.Debug;
import main.Tools.StringHelp;

import java.util.UUID;

public class Icon extends Image
{
    /*
        Effect = Scenario launched when a character reaches this icon on the map
        GridLink = Grid to swap the map view to when character reaches this icon on the map
     */
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();
    private final SimpleObjectProperty<UUID> effect = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<UUID> gridLink = new SimpleObjectProperty<>();
    public Icon(String url)
    {
        super(url);
        int lastIndex = url.lastIndexOf("/");
        if (lastIndex == -1)
        {
            Debug.println("NAME FUCKED");
        }
        else
        {
            name.set(url.substring(lastIndex+1, url.indexOf(".")));
            if (GetName().toLowerCase().contains("icon"))
            {
                name.set(StringHelp.Capitalize(GetName().toUpperCase().replace("ICON", "")));
            }
        }
    }

    public final void SetName(final String iconName){name.set(iconName);}
    public final void SetDescription(final String desc){description.set(desc);}
    public final void SetEffect(final UUID effectID){effect.set(effectID);}
    public final void SetGridLink(final UUID gridID){gridLink.set(gridID);}

    public final String GetName(){return name.get();}
    public final SimpleStringProperty GetNameProp(){return name;}
    public final String GetDescription(){return description.get();}
    public final UUID GetEffect(){return effect.get();}
    public final UUID GetGridLink(){return gridLink.get();}

    public final ImagePattern AsImagePattern(){return new ImagePattern(this);}
}
