package nsu.fit.railway.entities.event;

public class Emergency extends Event {

    private String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void apply() {
        //TODO default realisation - give user a message
    }
}
