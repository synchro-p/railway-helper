package nsu.fit.railway.gui.graph;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nsu.fit.railway.entities.topology.ControlElement;
import nsu.fit.railway.entities.topology.Switch;
import nsu.fit.railway.entities.topology.TopologyNode;
import nsu.fit.railway.entities.topology.Track;

import java.util.ArrayList;
import java.util.List;

public class Cell extends Pane {

    Integer cellId;

    List<Cell> children = new ArrayList<>();
    List<Cell> parents = new ArrayList<>();

    Node view;
    TopologyNode node;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    double x;
    double y;

    public Cell(TopologyNode node) {
        this.cellId = node.getId();
        this.node = node;
        Circle view = new Circle( 8);
        view.setStroke(Color.DARKRED);
        view.setFill(Color.DARKRED);

        setView( view);
    }
    //public void setColor {
    //    this.node.getAssociated();
    //}

    public void setNewStyle() {
        Circle view = new Circle( 8);
        Color color = Color.BLACK;
        for (ControlElement ce : this.node.getAssociated()) {
            if (ce instanceof Switch) {
                color = Color.DARKRED;
                break;
            }
        }
        view.setStroke(color);
        view.setFill(color);
    }
    public void addCellChild(Cell cell) {
        children.add(cell);
    }

    public TopologyNode getNode() {
        return this.node;
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
