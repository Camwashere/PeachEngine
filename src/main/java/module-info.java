module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.fxmisc.richtext;
    requires org.fxmisc.flowless;
    requires org.fxmisc.undo;
    requires org.kordamp.jsilhouette.javafx;


    opens main to javafx.fxml;
    exports main;
    exports main.Module.Story;
    opens main.Module.Story to javafx.fxml;
    exports main.Module.Story.Scenario;
    opens main.Module.Story.Scenario to javafx.fxml;
    exports main.Module.Story.Scenario.ScenarioMenu;
    opens main.Module.Story.Scenario.ScenarioMenu to javafx.fxml;
    exports main.Module.Story.Scenario.Frame;
    opens main.Module.Story.Scenario.Frame;
    exports main.Module.ModuleManager;
    opens main.Module.ModuleManager to javafx.fxml;
    exports main.Module.ModuleBase;
    opens main.Module.ModuleBase to javafx.fxml;
    exports main.Project;
    opens main.Project to javafx.fxml;
}