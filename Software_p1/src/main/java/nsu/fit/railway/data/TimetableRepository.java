package nsu.fit.railway.data;

import nsu.fit.railway.entities.timetable.Timetable;

public interface TimetableRepository {

    void saveTimetable(Timetable timetable);

    Timetable getTimetable();
}
