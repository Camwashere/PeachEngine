package main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Module.ModuleBase.ModuleID;
import main.Module.ModuleManager.ModuleManager;
import main.Project.Project;
import main.Tools.InitHelp;
import main.Tools.StringHelp;

import java.io.File;
import java.util.Optional;

public class Splash extends VBox
{
    private final Button newProject;
    private final Button load;
    private final Stage stage;
    public Splash(final Stage mainStage)
    {
        stage = mainStage;
        newProject = new Button("New Project");
        load = new Button("Load Project");
        InitHelp.ButtonsInit(newProject, load);
        getChildren().addAll(newProject, load);
        ListenerInit();
    }
    private void ListenerInit()
    {
        newProject.setOnAction(evt->
        {
            Optional<String> optional = ChooseProjectName().showAndWait();
            if (optional.isPresent())
            {
                Project project = new Project(optional.get());
                OpenProject(project);
            }

        });
        load.setOnAction(evt->
        {
            FileChooser chooser = Project.FILE_CHOOSER();
            File file = chooser.showOpenDialog(null);
            if (file.exists())
            {
                Optional<String> optional = StringHelp.GetFileExtension(file);
                if (optional.isPresent())
                {
                    if (optional.get().equals("peach"))
                    {
                        Project project = new Project(file);
                        if (!project.IsFucked())
                        {
                            OpenProject(project);
                        }
                    }
                }
            }
        });
    }
    private void OpenProject(final Project project)
    {
        stage.setTitle("Peach Engine - " + project.GetName());
        stage.getScene().setRoot(project);
    }

    private TextInputDialog ChooseProjectName()
    {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Project Name");
        dialog.setHeaderText("Choose a project name");
        return dialog;
    }
}
