package nsu.fit.railway.data.impl.jackson;

import nsu.fit.railway.data.TopologyRepository;
import nsu.fit.railway.entities.topology.Topology;

public class JacksonTopologyRepositoryImpl implements TopologyRepository {

    private final static String TOPOLOGY_PATH_FILE = "topology.json";

    @Override
    public void saveTopology(Topology topology) {
        JacksonSerialization.serialize(topology, TOPOLOGY_PATH_FILE);
    }

    @Override
    public Topology getTopology() {
        return JacksonSerialization.deserialize(TOPOLOGY_PATH_FILE, Topology.class);
    }

}
