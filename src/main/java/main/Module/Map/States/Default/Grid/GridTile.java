package main.Module.Map.States.Default.Grid;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import main.Maths.Vec.Vec2;
import main.Module.Map.Game.Territory;
import main.Module.Map.States.Default.Layout.Icon;
import main.Module.Map.Texture;
import main.Tools.InitHelp;

public class GridTile extends Pane
{
    private final Grid parent;
    private final Vec2 pos;
    private final Tooltip toolTip = new Tooltip();;

    private final SimpleObjectProperty<Icon> icon = new SimpleObjectProperty<>(null);
    private final SimpleObjectProperty<Territory> territory = new SimpleObjectProperty<>(null);
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);
    private final SimpleObjectProperty<Paint> texture = new SimpleObjectProperty<Paint>();
    private final SimpleObjectProperty<Color> border = new SimpleObjectProperty<Color>();
    private final SimpleObjectProperty<Color> selectedColor = new SimpleObjectProperty<Color>();

    public GridTile(final Grid grid, final Vec2 loc, final double size)
    {
        parent = grid;
        pos = loc;
        setPrefSize(size, size);
        ListenerInit();
        SetTexture(Color.WHITE);
        selectedColor.set(Color.LIMEGREEN);
        TooltipInit();
        UpdateTooltip();
    }
    private void TooltipInit()
    {
        toolTip.setShowDuration(Duration.INDEFINITE);
        toolTip.setShowDelay(Duration.seconds(2));
        //Tooltip.install(this, toolTip);
    }
    public void UpdateTooltip()
    {
        String base = String.format("Position: %s\nSelected: %s\n", pos.toString(), IsSelected());
        String iconStr = "Icon: ";
        if (HasIcon())
        {
            iconStr += GetIcon().GetName()+"\n";
        }
        else
        {
            iconStr += "None\n";
        }
        String territoryStr = "Territory: ";
        if (HasTerritory())
        {
            territoryStr += GetTerritory().GetName() + "\n";
        }
        else
        {
            territoryStr += "None\n";
        }
        toolTip.setText(base + iconStr + territoryStr);
    }

    private void ListenerInit()
    {
        texture.addListener(new ChangeListener<Paint>()
        {
            @Override
            public void changed(ObservableValue<? extends Paint> observableValue, Paint paint, Paint t1)
            {
                if (t1 == null)
                {
                    SetTexture(Color.WHITE);
                }
                setBackground(InitHelp.Background(t1, 1));
                UpdateTooltip();
            }
        });

        border.addListener(new ChangeListener<Color>()
        {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color paint, Color t1)
            {
                setBorder(InitHelp.Border(t1, 1));
                UpdateTooltip();
            }
        });
        selected.addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1)
                {
                    SetBorder(selectedColor.get());
                }
                else
                {
                    SetBorder(Color.BLACK);
                }
                UpdateTooltip();
            }
        });
        selectedColor.addListener(new ChangeListener<Color>()
        {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1)
            {
                if (IsSelected())
                {
                    SetBorder(t1);
                }

            }
        });

        icon.addListener(new ChangeListener<Icon>()
        {
            @Override
            public void changed(ObservableValue<? extends Icon> observableValue, Icon icon, Icon t1)
            {
                if (t1 == null)
                {
                    getChildren().clear();
                }
                else
                {
                    ImageView view = new ImageView(t1);
                    view.setFitWidth(getWidth());
                    view.setFitHeight(getHeight());
                    getChildren().add(view);
                }
            }
        });
        territory.addListener(new ChangeListener<Territory>()
        {
            @Override
            public void changed(ObservableValue<? extends Territory> observableValue, Territory territory, Territory t1)
            {
                if (t1 == null)
                {
                    selectedColor.set(border.get());
                }
                else
                {
                    selectedColor.set(t1.GetColor());
                }
            }
        });

        border.setValue(Color.BLACK);
    }

    public final void RemoveIcon(){SetIcon(null);}
    public final void Clear(){SetTexture(Color.WHITE); RemoveIcon(); SetSelected(false);}


    public final void SetSize(final double size)
    {
        setPrefSize(size, size);
    }

    public final void SetTexture(final Texture t) {texture.set(t.AsImagePattern());}
    public final void SetIcon(final Icon i){icon.set(i);}
    public final void SetTexture(final Paint tex){texture.set(tex);}
    public final void SetSelected(final boolean bool){selected.set(bool);}
    public final void SetBorder(final Color c){border.set(c);}
    public final void SetTerritory(final Territory t){territory.set(t);}
    public final void ClearTerritory(){SetTerritory(null);}
    public final void ToggleSelected(){SetSelected(!IsSelected());}

    public final boolean IsSelected(){return selected.get();}
    public final boolean HasTerritory(){return GetTerritory() != null;}
    public final boolean HasTerritory(final Territory t){return t == GetTerritory();}
    public final boolean HasIcon(){return GetIcon() != null;}
    public final Paint GetTexture(){return texture.get();}
    public final Icon GetIcon(){return icon.get();}
    public final Color GetBorder(){return border.get();}
    public final Territory GetTerritory(){return territory.get();}
    public final Vec2 GetPos(){return pos;}
}
