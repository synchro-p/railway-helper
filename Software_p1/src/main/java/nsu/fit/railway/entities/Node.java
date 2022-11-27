package nsu.fit.railway.entities;

import java.util.List;

public class Node {

    private final List<ControlElement> associated;
    private final List<Track> tracks;
    private final boolean isInput;
    private final boolean isOutput;

    public Node(List<ControlElement> associated, List<Track> tracks, boolean isInput, boolean isOutput) {
        this.associated = associated;
        this.tracks = tracks;
        this.isInput = isInput;
        this.isOutput = isOutput;
    }


    public List<ControlElement> getAssociated() {
        return associated;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public boolean isInput() {
        return isInput;
    }

    public boolean isOutput() {
        return isOutput;
    }
}
