package nsu.fit.railway.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import nsu.fit.railway.control.EmergencyConfiguration;
import nsu.fit.railway.control.EmergencyGenerator;
import nsu.fit.railway.control.EventProcessor;
import nsu.fit.railway.control.Planner;
import nsu.fit.railway.entities.event.EventQueue;
import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.timetable.Type;
import nsu.fit.railway.entities.topology.*;
import nsu.fit.railway.gui.graph.*;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainController {
    @FXML
    private Button backBtn;
    @FXML
    private Button forwardBtn;
    @FXML
    private void forwardBtnClicked() {
        System.out.println("Starting simulation step...");
        simulationStep();
        //addGraphComponents();
        System.out.println("Step made");
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
    private final Topology topology;
    private final Timetable timetable;
    private final MainApp app;

    private EventQueue events;
    private EventProcessor processor;

    Graph graph;
    Model model;
    public MainController() {
        topology = TimetableTopologyGenerator.createTopology();
        timetable = TimetableTopologyGenerator.createTimetable();
        app = MainApp.app;
        graph = new Graph(topology.getNodes().iterator().next());
        addGraphComponents();
        Layout layout = new RandomLayout(graph);
        layout.execute();
    }

    private void restartSimulation() {
        //addGraphComponents();
        this.events = new Planner().createSchedule(topology,timetable);
        EmergencyConfiguration configuration = new EmergencyConfiguration();
        this.processor = new EventProcessor(
                new EmergencyGenerator(
                        configuration.getFrequency(), configuration.getPossibleTypes()),
                events);
        app.doGraph(graph);
    }

    public void simulationStep() {
        if (events.peek() == null) {
//            app.tellUserNoMoreEvents();
        }
        AbstractMap.SimpleEntry<LocalDateTime, LocalDateTime> timestamp = processor.processEvent(events.poll());
//        app.showUpdated(timestamp);
        redrawStep();
    }

    public void redrawStep() {
        System.out.println("redrawing");
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
        addTooltips();
        //graph.endUpdate();
        //app.doGraph(graph);
        //TODO
        //А КАК ПОКАЗАТЬ????
        //app.doGraph(graph);
    }

    private void addGraphComponents() {

        model = graph.getModel();

        graph.beginUpdate();

        for (Track track : topology.getTracks()) {
            model.addCell(track.getStartNode());
            model.addCell(track.getFinishNode());
            model.addEdge(track.getStartNode().getId(), track.getFinishNode().getId(), track);
        }

        addTooltips();
        redrawStep();

        graph.endUpdate();

    }
    private void addTooltips() {
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
            tooltipText.append("\nServe:\n");
            for (Type serve : edge.getTrack().getCanServe()) {
                tooltipText.append("\t" + serve + "\n");
            }
            if (edge.getTrack().isOccupied())
                tooltipText.append("Train: " + edge.getTrack().getCurrentTrain());

            cellTooltip.setText(tooltipText.toString());
            Tooltip.install(edge, cellTooltip);
        }
    }
}
