package main.Maths.Vec;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class Vec4 implements Serializable
{
    public double r;
    public double g;
    public double b;
    public double a;

    public Vec4(final Color c)
    {
        r = c.getRed();
        g = c.getGreen();
        b = c.getBlue();
        a = c.getOpacity();
    }
}
