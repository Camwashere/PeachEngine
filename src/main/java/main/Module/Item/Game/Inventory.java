package main.Module.Item.Game;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Tools.LinkedMapProperty;

import java.util.UUID;

public class Inventory
{
    private final StoryCharacter parent;
    private final LinkedMapProperty<UUID, Item> items;
    private final SimpleDoubleProperty maxWeight = new SimpleDoubleProperty(100);
    private final SimpleDoubleProperty currentWeight = new SimpleDoubleProperty(0);

    public Inventory(final StoryCharacter parentCharacter)
    {
        parent = parentCharacter;
        items = new LinkedMapProperty<UUID, Item>();
        currentWeight.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1 == null)
                {
                    currentWeight.set(0);
                }
                else if (t1.doubleValue() < 0)
                {
                    currentWeight.set(0);
                }
            }
        });
        maxWeight.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1 == null)
                {
                    maxWeight.setValue(0);
                }
                else if (t1.doubleValue() < 0)
                {
                    maxWeight.setValue(0);
                }
            }
        });


    }
    public final boolean AddItem(final Item item)
    {
        if (currentWeight.get() + item.GetWeight() > maxWeight.get())
        {
            return false;
        }
        currentWeight.set(currentWeight.get() + item.GetWeight());
        if (items.containsKey(item.GetID()))
        {
            items.get(item.GetID()).IncAmount();
        }
        else
        {
            items.put(item.GetID(), item);
        }
        return true;
    }
    public final void RemoveItem(final Item item)
    {
        currentWeight.set(currentWeight.get() - item.GetWeight());
        if (items.containsKey(item.GetID()))
        {
            items.get(item.GetID()).DecAmount();
        }
        else
        {
            items.remove(item.GetID());
        }
    }
    public final Item GetItem(final UUID id){return items.get(id);}
    public final Item GetItem(final String itemName)
    {
        for (final Item i : items.values())
        {
            if (i.GetName().equals(itemName))
            {
                return i;
            }
        }
        return null;
    }
    public final int GetItemAmount(final UUID id)
    {
        if (items.containsKey(id))
        {
            return items.get(id).GetAmount();
        }
        return 0;
    }
    public final int GetItemAmount(final String itemName)
    {
        for (final Item i : items.values())
        {
            if (i.GetName().equals(itemName))
            {
                return i.GetAmount();
            }
        }
        return 0;
    }
    public final boolean HasItem(final UUID id)
    {
        return GetItemAmount(id) > 0;
    }
    public final boolean HasItem(final String itemName)
    {
        return GetItemAmount(itemName) > 0;
    }
    public final void Clear()
    {
        for (final UUID i : items.keySet())
        {
            items.remove(i);
        }
        items.clear();
        currentWeight.set(0);
    }
    public final double GetAvailableSpace(){return GetMaxWeight() - GetCurrentWeight();}
    public final double GetPercentageUsed(){return (GetCurrentWeight()/GetMaxWeight()) * 100;}
    public final double GetPercentageAvailable(){return 100 - GetPercentageUsed();}


    public final void SetMaxWeight(final double d){maxWeight.set(d);}

    public final StoryCharacter GetParent(){return parent;}
    public final LinkedMapProperty<UUID, Item> GetItems(){return items;}
    public final double GetMaxWeight(){return maxWeight.get();}
    public final double GetCurrentWeight(){return currentWeight.get();}
    public final SimpleDoubleProperty GetMaxWeightProp(){return maxWeight;}
    public final SimpleDoubleProperty GetCurrentWeightProp(){return currentWeight;}

    @Override
    public String toString()
    {
        String rtn = String.format("Max Weight: %.3f\nCurrent Weight: %.3f\n", maxWeight.get(), currentWeight.get());
        rtn += "Items: {\n";
        for (final Item i : items.values())
        {
            rtn += i.toString();
        }
        rtn += "}\n";
        return rtn;
    }
}
