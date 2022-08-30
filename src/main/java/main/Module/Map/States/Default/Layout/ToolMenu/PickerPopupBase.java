package main.Module.Map.States.Default.Layout.ToolMenu;

import javafx.scene.control.PopupControl;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import main.Tools.InitHelp;

public abstract class PickerPopupBase extends PopupControl
{
    protected final TexturePickerBase parent;
    protected final TabPane pane = new TabPane();
    public PickerPopupBase(final TexturePickerBase pickerParent)
    {
        parent = pickerParent;
        SelfInit();
    }


    private void SelfInit()
    {
        pane.setBackground(InitHelp.Background(Color.WHITE, 1));
        pane.setBorder(InitHelp.Border(Color.BLACK, 2));
        getScene().setRoot(pane);
        setAutoHide(true);
        setAutoFix(true);
    }


    public abstract Paint GetPaintValue();
}
