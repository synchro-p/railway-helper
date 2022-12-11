package nsu.fit.railway.entities.topology;

import java.util.*;

public class Topology {

    private final Set<Node> nodes;

    private final Set<Track> tracks;

    public Topology(Set<Node> nodes, Set<Track> tracks) {
        this.nodes = nodes;
        this.tracks = tracks;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public Set<Track> getTracks() {
        return tracks;
    }
}
