package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.timetable.TimetableEntry;
import nsu.fit.railway.entities.topology.Topology;

import java.util.List;

public class MissingEmergency extends Emergency{

    private final TimetableEntry missing;

    public MissingEmergency(Timetable timetable, Topology topology, TimetableEntry missing) {
        super(timetable, topology);
        this.missing = missing;
    }

    public MissingEmergency(String message, Timetable timetable, Topology topology, TimetableEntry missing) {
        super(message, timetable, topology);
        this.missing = missing;
    }

    public TimetableEntry getMissing() {
        return missing;
    }

    @Override
    public void apply() {
        List<TimetableEntry> timetableEntries = timetable.getEntries();

        timetableEntries.remove(missing);
    }
}
