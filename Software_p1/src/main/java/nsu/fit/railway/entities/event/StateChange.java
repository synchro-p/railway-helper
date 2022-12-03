package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.topology.ElementInfo;

public class Change extends Event{

    private final ChangeFunction function;
    private final ElementInfo info;

    public Change(ChangeFunction function, ElementInfo info) {
        this.function = function;
        this.info = info;
    }

    public void apply() {
        function.apply(info);
    }
}
