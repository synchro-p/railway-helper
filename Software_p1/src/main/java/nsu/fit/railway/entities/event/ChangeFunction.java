package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.topology.ElementInfo;

public abstract interface ChangeFunction {
    public void apply(ElementInfo elementInfo);
}
