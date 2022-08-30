package main.Maths;

public class MathHelp
{
    public static double MRound(double value, double factor)
    {
        return Math.round(value / factor) * factor;
    }
}
