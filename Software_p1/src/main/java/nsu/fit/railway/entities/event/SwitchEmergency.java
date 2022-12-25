package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.topology.Node;
import nsu.fit.railway.entities.topology.Topology;
import nsu.fit.railway.entities.topology.Track;

import java.util.ArrayList;
import java.util.List;

public class SwitchEmergency extends Emergency {

    private final Node nodeWithBrokenSwitch;

    public SwitchEmergency(Node node) {
        nodeWithBrokenSwitch = node;
    }

    public Node getNodeWithBrokenSwitch() {
        return nodeWithBrokenSwitch;
    }

    @Override
    public void handle(Timetable timetable, Topology topology) {
        List<Track> neighbourTracks = new ArrayList<>(nodeWithBrokenSwitch.getInTracks());
        neighbourTracks.addAll(nodeWithBrokenSwitch.getOutTracks());

        for (Track track: neighbourTracks) {
            new TrackEmergency(track).handle(timetable, topology);
        }
    }
}
