package main.Module.Character.Layout.Box.TemplateBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.util.Duration;
import main.Module.Character.Game.CharacterTemplate.CharacterTemplate;
import main.Module.Character.Game.Variables.CharacterVariableBase;
import main.Module.Character.Layout.Box.VariableBoxBase;
import main.Tools.Field.LabeledTextArea;

public class VariableTemplateBox extends VariableBoxBase
{

    private final LabeledTextArea desc;
    private final ContextMenu menu;
    public VariableTemplateBox(final CharacterTemplate template,final CharacterVariableBase temp)
    {
        super(temp.GetID(), temp.GetName());
        desc = new LabeledTextArea("Description: ");
        menu = new ContextMenu();
        ContextMenuInit(template, temp);
        AddContent(desc);
        NameInit(temp);
        DescriptionInit(temp);
        name.setContextMenu(menu);

    }
    private void ContextMenuInit(final CharacterTemplate template, final CharacterVariableBase temp)
    {
        MenuItem rename = new MenuItem("Rename");
        rename.setOnAction(evt->
        {
            name.StartEdit();
        });
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(evt->
        {
            template.RemoveVariable(temp);
            Delete();
        });
        menu.getItems().addAll(rename, delete);
    }
    private void NameInit(final CharacterVariableBase temp)
    {
        name.setTooltip(new Tooltip(temp.toString()));
        name.setUserData(temp);
        temp.GetNameProp().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                name.setText(t1);
                UpdateTooltip();
                //name.getTooltip().setText(temp.toString());
            }
        });
        name.GetTextProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                temp.SetName(t1);
            }
        });

        name.getTooltip().setShowDuration(Duration.INDEFINITE);
        name.getTooltip().setShowDelay(Duration.seconds(0.4));
    }
    private void DescriptionInit(final CharacterVariableBase temp)
    {
        desc.GetTextArea().textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                temp.SetDescription(t1);
            }
        });
        temp.GetDescriptionProp().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if (! desc.GetTextArea().getText().equals(t1))
                {
                    desc.GetTextArea().setText(t1);
                }
                UpdateTooltip();
                //name.getTooltip().setText(temp.toString());
            }
        });
    }
    protected void UpdateTooltip()
    {
        name.getTooltip().setText(name.getUserData().toString());

    }


}
