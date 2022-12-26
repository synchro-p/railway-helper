package nsu.fit.railway.entities.timetable;

import nsu.fit.railway.entities.topology.Node;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimetableEntry {
    private LocalDateTime arrivalIntervalStart;
    private LocalDateTime arrivalIntervalFinish;
    private Node inputNode;
    private Node outputNode;
    private Duration stationingTime;
    private Train train;


    public LocalDateTime getArrivalIntervalStart() {
        return arrivalIntervalStart;
    }

    public void setArrivalIntervalStart(LocalDateTime arrivalIntervalStart) {
        this.arrivalIntervalStart = arrivalIntervalStart;
    }

    public LocalDateTime getArrivalIntervalFinish() {
        return arrivalIntervalFinish;
    }

    public void setArrivalIntervalFinish(LocalDateTime arrivalIntervalFinish) {
        this.arrivalIntervalFinish = arrivalIntervalFinish;
    }

    public Node getInputNode() {
        return inputNode;
    }

    public void setInputNode(Node inputNode) {
        this.inputNode = inputNode;
    }

    public Node getOutputNode() {
        return outputNode;
    }

    public void setOutputNode(Node outputNode) {
        this.outputNode = outputNode;
    }

    public Duration getStationingTime() {
        return stationingTime;
    }

    public void setStationingTime(Duration stationingTime) {
        this.stationingTime = stationingTime;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
}
