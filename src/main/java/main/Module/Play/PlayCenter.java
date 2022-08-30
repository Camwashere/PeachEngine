package main.Module.Play;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import main.Debug.Debug;
import main.Module.Story.Scenario.Frame.BaseFrame;
import main.Module.Story.Scenario.Frame.FunctionFrame.FunctionFrame;
import main.Module.Story.Scenario.Frame.InputFrame.InputFrameBase;
import main.Module.Story.Scenario.Frame.Parameter.OutputParameter.OutputParameter;
import main.Module.Story.Scenario.Frame.ScenarioFrame.ScenarioInputFrame;
import main.Module.Story.Scenario.Frame.StoryFrame.StoryFrame;
import main.Module.Story.Scenario.Frame.VariableFrame.VariableFrame;
import main.Module.Story.Scenario.Scenario;
import main.Tools.InitHelp;

public class PlayCenter extends VBox
{
    private final PlayModule parent;
    private final Text text;

    public PlayCenter(final PlayModule parentModule)
    {
        parent = parentModule;
        text = new Text();
        text.setFont(new Font(150));
        text.setTextAlignment(TextAlignment.CENTER);
    }

    public final void LoadScenario(final Scenario s)
    {
        LoadFrame(s.GetInputFrame());
    }

    private void Reload()
    {
        text.setText("");
        getChildren().clear();
        getChildren().add(text);
    }

    private void LoadFrame(final BaseFrame f)
    {
        if (f == null)
        {
            return;
        }
        f.UpdateInputValues();
        Reload();
        if (f instanceof ScenarioInputFrame frame)
        {
            LoadScenarioInputFrame(frame);
        }
        else if (f instanceof StoryFrame frame)
        {
            LoadStoryFrame(frame);
        }
        else if (f instanceof InputFrameBase<?> frame)
        {
            LoadInputFrame(frame);
        }
        else if (f instanceof FunctionFrame frame)
        {
            LoadFunctionFrame(frame);
        }

    }

    private void LoadScenarioInputFrame(final ScenarioInputFrame frame)
    {
        if (frame.GetOutput().IsConnected())
        {
            LoadFrame(frame.GetOutput().GetFirstConnector().GetInput().GetParent());
        }
    }

    private void LoadStoryFrame(final StoryFrame frame)
    {
        text.setText(frame.Read());

        for (OutputParameter<?> param : frame.GetOutputParams())
        {
            Button b = new Button(param.GetName());
            b.setOnMouseReleased(evt->
            {
                if (param.IsConnected())
                {
                    LoadFrame(param.GetFirstConnector().GetInput().GetParent());
                }
            });
            InitHelp.ButtonInit(b);
            getChildren().add(b);
        }
    }
    private void LoadInputFrame(final InputFrameBase<?> frame)
    {
        if (frame.HasPrompt())
        {
            text.setText(frame.GetPromptText());
        }
        getChildren().add(frame.GetField());

        Button b = new Button("Confirm");
        b.setOnMouseReleased(evt->
        {
            if (frame.IsReady())
            {
                if (frame.GetNext() != null)
                {
                    LoadFrame(frame.GetNext());
                }
            }
        });
        getChildren().add(b);
    }
    private void LoadFunctionFrame(final FunctionFrame frame)
    {
        LoadFrame(frame.Apply());
    }




    public final PlayModule GetParent(){return parent;}
}
