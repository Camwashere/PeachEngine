package main.Module.Character.Game.Interaction;

import main.Maths.Vec.Vec2;

public class Interaction
{
    private final Vec2 values = new Vec2();

    public Interaction()
    {

    }

    public final void SetLoveValue(double val){values.x=val;}
    public final void SetIntensityValue(double val){values.y=val;}

    public final double GetLoveValue(){return values.x;}
    public final double GetIntensityValue(){return values.y;}
}
