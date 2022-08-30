package main.Data;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Objects;

public class DataBase implements Serializable
{
    private final LinkedHashMap<String, Serializable> data = new LinkedHashMap<>();
    public static final String path = "testSave.txt";
    private final String className;
    public DataBase(final String className)
    {
        this.className = className;
        System.out.println(getClass().getName());
    }
    public DataBase(final Object obj)
    {
        className = obj.getClass().getName();
    }
    public final void Add(final String key, final Serializable val)
    {
        data.put(key, val);
    }
    public final Serializable Get(final String key)
    {
        return data.get(key);
    }
    public final Number GetNumber(final String key)
    {
        Serializable obj = data.get(key);
        if (obj instanceof Number)
        {
            return (Number)obj;
        }
        return null;
    }
    public final String GetString(final String key)
    {
        Serializable obj = data.get(key);
        if (obj instanceof String)
        {
            return (String)obj;
        }
        return null;
    }
    public final Boolean GetBool(final String key)
    {
        Serializable obj = data.get(key);
        if (obj instanceof Boolean)
        {
            return (Boolean)obj;
        }
        return null;
    }
    public final void Save()
    {
        try
        {
            FileOutputStream f = new FileOutputStream(new File(path));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(this);
            o.close();
            f.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static DataBase Load()
    {
        try
        {
            FileInputStream f = new FileInputStream(new File(path));
            ObjectInputStream o = new ObjectInputStream(f);
            DataBase data = (DataBase)o.readObject();
            o.close();
            f.close();
            return data;
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public final LinkedHashMap<String, Serializable> GetData(){return data;}
    public final String GetClassName(){return className;}
    public final boolean IsValidLoad(final Object obj)
    {
        return Objects.equals(obj.getClass().getName(), className);
    }

    @Override
    public String toString()
    {
        String str = String.format("Class Name: %s\n", className);
        for (final Serializable d : data.values())
        {
            str += d.toString()+"\n";
        }
        return str;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (obj instanceof DataBase other)
        {
            return Objects.equals(toString(), other.toString());
        }
        return false;
    }
}
