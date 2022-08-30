package main.Module.Character.Game.Variables;

import javafx.beans.property.SimpleStringProperty;

import java.util.UUID;

public abstract class CharacterVariableBase
{
    private final CharacterVariableBase parent;
    private final UUID id;
    private final boolean isRoot;
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final CharacterVariableType type;

    public CharacterVariableBase(final String varName, final CharacterVariableType varType)
    {
        parent = null;
        id = UUID.randomUUID();
        isRoot = true;
        name = new SimpleStringProperty(varName);
        description = new SimpleStringProperty();
        type = varType;
    }
    public CharacterVariableBase(final CharacterVariableBase temp)
    {
        parent = temp;
        id = parent.GetID();
        isRoot = false;
        name = parent.name;
        description = parent.description;
        type = parent.GetType();
    }
    public final void SetName(final String str){name.set(str);}
    public final void SetDescription(final String str){description.set(str);}

    public final String GetDescription(){return description.get();}
    public final CharacterVariableType GetType(){return type;}
    public final String GetName(){return name.get();}
    public final SimpleStringProperty GetNameProp(){return name;}
    public final SimpleStringProperty GetDescriptionProp(){return description;}
    public final UUID GetID(){return id;}
    public final CharacterVariableBase GetParent(){return parent;}
    public final boolean IsRoot(){return isRoot;}

    @Override
    public String toString()
    {
        return String.format("Type: %s\nID: %s\nName: %s\nDescription: %s\n", type, id, name, description);
    }
}
