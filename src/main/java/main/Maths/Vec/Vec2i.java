package main.Maths.Vec;

import java.io.Serializable;
import java.util.Objects;

public class Vec2i implements Serializable
{
    public int x;
    public int y;

    public Vec2i()
    {
        x=0;
        y=0;
    }
    public Vec2i(int xy)
    {
        x = xy;
        y = xy;
    }
    public Vec2i(int X, int Y)
    {
        x = X;
        y = Y;
    }
    public Vec2i(final Vec2i other)
    {
        x = other.x;
        y = other.y;
    }

    public final int Difference(){return Math.abs(x-y);}
    public final int Sum(){return x+y;}


    public final void Set(int X, int Y)
    {
        x=X;
        y=Y;
    }

    public void MakeEqual(final Vec2i other)
    {
        x = other.x;
        y = other.y;
    }
    public void MakeEqual(final int X, final int Y)
    {
        x=X;
        y=Y;
    }
    public void MakeDefault()
    {
        x=0;
        y=0;
    }

    public final void SetStart(final int start){x = start;}
    public final void SetEnd(final int end){y = end;}
    public final int GetStart(){return x;}
    public final int GetEnd(){return y;}
    public final boolean IsDefault(){return x==0 && y==0;}

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Vec2))
        {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Vec2 c = (Vec2) o;
        return Objects.equals(x, c.x) && Objects.equals(y, c.y);

        // Compare the data members and return accordingly
    }



    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    @Override
    public String toString()
    {
        return String.valueOf(x) + " x " + String.valueOf(y);
    }
}
