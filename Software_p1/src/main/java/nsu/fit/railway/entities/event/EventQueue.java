package nsu.fit.railway.entities.event;

import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.PriorityQueue;

public class EventQueue extends PriorityQueue<SimpleEntry<LocalDateTime, Event>>{

    public EventQueue() {
        super(java.util.Map.Entry.comparingByKey());
    }
}
