package nsu.fit.railway.entities.topology;

public class Signal extends ControlElement {

    private final SignalInfo signalInfo;
    private SignalState signalState = SignalState.STOP;

    public Signal(Integer id) {
        this.signalInfo = new SignalInfo(id);
    }

    public SignalInfo getSignalInfo() {
        return signalInfo;
    }

    public SignalState getSignalState() {
        return signalState;
    }

    public void setSignalState(SignalState signalState) {
        this.signalState = signalState;
    }
}
