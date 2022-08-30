package main.Tools.Texture;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Swatch extends Rectangle
{
    public Swatch()
    {
        super(25, 25, Color.WHITE);
        Init();
    }
    public Swatch(Paint tex)
    {
        super(25, 25, tex);
        Init();
    }
    private void Init()
    {
        setStrokeWidth(2);
        setStroke(Color.TRANSPARENT);
        EventInit();
    }
    private void EventInit()
    {
        setOnMouseEntered(evt->
        {
            setStroke(Color.LIMEGREEN);
        });
        setOnMouseExited(evt->
        {
            setStroke(Color.TRANSPARENT);
        });
    }

    public void SetTexture(final Paint p){setFill(p);}
    public Paint GetTexture(){return getFill();}
}
