package main.Module.Relationship;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.Debug.Debug;
import main.Maths.Vec.Vec2;
import main.Maths.Vec.Vec2Prop;

public class RelationshipStartPoint extends Circle
{
    private final RelationshipGrid parent;
    private final Vec2 gridCenter = new Vec2();
    private final Vec2Prop startOffset = new Vec2Prop();
    private final Vec2 delta = new Vec2();
    public RelationshipStartPoint(final RelationshipGrid grid)
    {
        super(25);
        parent = grid;
        setFill(Color.RED);
        EventInit();
        ListenerInit();
    }
    private void EventInit()
    {
        setOnMouseEntered(evt->
        {
            setCursor(Cursor.HAND);

        });
        setOnMouseExited(evt->
        {
            setCursor(Cursor.DEFAULT);
        });
        setOnMousePressed(evt->
        {
            setCursor(Cursor.CLOSED_HAND);
            SetDelta(evt);
        });
        setOnMouseReleased(evt->
        {
            setCursor(Cursor.HAND);

        });
        setOnMouseDragged(evt->
        {
            setCursor(Cursor.CLOSED_HAND);
            Drag(evt);
        });
    }
    public void SetDelta(MouseEvent evt)
    {
        delta.x = evt.getX();
        delta.y = evt.getY();
    }

    public void Drag(MouseEvent evt)
    {
        double centerX = evt.getX() - delta.x;
        double centerY = evt.getY() - delta.y;
        Vec2 dif = new Vec2(centerX, centerY);
        SetDelta(evt);
        SetStartLocationX(GetOffsetProp().GetX()+dif.x);
        SetStartLocationY(GetOffsetProp().GetY()+dif.y);


    }
    private void ListenerInit()
    {
        startOffset.GetXProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                double oldRange = parent.GetUpperBound() - parent.GetLowerBound();
                double newRange = parent.GetAxisX().getWidth()+parent.GetAxisX().getLayoutX() - parent.GetAxisX().getLayoutX();
                double oldValue = t1.doubleValue();
                double oldMin = parent.GetLowerBound();
                double newMin = parent.GetAxisX().getLayoutX();
                double newValue = (((oldValue - oldMin) * newRange) / oldRange) + newMin;
                setCenterX(newValue);
            }
        });
        startOffset.GetYProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                double oldRange = parent.GetUpperBound() - parent.GetLowerBound();
                double newRange = parent.GetAxisY().getHeight() - parent.GetAxisY().getLayoutY();
                double oldValue = t1.doubleValue();
                double oldMin = parent.GetLowerBound();
                double newMin = parent.GetAxisY().getLayoutY();
                double newValue = (((oldValue - oldMin) * newRange) / oldRange) + newMin;
                setCenterY(newValue);
            }
        });

    }
    public void RecalculatePosition()
    {
        setCenterX(gridCenter.x + startOffset.GetX());
        setCenterY(gridCenter.y + startOffset.GetY());
    }
    public void ToCenter()
    {
        setCenterX(gridCenter.x);
        setCenterY(gridCenter.y);
    }



    public final void SetGridCenterX(double x){gridCenter.x = x;}
    public final void SetGridCenterY(double y){gridCenter.y = y;}
    public final void SetStartLocation(double x, double y){startOffset.Set(x, y);}
    public final void SetStartLocation(final Vec2 vec){startOffset.Set(vec);}
    public final void SetStartLocationX(final double x){startOffset.SetX(x);}
    public final void SetStartLocationY(final double y){startOffset.SetY(y);}

    public final Vec2Prop GetOffsetProp(){return startOffset;}
}
