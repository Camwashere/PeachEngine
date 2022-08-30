package main.Data;

public interface SaveObject
{
    DataBase ToData();
    void Load(final DataBase data);
}
