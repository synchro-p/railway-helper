package nsu.fit.railway.entities.topology;

import java.util.List;

public class SwitchInfo{

    private final Track trackFrom;
    private final List<Track> tracksTo;

    public SwitchInfo(Track trackFrom, List<Track> tracksTo) {
        this.trackFrom = trackFrom;
        this.tracksTo = tracksTo;
    }


    public Track getTrackFrom() {
        return trackFrom;
    }

    public List<Track> getTracksTo() {
        return tracksTo;
    }
}
