package main.Module.Map.States.Texture;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.io.File;
import java.util.HashMap;

public class TextureMakerLayer extends Canvas implements EventHandler<MouseEvent>
{
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final HashMap<TextureMakerTool, EventHandler<MouseEvent>> handlers;
    public final GraphicsContext gc;
    private TextureMakerTool current;

    public TextureMakerLayer(final String nameStr)
    {
        name.set(nameStr);
        handlers = new HashMap<>();
        gc = getGraphicsContext2D();
        Init();
        HandlersInit();

    }

    private void HandlersInit()
    {
        // DRAW TOOL
        handlers.put(TextureMakerTool.DRAW, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent evt)
            {

                if (evt.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    gc.beginPath();
                    gc.moveTo(evt.getX(), evt.getY());
                    gc.stroke();
                } else if (evt.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {
                    gc.lineTo(evt.getX(), evt.getY());
                    gc.stroke();
                    gc.closePath();
                    gc.beginPath();
                    gc.moveTo(evt.getX(), evt.getY());
                } else if (evt.getEventType() == MouseEvent.MOUSE_RELEASED)
                {

                }
            }
        });

        handlers.put(TextureMakerTool.ERASE, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent evt)
            {
                if (evt.getEventType() == MouseEvent.MOUSE_PRESSED)
                {

                    gc.setFill(Color.WHITE);
                    gc.setStroke(Color.WHITE);
                    gc.beginPath();
                    gc.moveTo(evt.getX(), evt.getY());
                    gc.stroke();
                } else if (evt.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {
                    gc.setFill(Color.WHITE);
                    gc.setStroke(Color.WHITE);
                    gc.lineTo(evt.getX(), evt.getY());
                    gc.stroke();
                    gc.closePath();
                    gc.beginPath();
                    gc.moveTo(evt.getX(), evt.getY());
                } else if (evt.getEventType() == MouseEvent.MOUSE_RELEASED)
                {
                    gc.setFill(Color.WHITE);
                    gc.setStroke(Color.WHITE);
                }
            }
        });


        // FILL TOOL
        handlers.put(TextureMakerTool.FILL, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent evt)
            {
                if (evt.getEventType() == MouseEvent.MOUSE_RELEASED)
                {
                    gc.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        });
    }


    private void Init()
    {
        setOnMousePressed(this);
        setOnMouseDragged(this);
        setOnMouseReleased(this);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        gc.setImageSmoothing(true);
        gc.setLineCap(StrokeLineCap.ROUND);
    }

    public void LoadImage(final File imgFile)
    {
        Image img = new Image("file:" + imgFile.getPath());
        if (img.isError())
        {
            System.out.println("Image error");
        } else
        {
            gc.clearRect(0, 0, getWidth(), getHeight());
            gc.drawImage(img, 0, 0, getWidth(), getHeight());
        }
    }


    public void SetTool(TextureMakerTool t)
    {
        current = t;
    }

    public void SetOpacity(double opacity)
    {
        Color currentColor = (Color) gc.getStroke();
        Color newColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), opacity);
        gc.setGlobalAlpha(opacity);
        gc.setStroke(newColor);
        gc.setFill(newColor);
    }

    @Override
    public void handle(MouseEvent event)
    {
        handlers.get(current).handle(event);

    }

    @Override
    public boolean isResizable()
    {
        return true;
    }

    @Override
    public double prefWidth(double height)
    {
        return getWidth();
    }

    @Override
    public double prefHeight(double width)
    {
        return getHeight();
    }

    public final void SetName(final String str)
    {
        name.set(str);
    }

    public final String GetName()
    {
        return name.get();
    }
}
