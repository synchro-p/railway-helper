package nsu.fit.railway.control;

import nsu.fit.railway.entities.event.EventQueue;
import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.timetable.TimetableEntry;
import nsu.fit.railway.entities.topology.Topology;

import java.time.LocalDateTime;

public class ViewController {

    private Topology topology;
    private Timetable timetable;

    public ViewController(Topology topology, Timetable timetable) {
        this.topology = topology;
        this.timetable = timetable;
    }

    public void startSimulation() {
        EventQueue events = new Planner().createSchedule(topology,timetable);
        EmergencyConfiguration configuration = new EmergencyConfiguration();
        EventProcessor processor = new EventProcessor(
                new EmergencyGenerator(
                        configuration.getFrequency(), configuration.getPossibleTypes()));
        while(!events.isEmpty()) {
            LocalDateTime timestamp = processor.processEvent(events.poll());
//            SimulationGUI.changeView(Topology, Datetime);
        }
    }

    public void addTimetableEntry(TimetableEntry entry) {
        this.timetable.addEntry(entry);
        //update GUI
    }

    public void removeTimetableEntry(Integer id) {
        this.timetable.removeEntry(id);
        //update GUI
    }

    public void editTimetableEntry(Integer id, TimetableEntry newEntry) {
        this.timetable.editEntry(id, newEntry);
        //update GUI
    }
}
