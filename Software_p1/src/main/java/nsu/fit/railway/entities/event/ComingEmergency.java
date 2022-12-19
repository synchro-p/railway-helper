package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.timetable.TimetableEntry;
import nsu.fit.railway.entities.topology.Topology;

import java.util.List;

public class ComingEmergency extends Emergency {

    private final TimetableEntry coming;

    public ComingEmergency(TimetableEntry coming) {
        this.coming = coming;
    }

    public TimetableEntry getComing() {
        return coming;
    }

    @Override
    public void handle(Timetable timetable, Topology topology) {
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
