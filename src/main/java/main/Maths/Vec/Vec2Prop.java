package main.Maths.Vec;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Vec2Prop
{
    private final SimpleDoubleProperty xProp = new SimpleDoubleProperty();
    private final SimpleDoubleProperty yProp = new SimpleDoubleProperty();

    public Vec2Prop()
    {

    }
    public Vec2Prop(final double xy)
    {
        SetX(xy);
        SetY(xy);
    }
    public Vec2Prop(final double x, final double y)
    {
        SetX(x);
        SetY(y);
    }
    public Vec2Prop(final Vec2 vec)
    {
        SetX(vec.x);
        SetY(vec.y);
    }



    public final void Set(final double x, final double y){SetX(x);SetY(y);}
    public final void Set(final Vec2 vec){SetX(vec.x); SetY(vec.y);}
    public final void SetX(double x){xProp.set(x);}
    public final void SetY(double y){yProp.set(y);}

    public final Vec2 Get(){return new Vec2(GetX(), GetY());}
    public final double GetX(){return xProp.get();}
    public final double GetY(){return yProp.get();}
    public final SimpleDoubleProperty GetXProp(){return xProp;}
    public final SimpleDoubleProperty GetYProp(){return yProp;}
}
