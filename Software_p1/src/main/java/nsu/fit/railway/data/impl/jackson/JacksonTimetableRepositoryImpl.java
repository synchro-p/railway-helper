package nsu.fit.railway.data.impl.jackson;

import nsu.fit.railway.data.TimetableRepository;
import nsu.fit.railway.entities.timetable.Timetable;

public class JacksonTimetableRepositoryImpl implements TimetableRepository {

    private static final String TIMETABLE_JSON_NAME = "timetable.json";

    @Override
    public void saveTimetable(Timetable timetable) {
        JacksonSerialization.serialize(timetable, TIMETABLE_JSON_NAME);
    }

    @Override
    public Timetable getTimetable() {
        return JacksonSerialization.deserialize(TIMETABLE_JSON_NAME, Timetable.class);
    }

}
