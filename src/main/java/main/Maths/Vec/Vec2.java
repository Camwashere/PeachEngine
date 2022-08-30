package main.Maths.Vec;

import javafx.geometry.Point2D;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import main.Maths.MathHelp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;

public class Vec2 implements Serializable
{
    public double x;
    public double y;

    public Vec2()
    {
        x=0;
        y=0;
    }
    public Vec2(final double X, final double Y)
    {
        x=X;
        y=Y;
    }
    public Vec2(final Vec2 other)
    {
        x = other.x;
        y = other.y;
    }
    public Vec2(final Point2D point)
    {
        x=point.getX();
        y=point.getY();
    }
    public Vec2(final double xy)
    {
        x = xy;
        y = xy;
    }
    public final void Set(double X, double Y)
    {
        x=X;
        y=Y;
    }
    public final void Set(final Vec2 other)
    {
        x = other.x;
        y = other.y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Vec2)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Vec2 c = (Vec2) o;
        return x == c.x && y == c.y;

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
        return AsString();
    }





    public final double GetSum(){return x+y;}
    public void MakeEqual(final Vec2 other)
    {
        x = other.x;
        y = other.y;
    }
    public void MakeEqual(final double X, final double Y)
    {
        x=X;
        y=Y;
    }
    public void MakeDefault()
    {
        x=0;
        y=0;
    }

    public static Vec2 Rotate(final Vec2 original, double theta)
    {
       return new Vec2(original.x * Math.cos(theta) - original.y * Math.sin(theta), original.x * Math.sin(theta) + original.y * Math.cos(theta));
    }

    public static double FindSlope(final Vec2 first, final Vec2 second) {return (second.y - first.y) / (second.x - first.x);}

    public static double FindYIntercept(final Vec2 first, double slope){return first.y - (slope*first.x);}

    // Generate points along a line to be used as reference
    public static ArrayList<Vec2> CreateLine(final Vec2 first, final Vec2 second)
    {
        double slope = FindSlope(first, second);
        double yIntercept = FindYIntercept(first, slope);
        double distance = DistanceBetween(first, second);
        ArrayList<Vec2> lineValues = new ArrayList<Vec2>();

        if (first.x < second.x)
        {
            for (double x=first.x; x<second.x; x+=(second.x-first.x)/distance)
            {
                double yVal = (slope*x) + yIntercept;
                lineValues.add(new Vec2(x, yVal));
            }
        }
        else if (first.x > second.x)
        {
            for (double x=first.x; x>second.x; x-=(first.x-second.x)/distance)
            {
                double yVal = (slope*x) + yIntercept;
                lineValues.add(new Vec2(x, yVal));
            }
        }
        else
        {
            if (first.y < second.y)
            {
                for (double y=first.y; y<second.y; y+=(second.y-first.y)/distance)
                {
                    lineValues.add(new Vec2(first.x, y));
                }
            }
            else if (first.y > second.y)
            {
                for (double y=first.y; y>second.y; y-=(first.y-second.y)/distance)
                {
                    lineValues.add(new Vec2(first.x, y));
                }
            }
            else
            {
                System.out.println("Equal Vec2's");
            }
        }
        return lineValues;
    }

    public static ArrayList<Vec2> CreateLine(final Vec2 first, final Vec2 second, int betweenPoints)
    {
        double slope = FindSlope(first, second);
        double yIntercept = FindYIntercept(first, slope);
        ArrayList<Vec2> lineValues = new ArrayList<Vec2>();

        if (first.x < second.x)
        {
            for (double x=first.x; x<second.x; x+=(second.x-first.x)/betweenPoints)
            {
                double yVal = (slope*x) + yIntercept;
                lineValues.add(new Vec2(x, yVal));
            }
        }
        else if (first.x > second.x)
        {
            for (double x=first.x; x>second.x; x-=(first.x-second.x)/betweenPoints)
            {
                double yVal = (slope*x) + yIntercept;
                lineValues.add(new Vec2(x, yVal));
            }
        }
        else
        {
            if (first.y < second.y)
            {
                for (double y=first.y; y<second.y; y+=(second.y-first.y)/betweenPoints)
                {
                    lineValues.add(new Vec2(first.x, y));
                }
            }
            else if (first.y > second.y)
            {
                for (double y=first.y; y>second.y; y-=(first.y-second.y)/betweenPoints)
                {
                    lineValues.add(new Vec2(first.x, y));
                }
            }
            else
            {
                System.out.println("Equal Vec2's");
            }
        }
        return lineValues;

    }


    public static double AverageDistanceFromLine(final ArrayList<Vec2> line, final Vec2 point)
    {
        double sum = 0;
        for (final Vec2 p : line)
        {
            sum += DistanceBetween(point, p);
        }
        return sum / line.size();
    }
    public static double ClosestDistanceFromLine(final ArrayList<Vec2> line, final Vec2 point)
    {
        double closest = Double.MAX_VALUE;
        for (final Vec2 p : line)
        {
            double newClosest = DistanceBetween(p, point);
            if (newClosest < closest)
            {
                closest = newClosest;
            }
        }
        return closest;
    }

    public int compareTo(Vec2 o)
    {
        return Double.compare(x, o.x);
    }

    // Return the convex hull of a list of points
    public static List<Vec2> ConvexHull(final List<Vec2> p)
    {
        if (p.isEmpty()) return emptyList();
        p.sort(Vec2::compareTo);
        List<Vec2> h = new ArrayList<>();

        // lower hull
        for (Vec2 pt : p)
        {
            while (h.size() >= 2 && !ccw(h.get(h.size() - 2), h.get(h.size() - 1), pt))
            {
                h.remove(h.size() - 1);
            }
            h.add(pt);
        }

        // upper hull
        int t = h.size() + 1;
        for (int i = p.size() - 1; i >= 0; i--) {
            Vec2 pt = p.get(i);
            while (h.size() >= t && !ccw(h.get(h.size() - 2), h.get(h.size() - 1), pt)) {
                h.remove(h.size() - 1);
            }
            h.add(pt);
        }

        h.remove(h.size() - 1);
        return h;
    }
    // ccw returns true if the three points make a counter-clockwise turn
    private static boolean ccw(Vec2 a, Vec2 b, Vec2 c)
    {
        return ((b.x - a.x) * (c.y - a.y)) > ((b.y - a.y) * (c.x - a.x));
    }


    public static Vec2 FindClosestPointTo(final ArrayList<Vec2> list, final Vec2 point)
    {
        double smallest = Double.MAX_VALUE;
        Vec2 closest = new Vec2();
        for (final Vec2 vec : list)
        {
            double temp = Vec2.DistanceBetween(vec, point);
            if (temp < smallest)
            {
                smallest=temp;
                closest.MakeEqual(vec);
            }
        }
        if (closest.IsDefault())
        {
            System.out.println("ERROR IN FIND CLOSEST POINT TO IN VEC2");
        }
        return closest;
    }

    public static void efla(int x, int y, int x2, int y2, Color color, PixelWriter pw)
    {
        int shortLen = y2-y;
        int longLen = x2-x;
        boolean yLonger;

        if ((shortLen ^ (shortLen >> 31)) - (shortLen >> 31) > (longLen ^ (longLen >> 31)) - (longLen >> 31))
        {
            shortLen ^= longLen;
            longLen ^= shortLen;
            shortLen ^= longLen;

            yLonger = true;
        }
        else
        {
            yLonger = false;
        }

        int inc = longLen < 0 ? -1 : 1;

        int multDiff = longLen == 0 ? shortLen : shortLen / longLen;

        if (yLonger)
        {
            for (int i = 0; i != longLen; i += inc)
            {
                pw.setColor(x + i*multDiff, y+i, color);
            }
        }
        else
        {
            for (int i = 0; i != longLen; i += inc)
            {
                pw.setColor(x+i, y+i*multDiff, color);
            }
        }
    }

    public static boolean Intersects(final Vec2 pos1, final Vec2 size1, final Vec2 pos2, final Vec2 size2)
    {
        if (pos1.x == size1.x || pos1.y == size1.y || pos2.x == size2.x
                || pos2.y == size2.y) {
            // the line cannot have positive overlap
            return false;
        }

        // If one rectangle is on left side of other
        if (pos1.x >= size2.x || pos2.x >= size1.x)
            return false;

        // If one rectangle is above other
        if (size1.y >= pos2.y || size2.y >= pos1.y)
            return false;

        return true;
    }

    public static ArrayList<Vec2> IncreasePathResolution(final ArrayList<Vec2> points)
    {
        ArrayList<Vec2> list = new ArrayList<Vec2>();
        for (int i=0; i<points.size()-1; i++)
        {
            list.add(points.get(i));
            list.add(Vec2.FindMidpoint(points.get(i), points.get(i+1)));
        }
        return list;
    }
    public static ArrayList<Vec2> IncreasePathResolution(final ArrayList<Vec2> points, int iterations)
    {
        ArrayList<Vec2> list = points;
        for (int i=0; i<iterations; i++)
        {
            list = IncreasePathResolution(list);
        }
        return list;
    }

    public static void ConvertPathToPoints(PathElement elem, final ArrayList<Vec2> list)
    {
        Vec2 p0 = new Vec2();
        int POINTS_CURVE = 5;
        if(elem instanceof MoveTo)
        {
            //list=new ArrayList<>();
            list.clear();
            p0=new Vec2(((MoveTo)elem).getX(),((MoveTo)elem).getY());
            list.add(p0);
        }
        else if(elem instanceof LineTo)
        {
            list.add(new Vec2(((LineTo)elem).getX(),((LineTo)elem).getY()));
        }
        else if(elem instanceof CubicCurveTo)
        {
            Vec2 ini = (list.size()>0?list.get(list.size()-1):p0);
            IntStream.rangeClosed(1, POINTS_CURVE).forEach(i->list.add(EvalCubicBezier((CubicCurveTo)elem, ini, ((double)i)/POINTS_CURVE)));
        }
        else if(elem instanceof QuadCurveTo)
        {
            Vec2 ini = (list.size()>0?list.get(list.size()-1):p0);
            IntStream.rangeClosed(1, POINTS_CURVE).forEach(i->list.add(EvalQuadBezier((QuadCurveTo)elem, ini, ((double)i)/POINTS_CURVE)));
        }
        else if(elem instanceof ClosePath)
        {
            list.add(p0);
        }
    }

    public static Vec2 EvalCubicBezier(CubicCurveTo c, Vec2 ini, double t)
    {
        return new Vec2(Math.pow(1-t,3)*ini.x+
                3*t*Math.pow(1-t,2)*c.getControlX1()+
                3*(1-t)*t*t*c.getControlX2()+
                Math.pow(t, 3)*c.getX(),
                Math.pow(1-t,3)*ini.y+
                        3*t*Math.pow(1-t, 2)*c.getControlY1()+
                        3*(1-t)*t*t*c.getControlY2()+
                        Math.pow(t, 3)*c.getY());
    }

    public static Vec2 EvalQuadBezier(QuadCurveTo c, Vec2 ini, double t)
    {
        return new Vec2(Math.pow(1-t,2)*ini.x+
                2*(1-t)*t*c.getControlX()+
                Math.pow(t, 2)*c.getX(),
                Math.pow(1-t,2)*ini.y+
                        2*(1-t)*t*c.getControlY()+
                        Math.pow(t, 2)*c.getY());
    }


    public static double DistanceBetween(final Vec2 start, final Vec2 end)
    {
        return Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2));
    }

    public static Vec2 FindMidpoint(final Vec2 start, final Vec2 end)
    {
        double x = (start.x + end.x) / 2;
        double y = (start.y + end.y) / 2;
        return new Vec2(x, y);
    }

    public final String AsString(){return (int)x + " x " + (int)y;}
    public final String AsDetailString(){return x + " x " + y;}
    public final String AsSaveString(){return x + "," + y + ";"; }
    public final boolean IsEqual(final Vec2 other){return x == other.x && y == other.y;}
    public final boolean IsDefault(){return x==0 && y==0;}

    public final void Print(){System.out.println(this.AsString());}

    public static void PrintAll(final Vec2...other)
    {
        System.out.println();
        for (Vec2 point : other)
        {
            point.Print();
        }
        System.out.println();
    }
    public static void PrintAll(final ArrayList<Vec2> Points)
    {
        for (final Vec2 point : Points)
        {
            point.Print();
        }
    }
}

