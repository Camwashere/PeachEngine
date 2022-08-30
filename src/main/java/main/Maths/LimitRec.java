package main.Maths;

import main.Maths.Vec.Vec2;

public class LimitRec
{
    private final LimitNumber x;
    private final LimitNumber y;

    public LimitRec()
    {
        x = new LimitNumber(0, -100, 100);
        y = new LimitNumber(0, -100, 100);
    }

    public final boolean Contains(final Vec2 vec) {return x.Contains(vec.x) && y.Contains(vec.y);}

    public final void SetPosX(double pos)
    {
        x.SetMax(x.GetSize() + pos);
        x.SetMin(pos);
    }
    public final void SetPosY(double pos)
    {
        y.SetMax(y.GetSize() + pos);
        y.SetMin(pos);
    }
    public final void SetWidth(double width){x.SetMax(x.GetMin()+width);}
    public final void SetHeight(double height){y.SetMax(y.GetMin()+height);}
}
