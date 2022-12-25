package nsu.fit.railway.data;

import nsu.fit.railway.entities.topology.Topology;

public interface TopologyRepository {

    void saveTopology(Topology topology);

    Topology getTopology();
}
