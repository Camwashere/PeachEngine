package main.Module.Character.Game.CharacterManager;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CharacterImageManager
{
    private final Image blankIcon;
    public CharacterImageManager()
    {
        Image blankIcon1;
        File file = new File("src/main/resources/Character/Icons/emptyProfile.jpg");
        try
        {
            FileInputStream input = new FileInputStream(file);
            blankIcon1 = new Image(input);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            blankIcon1 = null;
        }
        blankIcon = blankIcon1;
    }

    public final Image GetBlankIcon(){return blankIcon;}
}
