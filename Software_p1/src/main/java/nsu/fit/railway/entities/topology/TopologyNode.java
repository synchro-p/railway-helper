package nsu.fit.railway.entities.topology;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TopologyNode {
    private final Integer id;
    private final List<ControlElement> associated;
    private Set<Track> inTracks;
    private Set<Track> outTracks;
    private final boolean isInput;
    private final boolean isOutput;

    public TopologyNode(Integer id, List<ControlElement> associated, boolean isInput, boolean isOutput) {
        this.id = id;
        this.associated = associated;
        this.inTracks = new HashSet<>();
        this.outTracks = new HashSet<>();
        this.isInput = isInput;
        this.isOutput = isOutput;
    }


    public List<ControlElement> getAssociated() {
        return associated;
    }

    public boolean isInput() {
        return isInput;
    }

    public boolean isOutput() {
        return isOutput;
    }

    public Integer getId() {
        return id;
    }

    public Set<Track> getInTracks() {
        return inTracks;
    }

    public void setInTracks(Set<Track> inTracks) {
        this.inTracks = inTracks;
    }

    public Set<Track> getOutTracks() {
        return outTracks;
    }

    public void setOutTracks(Set<Track> outTracks) {
        this.outTracks = outTracks;
    }
}
