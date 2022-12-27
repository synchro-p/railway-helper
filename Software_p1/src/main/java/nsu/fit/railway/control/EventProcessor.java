package nsu.fit.railway.control;

import nsu.fit.railway.entities.event.Emergency;
import nsu.fit.railway.entities.event.Event;
import nsu.fit.railway.entities.event.EventQueue;

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
        SimpleEntry<LocalDateTime, LocalDateTime> interval = eventPair.getKey();
            LocalDateTime timestamp = eventPair.getKey().getValue();
            Event event = eventPair.getValue();

        //TODO normal implementation for emergency generation
        Emergency generated = emergencyGenerator.roll();
        if (generated != null) {
            LocalDateTime randomDateTime = RandomDateTimeGenerator.after(timestamp);
            eventQueue.add(new SimpleEntry<>(new SimpleEntry<>(randomDateTime, randomDateTime), generated));
        }

        System.out.println(event.toString());

        event.apply();
        return interval;
    }
}
