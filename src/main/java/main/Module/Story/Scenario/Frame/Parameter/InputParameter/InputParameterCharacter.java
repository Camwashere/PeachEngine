package main.Module.Story.Scenario.Frame.Parameter.InputParameter;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.WeakMapChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import main.Debug.Debug;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Module.Character.Layout.DynamicCharacterMenu;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Tools.ValueButton;

public class InputParameterCharacter extends InputParameter<StoryCharacter>
{

    private final ValueButton<StoryCharacter> box;
    SimpleBooleanProperty changeProp = new SimpleBooleanProperty(false);

    public InputParameterCharacter(final BaseFrame parentFrame)
    {
        super(parentFrame, ParamType.CHARACTER, false);

        box = new ValueButton<StoryCharacter>();
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                box.setOnMouseReleased(evt->
                {
                    DynamicCharacterMenu menu = new DynamicCharacterMenu(parent.GetParent().GetParent().GetParent().GetCharacterModule().GetLeft().GetTree(), box);
                    menu.show(parent, evt.getScreenX(), evt.getScreenY());
                });
            }
        });

        changeProp.addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                box.setText(box.GetValue().GetDisplayName());
            }
        });

        box.GetValueProp().addListener(new ChangeListener<StoryCharacter>()
        {
            @Override
            public void changed(ObservableValue<? extends StoryCharacter> observableValue, StoryCharacter storyCharacter, StoryCharacter t1)
            {
                if (t1 != null)
                {
                    changeProp.bind(t1.GetNameChangedProp());
                    box.setText(t1.GetDisplayName());
                }
                else
                {
                    changeProp.unbind();
                    box.setText(null);
                }
                SetValue(t1);
            }
        });


        getChildren().add(box);

    }

    @Override
    protected Node GetNode()
    {
        return box;
    }

    @Override
    protected StoryCharacter GetNodeValue()
    {
        return box.GetValue();
    }
}
