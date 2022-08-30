package main.Project;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class ProjectTime implements Serializable
{
    private final long createdMillis;

    public ProjectTime()
    {
        createdMillis = System.currentTimeMillis();
    }


    public long GetAgeInSeconds()
    {
        long nowMillis = System.currentTimeMillis();
        return TimeUnit.MILLISECONDS.toSeconds(nowMillis-this.createdMillis);
    }

    public long GetAgeInMinutes()
    {
        long nowMillis = System.currentTimeMillis();
        return TimeUnit.MILLISECONDS.toMinutes(nowMillis-this.createdMillis);
    }
    public long GetAgeInHours()
    {
        long nowMillis = System.currentTimeMillis();
        return TimeUnit.MILLISECONDS.toHours(nowMillis-this.createdMillis);
    }
    public long GetAgeInDays()
    {
        long nowMillis = System.currentTimeMillis();
        return TimeUnit.MILLISECONDS.toDays(nowMillis-this.createdMillis);
    }

    @Override
    public String toString()
    {
        long nowMillis = System.currentTimeMillis();
        return String.format("Days: %d\nHours: %d\nMinutes: %d\nSeconds: %d\n", TimeUnit.MILLISECONDS.toDays(nowMillis-this.createdMillis), TimeUnit.MILLISECONDS.toHours(nowMillis-this.createdMillis),
                TimeUnit.MILLISECONDS.toMinutes(nowMillis-this.createdMillis), TimeUnit.MILLISECONDS.toSeconds(nowMillis-this.createdMillis));
    }
}
