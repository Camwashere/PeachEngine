package main.Module.Item.Game;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import main.Tools.LinkedMapProperty;

import java.util.UUID;

public class ItemTemplate
{
    private final UUID id = UUID.randomUUID();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();

    private final SimpleDoubleProperty weight = new SimpleDoubleProperty(0);
    private final LinkedMapProperty<Long, ItemValue> values = new LinkedMapProperty<>();
    private final SimpleLongProperty onPickup = new SimpleLongProperty();
    private final SimpleLongProperty onActivate = new SimpleLongProperty();
    private final SimpleLongProperty onDrop = new SimpleLongProperty();
    private final SimpleLongProperty onDeactivate = new SimpleLongProperty();
    private final SimpleBooleanProperty unique = new SimpleBooleanProperty(true);
    private final SimpleObjectProperty<Image> icon = new SimpleObjectProperty<>();

    public ItemTemplate()
    {

    }
    public final Item Spawn(){return new Item(this);}


    public final void SetName(final String str){name.set(str);}
    public final void SetDescription(final String str){description.set(str);}
    public final void SetWeight(final double w){weight.set(w);}
    public final void SetUnique(final boolean bool){unique.set(bool);}
    public final void SetIcon(final Image i){icon.set(i);}

    public final void SetOnPickup(final Long l){onPickup.setValue(l);}
    public final void SetOnActivate(final Long l){onActivate.setValue(l);}
    public final void SetOnDrop(final Long l){onDrop.setValue(l);}
    public final void SetOnDeactivate(final Long l){onDeactivate.setValue(l);}

    public final UUID GetID(){return id;}
    public final String GetName(){return name.get();}
    public final String GetDescription(){return description.get();}
    public final double GetWeight(){return weight.get();}
    public final boolean IsUnique(){return unique.get();}
    public final Image GetIcon(){return icon.get();}

    public final Long GetOnPickup(){return onPickup.getValue();}
    public final Long GetOnActivate(){return onActivate.getValue();}
    public final Long GetOnDrop(){return onDrop.getValue();}
    public final Long GetOnDeactivate(){return onDeactivate.getValue();}

    public final SimpleStringProperty GetNameProp(){return name;}
    public final SimpleStringProperty GetDescriptionProp(){return description;}
}
