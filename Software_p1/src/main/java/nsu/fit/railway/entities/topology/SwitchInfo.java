package nsu.fit.railway.entities.topology;

import java.util.List;

public class SwitchInfo{

    private final Integer id;
    private final Track trackFrom;
    private final List<Track> tracksTo;

    public SwitchInfo(Integer id, Track trackFrom, List<Track> tracksTo) {
        this.id = id;
        this.trackFrom = trackFrom;
        this.tracksTo = tracksTo;
    }


    public Track getTrackFrom() {
        return trackFrom;
    }

    public List<Track> getTracksTo() {
        return tracksTo;
    }

    public Integer getId() {
        return id;
    }
}
