package main.Module.Map.States.Default.Layout.ToolMenu;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import main.Tools.Texture.CustomColorPicker;

public class TexturePickerPopup extends PickerPopupBase
{

    private final Tab color = new Tab("Color");
    private final Tab texture = new Tab("Texture");
    private final Tab tilemap = new Tab("Tilemap");
    private final SimpleObjectProperty<Paint> value = new SimpleObjectProperty<>();
    public TexturePickerPopup(final TexturePicker pickerParent)
    {
        super(pickerParent);
        ListenerInit();
        TabInit();
    }
    private void TabInit()
    {
        ColorTabInit();
        texture.setClosable(false);
        tilemap.setClosable(false);
        pane.getTabs().addAll(color, texture);
    }
    private void ColorTabInit()
    {
        color.setClosable(false);
        CustomColorPicker picker = new CustomColorPicker();
        picker.GetValueProp().addListener(new ChangeListener<Paint>()
        {
            @Override
            public void changed(ObservableValue<? extends Paint> observableValue, Paint old, Paint t1)
            {
                value.set(t1);
                //parent.SetTexture(t1);
            }
        });
        picker.SetValue(Color.BLUE);
        color.setContent(picker);
    }


    @Override
    public Paint GetPaintValue()
    {
        return value.get();
    }

    private void ListenerInit()
    {
        value.addListener(new ChangeListener<Paint>()
        {
            @Override
            public void changed(ObservableValue<? extends Paint> observableValue, Paint paint, Paint t1)
            {
                parent.SetTexture(t1);
            }
        });
    }

    public final Paint GetValue(){return value.get();}
}
