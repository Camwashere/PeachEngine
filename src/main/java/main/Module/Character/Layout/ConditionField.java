package main.Module.Character.Layout;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import main.Module.ModuleBase.ModuleID;
import main.Module.Story.Layout.StoryLeftBar.ScenarioItem;
import main.Module.Story.Layout.StoryLeftBar.ScenarioTree;
import main.Module.Story.Scenario.Scenario;
import main.Module.Story.Scenario.ScenarioType;
import main.Tools.InitHelp;
import main.Tools.ValueButton;

import java.util.Optional;

public class ConditionField extends HBox
{
    private static final Image FOLDER_IMAGE = new Image("C://Users//perso//IdeaProjects//PeachEngine//src//main//resources//Story//folderIcon.png");
    private final Label name;
    private final ValueButton<ScenarioItem> choose;

    public ConditionField(final ScenarioTree leftBar, final String nameStr)
    {
        name = new Label(nameStr);
        choose = new ValueButton<ScenarioItem>("Choose +");
        Init(leftBar);
    }
    private void Init(final ScenarioTree left)
    {
        InitHelp.ButtonInit(choose);
        choose.GetValueProp().addListener(new ChangeListener<ScenarioItem>()
        {
            @Override
            public void changed(ObservableValue<? extends ScenarioItem> observableValue, ScenarioItem scenarioItem, ScenarioItem t1)
            {
                if (t1 == null)
                {
                    choose.setText("Choose +");
                }
                else
                {
                    choose.setText(t1.GetName());
                }
            }
        });
        choose.setOnMouseClicked(evt->
        {
            VBox box = new VBox();
            TreeView<ScenarioItem> tree = new TreeView<>();
            tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            tree.setShowRoot(false);
            tree.setCellFactory(new Callback<TreeView<ScenarioItem>, TreeCell<ScenarioItem>>()
            {
                @Override
                public TreeCell<ScenarioItem> call(TreeView<ScenarioItem> scenarioItemTreeView)
                {
                    return new TreeCell<ScenarioItem>()
                    {
                        @Override
                        protected void updateItem(ScenarioItem item, boolean empty)
                        {
                            super.updateItem(item, empty);
                            if (item == null || empty)
                            {
                                setText(null);
                                setGraphic(null);
                            }
                            else
                            {
                                if (item.IsDirectory())
                                {
                                    ImageView view = new ImageView(FOLDER_IMAGE);
                                    view.setFitHeight(20);
                                    view.setFitWidth(20);
                                    setGraphic(view);
                                }
                                else
                                {
                                    if (left.GetScenario(item.GetID()).GetType() == ScenarioType.CHARACTER_EFFECT)
                                    {
                                        setText(item.GetName());
                                    }
                                    else
                                    {
                                        setText(null);
                                    }
                                }
                            }
                        }
                    };
                }
            });
            tree.setRoot(left.getRoot());
            box.getChildren().add(tree);
            Button newScenario = new Button("New +");
            InitHelp.ButtonInit(newScenario);

            Dialog<ScenarioItem> dialog = new Dialog<ScenarioItem>();
            DialogPane pane = new DialogPane();
            pane.setContent(box);
            pane.getButtonTypes().add(ButtonType.APPLY);
            pane.getButtonTypes().add(ButtonType.CLOSE);
            newScenario.setOnMouseClicked(event->
            {
                left.GetParent().GetParent().GetStoryModule().GetScenarioTree().AddScenario(new Scenario(left.GetParent().GetParent().GetStoryModule(), ScenarioType.CHARACTER_EFFECT));
                left.GetParent().GetParent().SetModule(ModuleID.STORY);
                dialog.close();

            });
            box.getChildren().add(newScenario);
            dialog.setResultConverter(new Callback<ButtonType, ScenarioItem>()
            {
                @Override
                public ScenarioItem call(ButtonType buttonType)
                {
                    if (buttonType == ButtonType.APPLY)
                    {
                        if (tree.getSelectionModel().getSelectedItem() != null)
                        {
                            return tree.getSelectionModel().getSelectedItem().getValue();
                        }
                        return null;
                    }
                    return null;
                }
            });
            dialog.setDialogPane(pane);
            Optional<ScenarioItem> item = dialog.showAndWait();
            if (item.isPresent())
            {
                choose.SetValue(item.get());
            }
        });
        getChildren().addAll(name, choose);
    }


    public final void SetCurrent(final ScenarioItem l){choose.SetValue(l);}
    public final ScenarioItem GetCurrent(){return choose.GetValue();}
    public final SimpleObjectProperty<ScenarioItem> GetCurrentProp(){return choose.GetValueProp();}
}
