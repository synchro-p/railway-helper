package nsu.fit.railway.entities;

import java.util.List;

public class Topology {

    private List<Node> nodes;

    public Topology(List<Node> nodes) {
        this.nodes = nodes;
    }


    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
