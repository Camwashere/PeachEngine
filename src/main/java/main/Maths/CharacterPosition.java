package main.Maths;

import main.Maths.Vec.Vec2;

public class CharacterPosition
{
    private final Speed speed;
    private final Vec2 pos;
    private  DistanceType scale;

    public CharacterPosition()
    {
        speed = new Speed();
        pos = new Vec2();
        scale = DistanceType.MILES;
    }
    public final double CalculateTravelTime(final Vec2 dest)
    {
        double dist = Distance.Convert(Vec2.DistanceBetween(pos, dest), scale, speed.GetDistanceType());

        return dist / speed.GetDistanceAmount();
    }




    public final void SetSpeed(final double amount){speed.SetDistanceAmount(amount);}
    public final void SetSpeed(final double amount, final DistanceType type){speed.SetDistanceAmount(amount);speed.SetDistanceType(type);}
    public final void SetSpeed(final double amount, final DistanceType dType, final TimeType tType)
    {
        speed.SetDistanceAmount(amount);
        speed.SetDistanceType(dType);
        speed.SetTimeType(tType);
    }
    public final void SetScale(final DistanceType type){scale = type;}
    public final void SetPos(final Vec2 vec){pos.MakeEqual(pos);}
    public final Vec2 GetPos(){return pos;}
    public final DistanceType GetScale(){return scale;}
    public final double GetSpeedAmount(){return speed.GetDistanceAmount();}
    public final Speed GetSpeed(){return speed;}
}
