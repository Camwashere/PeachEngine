package main.Module.Map.Game;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;
import main.Tools.InitHelp;

import java.util.UUID;

public class Territory
{
    private final UUID id;
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();
    private final SimpleObjectProperty<Color> color = new SimpleObjectProperty<>();

    public Territory( final String territoryName)
    {
        id = UUID.randomUUID();
        name.set(territoryName);
        color.set(InitHelp.RandomColor());
    }

    public final void SetName(final String str){name.set(str);}
    public final void SetDescription(final String str){description.set(str);}
    public final void SetColor(final Color c){color.set(c);}

    public final UUID GetID(){return id;}
    public final String GetName(){return name.get();}
    public final String GetDescription(){return description.get();}
    public final Color GetColor(){return color.get();}

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj instanceof Territory other)
        {
            if (this == other)
            {
                return true;
            }
            return id.equals(other.id) && name.get().equals(other.name.get()) && description.get().equals(other.description.get());

        }
        else
        {
            return false;
        }
    }
}
