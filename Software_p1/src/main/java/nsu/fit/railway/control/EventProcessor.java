package nsu.fit.railway.control;

import nsu.fit.railway.entities.event.Event;
import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.topology.Topology;

import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;

public class EventProcessor {

    private final EmergencyGenerator emergencyGenerator;

    public EventProcessor(EmergencyGenerator emergencyGenerator) {
        this.emergencyGenerator = emergencyGenerator;
    }

    public SimpleEntry<LocalDateTime, LocalDateTime> processEvent(
            SimpleEntry<SimpleEntry<LocalDateTime, LocalDateTime>, Event> eventPair
    ) {
            SimpleEntry<LocalDateTime, LocalDateTime> timestamp = eventPair.getKey();
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
