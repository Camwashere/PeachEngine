package main.Module.ModuleBase;

import javafx.scene.layout.BorderPane;
import main.Module.ModuleManager.ModuleManager;

/**
 * Abstract base class for all modules of the program.
 * Contains the module type and a reference to the module manager.
 */
public abstract class BaseModule extends BorderPane
{
    private final ModuleID id;
    private final ModuleManager parent;

    public BaseModule(final ModuleID modID, final ModuleManager modManager)
    {
        id = modID;
        parent = modManager;
    }
    public final ModuleID GetID(){return id;}
    public final ModuleManager GetParent(){return parent;}

}
