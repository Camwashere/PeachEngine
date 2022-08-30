package main.Module.Story.Scenario.Frame.StoryFrame.TextEditor;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.IndexRange;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Debug.Debug;
import main.Maths.Vec.Vec2i;
import main.Module.Story.Scenario.Frame.Parameter.InputParameter.InputParameter;
import main.Module.Story.Scenario.Frame.Parameter.ParameterBase;
import main.Module.Story.Scenario.Frame.StoryFrame.StoryFrame;
import org.fxmisc.richtext.InlineCssTextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextEditor extends BorderPane
{
    private final TextEditorTopBar topBar;
    private final InlineCssTextArea editor;
    private final StoryFrame parent;
    private final SimpleBooleanProperty editMode;
    private final ArrayList<TextVariable> textVariables;
    private final FontStyle currentStyle = new FontStyle();



    public TextEditor(final StoryFrame parentFrame)
    {
        parent = parentFrame;
        topBar = new TextEditorTopBar(this);
        editor = new InlineCssTextArea();
        editMode = new SimpleBooleanProperty();
        textVariables = new ArrayList<TextVariable>();
        

        Init();
    }

    private void Init()
    {
        parent.GetExpandedProp().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                EditMode(t1);
            }
        });
        editMode.addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (t1) // Edit mode
                {
                    setTop(topBar);
                    setDisable(false);
                    editor.requestFocus();
                }
                else // View mode
                {
                    setTop(null);
                    setDisable(true);

                }
            }
        });
        editor.setWrapText(true);
        VBox.setVgrow(editor, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
        editor.insert(0, "", currentStyle.GetFontStyle());

        EventInit();
        setTop(topBar);
        setCenter(editor);
        EditMode(true);
    }
    private void EventInit()
    {
        editor.caretPositionProperty().addListener(new ChangeListener<Integer>()
        {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer prev, Integer point)
            {
                int dif = Math.abs(prev-point);
                if (prev < point) // Moving down and/or to the right
                {
                    if (!editor.getTextStyleForInsertionAt(point).equals(currentStyle.GetFontStyle()))
                    {
                        Vec2i vec = SelectVariable(point);
                        if (dif > 1) // Click or move down
                        {
                            // Find if the point is closer to the left or right side of the selected word
                            if (Math.abs(vec.GetStart() - point) < Math.abs(vec.GetEnd() - point)) // If the start is closer to the point
                            {
                                editor.selectRange(vec.GetEnd(), vec.GetStart());
                            }
                            else
                            {
                                editor.selectRange(vec.GetStart(), vec.GetEnd());
                            }
                        }
                        else // Arrow Key to the right
                        {
                            editor.selectRange(vec.GetStart(), vec.GetEnd());
                        }

                    }
                }
                else // Moving up and/or to the left
                {
                    if (!editor.getTextStyleForInsertionAt(point+1).equals(currentStyle.GetFontStyle()))
                    {
                        Vec2i vec = SelectVariable(point+1);
                        if (dif > 1) // Click or move up
                        {
                            if (Math.abs(vec.GetStart() - point) < Math.abs(vec.GetEnd() - point)) // If the start is closer to the point
                            {
                                editor.selectRange(vec.GetEnd(), vec.GetStart());
                            }
                            else
                            {
                                editor.selectRange(vec.GetStart(), vec.GetEnd());
                            }
                        }
                        else
                        {
                            editor.selectRange(vec.GetEnd(), vec.GetStart());
                        }
                    }

                }
            }
        });



    }
    private Vec2i SelectVariable(int start)
    {
        int begin = start;
        int end = start;
        String s = editor.getTextStyleForInsertionAt(start);
        while (!Objects.equals(s, currentStyle.GetFontStyle()))
        {
            begin--;
            if (begin <= 0)
            {
                break;
            }
            s = editor.getTextStyleForInsertionAt(begin);
        }
        s = editor.getTextStyleForInsertionAt(start);
        while (!Objects.equals(s, currentStyle.GetFontStyle()))
        {
            end++;
            if (end >= editor.getLength())
            {
                end = editor.getLength();
                break;
            }
            s = editor.getTextStyleForInsertionAt(end);
        }
        if (editor.getTextStyleForInsertionAt(end).equals(currentStyle.GetFontStyle()))
        {
            end--;
        }
        return new Vec2i(begin, end);

    }
    private List<Vec2i> FindVariablePositions()
    {
        boolean loop = false;
        List<Vec2i> list = new ArrayList<>();
        Vec2i vec = null;
        for (int i=0; i<editor.getLength(); i++)
        {
            if (!editor.getTextStyleForInsertionAt(i).equals(currentStyle.GetFontStyle()))
            {
                if (! loop)
                {
                    loop = true;
                    vec = new Vec2i();
                    vec.SetStart(i-1);
                    //list.add(new Vec2i(i));
                }
            }
            else
            {
                loop = false;
                if (vec != null)
                {
                    vec.SetEnd(i-1);
                    list.add(new Vec2i(vec));
                    vec = null;
                }
            }
        }
        return list;
    }
    private List<Vec2i> FindVariablePositions(final TextVariable t)
    {
        boolean loop = false;
        List<Vec2i> list = new ArrayList<>();
        Vec2i vec = null;
        for (int i=0; i<editor.getLength(); i++)
        {
            if (editor.getTextStyleForInsertionAt(i).equals(t.GetStyle()))
            {
                if (! loop)
                {
                    loop = true;
                    vec = new Vec2i();
                    vec.SetStart(i-1);
                    //list.add(new Vec2i(i));
                }
            }
            else
            {
                loop = false;
                if (vec != null)
                {
                    vec.SetEnd(i-1);
                    String var = editor.getText(vec.GetStart(), vec.GetEnd());
                    Debug.println(var);
                    if (t.GetName().equals(var))
                    {
                        list.add(new Vec2i(vec));
                    }
                    vec = null;
                }
            }
        }
        return list;
    }
    public final void UpdateVariables()
    {
        int offset = 0;
        for (final TextVariable v : textVariables)
        {
            for (final Vec2i vec : FindVariablePositions())
            {
                if (editor.getTextStyleForInsertionAt(vec.GetStart()+1+offset).equals(v.GetStyle()))
                {

                    int oldLen = editor.getLength();
                    editor.replace(vec.GetStart() + offset, vec.GetEnd()+offset, v.GetName(), v.GetStyle());
                    int newLen = editor.getLength();

                    offset += newLen - oldLen;

                }
            }
        }
    }
    public final void InsertVariable(TextVariable var)
    {
        if (editor.getCaretPosition() == 0)
        {
            editor.insert(editor.getCaretPosition(), var.GetName(), var.GetStyle());
            editor.insert(editor.getCaretPosition(), " ", currentStyle.GetFontStyle());
            return;
        }

        if (! editor.getText(editor.getCaretPosition()-1, editor.getCaretPosition()).equals(" "))
        {
            editor.insert(editor.getCaretPosition(), " ", currentStyle.GetFontStyle());
        }
        editor.insert(editor.getCaretPosition(), var.GetName(), var.GetStyle());
        editor.insert(editor.getCaretPosition(), " ", currentStyle.GetFontStyle());
    }
    public final void AddTextVariable(final InputParameter<?> param)
    {
        TextVariable t = new TextVariable(param);
        boolean add = true;
        for (final TextVariable v : textVariables)
        {
            if (v.equals(t))
            {
                add = false;
                break;
            }
        }
        if (add)
        {
            t.GetNameProp().addListener(new ChangeListener<String>()
            {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
                {
                    UpdateVariables();
                }
            });
            textVariables.add(t);
        }
        InsertVariable(t);

    }
    public final void RemoveTextVariable(final ParameterBase<?> param)
    {
        for (final TextVariable t : textVariables)
        {
            if (t.GetName().equals(param.GetName()))
            {
                DeleteTextVariable(t);
                break;
            }
        }
    }
    private void DeleteTextVariable(final TextVariable v)
    {
        int offset = 0;
        for (final Vec2i vec : FindVariablePositions(v))
        {
            if (editor.getTextStyleForInsertionAt(vec.GetStart()+1+offset).equals(v.GetStyle()))
            {

                int oldLen = editor.getLength();
                editor.replace(vec.GetStart() + offset, vec.GetEnd()+offset, "", v.GetStyle());
                int newLen = editor.getLength();

                offset += newLen - oldLen;

            }
        }
        textVariables.remove(v);
    }


    public final void SetText(String txt)
    {
        editor.insertText(0, txt);
    }

    public final String GetText(){return editor.getText();}
    public final StoryFrame GetParent(){return parent;}
    public final InlineCssTextArea GetEditor(){return editor;}
    public final FontStyle GetCurrentStyle(){return currentStyle;}
    public void EditMode(boolean bool){editMode.set(bool);}

}
