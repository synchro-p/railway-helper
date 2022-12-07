package nsu.fit.railway.entities.topology;

public class TrackInfo{
    private final Integer id;
    private final Integer length;

    public TrackInfo (Integer id, Integer length) {
        this.id = id;
        this.length = length;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getId() {
        return id;
    }
}
