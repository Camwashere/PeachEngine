package main.Module.Character.Game.Variables;


import javafx.beans.property.SimpleLongProperty;

public class Perk extends CharacterVariableBase
{
    private final SimpleLongProperty effect = new SimpleLongProperty();
    public Perk(final String varName)
    {
        super(varName, CharacterVariableType.PERK);
        SetEffect(null);

    }
    public Perk(final Perk p)
    {
        super(p);
        SetEffect(null);
    }

    public final void SetEffect(final Long l){effect.setValue(l);}
    public final Long GetEffect(){return effect.getValue();}

    @Override
    public String toString()
    {
        return super.toString();
    }
}
