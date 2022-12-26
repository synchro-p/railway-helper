package nsu.fit.railway.entities.event;

import java.util.function.Consumer;

public class StateChange<T> extends Event{

    private final T changedElement;
    private final Consumer<T> changeFunction;

    public StateChange(Consumer<T> changeFunction, T changedElement) {
        this.changedElement = changedElement;
        this.changeFunction = changeFunction;
    }

    public void apply() {
        changeFunction.accept(changedElement);
    }
}
