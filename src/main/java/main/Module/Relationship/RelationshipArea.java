package main.Module.Relationship;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.chart.Axis;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Debug.Debug;
import main.Maths.MathHelp;
import main.Maths.Vec.Vec2;
import main.Tools.DragResizer;
import main.Tools.InitHelp;

public class RelationshipArea extends VBox
{
    private final RelationshipGrid parent;
    private final SimpleObjectProperty<Color> color = new SimpleObjectProperty<Color>();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final TextField label = new TextField();
    private final Vec2 delta = new Vec2();
    private final ContextMenu menu = new ContextMenu();


    public RelationshipArea(final RelationshipGrid grid, double x, double y)
    {
        parent = grid;
        DisplayInit(x, y);
        Init();
    }

    public final void ReCalculate()
    {
        //CalculateGridScale();
        PosSnap(getLayoutX(), getLayoutY());
        SizeSnap(getWidth(), getHeight());
    }


    private void DisplayInit(double x, double y)
    {
        //CalculateGridScale();
        PosSnap(x, y);
        setPrefWidth(GetSectionX());
        setPrefHeight(GetSectionY());
    }



    private void PosSnap(double x, double y)
    {
        Vec2 pos = FindClosestCoordinate(new Vec2(x, y), false);
        //Debug.Println("Pos: " + pos);
        setLayoutX(pos.x);
        if (pos.y + getHeight() <= parent.GetAxisX().getLayoutY())
        {
            setLayoutY(pos.y);
        }
        else
        {
            setLayoutY(parent.GetAxisX().getLayoutY()-getHeight());
        }
    }
    private void SizeSnap(double w, double h)
    {
        Vec2 size = FindClosestCoordinate(new Vec2(w, h), true);
        if (size.x < getPrefWidth())
        {
            setPrefWidth(GetSectionX());
        }
        if (size.y < getPrefHeight())
        {
            setPrefHeight(GetSectionY());
        }
        setWidth(size.x);
        setHeight(size.y);
    }
    private Vec2 FindClosestCoordinate(final Vec2 start, boolean size)
    {
        double closest = Double.MAX_VALUE;
        Vec2 pos = new Vec2(Double.MAX_VALUE, start.y);
        double xOffset = parent.GetAxisY().getWidth();

        if (size)
        {
            xOffset=0;
        }

        for (final Axis.TickMark<Number> t : parent.GetAxisX().getTickMarks())
        {
            double dist = Vec2.DistanceBetween(start, new Vec2(t.getPosition()+xOffset, start.y));
            if (dist < closest)
            {
                closest = dist;
                pos.x = t.getPosition()+xOffset;

            }
        }
        closest = Double.MAX_VALUE;
        for (final Axis.TickMark<Number> t : parent.GetAxisY().getTickMarks())
        {
            double dist = Vec2.DistanceBetween(start, new Vec2(start.x, t.getPosition()));
            {
                if (dist < closest)
                {
                    closest = dist;
                    pos.y = t.getPosition();
                }
            }
        }
        return pos;
    }



    private void Init()
    {

        LabelInit();
        ListenerInit();
        EventInit();
        DragResizer.MakeResizable(this);
        ContextMenuInit();
    }
    private void ContextMenuInit()
    {
        MenuItem rename = new MenuItem("Rename");
        MenuItem changeColor = new MenuItem("Choose Color");
        MenuItem delete = new MenuItem("Delete");
        rename.setOnAction(evt->
        {
            label.setDisable(false);
            label.requestFocus();
        });
        changeColor.setOnAction(evt->
        {
            ColorPicker picker = new ColorPicker(GetColor());
            PopupControl popup = new PopupControl();
            popup.setAutoFix(true);
            popup.setAutoHide(true);
            popup.getScene().setRoot(picker);
            picker.setBackground(InitHelp.Background(Color.WHITE, 1));
            popup.show(this, menu.getX(), menu.getY());
            picker.show();
            picker.valueProperty().addListener(new ChangeListener<Color>()
            {
                @Override
                public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1)
                {
                    SetColor(t1);
                    popup.hide();
                }
            });
        });
        delete.setOnAction(evt->
        {
            parent.RemoveArea(this);
        });
        menu.getItems().addAll(rename, changeColor, delete);



    }
    private void EventInit()
    {
        setOnMousePressed(evt->
        {
            delta.x = getLayoutX() - evt.getSceneX();
            delta.y = getLayoutY() - evt.getSceneY();
        });
        setOnMouseReleased(evt->
        {
            if (evt.getButton() == MouseButton.SECONDARY)
            {
                menu.show(this, evt.getScreenX(), evt.getScreenY());
                evt.consume();
            }
        });

        setOnMouseClicked(evt->
        {
            if (evt.getClickCount() > 1)
            {
                label.setDisable(false);
                label.requestFocus();
            }
            evt.consume();
        });
        setOnMouseDragged(evt->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                if (getCursor() != Cursor.S_RESIZE && getCursor() != Cursor.E_RESIZE)
                {
                    Drag(evt);
                }

            }
            else
            {
                evt.consume();
            }
        });


        label.setOnKeyPressed(evt->
        {
            if (evt.getCode() == KeyCode.ENTER)
            {
                requestFocus();
            }
        });
    }
    private void LabelInit()
    {
        label.setText("Area");
        label.setFocusTraversable(true);
        getChildren().add(label);
        InitHelp.GrowInit(this);
        label.setDisable(true);

    }
    private void ListenerInit()
    {
        color.addListener(new ChangeListener<Color>()
        {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1)
            {
                setBackground(InitHelp.Background(t1, 0));
            }
        });
        name.addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                label.setText(t1);
            }
        });
        label.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1)
                {
                    label.setDisable(false);
                    label.selectAll();
                }
                else
                {
                    label.setDisable(true);
                }
            }
        });

        boundsInParentProperty().addListener(new ChangeListener<Bounds>()
        {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1)
            {
                //CalculateGridScale();
                PosSnap(getLayoutX(), getLayoutY());
                SizeSnap(getWidth(), getHeight());


                for (final RelationshipArea area : parent.GetRelationshipAreas())
                {
                    if (area != RelationshipArea.this)
                    {
                        if (Overlaps(area))
                        {
                            break;
                        }
                    }
                }

            }
        });
    }

    public final void Drag(MouseEvent evt)
    {
        setLayoutX(evt.getSceneX()+delta.x);
        setLayoutY(evt.getSceneY()+delta.y);
    }

    public final boolean Overlaps(final RelationshipArea other)
    {
        return getBoundsInParent().contains(other.getBoundsInParent());

    }


    public void SetColor(final Color c){color.set(new Color(c.getRed(), c.getGreen(), c.getBlue(), .5));}
    public void SetName(final String str){name.set(str);}

    public final double GetSectionX(){return parent.GetSectionX();}
    public final double GetSectionY(){return parent.GetSectionY();}
    public final Color GetColor(){return color.get();}
    public final String GetName(){return name.get();}
    public final RelationshipGrid GetParent(){return parent;}


}
