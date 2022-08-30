package main.Module.Map.States.Texture;

import javafx.scene.canvas.GraphicsContext;
import main.Module.Map.MapModule;
import main.Module.Map.States.MapStateBase;
import main.Module.Map.States.MapStateType;


public class MapStateTexture extends MapStateBase
{
    private final TextureMakerCenter center;
    private final TextureMakerSideBar left;
    public MapStateTexture(final MapModule parentMod)
    {
        super(parentMod, MapStateType.TEXTURE);
        center = new TextureMakerCenter(this);
        left = new TextureMakerSideBar(this);
        setCenter(center);
        setLeft(left);
    }
    public final TextureMakerLayer GetCurrentLayer(){return center.GetCurrentLayer();}
    public final GraphicsContext GetGC(){return GetCurrentLayer().gc;}
}
