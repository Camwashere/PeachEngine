package main.Tools;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class DynamicArrayList<T> extends ArrayList<T>
{
    private boolean single;
    private boolean replace;

    public DynamicArrayList()
    {
        super(FXCollections.observableArrayList());
        single = false;
        replace = false;
    }

    public DynamicArrayList(boolean isSingle)
    {
        super(FXCollections.observableArrayList());
        single = isSingle;
        replace = false;
    }
    public DynamicArrayList(boolean isSingle, boolean isReplace)
    {
        super(FXCollections.observableArrayList());
        single = isSingle;
        replace = isReplace;
    }


    @Override
    public boolean add(T t)
    {
        if (IsMulti())
        {
            return super.add(t);
        }
        else
        {
            if (IsReplace())
            {
                clear();
                return super.add(t);
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection)
    {
        if (IsMulti())
        {
            return super.addAll(collection);
        }
        return false;
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> collection)
    {
        if (IsMulti())
        {
            return super.addAll(i, collection);
        }
        return false;
    }

    @Override
    public T get(int i)
    {
        if (IsSingle())
        {
            return super.get(0);
        }
        return super.get(i);
    }

    @Override
    public void add(int i, T t)
    {
        if (IsMulti())
        {
            super.add(i, t);
        }
        else
        {
            if (IsReplace())
            {
                clear();
                super.add(t);
            }
        }
    }

    public final void Single(boolean bool){single = bool;}
    public final void Replace(boolean bool){replace = bool;}


    public final boolean IsSingle(){return single;}
    public final boolean IsMulti(){return !single;}
    public final boolean IsReplace(){return replace;}

    public final T GetFirst(){return get(0);}
    public final T GetLast(){return get(size()-1);}
}
