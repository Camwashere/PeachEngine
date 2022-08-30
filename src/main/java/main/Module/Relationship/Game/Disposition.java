package main.Module.Relationship.Game;

import javafx.beans.property.SimpleDoubleProperty;
import main.Maths.Vec.Vec2;
import main.Maths.Vec.Vec2Prop;
import main.Module.Character.Game.Variables.CharacterVariableBase;

import java.util.UUID;

public class Disposition
{
    private final CharacterVariableBase variable;
    private final Vec2Prop value = new Vec2Prop();

    public Disposition(final CharacterVariableBase base)
    {
        variable = base;
    }

    public Disposition(final CharacterVariableBase base, final Vec2 val)
    {
        variable = base;
        value.Set(val);
    }

    public final void SetValue(final Vec2 val){value.Set(val);}

    public final void Apply(final Vec2 pos){pos.x += GetValueProp().GetX(); pos.y += GetValueProp().GetY();}

    public final CharacterVariableBase GetVariable(){return variable;}
    public final Vec2 GetValue(){return value.Get();}
    public final Vec2Prop GetValueProp(){return value;}
    public final UUID GetID(){return variable.GetID();}


}
