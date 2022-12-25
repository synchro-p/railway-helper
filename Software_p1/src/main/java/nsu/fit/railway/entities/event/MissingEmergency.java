package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.timetable.TimetableEntry;
import nsu.fit.railway.entities.topology.Topology;

import java.util.List;

public class MissingEmergency extends Emergency{

    private final TimetableEntry missing;

    public MissingEmergency(TimetableEntry missing) {
        this.missing = missing;
    }

    public TimetableEntry getMissing() {
        return missing;
    }

    @Override
    public void handle(Timetable timetable, Topology topology) {
        List<TimetableEntry> timetableEntries = timetable.getEntries();

        timetableEntries.remove(missing);
    }
}
