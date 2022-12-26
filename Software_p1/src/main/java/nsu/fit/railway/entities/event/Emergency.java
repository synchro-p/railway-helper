package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.topology.Topology;

public abstract class Emergency extends Event {

    private String message = "";
    protected final Timetable timetable;
    protected final Topology topology;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Emergency(Timetable timetable, Topology topology) {
        this.timetable = timetable;
        this.topology = topology;
    }

    public Emergency(String message, Timetable timetable, Topology topology) {
        this.message = message;
        this.timetable = timetable;
        this.topology = topology;
    }
}
