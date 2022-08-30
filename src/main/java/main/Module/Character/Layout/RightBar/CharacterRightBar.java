package main.Module.Character.Layout.RightBar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;
import main.Module.Character.Game.CharacterBase;
import main.Module.Character.States.CharacterChildStateBase;


public class CharacterRightBar extends VBox
{
    private final CharacterChildStateBase<? extends CharacterBase> parent;
    private final CharacterIconBox icon;
    private final CharacterIconBox parentIcon;
    public CharacterRightBar(CharacterChildStateBase<? extends CharacterBase> parentState)
    {
        parent = parentState;
        icon = new CharacterIconBox(parent);
        parentIcon = new CharacterIconBox(parent);
        ListenerInit();
        IconInit();
    }
    private void ListenerInit()
    {
        parent.GetCurrentProp().addListener(new ChangeListener<CharacterBase>()
        {
            @Override
            public void changed(ObservableValue<? extends CharacterBase> observableValue, CharacterBase characterBase, CharacterBase t1)
            {
                icon.SetImage(t1.GetIcon());
                if (t1.GetParent().IsTemplate())
                {
                    getChildren().remove(parentIcon);
                }
                else
                {
                    if (! getChildren().contains(parentIcon))
                    {
                        getChildren().add(parentIcon);
                    }
                    parentIcon.SetImage(t1.GetParent().GetIcon());
                }
            }
        });
    }
    private void IconInit()
    {
        icon.SetText("Icon");
        parentIcon.SetText("Parent Icon");
        parentIcon.SetLocked(true);
        getChildren().addAll(parentIcon, icon);
    }

    public final CharacterChildStateBase<? extends CharacterBase> GetParent(){return parent;}

}
