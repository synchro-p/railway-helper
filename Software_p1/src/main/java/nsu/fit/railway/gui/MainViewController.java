package nsu.fit.railway.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nsu.fit.railway.control.ViewController;
import nsu.fit.railway.data.impl.jackson.JacksonTimetableRepositoryImpl;
import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.timetable.Train;
import nsu.fit.railway.entities.timetable.Type;
import nsu.fit.railway.entities.topology.*;
import nsu.fit.railway.gui.graph.*;
import nsu.fit.railway.gui.graph.Cell;

import java.io.IOException;
import java.util.*;

import static java.lang.String.valueOf;
import static nsu.fit.railway.entities.timetable.Type.PASSENGER;

public class MainViewController extends Application {

    Topology topology;
    Timetable timetable;

    @FXML
    private Button backBtn;
    @FXML
    private Button forwardBtn;
    @FXML
    private void forwardBtnClicked() {

    }
    @FXML
    private AnchorPane mainPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Button restartBtn;
    @FXML
    private MenuItem menuOpenTopology;
    @FXML
    private void openTopology() {
        //TODO
    }
    @FXML
    private MenuItem menuOpenTimetable;
    @FXML
    private void openTimetable() {
        //TODO
    }
    @FXML
    private MenuItem menuClose;
    @FXML
    private void restartBtnClicked() {
        restartSimulation();
    }


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
    Topology testTopology;
    ViewController viewController;
    Model model;
    @Override
    public void start(Stage stage) throws IOException {
        nodes.add(testNode1);
        nodes.add(testNode2);
        nodes.add(testNode3);
        testTrack1.setStartNode(testNode1);
        testTrack1.setCurrentTrain(new Train(12, 8, PASSENGER));
        testTrack1.setFinishNode(testNode3);
        testTrack2.setStartNode(testNode2);
        testTrack2.setFinishNode(testNode3);
        tracks.add(testTrack1);
        tracks.add(testTrack2);
        ArrayList<Type> canServe = new ArrayList<>();
        canServe.add(PASSENGER);
        testTrack1.setCanServe(canServe);

        testTopology = new Topology(nodes, tracks);
        topology = testTopology;
        //TODO: file input
        //timetable = JacksonTimetableRepositoryImpl.getTimetable();
        //topology = JacksonTopologyRepositoryImpl.getTopology();
        topology = TimetableTopologyGenerator.createTopology();
        timetable = TimetableTopologyGenerator.createTimetable();
        viewController = new ViewController(topology, timetable, this);
        FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("mainView.fxml"));
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

        model = graph.getModel();

        graph.beginUpdate();

        for (Track track : topology.getTracks()) {
            model.addCell(track.getStartNode());
            model.addCell(track.getFinishNode());
            model.addEdge(track.getStartNode().getId(), track.getFinishNode().getId(), track);
        }
        //adding tooltips
        for (Cell cell : model.getAddedCells()) {
            Tooltip cellTooltip = new Tooltip();
            StringBuilder tooltipText = new StringBuilder("ID : " + cell.getCellId());
            for (ControlElement ce : cell.getNode().getAssociated()) {
                if (ce instanceof Switch) {
                    tooltipText.append("\n------------\nSwitch\nFrom: ");
                    tooltipText.append(((Switch) ce).getSwitchInfo().getTrackFrom().getTrackInfo().getId());
                    tooltipText.append("\nTo: ").append(((Switch) ce).getCurrentTo().getTrackInfo().getId());
                }
                if (ce instanceof Signal) {
                    tooltipText.append("\n------------\nSignal\nState: ");
                    tooltipText.append(((Signal) ce).getSignalState());
                }
            }
            cellTooltip.setText(tooltipText.toString());
            Tooltip.install(cell, cellTooltip);
        }
        redrawStep();

        graph.endUpdate();

    }
    public void restartSimulation() {
        viewController.startSimulation();
    }

    public void setTopology(Topology newTopology) {
        topology = newTopology;
    }
    public void redrawStep() {
        List<Track> toTracks = new ArrayList<>(Collections.emptyList());
        for (Cell cell : model.getAddedCells()) {
            cell.setNewStyle();
            for (ControlElement ce : cell.getNode().getAssociated()) {
                if (ce instanceof Switch) {
                    Track to = ((Switch) ce).getCurrentTo();
                    toTracks.add(to);
                }
            }
        }
        for (Edge edge : model.getAddedEdges()) {
            if (toTracks.contains(edge.getTrack())) {
                edge.setActiveSwitch(true);
            } else {
                edge.setActiveSwitch(false);
            }
            edge.setNewStyle();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}