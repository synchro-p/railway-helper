package nsu.fit.railway.entities.topology;

import java.util.List;

public class Switch extends ControlElement {
    //TODO create checks for adequacy for tracks
    private final SwitchInfo switchInfo;
    private Track currentTo;

    public Switch(Track trackFrom, List<Track> tracksTo, Track currentTo) {
        this.switchInfo = new SwitchInfo(trackFrom, tracksTo);
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
