package main.Module.Character.Layout.Box.TemplateBox;

import javafx.collections.MapChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import main.Module.Character.Game.CharacterTemplate.CharacterTemplate;
import main.Module.Character.Game.Variables.MentalStateValue;
import main.Module.Character.Game.Variables.MentalState;
import main.Tools.InitHelp;

public class MentalStateTemplateBox extends VariableTemplateBox
{
    private final ListView<MentalStateValue> values;
    public MentalStateTemplateBox(final CharacterTemplate template, final MentalState temp)
    {
        super(template,temp);
        values = new ListView<>();
        Button b = new Button("Add Value");
        InitHelp.ButtonInit(b);
        b.setOnMouseClicked(evt->
        {
            temp.AddValue();
        });
        temp.GetValues().addListener(new MapChangeListener<Integer, MentalStateValue>()
        {
            @Override
            public void onChanged(Change<? extends Integer, ? extends MentalStateValue> change)
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
        values.setCellFactory(new Callback<ListView<MentalStateValue>, ListCell<MentalStateValue>>()
        {
            @Override
            public ListCell<MentalStateValue> call(ListView<MentalStateValue> MentalStateValueListView)
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

                        setConverter(new StringConverter<MentalStateValue>()
                        {
                            @Override
                            public String toString(MentalStateValue MentalStateValue)
                            {
                                return MentalStateValue.GetValue();
                            }

                            @Override
                            public MentalStateValue fromString(String s)
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
                    public void commitEdit(MentalStateValue MentalStateValue)
                    {
                        super.commitEdit(MentalStateValue);
                        values.setEditable(false);
                    }

                    @Override
                    public void updateItem(MentalStateValue item, boolean empty)
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
