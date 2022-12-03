package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.TimetableEntry;

public class ComingEmergency extends Emergency {

    private final TimetableEntry coming;

    public ComingEmergency(TimetableEntry coming) {
        this.coming = coming;
    }

    public TimetableEntry getComing() {
        return coming;
    }
}
