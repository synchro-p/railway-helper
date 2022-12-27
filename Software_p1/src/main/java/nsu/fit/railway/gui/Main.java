package nsu.fit.railway.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
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
    //!!Testing data
    //TODO: link with file input
    TopologyNode testNode1 = new TopologyNode(1, Collections.emptyList(), true, false);
    TopologyNode testNode2 = new TopologyNode(2, Collections.emptyList(), false, true);
    TopologyNode testNode3 = new TopologyNode(3, Collections.emptyList(), false, false);
    Set<TopologyNode> nodes = new HashSet<>();
    Track testTrack1 = new Track(new TrackInfo(1, 10), Collections.emptyList(), false);
    Track testTrack2 = new Track(new TrackInfo(1, 10), Collections.emptyList(), false);
    Set<Track> tracks = new HashSet<>();
    Topology topology;

    @Override
    public void start(Stage stage) throws IOException {
        nodes.add(testNode1);
        nodes.add(testNode2);
        nodes.add(testNode3);
        testTrack1.setStartNode(testNode1);
        testTrack1.setFinishNode(testNode3);
        testTrack2.setStartNode(testNode2);
        testTrack2.setFinishNode(testNode3);
        tracks.add(testTrack1);
        tracks.add(testTrack2);
        ArrayList<Type> canServe = new ArrayList<>();
        canServe.add(PASSENGER);
        testTrack1.setCanServe(canServe);

        topology = new Topology(nodes, tracks);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainView.fxml"));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainView.fxml")));
//        BorderPane root = new BorderPane();

        graph = new Graph(topology.getNodes().iterator().next());

        ((SplitPane) root).getItems().remove(1);
        ((SplitPane) root).getItems().add(1, graph.getScrollPane());
//        root.setCenter(graph.getScrollPane());
        Scene scene = new Scene(root, 600, 300);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

//        Scene scene = new Scene(root, 1024, 768);
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

        for (Track track : topology.getTracks()) {
            model.addCell(track.getStartNode());
            model.addCell(track.getFinishNode());
            model.addEdge(track.getStartNode().getId(), track.getFinishNode().getId(), track);
        }

        graph.endUpdate();

    }

    public static void main(String[] args) {
        launch(args);
    }
}