package nsu.fit.railway.control;

import nsu.fit.railway.entities.event.EventQueue;
import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.topology.Topology;
import nsu.fit.railway.gui.Main;

import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;

public class ViewController {

    private final Topology topology;
    private final Timetable timetable;

    private final Main gui;

    private EventQueue events;
    private EventProcessor processor;

    public ViewController(Topology topology, Timetable timetable, Main gui) {
        this.topology = topology;
        this.timetable = timetable;
        this.gui = gui;
    }

    public void startSimulation() {
        this.events = new Planner().createSchedule(topology,timetable);
        EmergencyConfiguration configuration = new EmergencyConfiguration();
        this.processor = new EventProcessor(
                new EmergencyGenerator(
                        configuration.getFrequency(), configuration.getPossibleTypes()),
                events);
    }

    public void simulationStep() {
        if (events.peek() == null) {
//            gui.tellUserNoMoreEvents();
        }
            SimpleEntry<LocalDateTime, LocalDateTime> timestamp = processor.processEvent(events.poll());
//            gui.changeView(topology, timestamp);
    }
}
