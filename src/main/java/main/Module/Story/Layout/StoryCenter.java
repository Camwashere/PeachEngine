package main.Module.Story.Layout;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.Maths.Vec.Vec2;
import main.Module.Story.Scenario.Scenario;
import main.Module.Story.Scenario.ScenarioState;
import main.Module.Story.StoryModule;

import java.util.UUID;

public class StoryCenter extends ScrollPane
{
    private final StoryModule parent;
    private final Vec2 prevMouse;
    private double scaleValue = 1.0;
    private double zoomIntensity = 0.02;
    private Node target;
    private Node zoomNode;

    public StoryCenter(final StoryModule parentMod)
    {
        parent = parentMod;
        prevMouse = new Vec2();
        Init();
        parent.GetCurrentScenarioProp().addListener(new ChangeListener<UUID>()
        {
            @Override
            public void changed(ObservableValue<? extends UUID> observableValue, UUID UUID, UUID t1)
            {
                target = parent.GetCurrentScenario();
                zoomNode = new Group(target);
                setContent(outerNode(zoomNode));
                updateScale();
            }
        });

    }

    private void Init()
    {
        Background background = new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY));
        setBackground(background);
        Border b = new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        setBorder(b);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.NEVER);
        EventInit();
        setFitToWidth(true);
        setFitToHeight(true);



    }
    private Node outerNode(Node node)
    {
        Node outerNode = centeredNode(node);
        outerNode.setOnScroll(e ->
        {
            e.consume();
            onScroll(e.getTextDeltaY(), new Point2D(e.getX(), e.getY()));
        });
        return outerNode;
    }

    private Node centeredNode(Node node)
    {
        VBox vBox = new VBox(node);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
    private void updateScale()
    {
        target.setScaleX(scaleValue);
        target.setScaleY(scaleValue);
    }
    private void onScroll(double wheelDelta, Point2D mousePoint)
    {
        double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = getViewportBounds();

        // calculate pixel offsets from [0, 1] range
        double valX = this.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double valY = this.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

        scaleValue = scaleValue * zoomFactor;
        updateScale();
        this.layout(); // refresh ScrollPane scroll positions & target bounds

        // convert target coordinates to zoomTarget coordinates
        javafx.geometry.Point2D posInZoomTarget = target.parentToLocal(zoomNode.parentToLocal(mousePoint));

        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = target.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
        this.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
        this.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
    }
    private void EventInit()
    {
        setOnMousePressed(evt ->
        {
            if (evt.getButton() == MouseButton.SECONDARY)
            {

                if (parent.GetCurrentScenario().GetCurrentState()!=ScenarioState.CONNECT)
                {
                    setPannable(true);
                    parent.GetCurrentScenario().SetState(ScenarioState.PAN);
                    prevMouse.x = evt.getX();
                    prevMouse.y = evt.getY();
                }

            }
        });


        setOnMouseReleased(evt ->
        {
            if (evt.getButton() == MouseButton.SECONDARY)
            {
                setPannable(false);
                double distance = Vec2.DistanceBetween(prevMouse, new Vec2(evt.getX(), evt.getY()));
                if (distance < 10)
                {
                    parent.GetCurrentScenario().SetState(ScenarioState.DEFAULT);
                    parent.GetCurrentScenario().handle(evt);
                }
            }
        });
    }

    public void LoadScenario(final Scenario s)
    {
        setContent(s);
    }
}
