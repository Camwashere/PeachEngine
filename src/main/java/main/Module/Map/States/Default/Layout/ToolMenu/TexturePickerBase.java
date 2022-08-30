package main.Module.Map.States.Default.Layout.ToolMenu;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.File;

public abstract class TexturePickerBase extends Rectangle
{
    private static final ImagePattern xImage = new ImagePattern(new Image(new File("src/main/resources/Map/x.png").toURI().toString()));
    protected final SimpleBooleanProperty ignore = new SimpleBooleanProperty(false);


    public TexturePickerBase()
    {
        SelfInit();
        ListenerInit();
        EventInit();
    }

    private void SelfInit()
    {
        setStroke(Color.BLACK);
        setWidth(25);
        setHeight(25);
    }
    private void ListenerInit()
    {
        ignore.addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1)
                {
                    setFill(xImage);
                }
                else
                {
                    setFill(GetPopupFill());
                }
            }
        });
    }
    private void EventInit()
    {
        setOnMouseClicked(evt->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                ShowPopup(evt);
            }
            else if (evt.getButton() == MouseButton.SECONDARY)
            {
                ToggleIgnore();
            }
        });
    }
    protected abstract void ShowPopup(final MouseEvent evt);
    protected abstract Paint GetPopupFill();




    public final void ToggleIgnore(){ignore.set(!ignore.get());}
    public final void SetIgnore(boolean bool){ignore.set(bool);}
    public final void SetTexture(final Paint p){setFill(p);}

    public final Paint GetTexture()
    {
        if (IsIgnore())
        {
            return Color.WHITE;
        }
        return getFill();
    }

    public final boolean IsIgnore(){return ignore.get();}
}
