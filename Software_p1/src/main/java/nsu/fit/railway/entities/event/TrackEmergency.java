package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.topology.Topology;
import nsu.fit.railway.entities.topology.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackEmergency {

    private final Track brokenTrack;

    public TrackEmergency(Track track) {
        brokenTrack = track;
    }

    public Track getBrokenTrack() {
        return brokenTrack;
    }

    public void handle(Timetable timetable, Topology topology) {
        brokenTrack.setActive(false);
        List<Track> neighbourTracks = new ArrayList<>(brokenTrack.getStartNode().getInTracks());
        neighbourTracks.addAll(brokenTrack.getFinishNode().getOutTracks());

        for (Track track : neighbourTracks) {
            handleNeighbourTrack(timetable, topology, track);
        }
    }

    private void handleNeighbourTrack(Timetable timetable, Topology topology, Track neighbourTrack) {
        boolean isNotThereWayIn = neighbourTrack
                .getStartNode()
                .getInTracks()
                .stream()
                .noneMatch(Track::isActive);

        boolean isNotThereWayOut = neighbourTrack
                .getFinishNode()
                .getOutTracks()
                .stream()
                .noneMatch(Track::isActive);

        if (isNotThereWayIn || isNotThereWayOut) {
            neighbourTrack.setActive(false);

            List<Track> neighbourTracks = new ArrayList<>(neighbourTrack.getStartNode().getInTracks());
            neighbourTracks.addAll(neighbourTrack.getFinishNode().getOutTracks());

            for (Track track: neighbourTracks) {
                handleNeighbourTrack(timetable, topology, track);
            }
        }
    }
}
