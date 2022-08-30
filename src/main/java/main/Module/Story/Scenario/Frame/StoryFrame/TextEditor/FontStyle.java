package main.Module.Story.Scenario.Frame.StoryFrame.TextEditor;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.converter.FontConverter;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import main.Tools.StringHelp;

public class FontStyle
{
    public final static String BaseColor = StringHelp.ColorToStyle(Color.BLACK);
    private final SimpleDoubleProperty size=new SimpleDoubleProperty(Font.getDefault().getSize());
    private final SimpleStringProperty family=new SimpleStringProperty(Font.getDefault().getFamily());
    private final SimpleStringProperty weight=new SimpleStringProperty("normal");
    private final SimpleStringProperty style = new SimpleStringProperty("normal");
    private final SimpleObjectProperty<Color> color = new SimpleObjectProperty<Color>(Color.BLACK);
    private final SimpleStringProperty fontString = new SimpleStringProperty();
    public FontStyle()
    {

        Init();
        UpdateString();

    }
    private void Init()
    {
        size.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                UpdateString();
            }
        });
        family.addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                UpdateString();
            }
        });
        weight.addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                UpdateString();
            }
        });
        color.addListener(new ChangeListener<Color>()
        {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1)
            {
                UpdateString();
            }
        });
    }

    private void UpdateString()
    {

        fontString.set("-fx-font-family: " + family.get() + " ;-fx-font-size: " + size.get() + "px; " + StringHelp.ColorToStyle(color.get()) + " -fx-font-style: " + style.get() + "; -fx-font-weight: " + weight.get());
    }

    public final void SetAll(String name)
    {
        Font font = Font.font(name);
        SetFamily(font.getFamily());
        SetSize(font.getSize());
    }

    public final void SetSize(double s){size.set(s);}
    public final void SetFamily(final String s){family.set(s);}
    public final void SetWeight(final String s){weight.set(s);}
    public final void SetStyle(final String s){style.set(s);}
    public final void SetColor(final Color c){color.set(c);}

    public final String GetFontStyle(){return fontString.get();}
    public final SimpleStringProperty GetFontStyleProp(){return fontString;}


}
