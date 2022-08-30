package main.Module.Character.Game.CharacterManager;

import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import main.Module.Character.Game.CharacterBase;
import main.Module.Character.Game.CharacterClass.CharacterClass;
import main.Module.Character.CharacterModule;
import main.Module.Character.Game.CharacterClass.CharacterClassBase;
import main.Module.Character.Game.CharacterClass.CharacterSpawner;
import main.Module.Character.Game.CharacterTemplate.CharacterTemplate;
import main.Module.Character.Game.StoryCharacter.StoryCharacter;
import main.Module.Character.Layout.LeftBar.CharacterTree;
import main.Tools.StringHelp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

public class CharacterManager
{
    private final CharacterModule parent;
    private final CharacterTemplate template;
    private final SimpleMapProperty<UUID, StoryCharacter> characters;
    private final SimpleMapProperty<UUID, CharacterClass> classes;
    private final ArrayList<String> boyNames;
    private final ArrayList<String> girlNames;
    private final ArrayList<String> lastNames;
    private final CharacterImageManager imageManager;

    public CharacterManager(final CharacterModule parentModule)
    {
        parent = parentModule;
        imageManager = new CharacterImageManager();
        template = new CharacterTemplate(this);
        characters = new SimpleMapProperty<UUID, StoryCharacter>(FXCollections.observableMap(new LinkedHashMap<UUID, StoryCharacter>()));
        classes = new SimpleMapProperty<UUID, CharacterClass>(FXCollections.observableHashMap());
        boyNames = new ArrayList<String>();
        girlNames = new ArrayList<String>();
        lastNames = new ArrayList<String>();
        Init();
        //AddClass(template);

    }
    private void Init()
    {
        NameInit();
    }
    private void NameInit()
    {
        FillBoyNames();
        FillGirlNames();
        FillLastNames();
    }

    private void FillBoyNames()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(StringHelp.AbsoluteFile("C:\\Users\\perso\\IdeaProjects\\PeachEngine\\src\\main\\resources\\Character\\boyNames.txt"))));
            String line = reader.readLine();
            while (line != null)
            {
                boyNames.add(line);
                line = reader.readLine();
            }
            reader.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void FillGirlNames()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(StringHelp.AbsoluteFile("C:\\Users\\perso\\IdeaProjects\\PeachEngine\\src\\main\\resources\\Character\\girlNames.txt"))));
            String line = reader.readLine();
            while (line != null)
            {
                girlNames.add(line);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void FillLastNames()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(StringHelp.AbsoluteFile("C:\\Users\\perso\\IdeaProjects\\PeachEngine\\src\\main\\resources\\Character\\lastNames.txt"))));
            String line = reader.readLine();
            while (line != null)
            {
                lastNames.add(line);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public final void TestFill(final CharacterTree tree, boolean full)
    {
        template.TestFill();
        if (full)
        {
            CharacterSpawner spawner = new CharacterSpawner(template);
            for (int i=0; i<3000; i++)
            {
                StoryCharacter temp = spawner.Spawn();
                tree.AddCharacter(temp);
                AddCharacter(temp);
            }
        }

    }

    public final boolean IsClass(final UUID l) {return classes.containsKey(l);}
    public final boolean IsCharacter(final UUID l){return characters.containsKey(l);}
    public final boolean IsTemplate(final UUID l)
    {
        if (l == null)
        {
            return false;
        }
        return template.GetID()==l;
    }

    public final void AddCharacter(final StoryCharacter c)
    {
        int counter = 0;
        String last = "";
        AddCharacterHelp(counter, last, c);
        characters.put(c.GetID(), c);
    }

    private void AddCharacterHelp(int counter, String last, final StoryCharacter c)
    {
        boolean bet = true;
        for (final StoryCharacter i : characters.values())
        {
            if ((c.GetDisplayName() + last).equals(i.GetDisplayName()))
            {
                counter++;
                last = String.valueOf(counter);
                bet = false;
            }
        }
        if (bet)
        {
            c.SetLastName(c.GetLastName() + last);
        }
        else
        {
            AddCharacterHelp(counter, last, c);
        }

    }
    public final void AddClass(final CharacterClass c)
    {
        int counter = 0;
        String last = "";
        AddClassHelp(counter, last, c);
        classes.put(c.GetID(), c);
    }

    private void AddClassHelp(int counter, String last, final CharacterClass c)
    {
        boolean bet = true;
        for (final CharacterClass i : classes.values())
        {
            if ((c.GetDisplayName() + last).equals(i.GetDisplayName()))
            {
                counter++;
                last = String.valueOf(counter);
                bet = false;
            }
        }
        if (bet)
        {
            c.SetName(c.GetName() + last);
        }
        else
        {
            AddClassHelp(counter, last, c);
        }

    }

    public final ArrayList<String> GetBoyNames(){return boyNames;}
    public final ArrayList<String> GetGirlNames(){return girlNames;}
    public final ArrayList<String> GetLastNames(){return lastNames;}
    public final StoryCharacter GetCharacter(final UUID val){return characters.get(val);}
    public final CharacterClass GetClass(final UUID val){return classes.get(val);}
    public final SimpleMapProperty<UUID, StoryCharacter> GetCharacters(){return characters;}
    public final SimpleMapProperty<UUID, CharacterClass> GetClasses(){return classes;}
    public final CharacterBase GetCharacterBase(final UUID val)
    {
        if (characters.containsKey(val))
        {
            return characters.get(val);
        }
        else
        {
            return classes.get(val);
        }

    }
    public final CharacterTemplate GetTemplate(){return template;}
    public final Image GetBlankIcon(){return imageManager.GetBlankIcon();}
    public final CharacterModule GetParentModule(){return parent;}
}

