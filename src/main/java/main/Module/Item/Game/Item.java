package main.Module.Item.Game;


import javafx.beans.property.*;
import javafx.scene.image.Image;
import main.Tools.LinkedMapProperty;

import java.util.UUID;

public class Item
{
    private final ItemTemplate temp;
    private final UUID id;
    private final SimpleBooleanProperty active = new SimpleBooleanProperty(false);
    private final LinkedMapProperty<Long, ItemValue> values = new LinkedMapProperty<>();
    private final SimpleIntegerProperty amount = new SimpleIntegerProperty(1);


    public Item(final ItemTemplate template)
    {
        temp = template;
        if (temp.IsUnique())
        {
            id = UUID.randomUUID();
        }
        else
        {
            id = temp.GetID();
        }
    }

    public final void SetActive(final boolean bool){active.set(bool);}
    public final void SetAmount(final int i){amount.set(i);}
    public final void IncAmount(){amount.set(amount.get()+1);}
    public final void IncAmount(int i){amount.set(amount.get()+i);}
    public final void DecAmount(){amount.set(amount.get()-1);}
    public final void DecAmount(int i){amount.set(amount.get()-i);}


    public final UUID GetID(){return id;}
    public final UUID GetTemplateID(){return temp.GetID();}
    public final ItemTemplate GetTemplate(){return temp;}
    public final String GetName(){return temp.GetName();}
    public final String GetDescription(){return temp.GetDescription();}
    public final int GetAmount(){return amount.get();}
    public final boolean IsActive(){return active.get();}
    public final double GetWeight(){return temp.GetWeight();}
    public final Image GetIcon(){return temp.GetIcon();}

    public final Long GetOnPickup(){return temp.GetOnPickup();}
    public final Long GetOnActivate(){return temp.GetOnActivate();}
    public final Long GetOnDrop(){return temp.GetOnDrop();}
    public final Long GetOnDeactivate(){return temp.GetOnDeactivate();}

    @Override
    public String toString()
    {
        String rtn = String.format("ID: %s\nName: %s\nDescription: %s\nAmount: %d\nActive: %s\nWeight: %.2f\n", GetID(), GetName(), GetDescription(), amount.get(), active.get(), GetWeight());
        rtn += "Values: {\n";
        for (ItemValue val : values.values())
        {
            rtn += val.toString() + "\n";
        }
        rtn += "}\n";
        return rtn;
    }
}
