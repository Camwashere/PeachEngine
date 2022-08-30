package main.Module.Map.States.Default.Layout.ToolMenu;


import javafx.scene.input.MouseEvent;

import javafx.scene.paint.Paint;



public class TexturePicker extends TexturePickerBase
{
    private final TexturePickerPopup popup;


    public TexturePicker()
    {
        popup = new TexturePickerPopup(this);
    }




    @Override
    protected void ShowPopup(MouseEvent evt)
    {
        popup.show(this, evt.getScreenX(), evt.getScreenY());
    }

    @Override
    protected Paint GetPopupFill()
    {
        return popup.GetValue();
    }
}
