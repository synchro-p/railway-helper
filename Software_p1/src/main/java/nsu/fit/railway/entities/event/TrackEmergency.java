package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.topology.Track;

public class TrackEmergency {

    private final Track brokenTrack;

    public TrackEmergency(Track track) {
        brokenTrack = track;
    }

    public Track getBrokenTrack() {
        return brokenTrack;
    }
}
