package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.topology.ElementState;

public class StateChange extends Event{

    private final ChangeFunction function;
    private final ElementState state;

    public StateChange(ChangeFunction function, ElementState state) {
        this.function = function;
        this.state = state;
    }

    public void apply() {
        function.apply(state);
    }
}
