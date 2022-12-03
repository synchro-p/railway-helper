package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.TimetableEntry;

public class MissingEmergency extends Emergency{

    private final TimetableEntry missing;

    public MissingEmergency(TimetableEntry missing) {
        this.missing = missing;
    }


    public TimetableEntry getMissing() {
        return missing;
    }
}
