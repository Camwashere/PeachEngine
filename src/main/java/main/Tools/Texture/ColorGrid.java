package main.Tools.Texture;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ColorGrid extends GridPane
{
    private final CustomColorPicker parent;
    public ColorGrid(final CustomColorPicker pickerParent)
    {
        parent = pickerParent;
        Init();
    }
    private void Init()
    {
        setAlignment(Pos.CENTER);
        setHgap(1);
        setVgap(1);
        WhiteRowInit();
        RowInit();

    }

    private Swatch MakeSwatch(Color c)
    {
        Swatch swatch = new Swatch(c);
        GridPane.setHgrow(swatch, Priority.ALWAYS);
        GridPane.setVgrow(swatch, Priority.ALWAYS);
        swatch.setOnMouseClicked(evt->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                parent.SetValue(swatch.GetTexture());
            }
        });
        return swatch;
    }
    private void WhiteRowInit()
    {
        String[] WHITE_ROW = {"0xffffffff", "0xf2f2f2ff","0xe6e6e6ff","0xccccccff","0xb3b3b3ff","0x999999ff","0x808080ff","0x666666ff","0x4d4d4dff","0x333333ff","0x1a1a1aff","0x000000ff"};
        for (int i=0; i<WHITE_ROW.length; i++)
        {
            add(MakeSwatch(Color.valueOf(WHITE_ROW[i])), i, 0);
        }
    }
    private void RowInit()
    {
        String[] FIRST_ROW = {"0x003333ff","0x001a80ff","0x1a0068ff","0x330033ff","0x4d001aff","0x990000ff","0x993300ff","0x994d00ff","0x996600ff","0x999900ff","0x666600ff","0x003300ff"};
        String[] LAST_ROW = {"0xccffffff","0xcce6ffff","0xe6ccffff","0xffccffff","0xffcce6ff","0xffccccff","0xffccb3ff","0xffe6ccff","0xffffb3ff","0xffffccff","0xe6e6ccff","0xccffccff"};
        for (int i=0; i<FIRST_ROW.length; i++)
        {
            Color start = Color.valueOf(FIRST_ROW[i]);
            Color end = Color.valueOf(LAST_ROW[i]);
            add(MakeSwatch(start), i, 1);
            BuildColumn(i, start, end);

        }
    }
    private void BuildColumn(int index, Color start, Color end)
    {
        Color current = start;
        for (int i=2; i<10; i++)
        {
            current = IncrementColor(current, end);
            add(MakeSwatch(current), index, i);
        }

    }
    private Color IncrementColor(final Color c, final Color max)
    {
        double r = c.getRed() + .1;
        double g = c.getGreen()+.1;
        double b = c.getBlue()+.1;
        double a = c.getOpacity()+.1;
        if (r > max.getRed())
        {
            r = max.getRed();
        }
        if (g > max.getGreen())
        {
            g = max.getGreen();
        }
        if (b > max.getBlue())
        {
            b = max.getBlue();
        }
        if (a > max.getOpacity())
        {
            a = max.getOpacity();
        }
        return new Color(r, g, b, a);
    }


}
