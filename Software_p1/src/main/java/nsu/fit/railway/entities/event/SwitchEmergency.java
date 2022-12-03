package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.topology.Node;

public class SwitchEmergency extends Emergency {

    private final Node nodeWithBrokenSwitch;

    public SwitchEmergency(Node node) {
        nodeWithBrokenSwitch = node;
    }


    public Node getNodeWithBrokenSwitch() {
        return nodeWithBrokenSwitch;
    }
}
