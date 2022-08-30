package main.Tools.Texture;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;

public class SwatchBar extends HBox
{
    private final SimpleListProperty<Swatch> swatches = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final SimpleObjectProperty<Paint> value = new SimpleObjectProperty<>();
    public SwatchBar()
    {
        setSpacing(1);
        ListenerInit();

    }
    private void ListenerInit()
    {
        swatches.addListener(new ListChangeListener<Swatch>()
        {
            @Override
            public void onChanged(Change<? extends Swatch> change)
            {
                if (change.next())
                {
                    if (change.wasRemoved())
                    {
                        getChildren().removeAll(change.getRemoved());
                    }
                    if (change.wasAdded())
                    {
                        getChildren().addAll(change.getAddedSubList());
                    }
                }
            }
        });
    }

    public final void AddSwatch(final Paint p)
    {
        Swatch s = new Swatch(p);
        s.setOnMouseClicked(evt->
        {
            value.set(s.GetTexture());
        });
        HBox.setHgrow(s, Priority.ALWAYS);
        swatches.add(s);
    }

    public final SimpleObjectProperty<Paint> GetValueProp(){return value;}
    public final Paint GetValue(){return value.get();}
}
