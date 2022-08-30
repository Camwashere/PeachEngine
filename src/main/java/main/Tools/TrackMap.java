package main.Tools;

import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class TrackMap<K, V> extends LinkedMapProperty<K, V>
{
    private final SimpleObjectProperty<K> current;
    public TrackMap()
    {
        current = new SimpleObjectProperty<K>();
    }

    public final SimpleObjectProperty<K> GetCurrentProp(){return current;}
    public final K GetCurrentKey(){return current.getValue();}
    public final V GetCurrent(){return get(current.getValue());}
    public final void SetCurrent(final K key){current.setValue(key);}
}
