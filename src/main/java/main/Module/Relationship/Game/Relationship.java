package main.Module.Relationship.Game;

import main.Maths.Vec.Vec2;
import main.Maths.Vec.Vec2Prop;

import java.util.UUID;

public class Relationship
{
    private final Vec2 pos = new Vec2();
    private final UUID character;
    private final UUID other;

    public Relationship(final UUID mainCharacter, final UUID otherCharacter)
    {
        character = mainCharacter;
        other = otherCharacter;
    }

    public final void SetPos(double x, double y){pos.Set(x, y);}
    public final void SetPos(final Vec2 xy){pos.Set(xy);}

    public final Vec2 GetPos(){return pos;}
    public final UUID GetMainCharacter(){return character;}
    public final UUID GetOtherCharacter(){return other;}

}
