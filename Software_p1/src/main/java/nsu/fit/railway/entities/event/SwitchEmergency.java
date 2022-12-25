package nsu.fit.railway.entities.event;

import nsu.fit.railway.entities.topology.TopologyNode;

public class SwitchEmergency extends Emergency {

    private final TopologyNode nodeWithBrokenSwitch;

    public SwitchEmergency(TopologyNode node) {
        nodeWithBrokenSwitch = node;
    }


    public TopologyNode getNodeWithBrokenSwitch() {
        return nodeWithBrokenSwitch;
    }
}
