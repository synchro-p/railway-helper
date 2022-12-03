package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.topology.ElementState;

public abstract interface ChangeFunction {
    public void apply(ElementState elementState);
}
