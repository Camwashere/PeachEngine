package main.Module.Relationship;



import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import main.Debug.Debug;
import main.Maths.Vec.Vec2;
import main.Maths.Vec.Vec2Prop;
import main.Tools.InitHelp;

import java.util.ArrayList;


public class RelationshipGrid extends Pane
{
    private final RelationshipCenter parent;
    private final SimpleDoubleProperty tickUnit = new SimpleDoubleProperty(20);
    private final SimpleDoubleProperty lowerBound = new SimpleDoubleProperty(-100);
    private final SimpleDoubleProperty upperBound = new SimpleDoubleProperty(100);
    private final ArrayList<RelationshipArea> areas = new ArrayList<RelationshipArea>();
    private final ContextMenu menu = new ContextMenu();
    private final Vec2 menuPos = new Vec2();
    private final NumberAxis xAxis = new NumberAxis("X", lowerBound.get(), upperBound.get(), tickUnit.get());
    private final NumberAxis yAxis = new NumberAxis("Y", lowerBound.get(), upperBound.get(), tickUnit.get());
    private final RelationshipStartPoint startPoint;
    private final ArrayList<Line> lines = new ArrayList<Line>();
    private final SimpleBooleanProperty showLines = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty showStart = new SimpleBooleanProperty(true);
    private final SimpleDoubleProperty sectionX = new SimpleDoubleProperty();
    private final SimpleDoubleProperty sectionY = new SimpleDoubleProperty();



    public RelationshipGrid(final RelationshipCenter centerParent)
    {
        parent = centerParent;
        getChildren().add(xAxis);
        getChildren().add(yAxis);
        startPoint = new RelationshipStartPoint(this);
        getChildren().add(startPoint);
        xAxis.setSide(Side.BOTTOM);
        yAxis.setSide(Side.LEFT);
        ContextMenuInit();
        EventInit();
        SizeInit();
        ListenerInit();
        CalculateGridScale();
    }
    private void CalculateGridScale()
    {
        double sum = 0;
        double count = 0;
        for (final Axis.TickMark<Number> t : xAxis.getTickMarks())
        {
            sum += t.getPosition();
            count++;
        }
        double halfWay = sum/count;
        double axisLayout = xAxis.getLayoutX();
        double fullWay = halfWay*2.0;
        double section = fullWay/count;
        SetSectionX(section);
        // Add layout to section for adjusted coords
        sum = 0;
        count = 0;
        for (final Axis.TickMark<Number> t : yAxis.getTickMarks())
        {
            sum += t.getPosition();
            count++;
        }
        halfWay = sum/count;
        fullWay = halfWay*2.0;
        section = fullWay/count;
        SetSectionY(section);
        //Debug.Println("SECTION: " + new Vec2(GetSectionX(), GetSectionY()));
    }

    public final void ReCalculateAreas()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                CalculateGridScale();
                ReCalculateLines();
                startPoint.RecalculatePosition();
                for (final RelationshipArea area : areas)
                {
                    area.ReCalculate();
                }
                layout();
            }
        });
    }
    private void ReCalculateLines()
    {
        for (final Line n : lines)
        {
            getChildren().remove(n);
        }
        lines.clear();
        double adjust = .5;
        double sum = 0;
        double count = 0;
        // HORIZONTAL
        for (final Axis.TickMark<Number> yTick : yAxis.getTickMarks())
        {

            Vec2 start = new Vec2(xAxis.getLayoutX(), yTick.getPosition()+adjust);
            Vec2 end = new Vec2(getWidth(), yTick.getPosition()+adjust);
            AddLine(start, end);
            sum+=yTick.getPosition();
            count++;

        }
        startPoint.SetGridCenterY(sum/count);
        sum=0;
        count=0;

        // VERTICAL
        for (final Axis.TickMark<Number> xTick : xAxis.getTickMarks())
        {
            Vec2 start = new Vec2(xTick.getPosition()+xAxis.getLayoutX()+adjust, xAxis.getLayoutY());
            Vec2 end = new Vec2(xTick.getPosition()+xAxis.getLayoutX()+adjust, 0);
            sum += xTick.getPosition();
            count++;
            AddLine(start, end);
        }
        startPoint.SetGridCenterX((sum/count)+xAxis.getLayoutX());

    }

    private void AddLine(final Vec2 start, final Vec2 end)
    {
        Line l = new Line();
        l.setStartX(start.x);
        l.setStartY(start.y);
        l.setEndX(end.x);
        l.setEndY(end.y);
        Color c = Color.LIGHTGRAY;
        l.setFill(c);
        l.setStroke(c);

        l.setDisable(true);
        lines.add(l);
        getChildren().add(l);
        l.toBack();
    }



    private void ListenerInit()
    {
        tickUnit.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                xAxis.setTickUnit(t1.doubleValue());
                yAxis.setTickUnit(t1.doubleValue());
                ReCalculateAreas();
            }
        });
        lowerBound.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                xAxis.setLowerBound(t1.doubleValue());
                yAxis.setLowerBound(t1.doubleValue());
                ReCalculateAreas();
            }
        });
        upperBound.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                xAxis.setUpperBound(t1.doubleValue());
                yAxis.setUpperBound(t1.doubleValue());
                ReCalculateAreas();
            }
        });
        boundsInParentProperty().addListener(new ChangeListener<Bounds>()
        {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1)
            {
                xAxis.setLayoutY(getHeight()-xAxis.getHeight());
                xAxis.setLayoutX(yAxis.getWidth());
                xAxis.setPrefWidth(getWidth()-xAxis.getLayoutX());

                yAxis.setPrefHeight(getHeight()-xAxis.getHeight());
            }
        });
        layoutBoundsProperty().addListener(new ChangeListener<Bounds>()
        {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1)
            {
                xAxis.setLayoutY(getHeight()-xAxis.getHeight());
                xAxis.setLayoutX(yAxis.getWidth());
                xAxis.setPrefWidth(getWidth()-xAxis.getLayoutX());

                yAxis.setPrefHeight(getHeight()-xAxis.getHeight());
                ReCalculateAreas();
            }
        });
        showLines.addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1)
                {
                    ShowLines();
                }
                else
                {
                    HideLines();
                }
            }
        });
        showStart.addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1)
                {
                    getChildren().add(startPoint);
                }
                else
                {
                    getChildren().remove(startPoint);
                }
            }
        });


    }
    private void EventInit()
    {
        setOnMouseClicked(evt->
        {
            if (evt.getButton() == MouseButton.SECONDARY)
            {
                menuPos.MakeEqual(evt.getX(), evt.getY());
                menu.show(this, evt.getScreenX(), evt.getScreenY());
            }
        });
    }
    private void ContextMenuInit()
    {
        MenuItem add = new MenuItem("New Area");
        add.setOnAction(evt->
        {
            AddArea(menuPos.x, menuPos.y);
        });
        menu.getItems().add(add);
        menu.setAutoFix(true);
        menu.setAutoHide(true);

    }
    private void SizeInit()
    {
        InitHelp.GrowInit(this);
        maxWidthProperty().bind(parent.widthProperty());
        maxHeightProperty().bind(parent.heightProperty());
        prefWidthProperty().bind(parent.widthProperty());
        prefHeightProperty().bind(parent.heightProperty());
    }
    private void ShowLines()
    {
        for (final Line l : lines)
        {
            l.setVisible(true);
        }
    }
    private void HideLines()
    {
        for (final Line l : lines)
        {
            l.setVisible(false);
        }
    }
    public final void DisplayLines(boolean bool){showLines.set(bool);}

    public final void AddArea(final double x, final double y)
    {
        RelationshipArea area = new RelationshipArea(this, x, y);
        area.SetColor(InitHelp.RandomColor());
        areas.add(area);
        getChildren().add(area);
    }
    public final void RemoveArea(final RelationshipArea area)
    {
        areas.remove(area);
        getChildren().remove(area);
    }







    public final void SetXName(final String str){xAxis.setLabel(str);}
    public final void SetYName(final String str){yAxis.setLabel(str);}
    public final void SetTickUnit(final double d){tickUnit.set(d);}
    public final void SetStartLocation(double x, double y){startPoint.SetStartLocation(x, y);}
    public final void SetStartLocation(final Vec2 vec){startPoint.SetStartLocation(vec);}
    public final void SetStartLocationX(final double x){startPoint.SetStartLocationX(x);}
    public final void SetStartLocationY(final double y){startPoint.SetStartLocationY(y);}
    private void SetSectionX(double d){sectionX.set(d);}
    private void SetSectionY(double d){sectionY.set(d);}




    public final RelationshipCenter GetParent(){return parent;}
    public final double GetTickUnit(){return tickUnit.get();}
    public final double GetLowerBound(){return lowerBound.get();}
    public final double GetUpperBound(){return upperBound.get();}
    public final boolean IsShowLines(){return showLines.get();}
    public final boolean IsStartShowing(){return showStart.get();}
    public final SimpleBooleanProperty GetShowLinesProp(){return showLines;}
    public final SimpleBooleanProperty GetShowStartProp(){return showStart;}
    public final SimpleDoubleProperty GetTickUnitProp(){return tickUnit;}
    public final SimpleDoubleProperty GetLowerBoundProp(){return lowerBound;}
    public final SimpleDoubleProperty GetUpperBoundProp(){return upperBound;}
    public final ArrayList<RelationshipArea> GetRelationshipAreas(){return areas;}
    public final RelationshipStartPoint GetStartPoint(){return startPoint;}
    public final double GetSectionX(){return sectionX.get();}
    public final double GetSectionY(){return sectionY.get();}
    public final NumberAxis GetAxisX(){return xAxis;}
    public final NumberAxis GetAxisY(){return yAxis;}

}
