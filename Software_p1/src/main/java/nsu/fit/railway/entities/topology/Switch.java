package nsu.fit.railway.entities.topology;

import java.util.List;

public class Switch extends ControlElement {
    private final SwitchInfo switchInfo;
    private Track currentTo;

    public Switch(Integer id, Track trackFrom, List<Track> tracksTo, Track currentTo) {
        this.switchInfo = new SwitchInfo(id, trackFrom, tracksTo);
        this.currentTo = currentTo;
    }


    public SwitchInfo getSwitchInfo() {
        return switchInfo;
    }

    public Track getCurrentTo() {
        return currentTo;
    }

    public void setCurrentTo(Track currentTo) {
        this.currentTo = currentTo;
    }
}
