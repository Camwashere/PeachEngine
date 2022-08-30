package main.Module.Map.States.Texture;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import main.Module.Map.Texture;
import main.Tools.InitHelp;
import main.Tools.Field.LabeledNumericTextField;
import main.Tools.LabeledSlider;

public class TextureMakerSideBar extends VBox
{
    public final LabeledNumericTextField sizeField;
    private final MapStateTexture parent;
    private final ColorPicker picker;
    private final ComboBox<BlendMode> blendField;
    private final ComboBox<TextureMakerTool> toolsField;
    private final LabeledSlider opacity;
    private final Button finish;


    public TextureMakerSideBar(final MapStateTexture maker)
    {
        parent = maker;
        picker = new ColorPicker();
        PickerInit();
        blendField = new ComboBox<BlendMode>();
        BlendFieldInit();
        toolsField = new ComboBox<TextureMakerTool>();
        ToolsFieldInit();
        sizeField = new LabeledNumericTextField("Size");
        SizeFieldInit();
        opacity = new LabeledSlider("Opacity", 0.0, 100.0, 100.0);
        OpacityInit();
        finish = new Button("Finish");
        FinishInit();
        sizeField.SetValue(1);
        getChildren().addAll(picker, sizeField, opacity, toolsField, blendField, finish);

    }

    private void ToolsFieldInit()
    {
        toolsField.setFocusTraversable(false);
        toolsField.getItems().addAll(TextureMakerTool.values());
        toolsField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TextureMakerTool>()
        {
            @Override
            public void changed(ObservableValue<? extends TextureMakerTool> observableValue, TextureMakerTool textureMakerTools, TextureMakerTool t1)
            {
                parent.GetCurrentLayer().SetTool(t1);
            }
        });
        toolsField.getSelectionModel().select(TextureMakerTool.DRAW);
    }

    private void OpacityInit()
    {
        opacity.GetSlider().valueProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                parent.GetCurrentLayer().SetOpacity(t1.doubleValue() / 100);
            }
        });
    }

    private void BlendFieldInit()
    {
        blendField.setFocusTraversable(false);
        blendField.getItems().addAll(BlendMode.values());
        blendField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BlendMode>()
        {
            @Override
            public void changed(ObservableValue<? extends BlendMode> observableValue, BlendMode blendMode, BlendMode t1)
            {
                parent.GetCurrentLayer().gc.setGlobalBlendMode(t1);
            }
        });

        blendField.getSelectionModel().select(BlendMode.SRC_OVER);
    }

    private void FinishInit()
    {
        InitHelp.ButtonInit(finish);
        finish.setOnMouseReleased(evt ->
        {
            Texture t = new Texture("Tex", (int) parent.GetCurrentLayer().getWidth(), (int) parent.GetCurrentLayer().getHeight());
            SnapshotParameters params = new SnapshotParameters();
            parent.GetCurrentLayer().snapshot(params, t);
            //parent.GetParent().GetLeftBar().GetTexturePicker().AddTexture(t);
        });

    }

    private void PickerInit()
    {
        picker.valueProperty().addListener(new ChangeListener<Color>()
        {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1)
            {
                parent.GetGC().setStroke(t1);
                parent.GetGC().setFill(t1);
            }
        });
    }

    private void SizeFieldInit()
    {
        sizeField.GetValueProp().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                parent.GetGC().setLineWidth(t1.doubleValue());
                parent.GetGC().setLineCap(StrokeLineCap.ROUND);
            }
        });
    }

    public final MapStateTexture GetParent()
    {
        return parent;
    }
}
