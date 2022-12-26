package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.topology.Node;
import nsu.fit.railway.entities.topology.Topology;
import nsu.fit.railway.entities.topology.Track;

import java.util.ArrayList;
import java.util.List;
import nsu.fit.railway.entities.topology.TopologyNode;

public class SwitchEmergency extends Emergency {

    private final TopologyNode nodeWithBrokenSwitch;

    public SwitchEmergency(Timetable timetable, Topology topology, Node node) {
        super(timetable, topology);
        nodeWithBrokenSwitch = node;
    }

    public SwitchEmergency(String message, Timetable timetable, Topology topology, Node node) {
        super(message, timetable, topology);
        nodeWithBrokenSwitch = node;
    }

    public TopologyNode getNodeWithBrokenSwitch() {
        return nodeWithBrokenSwitch;
    }

    @Override
    public void apply() {
        List<Track> neighbourTracks = new ArrayList<>(nodeWithBrokenSwitch.getInTracks());
        neighbourTracks.addAll(nodeWithBrokenSwitch.getOutTracks());

        for (Track track: neighbourTracks) {
            new TrackEmergency(timetable, topology, track).apply();
        }
    }
}
