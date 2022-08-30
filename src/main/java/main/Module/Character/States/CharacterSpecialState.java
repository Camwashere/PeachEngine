package main.Module.Character.States;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Module.Character.Layout.LeftBar.CharacterTree;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Tools.Field.LabeledTextField;

public class CharacterSpecialState extends CharacterChildStateBase<StoryCharacter>
{
    //private final CharacterRightBar right;
    private final LabeledTextField first;
    private final LabeledTextField middle;
    private final LabeledTextField last;

    public CharacterSpecialState(final CharacterTree tree)
    {
        super(tree);
        first = new LabeledTextField("First Name");
        middle = new LabeledTextField("Middle Name");
        last = new LabeledTextField("Last Name");
        //right = new CharacterRightBar(this);
        Init();
    }

    @Override
    protected void OnLoad()
    {
        LoadNames();
    }

    @Override
    protected void OnUnload()
    {
        UnloadNames();

    }

    public CharacterSpecialState(final CharacterTree tree, final StoryCharacter storyCharacter)
    {
        super(tree);
        current.set(storyCharacter);
        first = new LabeledTextField("First Name");
        middle = new LabeledTextField("Middle Name");
        last = new LabeledTextField("Last Name");
        //right = new CharacterRightBar(this);
        Init();

    }

    private void Init()
    {
        InitTop();
        //InitCenter();
        ListenerInit();
        InitRight();
    }

    private void InitTop()
    {
        HBox top = new HBox();
        VBox names = new VBox();
        names.getChildren().addAll(first, middle, last);
        top.getChildren().add(names);
        setTop(top);
    }


    private void InitRight()
    {
        setRight(right);
    }


    private void ListenerInit()
    {
        first.GetFieldTextProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if (current.get() != null)
                {
                    current.get().SetFirstName(t1);
                    GetTree().refresh();
                }
            }
        });

        middle.GetFieldTextProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if (current.get() != null)
                {
                    current.get().SetMiddleName(t1);
                    GetTree().refresh();
                }
            }
        });

        last.GetFieldTextProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if (current.get() != null)
                {
                    current.get().SetLastName(t1);
                    GetTree().refresh();
                }
            }
        });


    }

    private void LoadNames()
    {
        first.SetText(current.get().GetFirstName());
        middle.SetText(current.get().GetMiddleName());
        last.SetText(current.get().GetLastName());
    }
    private void UnloadNames()
    {
        first.SetText("");
        middle.SetText("");
        last.SetText("");
    }

    //public final CharacterRightBar GetRight(){return right;}

}
