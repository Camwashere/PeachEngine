package main.Debug;

public class Debug
{
    private static int DEBUG_LEVEL=0;
    public static void SET_DEBUG_LEVEL(int level){DEBUG_LEVEL = level;}
    public static void print(Object x)
    {
        System.out.print(x);
    }
    public static void print(Object x, int level)
    {
        if (DEBUG_LEVEL >= level)
        {
            System.out.print(x);

        }
    }
    public static void println()
    {
        System.out.println();
    }
    public static void println(Object x)
    {
        System.out.println(x);
    }
    public static void println(Object x, int level)
    {
        if (DEBUG_LEVEL >= level)
        {
            System.out.println(x);
        }
    }
    public static void printf(String format, Object...args)
    {
        System.out.printf(format, args);
    }
    public static void printf(int level, String format, Object...args)
    {
        if (DEBUG_LEVEL >= level)
        {
            System.out.printf(format, args);
        }
    }
}
