package main.Tools;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

/**
 * {@link DragResizer} can be used to add mouse listeners to a {@link Region}
 * and make it resizable by the user by clicking and dragging the border in the
 * same way as a window.
 * <p>
 * Only height resizing is currently implemented. Usage: <pre>DragResizer.makeResizable(myAnchorPane);</pre>
 *
 * @author atill
 *
 */
public class DragResizer {

    /**
     * The margin around the control that a user can click in to start resizing
     * the region.
     */
    private static final int RESIZE_MARGIN = 5;

    private final Region region;

    private double x;

    private double y;

    private boolean initMinHeight;
    private boolean initMinWidth;

    private boolean dragging;

    private DragResizer(Region aRegion) {
        region = aRegion;
    }

    public static void MakeResizable(Region region) {
        final DragResizer resizer = new DragResizer(region);
        region.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                resizer.mousePressed(event);
            }
        });
        region.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                resizer.mouseReleased(event);
            }
        });
        region.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                resizer.mouseDragged(event);
            }
        });

        region.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                resizer.mouseOver(event);
            }
        });

    }

    protected void mouseReleased(MouseEvent event) {
        dragging = false;
        region.setCursor(Cursor.DEFAULT);
    }

    protected void mouseOver(MouseEvent event)
    {

        if(isInDraggableZoneY(event))
        {
            region.setCursor(Cursor.S_RESIZE);
        }
        else if (isInDraggableZoneX(event))
        {
            region.setCursor(Cursor.E_RESIZE);
        }
        else
        {
            region.setCursor(Cursor.DEFAULT);
        }
    }

    protected boolean isInDraggableZoneY(MouseEvent event)
    {
        return event.getY() > (region.getHeight() - RESIZE_MARGIN);
    }
    protected boolean isInDraggableZoneX(MouseEvent event)
    {
        return event.getX() > (region.getWidth()-RESIZE_MARGIN);
    }

    protected void mouseDragged(MouseEvent event)
    {
        if(!dragging)
        {
            return;
        }

        if (region.getCursor() == Cursor.S_RESIZE)
        {
            double mousey = event.getY();

            double newHeight = region.getMinHeight() + (mousey - y);

            region.setMinHeight(newHeight);

            y = mousey;
        }
        else if (region.getCursor() == Cursor.E_RESIZE)
        {
            double mousex = event.getX();

            double newWidth = region.getMinWidth() + (mousex - x);

            region.setMinWidth(newWidth);

            x = mousex;
        }
    }

    protected void mousePressed(MouseEvent event) {

        // ignore clicks outside of the draggable margin
        if(!isInDraggableZoneY(event) && !isInDraggableZoneX(event))
        {
            return;
        }

        dragging = true;

        // make sure that the minimum height is set to the current height once,
        // setting a min height that is smaller than the current height will
        // have no effect
        if (!initMinHeight) {
            region.setMinHeight(region.getHeight());
            initMinHeight = true;
        }
        if (! initMinWidth)
        {
            region.setMinWidth(region.getWidth());
            initMinWidth=true;
        }

        x = event.getX();
        y = event.getY();
    }
}
