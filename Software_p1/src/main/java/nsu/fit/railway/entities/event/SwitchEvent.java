package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.topology.Switch;
import nsu.fit.railway.entities.topology.Track;

public class SwitchEvent extends Event {

    private final Switch aSwitch;
    private final Track toTrack;

    public SwitchEvent(Switch aSwitch, Track toTrack) {
        this.aSwitch = aSwitch;
        this.toTrack = toTrack;
    }

    @Override
    public void apply() {
        aSwitch.setCurrentTo(toTrack);
    }

    @Override
    public String toString() {
        return "SwitchEvent{" +
                "aSwitch=" + aSwitch.getSwitchInfo().getId() +
                ", toTrack=" + toTrack.getTrackInfo().getId() +
                '}';
    }
}
