package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.topology.Signal;
import nsu.fit.railway.entities.topology.SignalState;

public class SignalEvent extends Event {

    private final Signal signal;
    private final SignalState newSignalState;

    public SignalEvent(Signal signal, SignalState newSignalState) {
        this.signal = signal;
        this.newSignalState = newSignalState;
    }

    @Override
    public void apply() {
        signal.setSignalState(newSignalState);
    }

    @Override
    public String toString() {
        return "SignalEvent{" +
                "signal=" + signal.getSignalInfo().getId() +
                ", newSignalState=" + newSignalState +
                '}';
    }
}
