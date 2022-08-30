package main.Module.Character.Game.StoryCharacter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import main.Debug.Debug;
import main.Maths.Vec.Vec2;
import main.Module.Character.Game.CharacterBase;
import main.Module.Character.Game.CharacterClass.CharacterClassBase;
import main.Module.Character.Game.Variables.*;
import main.Module.Item.Game.Inventory;
import main.Module.Map.Game.Time.GameTime;
import main.Module.Relationship.Game.Disposition;
import main.Module.Relationship.Game.RelationshipManager;
import main.Tools.LinkedMapProperty;

import java.util.UUID;

public class StoryCharacter extends CharacterBase
{
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty middleName;
    private final SimpleStringProperty lastName;
    private final SimpleBooleanProperty nameProp = new SimpleBooleanProperty(false);
    private final GameTime birthday = new GameTime();
    private final LinkedMapProperty<UUID, Disposition> dispositions = new LinkedMapProperty<>();
    private final Inventory inventory;
    private final RelationshipManager relationshipManager;
    private final SimpleBooleanProperty destroyed = new SimpleBooleanProperty(false);


    public StoryCharacter(final CharacterClassBase templateParent)
    {
        super(templateParent);
        firstName = new SimpleStringProperty();
        middleName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        inventory = new Inventory(this);
        relationshipManager = new RelationshipManager(this);
        Init();

    }
    public StoryCharacter(final CharacterClassBase templateParent, final String first, final String middle, final String last)
    {
        super(templateParent);
        firstName = new SimpleStringProperty(first);
        middleName = new SimpleStringProperty(middle);
        lastName = new SimpleStringProperty(last);
        inventory = new Inventory(this);
        relationshipManager = new RelationshipManager(this);
        Init();
    }

    private void Init()
    {
        firstName.addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                nameProp.set(!nameProp.get());
            }
        });
        middleName.addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                nameProp.set(!nameProp.get());
            }
        });
        lastName.addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                nameProp.set(!nameProp.get());
            }
        });
        perks.addListener(new MapChangeListener<UUID, Perk>()
        {
            @Override
            public void onChanged(Change<? extends UUID, ? extends Perk> change)
            {
                if (change.wasAdded())
                {
                    Debug.println("Was Added to: " + GetFullName(), 1);
                }
            }
        });
        destroyed.addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1)
                {
                    inventory.Clear();
                }
            }
        });
    }

    public final void Destroy() {destroyed.set(true);}
    @Override
    public boolean IsClass() {return false;}

    public final void AddDisposition(final CharacterVariableBase var, final Vec2 val){AddDisposition(new Disposition(var, val));}
    public final void AddDisposition(final Disposition disposition) {dispositions.put(disposition.GetID(), disposition);}

    // SET
    public final void SetFirstName(final String name){firstName.set(name);}
    public final void SetMiddleName(final String name){middleName.set(name);}
    public final void SetLastName(final String name){lastName.set(name);}
    public final void SetBirthday(int month, int day, int year){birthday.SetDate(month, day, year);}
    public final void SetBirthday(int sec, int min, int hour, int month, int day, int year){birthday.Set(sec, min, hour, month, day, year);}



    // GET
    public final String GetFirstName(){return firstName.get();}
    public final String GetMiddleName(){return middleName.get();}
    public final String GetLastName(){return lastName.get();}
    /**
     * Get the first name and last name, seperated by a space.
     * IE: Joe Smith
     * @return Character's full name.
     */
    public final String GetFullName(){return firstName.get() + " " + lastName.get();}
    /**
     * Get the first, middle and last name all spelled out and seperated by spaces.
     * IE: Joe Thomas Smith
     * @return Character's legal name.
     */
    public final String GetLegalName(){return firstName.get() + " " + middleName.get() + " " + lastName.get();}
    /**
     * Get the first name, middle initial, and last name seperated by spaces.
     * IE: Joe T Smith
     * @return The character's abbreviated legal name.
     */
    public final String GetLegalNameAbr(){return firstName.get() + " " + middleName.get().charAt(0) + " " + lastName.get();}
    public final SimpleStringProperty GetFirstNameProp(){return firstName;}
    public final SimpleStringProperty GetMiddleNameProp(){return middleName;}
    public final SimpleStringProperty GetLastNameProp(){return lastName;}
    public final Inventory GetInventory(){return inventory;}
    public final GameTime GetBirthday(){return birthday;}
    public final int GetAge(final GameTime currentTime){return birthday.YearsBetween(currentTime);}
    public final boolean IsDestroyed(){return destroyed.get();}
    public final SimpleBooleanProperty GetNameChangedProp(){return nameProp;}
    public final Disposition GetDisposition(final UUID varID) {return dispositions.get(varID);}
    public final LinkedMapProperty<UUID, Disposition> GetDispositions(){return dispositions;}
    public final RelationshipManager GetRelationshipManager(){return relationshipManager;}





    @Override
    public String toString()
    {
        String selfString = String.format("Name: %s\nID: %s\n", GetLegalName(), GetID());
        String attributeString = "Attributes: \n";
        for (Attribute a : attributes.values())
        {
            attributeString += a.toString() + "\n";
        }
        String perkString = "Perks: \n";
        for (Perk p : perks.values())
        {
            perkString += p.toString() + "\n";
        }
        String traitString = "Traits: \n";
        for (Trait t : traits.values())
        {
            traitString += t.toString() + "\n";
        }
        String descriptorString = "Descriptors: \n";
        for (Descriptor d : descriptors.values())
        {
            descriptorString += d.toString() + "\n";
        }
        String mentalString = "Mental States: \n";
        for (MentalState m : mentalStates.values())
        {
            mentalString += m.toString() + "\n";
        }

        return String.format("Story Character: \n%s\n%s\n%s\n%s\n%s\n%s\n", selfString, attributeString, perkString, traitString, descriptorString, mentalString);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof StoryCharacter other)
        {
            return other.GetID() == GetID();
        }
        return false;
    }

    @Override
    public String GetDisplayName()
    {
        if (middleName.get() != null)
        {
            if (! middleName.get().isEmpty())
            {
                return GetLegalName();
            }
        }

        return GetFullName();
    }
}
