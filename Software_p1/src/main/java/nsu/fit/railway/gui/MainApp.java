package nsu.fit.railway.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import nsu.fit.railway.gui.graph.Graph;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {
    public static MainApp app;
    public static Parent root;
    @Override
    public void start(Stage stage) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainView.fxml")));
        Scene scene = new Scene(root, 600, 300);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
        stage.setTitle("Railway helper");
        stage.setScene(scene);
        stage.show();
    }

    public MainApp() {
        app = this;
    }

    public void doGraph(Graph graph) {
        ((SplitPane) root).getItems().remove(1);
        ((SplitPane) root).getItems().add(1, graph.getScrollPane());
    }
    public static void main(String[] args) {
        launch(args);
    }

}