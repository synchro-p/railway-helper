package nsu.fit.railway.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import nsu.fit.railway.entities.timetable.Type;
import nsu.fit.railway.entities.topology.Topology;
import nsu.fit.railway.entities.topology.TopologyNode;
import nsu.fit.railway.entities.topology.Track;
import nsu.fit.railway.entities.topology.TrackInfo;
import nsu.fit.railway.gui.graph.Graph;
import nsu.fit.railway.gui.graph.Layout;
import nsu.fit.railway.gui.graph.Model;
import nsu.fit.railway.gui.graph.RandomLayout;

import java.io.IOException;
import java.util.*;

import static nsu.fit.railway.entities.timetable.Type.PASSENGER;

public class Main extends Application {

    Graph graph;

    TopologyNode testNode1 = new TopologyNode(1, Collections.emptyList(), true, false);
    TopologyNode testNode2 = new TopologyNode(2, Collections.emptyList(), false, true);
    Set<TopologyNode> nodes = new HashSet<>();
    Track testTrack1 = new Track(new TrackInfo(1, 10), Collections.emptyList(), false);
    Set<Track> tracks = new HashSet<>();
    Topology topology;

    @Override
    public void start(Stage stage) throws IOException {
        nodes.add(testNode1);
        nodes.add(testNode2);
        ArrayList<Type> canServe = new ArrayList<>();
        canServe.add(PASSENGER);
        testTrack1.setCanServe(canServe);
        topology = new Topology(nodes, tracks);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainView.fxml"));
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainView.fxml")));
        BorderPane root = new BorderPane();

        graph = new Graph(topology.getNodes().iterator().next());

        root.setCenter(graph.getScrollPane());
        //Scene scene = new Scene(fxmlLoader.load(), 600, 300);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        Scene scene = new Scene(root, 1024, 768);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
        stage.setTitle("Railway helper");
        stage.setScene(scene);


        addGraphComponents();

        Layout layout = new RandomLayout(graph);
        layout.execute();
        stage.show();
    }

    private void addGraphComponents() {

        Model model = graph.getModel();

        graph.beginUpdate();


        for (TopologyNode cell : topology.getNodes()) {
            model.addCell(cell);
        }


        //model.addEdge();
        model.addEdge(1, 2);
        //model.addEdge(2, 3);
        //model.addEdge(2, 4);
        //model.addEdge(3, 4);
        //model.addEdge(2, 5);
        //model.addEdge(4, 6);
        //model.addEdge(5, 7);

        graph.endUpdate();

    }

    public static void main(String[] args) {
        launch(args);
    }
}