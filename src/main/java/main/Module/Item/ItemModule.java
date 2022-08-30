package main.Module.Item;

import main.Module.Item.Layout.ItemTopBar;
import main.Module.ModuleBase.BaseModule;
import main.Module.ModuleBase.ModuleID;
import main.Module.ModuleManager.ModuleManager;

public class ItemModule extends BaseModule
{
    private final ItemTopBar top;
    public ItemModule(final ModuleManager parentManager)
    {
        super(ModuleID.ITEM, parentManager);
        top = new ItemTopBar(this);
        setTop(top);
    }

    public final ItemTopBar GetTop(){return top;}
}
