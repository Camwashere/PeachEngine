package main.Module.Story.Scenario.ScenarioMenu;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import main.Debug.Debug;
import main.Module.Story.Layout.StoryLeftBar.ScenarioItem;
import main.Module.Story.Scenario.Frame.FunctionFrame.FunctionFrame;
import main.Module.Story.Scenario.Frame.FunctionFrame.FunctionType;
import main.Module.Story.Scenario.Frame.FunctionFrame.Functions;
import main.Module.Story.Scenario.Frame.InputFrame.InputFrameBool;
import main.Module.Story.Scenario.Frame.InputFrame.InputFrameNumber;
import main.Module.Story.Scenario.Frame.InputFrame.InputFrameText;
import main.Module.Story.Scenario.Frame.Parameter.ParamType;
import main.Module.Story.Scenario.Frame.Parameter.ParameterBase;
import main.Module.Story.Scenario.Frame.ScenarioFrame.ScenarioFrame;
import main.Module.Story.Scenario.Frame.StoryFrame.StoryFrame;
import main.Module.Story.Scenario.Frame.VariableFrame.VariableFrame;
import main.Module.Story.Scenario.Scenario;
import main.Module.Story.Scenario.ScenarioState;
import main.Tools.StringHelp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScenarioMenu extends PopupControl
{
    private final Scenario parent;
    private final BorderPane pane;
    private final TextField search;
    private final TreeView<FrameItem> tree;
    private final ArrayList<TreeItem<FrameItem>> searchItems;
    private final ArrayList<TreeItem<FrameItem>> directoryItems;
    private TreeItem<FrameItem> root;

    public ScenarioMenu(final Scenario s)
    {
        parent = s;
        pane = new BorderPane();
        search = new TextField();
        tree = new TreeView<FrameItem>();
        searchItems = new ArrayList<TreeItem<FrameItem>>();
        directoryItems = new ArrayList<TreeItem<FrameItem>>();
        TreeInit(tree);
        SearchInit();
        SelfInit();
        BorderPaneInit();
        TreeItemInit();
    }
    private void TreeInit(final TreeView<FrameItem> aTree)
    {
        aTree.setEditable(false);
        aTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        aTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<FrameItem>>()
        {
            @Override
            public void changed(ObservableValue<? extends TreeItem<FrameItem>> observableValue, TreeItem<FrameItem> FrameItemTreeItem, TreeItem<FrameItem> t1)
            {
                if (t1 != null)
                {
                    if (t1.getValue().HasFrame())
                    {
                        t1.getValue().handle(new ActionEvent());
                        ScenarioMenu.this.hide();
                    }
                }
            }
        });
        aTree.setCellFactory(new Callback<TreeView<FrameItem>, TreeCell<FrameItem>>() {
            @Override
            public TreeCell<FrameItem> call(TreeView<FrameItem> treeItem)
            {
                return new TreeCell<FrameItem>()
                {
                    @Override
                    protected void updateItem(FrameItem item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        super.setOpacity(0.75);
                        if (item == null || empty)
                        {
                            setText(null);
                        }
                        else
                        {
                            setText(item.GetName());
                        }
                    }
                };
            }
        });
        double borderWidth = 2;
        aTree.setBackground(new Background(new BackgroundFill(Color.LIGHTSLATEGRAY, CornerRadii.EMPTY, new Insets(borderWidth, borderWidth, borderWidth, borderWidth))));
        root = new TreeItem<FrameItem>(new FrameItem(null, "Root"));
        tree.setRoot(root);
        tree.setShowRoot(false);
    }
    private void BorderPaneInit()
    {
        double borderWidth = 2;
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(borderWidth, borderWidth, borderWidth, borderWidth))));

        pane.setMaxHeight(300);
    }
    private void SearchInit()
    {
        search.setPromptText("Search...");
        search.setOpacity(0.75);
        search.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if (! t1.isEmpty())
                {
                    //Search();
                }
                else
                {
                    searchItems.clear();
                    pane.setCenter(tree);
                }
            }
        });
    }
    private void SelfInit()
    {
        pane.setTop(search);
        pane.setCenter(tree);
        getScene().setRoot(pane);
        setAutoFix(true);
        setAutoHide(true);
        setOnShowing(evt->
        {
            tree.getSelectionModel().clearSelection();
            UpdateTree();
            Collapse();
            tree.setRoot(root);
        });
        setOnHidden(evt->
        {
            if (parent.GetCurrentState() == ScenarioState.CONNECT)
            {
                parent.AbortConnection();
            }
            ResetTree();

        });
    }

    public void ResetTree()
    {
        TreeItemInit();
        pane.setCenter(tree);
    }
    public void UpdateTree()
    {
        root.getChildren().clear();
        TreeItemInit();
    }

    private void TreeItemInit()
    {
        directoryItems.clear();
        searchItems.clear();
        root.getChildren().add(StoryInit());
        root.getChildren().add(VariableInit());
        root.getChildren().add(ArrayVariableInit());
        root.getChildren().add(FunctionInit());
        root.getChildren().add(InputInit());
        root.getChildren().add(ScenarioFunctionInit());
    }
    private TreeItem<FrameItem> StoryInit()
    {
        TreeItem<FrameItem> storyFuncs = FrameItem.CREATE(null, "Story");
        directoryItems.add(storyFuncs);
        TreeItem<FrameItem> story = FrameItem.CREATE("Story", "Story Frame", new StoryFrame(parent));
        storyFuncs.getChildren().add(story);
        return storyFuncs;
    }

    private TreeItem<FrameItem> VariableInit()
    {
        TreeItem<FrameItem> vars = FrameItem.CREATE(null,"Variables");
        directoryItems.add(vars);
        vars.getChildren().add(FrameItem.CREATE("Variables","Bool", new VariableFrame<Boolean>(parent, ParamType.BOOL, false)));
        vars.getChildren().add(FrameItem.CREATE("Variables","Number", new VariableFrame<Boolean>(parent, ParamType.NUMBER, false)));
        vars.getChildren().add(FrameItem.CREATE("Variables","Text", new VariableFrame<Boolean>(parent, ParamType.TEXT, false)));
        vars.getChildren().add(FrameItem.CREATE("Variables", "Character", new VariableFrame<Character>(parent, ParamType.CHARACTER, false)));
        return vars;
    }
    private TreeItem<FrameItem> ArrayVariableInit()
    {
        TreeItem<FrameItem> vars = FrameItem.CREATE(null, "Variables (Array)");
        directoryItems.add(vars);
        vars.getChildren().add(FrameItem.CREATE("Variables (Array)","Bool Array", new VariableFrame<Boolean>(parent, ParamType.BOOL, true)));
        vars.getChildren().add(FrameItem.CREATE("Variables (Array)","Number Array", new VariableFrame<Boolean>(parent, ParamType.NUMBER, true)));
        vars.getChildren().add(FrameItem.CREATE("Variables (Array)","Text Array", new VariableFrame<Boolean>(parent, ParamType.TEXT, true)));
        return vars;
    }
    private TreeItem<FrameItem> FunctionInit()
    {
        TreeItem<FrameItem> funcs = FrameItem.CREATE(null, "Functions");
        directoryItems.add(funcs);
        funcs.getChildren().add(OperatorInit());
        funcs.getChildren().add(LogicInit());
        funcs.getChildren().add(MathInit());
        funcs.getChildren().add(ArrayInit());
        funcs.getChildren().add(TextInit());
        funcs.getChildren().add(CharacterInit());

        return funcs;
    }
    private TreeItem<FrameItem> OperatorInit()
    {
        TreeItem<FrameItem> ops = FrameItem.CREATE("Functions", "Operators");
        directoryItems.add(ops);
        List<FunctionFrame> list = Functions.OPERATORS(parent);
        for (final FunctionFrame f : list)
        {
            ops.getChildren().add(FrameItem.CREATE("Operators", f.GetName(), f));
        }

        return ops;
    }
    private TreeItem<FrameItem> LogicInit()
    {
        TreeItem<FrameItem> logic = FrameItem.CREATE("Functions", "Logic");
        directoryItems.add(logic);
        List<FunctionFrame> list = Functions.LOGIC(parent);
        for (final FunctionFrame f : list)
        {
            logic.getChildren().add(FrameItem.CREATE("Logic", f.GetName(), f));
        }

        return logic;
    }
    private TreeItem<FrameItem> MathInit()
    {
        TreeItem<FrameItem> math = FrameItem.CREATE("Functions", "Math");
        directoryItems.add(math);
        List<FunctionFrame> list = Functions.MATH(parent);
        for (final FunctionFrame f : list)
        {
            math.getChildren().add(FrameItem.CREATE("Math", f.GetName(), f));
        }

        return math;
    }
    private TreeItem<FrameItem> ArrayInit()
    {
        TreeItem<FrameItem> arr = FrameItem.CREATE("Functions", "Array");
        directoryItems.add(arr);
        List<FunctionFrame> list = Functions.ARRAY(parent);
        for (final FunctionFrame f : list)
        {
            arr.getChildren().add(FrameItem.CREATE("Array", f.GetName(), f));
        }

        return arr;
    }
    private TreeItem<FrameItem> TextInit()
    {
        TreeItem<FrameItem> txt = FrameItem.CREATE("Functions", "Text");
        directoryItems.add(txt);
        List<FunctionFrame> list = Functions.TEXT(parent);
        for (final FunctionFrame f : list)
        {
            txt.getChildren().add(FrameItem.CREATE("Text", f.GetName(), f));
        }

        return txt;
    }
    private TreeItem<FrameItem> CharacterInit()
    {
        TreeItem<FrameItem> ops = FrameItem.CREATE("Functions", "Character Functions");
        directoryItems.add(ops);
        ops.getChildren().add(CharacterNameInit());

        return ops;
    }
    private TreeItem<FrameItem> CharacterNameInit()
    {
        TreeItem<FrameItem> ops = FrameItem.CREATE("Character Functions", "Name");
        directoryItems.add(ops);
        List<FunctionFrame> list = Functions.CHARACTER_NAME(parent);
        for (final FunctionFrame f : list)
        {
            ops.getChildren().add(FrameItem.CREATE("Name", f.GetName(), f));
        }

        return ops;
    }
    private TreeItem<FrameItem> ScenarioFunctionInit()
    {
        TreeItem<FrameItem> funcs = FrameItem.CREATE(null, "Scenarios");
        directoryItems.add(funcs);
        for (TreeItem<ScenarioItem> item : parent.GetParent().GetScenarioTree().getRoot().getChildren())
        {
            ScenarioFunctionHelp(item, funcs);
        }
        return funcs;
    }
    private TreeItem<FrameItem> InputInit()
    {
        TreeItem<FrameItem> inputs = FrameItem.CREATE(null, "Input");
        directoryItems.add(inputs);
        inputs.getChildren().add(FrameItem.CREATE("Input", "Text", new InputFrameText(parent)));
        inputs.getChildren().add(FrameItem.CREATE("Input", "Number", new InputFrameNumber(parent)));
        inputs.getChildren().add(FrameItem.CREATE("Input", "Bool", new InputFrameBool(parent)));

        return inputs;
    }
    private void ScenarioFunctionHelp(final TreeItem<ScenarioItem> item, TreeItem<FrameItem> funcs)
    {
        if (item != null)
        {
            if(!item.isLeaf()) // A folder with children
            {
                TreeItem<FrameItem> i = FrameItem.CREATE(item.getParent().getValue().GetName(), item.getValue().GetName());
                funcs.getChildren().add(i);
                for(TreeItem<ScenarioItem> child : item.getChildren())
                {
                    ScenarioFunctionHelp(child, i);
                }
            }
            else if (item.getValue().IsDirectory()) // A folder without children
            {
                TreeItem<FrameItem> i = FrameItem.CREATE(item.getParent().getValue().GetName(), item.getValue().GetName());
                funcs.getChildren().add(i);
            }
            else // A scenario
            {
                if (parent.GetParent().GetScenario(item.getValue().GetID()).GetID() != parent.GetID())
                {
                    TreeItem<FrameItem> i = FrameItem.CREATE(item.getParent().getValue().GetName(), item.getValue().GetName(), new ScenarioFrame(parent, parent.GetParent().GetScenario(item.getValue().GetID())));
                    funcs.getChildren().add(i);
                }
            }

        }

    }

    public void Collapse()
    {
        for (TreeItem<FrameItem> child : tree.getRoot().getChildren())
        {
            CollapseTreeView(child);
        }
    }
    private void CollapseTreeView(TreeItem<FrameItem> item)
    {
        if(item != null && !item.isLeaf())
        {
            item.setExpanded(false);
            for(TreeItem<FrameItem> child : item.getChildren())
            {
                CollapseTreeView(child);
            }
        }
    }
    public void FilterByParameter(final ParameterBase<?> param)
    {
        searchItems.clear();
        for(TreeItem<FrameItem> item : root.getChildren())
        {
            FilterByParameterHelper(item, param);
        }
        TreeView<FrameItem> searchTree = new TreeView<FrameItem>();
        searchTree.setRoot(new TreeItem<FrameItem>());
        searchTree.setShowRoot(false);
        ArrayList<TreeItem<FrameItem>> directoryCopy = new ArrayList<>(directoryItems);
        for (TreeItem<FrameItem> item : directoryCopy)
        {
            item.getChildren().clear();
            for (TreeItem<FrameItem> s : searchItems)
            {
                if (s.getValue().HasParent())
                {
                    if (s.getValue().GetParent().equals(item.getValue().GetName()))
                    {

                        item.getChildren().add(s);
                    }
                }
                else
                {
                    searchTree.getRoot().getChildren().add(s);
                }
            }
        }
        for (TreeItem<FrameItem> item : directoryCopy)
        {
            if (!item.getChildren().isEmpty())
            {
                item.setExpanded(true);
                searchTree.getRoot().getChildren().add(item);
            }
        }
        TreeInit(searchTree);
        pane.setCenter(searchTree);
    }
    private void FilterByParameterHelper(final TreeItem<FrameItem> item, final ParameterBase<?> param)
    {
        if(item != null)
        {
            if (! item.isLeaf())
            {
                for(TreeItem<FrameItem> child : item.getChildren())
                {
                    FilterByParameterHelper(child, param);
                }
            }
            else
            {
                if (item.getValue().ParamMatch(param))
                {
                    searchItems.add(item);
                }
            }
        }
    }


    
    
}
