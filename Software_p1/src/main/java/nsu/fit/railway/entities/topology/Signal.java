package nsu.fit.railway.entities.topology;

public class Signal extends ControlElement {
    private final SignalInfo signalInfo;

    public Signal() {
        this.signalInfo = new SignalInfo();
    }

    public SignalInfo getSignalInfo() {
        return signalInfo;
    }
}
