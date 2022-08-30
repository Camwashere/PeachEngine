package main.Module.Item.Game;

import main.Tools.LinkedMapProperty;

import java.util.UUID;

public class ItemManager
{
    private final LinkedMapProperty<UUID, Item> items = new LinkedMapProperty<>();

    public ItemManager()
    {

    }

    public final void AddItem(final Item item)
    {
        items.put(item.GetID(), item);
    }
    public final void RemoveItem(final Item item)
    {
        items.remove(item.GetID());
    }
    public final LinkedMapProperty<UUID, Item> GetItems(){return items;}
}
