package main.Module.Map.Game;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.paint.*;
import java.util.UUID;

public class Climate
{
    private final UUID id;
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();
    private SimpleObjectProperty<UUID> effect = new SimpleObjectProperty<>();
    private final SimpleListProperty<Paint> textures = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final SimpleObjectProperty<Color> displayPaint = new SimpleObjectProperty<>();

    public Climate(final String climateName)
    {
        id = UUID.randomUUID();
        name.set(climateName);
        displayPaint.set(Color.GREEN);
    }

    public final void AddTexture(final Paint p){textures.add(p);}
    public final void RemoveTexture(final Paint p){textures.remove(p);}

    public final void SetName(final String str){name.set(str);}
    public final void SetDescription(final String str){description.set(str);}
    public final void SetEffect(final UUID characterEffect){effect.set(characterEffect);}
    public final void SetDisplayPaint(final Color p){displayPaint.set(p);}

    public final UUID GetID(){return id;}
    public final String GetName(){return name.get();}
    public final String GetDescription(){return description.get();}
    public final UUID GetEffect(){return effect.get();}
    public final SimpleListProperty<Paint> GetTextures(){return textures;}
    public final Color GetDisplayPaint(){return displayPaint.get();}
}
