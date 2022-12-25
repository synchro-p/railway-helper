package nsu.fit.railway.gui.graph;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nsu.fit.railway.entities.topology.TopologyNode;

import java.util.ArrayList;
import java.util.List;

public class Cell extends Pane {

    Integer cellId;

    List<Cell> children = new ArrayList<>();
    List<Cell> parents = new ArrayList<>();

    Node view;

    public Cell(TopologyNode node) {
        this.cellId = node.getId();
        Circle view = new Circle( 10);
        view.setStroke(Color.DARKRED);
        view.setFill(Color.DARKRED);

        setView( view);
    }

    public void addCellChild(Cell cell) {
        children.add(cell);
    }

    public List<Cell> getCellChildren() {
        return children;
    }

    public void addCellParent(Cell cell) {
        parents.add(cell);
    }

    public List<Cell> getCellParents() {
        return parents;
    }

    public void removeCellChild(Cell cell) {
        children.remove(cell);
    }

    public void setView(Node view) {

        this.view = view;
        getChildren().add(view);

    }

    public Node getView() {
        return this.view;
    }

    public Integer getCellId() {
        return cellId;
    }
}
