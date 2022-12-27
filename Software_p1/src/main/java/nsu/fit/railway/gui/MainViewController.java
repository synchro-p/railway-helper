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
        //makeStepSimulation();
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
    Topology testTopology;
    ViewController viewController;
    Model model;
    @Override
    public void start(Stage stage) throws IOException {
        //TODO: file input
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
        for (Edge edge : model.getAddedEdges()) {
            Tooltip cellTooltip = new Tooltip();
            StringBuilder tooltipText = new StringBuilder("ID : " + edge.getTrack().getTrackInfo().getId());
            tooltipText.append("\nLength: " + edge.getTrack().getTrackInfo().getLength());
            tooltipText.append("\nCan serve:\n");
            for (Type serve : edge.getTrack().getCanServe()) {
                tooltipText.append("\t" + valueOf(serve) + "\n");
            }
            if (edge.getTrack().isOccupied()) {
                tooltipText.append("Train ID: " + edge.getTrack().getCurrentTrain().getId());
            }
            cellTooltip.setText(tooltipText.toString());
            Tooltip.install(edge, cellTooltip);
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
            edge.setActiveSwitch(toTracks.contains(edge.getTrack()));
            edge.setNewStyle();
        }
    }

    public MainViewController( ) {
        //TODO: file input
        //timetable = JacksonTimetableRepositoryImpl.getTimetable();
        //topology = JacksonTopologyRepositoryImpl.getTopology();
        topology = TimetableTopologyGenerator.createTopology();
        timetable = TimetableTopologyGenerator.createTimetable();
        viewController = new ViewController(topology, timetable, this);
        System.out.println(viewController);
    }
    public static void main(String[] args) {
        launch(args);
    }

}