package nsu.fit.railway.entities.topology;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final Integer id;
    private final List<ControlElement> associated;
    private List<Track> tracks;
    private final boolean isInput;
    private final boolean isOutput;

    public Node(Integer id, List<ControlElement> associated, boolean isInput, boolean isOutput) {
        this.id = id;
        this.associated = associated;
        this.tracks = new ArrayList<>();
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

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(Track track) {
        this.tracks.add(track);
    }
}
