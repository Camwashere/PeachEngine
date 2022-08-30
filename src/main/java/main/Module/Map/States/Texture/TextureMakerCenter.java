package main.Module.Map.States.Texture;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class TextureMakerCenter extends StackPane
{
    private final MapStateTexture parent;
    private final ArrayList<TextureMakerLayer> layers = new ArrayList<>();
    private final SimpleIntegerProperty current = new SimpleIntegerProperty();

    public TextureMakerCenter(final MapStateTexture parentState)
    {
        parent = parentState;
        AddLayer();
    }

    public final void AddLayer()
    {
        TextureMakerLayer layer = new TextureMakerLayer("layer");
        layer.widthProperty().bind(widthProperty());
        layer.heightProperty().bind(heightProperty());
        layers.add(layer);
        current.set(layers.size()-1);
        layer.SetTool(TextureMakerTool.DRAW);
        getChildren().add(layer);
    }

    public final TextureMakerLayer GetCurrentLayer(){return layers.get(current.get());}
}
