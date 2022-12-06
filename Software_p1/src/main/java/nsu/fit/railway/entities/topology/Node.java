package nsu.fit.railway.entities.topology;

import java.util.List;

public class Node {
    private final Integer id;
    private final List<ControlElement> associated;
    private final List<Integer> trackIds;
    private final boolean isInput;
    private final boolean isOutput;

    public Node(Integer id, List<ControlElement> associated, List<Integer> trackIds, boolean isInput, boolean isOutput) {
        this.id = id;
        this.associated = associated;
        this.trackIds = trackIds;
        this.isInput = isInput;
        this.isOutput = isOutput;
    }


    public List<ControlElement> getAssociated() {
        return associated;
    }

    public List<Integer> getTrackIds() {
        return trackIds;
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
}
