package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.topology.Topology;

public abstract class Event {
    public abstract void apply();
}
