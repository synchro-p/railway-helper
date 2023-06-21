export default class Topology {
    constructor(JSONobj) {
        this.tracks = JSONobj.tracks;
        this.nodes = JSONobj.nodes;
        }

    getAssociatedEdges(node) {
        for (let i = 0; i < node.inTracks.length; i++) {
            
        }
    }

    getAssociatedNodes(track) {
        let startNode, finishNode = null;
        let cnt = 0;
        for (let i = 0; i < this.nodes.length; i++) {
            let node = this.nodes[i];
            //проверяем, если это конечный узел пути
            for (let j = 0; j < node.inTracks.length; j++) {
                if (finishNode != null) break;
                if (node.inTracks[j].id == track.id) {
                    finishNode = node;
                    cnt++;
                    break;
                }
            }
            //проверяем, если это начальный узел пути
            for (let j = 0; j < node.outTracks.length; j++) {
                if (startNode != null) break;
                if (node.outTracks[j].id == track.id) {
                    startNode = node;
                    cnt++;
                    break;
                }
            }
            if (cnt == 2) {
                break;
            }
        }
        //console.log({start: startNode, finish: finishNode})
        return {start: startNode, finish: finishNode};
    }
}
