package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.timetable.Timetable;
import nsu.fit.railway.entities.timetable.TimetableEntry;
import nsu.fit.railway.entities.timetable.Train;
import nsu.fit.railway.entities.topology.Topology;
import nsu.fit.railway.entities.topology.Track;

import java.time.LocalDateTime;

public class MoveTrainEvent extends Event {

    private final Train train;
    private final Track fromTrack;
    private final Track toTrack;

    public MoveTrainEvent(Train train, Track fromTrack, Track toTrack) {
        this.train = train;
        this.fromTrack = fromTrack;
        this.toTrack = toTrack;
    }

    @Override
    public void apply() {
        fromTrack.freeTrack();
        toTrack.setCurrentTrain(train);
        train.setOccupiedTrack(toTrack);
    }

    @Override
    public String toString() {
        return "MoveTrainEvent{" +
                "train=" + train.getId() +
                ", fromTrack=" + (fromTrack != null ? fromTrack.getTrackInfo().getId() : -1) +
                ", toTrack=" + (toTrack != null ? toTrack.getTrackInfo().getId() : -1) +
                '}';
    }
}
