package nsu.fit.railway.control;

import nsu.fit.railway.entities.event.Event;

import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;

public class EventProcessor {

    private final EmergencyGenerator emergencyGenerator;

    public EventProcessor(EmergencyGenerator emergencyGenerator) {
        this.emergencyGenerator = emergencyGenerator;
    }

    public LocalDateTime processEvent(SimpleEntry<LocalDateTime, Event> eventPair) {
            LocalDateTime timestamp = eventPair.getKey();
            Event event = eventPair.getValue();

            //TODO normal implementation for emergency generation
//          Emergency generated = emergencyGenerator.roll();
//          if (generated != null) {
//              eventQueue.add();
//          }
            event.apply();
            return timestamp;
    }
}
