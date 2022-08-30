package main.Module.Character.States;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.Module.Character.Game.CharacterClass.CharacterClass;
import main.Module.Character.Layout.LeftBar.CharacterTree;
import main.Tools.Field.LabeledTextField;


public class CharacterClassState extends CharacterChildStateBase<CharacterClass>
{
    private final LabeledTextField name;
    public CharacterClassState(final CharacterTree tree)
    {
        super(tree);
        name = new LabeledTextField("Class Name");
        NameInit();
    }

    @Override
    protected void OnLoad()
    {
        name.SetText(current.get().GetName());
    }

    @Override
    protected void OnUnload()
    {
        name.SetText("");

    }

    private void NameInit()
    {
        HBox.setHgrow(name.GetLabel(), Priority.SOMETIMES);
        name.GetFieldTextProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if (t1 != null)
                {
                    if (! t1.isEmpty())
                    {
                        if (current.get() != null)
                        {
                            current.get().SetName(t1);
                            GetTree().refresh();
                        }
                    }
                }
            }
        });
        setTop(name);
    }
}
