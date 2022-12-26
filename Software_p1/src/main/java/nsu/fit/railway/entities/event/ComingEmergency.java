package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.timetable.TimetableEntry;
import nsu.fit.railway.entities.topology.Topology;

import java.util.List;

public class ComingEmergency extends Emergency {

    private final TimetableEntry coming;

    public ComingEmergency(Timetable timetable, Topology topology, TimetableEntry coming) {
        super(timetable, topology);
        this.coming = coming;
    }

    public ComingEmergency(String message, Timetable timetable, Topology topology, TimetableEntry coming) {
        super(message, timetable, topology);
        this.coming = coming;
    }

    public TimetableEntry getComing() {
        return coming;
    }

    @Override
    public void apply() {
        List<TimetableEntry> timetableEntries = timetable.getEntries();

        int index;
        if ((index = timetableEntries.indexOf(coming)) != -1) {
            timetableEntries.get(index).setArrivalIntervalStart(coming.getArrivalIntervalStart());
            timetableEntries.get(index).setArrivalIntervalFinish(coming.getArrivalIntervalFinish());
        } else {
            timetableEntries.add(coming);
        }
    }
}
