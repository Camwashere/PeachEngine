package main.Module.Character.Layout;

import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Duration;
import main.Module.Character.Game.CharacterBase;
import main.Module.Character.Game.CharacterTemplate.CharacterTemplate;
import main.Module.Character.Game.Variables.Perk;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;

import java.util.ArrayList;

public class PerkSelectionDialog extends Dialog<ArrayList<Perk>>
{

    private final ListView<Perk> list;
    public PerkSelectionDialog(final CharacterBase current, final CharacterTemplate template)
    {
        list = new ListView<Perk>();
        for (final Perk p : template.GetPerks().values())
        {
            if (! current.GetPerks().containsKey(p.GetID()))
            {
                list.getItems().add(new Perk(p));
            }
        }
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        list.setCellFactory(new Callback<ListView<Perk>, ListCell<Perk>>()
        {
            @Override
            public ListCell<Perk> call(ListView<Perk> perkListView)
            {
                return new ListCell<Perk>()
                {
                    {
                        setTooltip(new Tooltip());
                        getTooltip().setShowDuration(Duration.INDEFINITE);
                        getTooltip().setShowDelay(Duration.seconds(0.5));
                    }

                    @Override
                    protected void updateItem(Perk item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (item == null || empty)
                        {
                            setText(null);
                        }
                        else
                        {
                            setText(item.GetName());
                            getTooltip().setText(item.GetDescription());
                        }
                    }
                };
            }
        });
        getDialogPane().setContent(list);
        getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE, ButtonType.CANCEL, ButtonType.APPLY);
        setResultConverter(new Callback<ButtonType, ArrayList<Perk>>()
        {
            @Override
            public ArrayList<Perk> call(ButtonType type)
            {
                if (type == ButtonType.APPLY)
                {
                    ArrayList<Perk> perks = new ArrayList<Perk>(list.getSelectionModel().getSelectedItems());
                    if (perks.isEmpty())
                    {
                        return null;
                    }
                    return perks;

                }
                else
                {
                    return null;
                }
            }
        });

    }
}
