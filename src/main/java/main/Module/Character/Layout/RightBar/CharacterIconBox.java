package main.Module.Character.Layout.RightBar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import main.Module.Character.Game.CharacterBase;
import main.Module.Character.States.CharacterChildStateBase;
import main.Tools.InitHelp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

public class CharacterIconBox extends VBox
{
    private final ImageView icon;
    private final Label label;
    private final Button button;
    private final HBox hbox;
    private final CharacterChildStateBase<? extends CharacterBase> parent;
    private boolean locked=false;
    public CharacterIconBox(final CharacterChildStateBase<? extends CharacterBase> parentState)
    {
        parent = parentState;
        icon = new ImageView();
        label = new Label();
        button = new Button("Change");
        hbox = new HBox();
        icon.imageProperty().addListener(new ChangeListener<Image>()
        {
            @Override
            public void changed(ObservableValue<? extends Image> observableValue, Image image, Image t1)
            {
                if (t1 == null)
                {
                    icon.setImage(parent.GetTree().GetManager().GetBlankIcon());
                }
                if (parent.GetCurrent() != null)
                {
                    parent.GetCurrent().SetIcon(t1);
                }
            }
        });
        icon.setImage(parent.GetTree().GetManager().GetBlankIcon());
        Init();
    }
    private void Init()
    {
        InitHelp.ButtonInit(button);
        ContextMenu context = new ContextMenu();
        MenuItem color = new MenuItem("Select Color");
        color.setOnAction(evt->
        {
            Dialog<Color> dialog = new Dialog<>();
            ColorPicker picker = new ColorPicker();
            dialog.getDialogPane().setContent(picker);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CLOSE);
            dialog.setResultConverter(new Callback<ButtonType, Color>()
            {
                @Override
                public Color call(ButtonType type)
                {
                    if (type == ButtonType.APPLY)
                    {
                        return picker.getValue();
                    }
                    else
                    {
                        return null;
                    }
                }
            });
            Optional<Color> option = dialog.showAndWait();
            if (option.isPresent())
            {
                WritableImage image = new WritableImage((int) icon.getFitWidth(), (int) icon.getFitHeight());
                for (int w=0; w<image.getWidth(); w++)
                {
                    for (int h=0; h<image.getHeight(); h++)
                    {
                        image.getPixelWriter().setColor(w, h, option.get());
                    }
                }
                icon.setImage(image);
            }
        });
        MenuItem clear = new MenuItem("Clear");
        clear.setOnAction(evt->
        {
            if (parent.GetCurrent() != null)
            {
                icon.setImage(null);
            }
        });
        MenuItem fromFile = new MenuItem("Select From File");
        fromFile.setOnAction(evt->
        {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(getScene().getWindow());
            if (file != null)
            {
                try
                {
                    FileInputStream input = new FileInputStream(file);
                    Image image = new Image(input);
                    if (! image.isError())
                    {
                        icon.setImage(image);

                    }
                    else
                    {
                        icon.setImage(null);
                    }
                    input.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }


        });
        context.getItems().addAll(clear, color, fromFile);
        button.setContextMenu(context);
        button.setOnMouseClicked(evt->
        {
            if (evt.getButton() == MouseButton.PRIMARY)
            {
                button.getContextMenu().show(button, evt.getScreenX(), evt.getScreenY());
            }
        });
        InitHelp.LabelInit(label);
        hbox.getChildren().add(label);
        hbox.getChildren().add(button);
        getChildren().add(hbox);
        getChildren().add(icon);
        icon.setFitWidth(100);
        icon.setFitHeight(100);
    }

    public final boolean IsLocked(){return locked;}
    public final void SetLocked(final boolean bool)
    {
        locked = bool;
        if (locked)
        {
            hbox.getChildren().remove(button);
        }
        else
        {
            hbox.getChildren().add(button);
        }
    }
    public final void SetText(final String str){label.setText(str);}
    public final void SetImage(final Image i){icon.setImage(i);}
}
