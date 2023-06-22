import * as PIXI from 'pixi.js';
import Graph, { MultiGraph } from 'graphology';
import Sigma from 'sigma';
import { animateNodes } from 'sigma/utils/animate';
import noverlap from 'graphology-layout-noverlap';
import testState from './testState.json' assert { type: 'json' };
import Topology from './topology';

import { $, jQuery } from 'jquery';
//const $ = require( "jquery" );
//window.$ = $;
//window.jQuery = jQuery;

function GetJSON(yourUrl) {
    var Httpreq = new XMLHttpRequest(); // a new request
    Httpreq.open('GET', yourUrl, false);
    Httpreq.send(null);
    return Httpreq.responseText;
}

function GetTopology() {
    return new Topology(JSON.parse(GetJSON('http://localhost:8080/simulation/next')));
}

GetJSON('http://localhost:8080/topology');
GetJSON('http://localhost:8080/simulation');
const topology = GetTopology();
const nodes = topology.nodes;
const tracks = topology.tracks;

var timestamp = document.getElementById('timestamp');
function prettyDateTime(datetime) {
    return datetime.slice(8, 10) + '/' + datetime.slice(5, 7) + '/' + datetime.slice(0, 4) +
        '   ' + datetime.slice(11, 19)
}


function GetCurrentState() {
    let corrState = JSON.parse(GetJSON('http://localhost:8080/simulation/next'));
    return corrState;
}

function CustomizeEdge(edge) {
    if (graph.getEdgeAttribute(edge, 'train') == 'None') {
        if (graph.getEdgeAttribute(edge, 'color') == 'orangered')
            graph.setEdgeAttribute(edge, 'color', 'black');
        graph.setEdgeAttribute(edge, 'label', edge);
    } else {
        //graph.setEdgeAttribute(edge, 'color', 'red');
        let train = graph.getEdgeAttribute(edge, 'train');
        //let label = 'Train \nID: ' + train.id.slice(0, 4) + '\nPurpose: ' + train.trainType;
        let label = 'Train \nID: ' + train.id + '\nPurpose: ' + train.trainType;
        graph.mergeEdgeAttributes(edge, { color: 'orangered', label: label});
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
        if (graph.getNodeAttribute(n.id, 'isInput'))
            label = label + 'IN-' + n.id;
        if (graph.getNodeAttribute(n.id, 'isOutput'))
        label = label + 'OUT-' + n.id;
        if (n.signal != null) {
            label = label + 'signal: ' + n.signal.currentState;
            if (n.signal.currentState == 'GO')
                graph.setNodeAttribute(n.id, 'color', 'lightgreen');
            else
            graph.setNodeAttribute(n.id, 'color', 'orangered');
        }
        if (n.aswitch != null) {
            label = label + 'switch to: ' + n.aswitch.currentTrackTo.id;
            for (const t of n.aswitch.tracksTo) 
            {
                graph.setEdgeAttribute(t.id, 'color', 'lightgray');
            }
            graph.setEdgeAttribute(n.aswitch.currentTrackTo.id, 'color', 'black');
        }
        graph.setNodeAttribute(n.id, 'label', label);
    }
    for (const track of state.tracks) {
        if (track.canServe.length == 0) {
            graph.mergeEdgeAttributes(track.id, {label: '!OUT OF ORDER!', color: 'red'});
        }
    }
    timestamp.textContent = prettyDateTime(state.timestamp);
    graph.edges().forEach((e) => CustomizeEdge(e));
}

const container = document.getElementById('sigma-container');

//Defining graph nodes and edges
const graph = new MultiGraph();

for (let i = 0; i < nodes.length; i++) {
    graph.updateNode(nodes[i].id);
    graph.mergeNodeAttributes(nodes[i].id, {
        size: 10,
        controlElements: nodes[i].controlElements,
    });
}

for (let i = 0; i < tracks.length; i++) {
    let curNodes = topology.getAssociatedNodes(tracks[i]);
    graph.addDirectedEdgeWithKey(tracks[i].id, curNodes.start.id, curNodes.finish.id, {
        type: 'arrow',
        label: 'id: ' + tracks[i].id,
        size: 5,
        color: 'black',
    });
}

graph.nodes().forEach((node) => {
    let inDeg = graph.inDegreeWithoutSelfLoops(node);
    let outDeg = graph.outDegreeWithoutSelfLoops(node);
    graph.mergeNodeAttributes(node, { isInput: false, isOutput: false });
    if (inDeg != 0 && outDeg == 0) {
        graph.mergeNodeAttributes(node, { isOutput: true, label: 'OUT-' + node});

    } else if (inDeg == 0 && outDeg != 0) {
        graph.mergeNodeAttributes(node, { isInput: true, label: 'IN-' + node});
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
});

//Симуляция пошагово по кнопке
const stepBtn = document.getElementById('stepBtn');
stepBtn.addEventListener('click', function () {
    let corrState = GetCurrentState();
    DisplayState(corrState);
});
//Старт симуляции по времени 
let interval = null;
const playBtn = document.getElementById('playBtn');
playBtn.addEventListener('click', function() {
    if (interval == null) {
    interval = setInterval(function () {
            let corrState = GetCurrentState();
            DisplayState(corrState);
        }, 1000);
    }
});
//Остановка симуляции по времени
const stopBtn = document.getElementById('stopBtn');
stopBtn.addEventListener('click', function() {
    if (interval != null) {
        clearInterval(interval);
        interval = null;
    }
});

//РАСПИСАНИЕ
GetJSON('http://localhost:8080/timetable');
let timetable = JSON.parse(GetJSON('http://localhost:8080/timetable/test-mapping'));
var table = document.getElementById('table');

function constructTable(timetable, selector) {
    const cols = ['Train ID', 'Arrival time', 'Stationing time', 'From', 'To'];
    var headerRow = table.insertRow(0);
    for (const c of cols) {
        var headerCell = document.createElement("TH");
        headerCell.innerHTML = c;
        headerRow.appendChild(headerCell);
    }
    for (const event of timetable) {
        let row = table.insertRow(-1);
        var cell1 = row.insertCell();
        cell1.textContent = event.train.id;

        var cell2 = row.insertCell();
        cell2.textContent = prettyDateTime(event.arrivalTime);

        var cell3 = row.insertCell();
        cell3.textContent = event.stationingTime;

        var cell4 = row.insertCell();
        cell4.textContent = "IN-" + event.inputNode.id;

        var cell5 = row.insertCell();
        cell5.textContent = "OUT-" + event.endNode.id;
    }
}

constructTable(timetable, 'table');


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
