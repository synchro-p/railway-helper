package nsu.fit.railway.entities.topology;

import nsu.fit.railway.entities.timetable.Type;

import java.util.List;

public class Track {

    private final TrackInfo trackInfo;
    private List<Type> canServe;
    private final List<Type> canServeInitial;
    private boolean isActive;

    public Track(Integer length, List<Type> canServe, boolean isActive) {
        this.trackInfo = new TrackInfo(length);
        this.canServe = canServe;
        this.canServeInitial = canServe;
        this.isActive = isActive;
    }

    public void setCanServe(List<Type> canServe) {
        this.canServe = canServe;
    }

    public List<Type> getCanServe() {
        return canServe;
    }

    public void restoreCanServe() {
        canServe = canServeInitial;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public TrackInfo getTrackInfo() {
        return trackInfo;
    }
}
