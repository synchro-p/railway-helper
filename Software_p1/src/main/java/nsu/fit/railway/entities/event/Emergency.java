package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.topology.Topology;

public abstract class Emergency extends Event {

    private String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public abstract void handle(Timetable timetable, Topology topology);
}
