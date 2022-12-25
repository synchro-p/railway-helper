package nsu.fit.railway.entities.topology;

import java.util.*;

public class Topology {

    private final Set<TopologyNode> nodes;

    private final Set<Track> tracks;

    public Topology(Set<TopologyNode> nodes, Set<Track> tracks) {
        this.nodes = nodes;
        this.tracks = tracks;
    }

    public Set<TopologyNode> getNodes() {
        return nodes;
    }

    public Set<Track> getTracks() {
        return tracks;
    }
}
