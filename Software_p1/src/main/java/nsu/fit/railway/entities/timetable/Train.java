package nsu.fit.railway.entities.timetable;

import nsu.fit.railway.entities.topology.Track;

public class Train {

    private final int id;
    private final int length;
    private final Type type;

    private Track occupiedTrack;

    public Train(int id, int length, Type type) {
        this.id = id;
        this.length = length;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public Type getType() {
        return type;
    }

    public Track getOccupiedTrack() {
        return occupiedTrack;
    }

    public void setOccupiedTrack(Track occupiedTrack) {
        this.occupiedTrack = occupiedTrack;
    }
}
