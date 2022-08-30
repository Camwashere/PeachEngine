package main.Tools.Texture;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import main.Tools.InitHelp;

public class CustomColorPicker extends BorderPane
{
    private final SwatchBar top;
    private final ColorGrid center;
    private final SimpleObjectProperty<Paint> value = new SimpleObjectProperty<Paint>(Color.WHITE);
    public CustomColorPicker()
    {
        top = new SwatchBar();
        center = new ColorGrid(this);
        TopInit();
        setCenter(center);
        setBackground(InitHelp.Background(Color.WHITE, 1));
    }
    private void TopInit()
    {
        top.AddSwatch(Color.CYAN);
        top.AddSwatch(Color.TEAL);
        top.AddSwatch(Color.BLUE);
        top.AddSwatch(Color.NAVY);
        top.AddSwatch(Color.MAGENTA);
        top.AddSwatch(Color.PURPLE);
        top.AddSwatch(Color.RED);
        top.AddSwatch(Color.MAROON);
        top.AddSwatch(Color.YELLOW);
        top.AddSwatch(Color.OLIVE);
        top.AddSwatch(Color.GREEN);
        top.AddSwatch(Color.LIME);

        top.GetValueProp().addListener(new ChangeListener<Paint>()
        {
            @Override
            public void changed(ObservableValue<? extends Paint> observableValue, Paint paint, Paint t1)
            {
                SetValue(t1);
            }
        });

        VBox box = new VBox();
        Label recent = new Label("Recent");
        InitHelp.LabelInit(recent);
        recent.setAlignment(Pos.TOP_LEFT);
        box.getChildren().add(recent);
        box.getChildren().add(top);
        box.setPadding(new Insets(15, 0, 15, 0));
        box.setBorder(InitHelp.Border(Color.BLACK, 0, 0, 2, 0));

        setTop(box);


    }

    public final Paint GetValue(){return value.get();}
    public final SimpleObjectProperty<Paint> GetValueProp(){return value;}

    public final void SetValue(final Paint val){value.set(val);}

}
