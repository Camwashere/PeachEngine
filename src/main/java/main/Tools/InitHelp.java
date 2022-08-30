package main.Tools;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import main.Maths.Chance;

public class InitHelp
{
    public static void ButtonInit(Control button)
    {
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setFocusTraversable(false);
        HBox.setHgrow(button, Priority.ALWAYS);
        VBox.setVgrow(button, Priority.ALWAYS);
    }
    public static void ButtonsInit(Control... buttons)
    {
        for (Control b : buttons)
        {
            ButtonInit(b);
        }
    }

    public static void LabelInit(Label label)
    {
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        label.setFocusTraversable(false);
        HBox.setHgrow(label, Priority.ALWAYS);
        VBox.setVgrow(label, Priority.ALWAYS);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setWrapText(true);
    }

    public static void GrowInit(Node node)
    {
        HBox.setHgrow(node, Priority.ALWAYS);
        VBox.setVgrow(node, Priority.ALWAYS);
    }

    public static void LabelsInit(Label... labels)
    {
        for (Label label : labels)
        {
            LabelInit(label);
        }
    }

    public static void NodeInit(Control node)
    {
        node.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        node.setFocusTraversable(false);
        HBox.setHgrow(node, Priority.ALWAYS);
        VBox.setVgrow(node, Priority.ALWAYS);
    }

    public static Border Border(final Paint c, final double width)
    {
        return new Border(new BorderStroke(c, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(width, width, width, width)));
    }
    public static Border Border(final Paint c, double top, double right, double bottom, double left)
    {
        return new Border(new BorderStroke(c, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(top, right, bottom, left)));
    }
    public static Background Background(final Paint c, final double width)
    {
        return new Background(new BackgroundFill(c, CornerRadii.EMPTY, new Insets(width, width, width, width)));
    }

    public static Color RandomColor()
    {
        return new Color(Chance.Rand(1), Chance.Rand(1), Chance.Rand(1), 1);
    }
    public static Color RandomColor(double opacity)
    {
        return new Color(Chance.Rand(1), Chance.Rand(1), Chance.Rand(1), opacity);
    }

}
