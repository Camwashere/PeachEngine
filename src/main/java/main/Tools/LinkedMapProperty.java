package main.Tools;

import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;

import java.util.LinkedHashMap;

public class LinkedMapProperty<K, V> extends SimpleMapProperty<K, V>
{
    public LinkedMapProperty()
    {
        super(FXCollections.observableMap(new LinkedHashMap<K, V>()));
    }
    public LinkedMapProperty(final LinkedMapProperty<K, V> other)
    {
        super(FXCollections.observableMap(new LinkedHashMap<K, V>(other)));
    }
    @SafeVarargs
    public LinkedMapProperty(final LinkedMapProperty<K, ? extends V>...others)
    {
        super(FXCollections.observableMap(new LinkedHashMap<K, V>()));
        for (final LinkedMapProperty<K, ? extends V> map : others)
        {
            map.addListener(new MapChangeListener<K, V>()
            {
                @Override
                public void onChanged(Change<? extends K, ? extends V> change)
                {
                    if (change.wasRemoved())
                    {
                        remove(change.getKey(), change.getValueRemoved());
                    }
                    if (change.wasAdded())
                    {
                        put(change.getKey(), change.getValueAdded());
                    }
                }
            });
            for (final K key : map.keySet())
            {
                put(key, map.get(key));
            }
        }
    }
}
