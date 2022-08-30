package main.Module.Map.States.Default.Grid.Tool;

import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GridToolMode
{
    private final String name;
    private EventHandler<MouseEvent> mouseHandler;
    private EventHandler<KeyEvent> keyHandler;

    public GridToolMode(final String str, final EventHandler<MouseEvent> mouseEventHandler)
    {
        name = str;
        mouseHandler = mouseEventHandler;
    }

    public final void Handle(final InputEvent event)
    {
        if (event instanceof MouseEvent evt)
        {
            mouseHandler.handle(evt);
        }
        else if (event instanceof KeyEvent evt)
        {
            keyHandler.handle(evt);
        }
    }

    public final void SetKeyHandler(final EventHandler<KeyEvent> keyEventHandler)
    {
        keyHandler = keyEventHandler;
    }
    public final void SetMouseHandler(final EventHandler<MouseEvent> mouseEventHandler)
    {
        mouseHandler = mouseEventHandler;
    }


    public final String GetName(){return name;}


}
