import * as PIXI from 'pixi.js';
import Graph, { MultiGraph } from 'graphology';
import Sigma from 'sigma';
import { animateNodes } from 'sigma/utils/animate';
import noverlap from 'graphology-layout-noverlap';
import testState from './testState.json' assert { type: 'json' };
import Topology from './topology';

function GetJSON(yourUrl) {
    var Httpreq = new XMLHttpRequest(); // a new request
    Httpreq.open('GET', yourUrl, false);
    Httpreq.send(null);
    return Httpreq.responseText;
}
function GetTopology() {
    return new Topology(JSON.parse(GetJSON('http://localhost:8080/simulation/next')));
    //return new Topology(topologyData);
}

GetJSON('http://localhost:8080/topology');
GetJSON('http://localhost:8080/simulation');
let topology = GetTopology();
const nodes = topology.nodes;
const tracks = topology.tracks;

function GetCurrentState() {
    let corrState = JSON.parse(GetJSON('http://localhost:8080/simulation/next'));
    return corrState;
}

function CustomizeEdge(edge) {
    if (graph.getEdgeAttribute(edge, 'train') == 'None') {
        graph.setEdgeAttribute(edge, 'color', 'black');
    } else {
        graph.setEdgeAttribute(edge, 'color', 'red');
    }
}

function DisplayState(state) {
    //Reset all tracks to empty
    graph.forEachEdge((edge, attributes, source, target, sourceAttributes, targetAttributes) => {
        graph.mergeEdgeAttributes(edge, { train: 'None' });
    });
    for (const t of state.trains) {
        let edge = t.occupiedTrack.id;
        graph.setEdgeAttribute(edge, 'train', t);
    }
    for (const n of state.nodes) {
        let label = '';
        if (n.signal != null) {
            label = label + 'signal: ' + n.signal.currentState;
        }
        if (n.aswitch != null) {
            label = label + 'switch to: ' + n.aswitch.currentTrackTo.id;
            for (const t of n.aswitch.tracksTo) graph.setEdgeAttribute(t.id, 'color', 'gray');
            graph.setEdgeAttribute(n.aswitch.currentTrackTo.id, 'color', 'black');
        }
        graph.setNodeAttribute(n.id, 'label', label);
    }
    graph.edges().forEach((e) => CustomizeEdge(e));
}

const container = document.getElementById('sigma-container');

//Defining graph nodes and edges
const graph = new MultiGraph();

for (let i = 0; i < nodes.length; i++) {
    graph.updateNode(nodes[i].id);
    graph.mergeNodeAttributes(nodes[i].id, {
        size: 15,
        label: i,
        controlElements: nodes[i].controlElements,
    });
}

for (let i = 0; i < tracks.length; i++) {
    let curNodes = topology.getAssociatedNodes(tracks[i]);
    graph.addDirectedEdgeWithKey(tracks[i].id, curNodes.start.id, curNodes.finish.id, {
        type: 'arrow',
        label: i,
        size: 5,
    });
}

graph.nodes().forEach((node) => {
    let inDeg = graph.inDegreeWithoutSelfLoops(node);
    let outDeg = graph.outDegreeWithoutSelfLoops(node);
    graph.mergeNodeAttributes(node, { isInput: false, isOutput: false });
    if (inDeg != 0 && outDeg == 0) {
        graph.mergeNodeAttributes(node, { isOutput: true });
    } else if (inDeg == 0 && outDeg != 0) {
        graph.mergeNodeAttributes(node, { isInput: true });
    }
});

graph.forEachNode((node) => {
    graph.setNodeAttribute(node, 'x', Math.random());
    graph.setNodeAttribute(node, 'y', Math.random());
});

const renderer = new Sigma(graph, container, {
    renderEdgeLabels: true,
});
graph.on('edgeAttributesUpdated', function ({ type }) {
    renderer.refresh();
    console.log('refresh');
});

//Симуляция пошагово по кнопке (не робит)
const startBtn = document.getElementById('startBtn');
//startBtn.onclick = startSim;
startBtn.addEventListener('click', function () {
    console.log('btn');
    let corrState = GetCurrentState();
    DisplayState(corrState);
});

//Воспроизведение симуляции по времени
//const interval = setInterval(function () {
//    let corrState = GetCurrentState();
//    DisplayState(corrState);
//}, 1000);

//
// Drag'n'drop feature
// ~~~~~~~~~~~~~~~~~~~
//

// State for drag'n'drop
let draggedNode = null;
let isDragging = false;

// On mouse down on a node
//  - we enable the drag mode
//  - save in the dragged node in the state
//  - highlight the node
//  - disable the camera so its state is not updated
renderer.on('downNode', (e) => {
    isDragging = true;
    draggedNode = e.node;
    graph.setNodeAttribute(draggedNode, 'highlighted', true);
});

// On mouse move, if the drag mode is enabled, we change the position of the draggedNode
renderer.getMouseCaptor().on('mousemovebody', (e) => {
    if (!isDragging || !draggedNode) return;

    // Get new position of node
    const pos = renderer.viewportToGraph(e);

    graph.setNodeAttribute(draggedNode, 'x', pos.x);
    graph.setNodeAttribute(draggedNode, 'y', pos.y);

    // Prevent sigma to move camera:
    e.preventSigmaDefault();
    e.original.preventDefault();
    e.original.stopPropagation();
});

// On mouse up, we reset the autoscale and the dragging mode
renderer.getMouseCaptor().on('mouseup', () => {
    if (draggedNode) {
        graph.removeNodeAttribute(draggedNode, 'highlighted');
    }
    isDragging = false;
    draggedNode = null;
});

// Disable the autoscale at the first down interaction
renderer.getMouseCaptor().on('mousedown', () => {
    if (!renderer.getCustomBBox()) renderer.setCustomBBox(renderer.getBBox());
});
