package main.Module.Map.States;

import javafx.scene.layout.BorderPane;
import main.Module.Map.MapModule;

import java.util.Map;

public abstract class MapStateBase extends BorderPane
{
    protected final MapModule parent;
    private final MapStateType type;

    protected MapStateBase(final MapModule parentMod, final MapStateType stateType)
    {
        parent = parentMod;
        type = stateType;
    }


    public final MapModule GetParent(){return parent;}
    public final MapStateType GetType(){return type;}
}
