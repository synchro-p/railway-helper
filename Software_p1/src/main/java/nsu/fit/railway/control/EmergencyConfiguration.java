package nsu.fit.railway.control;

import nsu.fit.railway.entities.event.Emergency;
import nsu.fit.railway.entities.event.SwitchEmergency;
import nsu.fit.railway.entities.event.TrackEmergency;

import java.util.List;

public class EmergencyConfiguration {

    private final Double frequency;
    private final List<Class<? extends Emergency>> possibleTypes;

    public EmergencyConfiguration() {
        this.frequency = 0.01;
        this.possibleTypes = List.of(TrackEmergency.class, SwitchEmergency.class);
    }

    public Double getFrequency() {
        return frequency;
    }

    public List<Class<? extends Emergency>> getPossibleTypes() {
        return possibleTypes;
    }
}
