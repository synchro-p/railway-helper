package nsu.fit.railway.entities.topology;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Topology {

    private final Map<Integer, Node> nodes;

    private final Map<Integer, Track> tracks;

    public Topology(List<Node> nodes, List<Track> tracks) {
        this.nodes = new HashMap<>();
        this.tracks = new HashMap<>();
        for (Node n : nodes) {
            this.nodes.put(n.getId(), n);
        }
        for (Track t : tracks) {
            this.tracks.put(t.getTrackInfo().getId(), t);
        }
    }

    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    public Map<Integer, Track> getTracks() {
        return tracks;
    }
}
