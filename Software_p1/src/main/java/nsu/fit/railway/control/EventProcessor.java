package nsu.fit.railway.control;

import nsu.fit.railway.entities.event.Emergency;
import nsu.fit.railway.entities.event.Event;
import nsu.fit.railway.entities.event.EventQueue;
import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.topology.Topology;

import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;

public class EventProcessor {

    private final EmergencyGenerator emergencyGenerator;
    private final EventQueue eventQueue;

    public EventProcessor(EmergencyGenerator emergencyGenerator, EventQueue eventQueue) {
        this.eventQueue = eventQueue;
        this.emergencyGenerator = emergencyGenerator;
    }


    public SimpleEntry<LocalDateTime, LocalDateTime> processEvent(
            SimpleEntry<SimpleEntry<LocalDateTime, LocalDateTime>, Event> eventPair
    ) {
            SimpleEntry<LocalDateTime, LocalDateTime> timestamp = eventPair.getKey();
            Event event = eventPair.getValue();

        //TODO normal implementation for emergency generation
        Emergency generated = emergencyGenerator.roll();
        if (generated != null) {
            LocalDateTime randomDateTime = RandomDateTimeGenerator.after(timestamp);
            eventQueue.add(new SimpleEntry<>(randomDateTime, generated));
        }
        event.apply();
        return timestamp;
    }
}
