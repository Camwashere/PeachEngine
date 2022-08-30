package main.Module.Theme;

import main.Module.ModuleBase.BaseModule;
import main.Module.ModuleBase.ModuleID;
import main.Module.ModuleManager.ModuleManager;

public class ThemeModule extends BaseModule
{
    public ThemeModule(final ModuleManager parentManager)
    {
        super(ModuleID.THEME, parentManager);
    }
}
