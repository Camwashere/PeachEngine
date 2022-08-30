package main.Module.Character.Layout.Box.TemplateBox;

import javafx.collections.MapChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import main.Module.Character.Game.CharacterTemplate.CharacterTemplate;
import main.Module.Character.Game.Variables.TraitValue;
import main.Module.Character.Game.Variables.Trait;
import main.Tools.InitHelp;

public class TraitTemplateBox extends VariableTemplateBox
{
    private final ListView<TraitValue> values;
    public TraitTemplateBox(final CharacterTemplate template, final Trait temp)
    {
        super(template, temp);
        values = new ListView<>();
        Button b = new Button("Add Value");
        InitHelp.ButtonInit(b);
        b.setOnMouseClicked(evt->
        {
            temp.AddValue();
        });
        temp.GetValues().addListener(new MapChangeListener<Integer, TraitValue>()
        {
            @Override
            public void onChanged(Change<? extends Integer, ? extends TraitValue> change)
            {
                if (change.wasAdded())
                {
                    values.getItems().add(change.getValueAdded());
                }
                else if (change.wasRemoved())
                {
                    values.getItems().remove(change.getValueRemoved());
                }
            }
        });
        values.setCellFactory(new Callback<ListView<TraitValue>, ListCell<TraitValue>>()
        {
            @Override
            public ListCell<TraitValue> call(ListView<TraitValue> TraitValueListView)
            {
                return new TextFieldListCell<>()
                {
                    {
                        ContextMenu menu = new ContextMenu();
                        MenuItem rename = new MenuItem("Rename");
                        rename.setOnAction(evt->
                        {
                            values.setEditable(true);
                            startEdit();
                        });
                        menu.getItems().add(rename);
                        MenuItem delete = new MenuItem("Delete");
                        delete.setOnAction(evt->
                        {
                            temp.GetValues().remove(getItem().GetID());
                        });
                        menu.getItems().add(delete);
                        setContextMenu(menu);

                        setConverter(new StringConverter<TraitValue>()
                        {
                            @Override
                            public String toString(TraitValue traitValue)
                            {
                                return traitValue.GetValue();
                            }

                            @Override
                            public TraitValue fromString(String s)
                            {
                                getItem().SetValue(s);
                                return getItem();
                            }
                        });

                    }

                    @Override
                    public void cancelEdit()
                    {
                        super.cancelEdit();
                        values.setEditable(false);
                    }

                    @Override
                    public void commitEdit(TraitValue traitValue)
                    {
                        super.commitEdit(traitValue);
                        values.setEditable(false);
                    }

                    @Override
                    public void updateItem(TraitValue item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (item == null || empty)
                        {
                            setText(null);
                        }
                        else
                        {
                            setText(item.GetValue());
                        }
                    }
                };
            }
        });
        values.setEditable(false);
        AddContent(b);
        AddContent(values);


    }
}
