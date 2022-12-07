package nsu.fit.railway.entities.topology;

import nsu.fit.railway.entities.timetable.Type;

import java.util.List;

public class Track extends ElementState {
    private final TrackInfo trackInfo;
    private List<Type> canServe;
    private Node startNode;
    private Node finishNode;
    private final List<Type> canServeInitial;
    private boolean isActive;

    public Track(TrackInfo trackInfo, List<Type> canServe, boolean isActive) {
        this.trackInfo = trackInfo;
        this.canServe = canServe;
        this.canServeInitial = canServe;
        this.isActive = isActive;
        this.startNode = null;
        this.finishNode = null;
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

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getFinishNode() {
        return finishNode;
    }

    public void setFinishNode(Node finishNode) {
        this.finishNode = finishNode;
    }
}
