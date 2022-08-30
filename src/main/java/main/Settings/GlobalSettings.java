package main.Settings;

import main.Maths.Vec.Vec2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GlobalSettings
{
    private static final String path = "src/main/resources/config";
    private double version;
    private final Vec2 resolution;

    public GlobalSettings()
    {
        resolution = new Vec2(340, 220);
        try
        {
            File file = new File(path);
            Scanner scnr = new Scanner(file);
            VersionInit(scnr.nextLine());
            ResolutionInit(scnr.nextLine());
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    private void VersionInit(final String line)
    {
        String sub = line.substring(line.indexOf("=")+1);
        version = Double.parseDouble(sub);
    }

    private void ResolutionInit(final String line)
    {
        String sub = line.substring(line.indexOf("=")+1);
        String[] split = sub.split("x");
        resolution.x = Double.parseDouble(split[0]);
        resolution.y = Double.parseDouble(split[1]);
    }

    public final Vec2 GetResolution(){return resolution;}
}
