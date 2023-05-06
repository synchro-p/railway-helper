import * as PIXI from 'pixi.js';
import Graph from "graphology";
import Sigma from "sigma";
import { animateNodes } from "sigma/utils/animate";
import noverlap from 'graphology-layout-noverlap';

function randomLayout() {
    // to keep positions scale uniform between layouts, we first calculate positions extents
    const xExtents = { min: 0, max: 0 };
    const yExtents = { min: 0, max: 0 };
    graph.forEachNode((node, attributes) => {
      xExtents.min = Math.min(attributes.x, xExtents.min);
      xExtents.max = Math.max(attributes.x, xExtents.max);
      yExtents.min = Math.min(attributes.y, yExtents.min);
      yExtents.max = Math.max(attributes.y, yExtents.max);
    });
    const randomPositions = {};
    graph.forEachNode((node) => {
      // create random positions respecting position extents
      randomPositions[node] = {
        x: Math.random() * (xExtents.max - xExtents.min),
        y: Math.random() * (yExtents.max - yExtents.min)
      };
    });
    // use sigma animation to update new positions
    animateNodes(graph, randomPositions, {
      duration: 1000
    });
}

function noverlapLayout() {
    noverlap.assign(graph);
}

function customLayout() {
    const xExtents = { min: 0, max: 0 };
    const yExtents = { min: 0, max: 0 };
    graph.forEachNode((node, attributes) => {
      xExtents.min = Math.min(attributes.x, xExtents.min);
      xExtents.max = Math.max(attributes.x, xExtents.max);
      yExtents.min = Math.min(attributes.y, yExtents.min);
      yExtents.max = Math.max(attributes.y, yExtents.max);
    });
    const finalPositions = {};
    let inCnt = 0;
    let outCnt = 0;
    function max(x, y) {
        if (x>y) return x;
        else return y;
    }
    graph.forEachNode((node, attributes) => {
        console.log(attributes);
        if (attributes.isInput) {
            console.log("input found");
            finalPositions[node] = {
                x: xExtents.min,
                y: (yExtents.max - yExtents.min) + inCnt*50
            };
            inCnt++;
        } else if (attributes.isOutput) {
            finalPositions[node] = {
                x: xExtents.max,
                y: (yExtents.max - yExtents.min) + outCnt*50
            };
            outCnt++;
        } else {
      // create random positions respecting position extents
      finalPositions[node] = {
        x: Math.random() * ((xExtents.max - 20) - (xExtents.min + 20)),
        y: Math.random() * (max(inCnt, outCnt)*50) + 200
      };
    }
    });
    // use sigma animation to update new positions
    animateNodes(graph, finalPositions, {
      duration: 1000
    });
}


const randomButton = document.getElementById("random");
const noverlapButton = document.getElementById("noverlap");
const customButton = document.getElementById("railway");
randomButton.addEventListener("click", randomLayout);
noverlapButton.addEventListener("click", noverlapLayout);
customButton.addEventListener("click", customLayout);

const container = document.getElementById("sigma-container");

import topology from './topology.json' assert {type: 'json'};
const nodes = topology["nodes"];
//console.log(nodes.length);

const graph = new Graph();

for (let i = 0; i < nodes.length; i++) {
    if (!graph.hasNode(nodes[i].id))
        graph.addNode(nodes[i].id);
        graph.mergeNodeAttributes(nodes[i].id, {size: 15, isInput: nodes[i].input, isOutput: nodes[i].output});
    for (let k = 0; k < nodes[i].associated.length; k++) {
        if (graph.hasNode(nodes[i].associated[k]) && !graph.areNeighbors(nodes[i].id, nodes[i].associated[k])) {
            graph.addEdge(nodes[i].id, nodes[i].associated[k]);
            graph.setEdgeAttribute(nodes[i].id, nodes[i].associated[k], 'size', 5);
        }
    }
}

/*for (const node in nodes) {
    if (!graph.hasNode(node))
        graph.addNode(node);
    for (const assoc in node.associated) {
        if (graph.hasNode(assoc) && !graph.areNeighbors(node, assoc)) {
            graph.addEdge(node, assoc);
        }
    }
}*/

graph.forEachNode(node => {
    graph.setNodeAttribute(node, 'x', Math.random());
    graph.setNodeAttribute(node, 'y', Math.random());
  });

graph.forEachNode(node => {
    //console.log(node);
});

graph.forEachEdge(edge => {
    console.log(edge);
})

/*const blueBox = new PIXI.Graphics();
blueBox.beginFill(0x0000ff);
blueBox.drawRect(150, 150, 150, 150);
blueBox.interactive = true;
blueBox.cursor = 'pointer';
const onBlueBoxClick = (event) => {
    blueBox.x = Math.random() * 200;
    blueBox.y = Math.random() * 200;
};
blueBox.on('click', onBlueBoxClick);*/
const renderer = new Sigma(graph, container);

//app.stage.addChild(blueBox);
