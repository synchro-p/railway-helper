package nsu.fit.railway.entities.topology;

public class TrackInfo{
    private final Integer id;
    private final Integer length;
    private final Integer[] nodeIds;

    public TrackInfo (Integer id, Integer length, Integer[] nodeIds) {
        this.id = id;
        this.length = length;
        this.nodeIds = nodeIds;
    }

    public Integer getLength() {
        return length;
    }

    public Integer[] getNodeIds() {
        return nodeIds;
    }

    public Integer getId() {
        return id;
    }
}
