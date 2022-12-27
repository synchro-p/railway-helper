package nsu.fit.railway.gui.graph;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Text;
import nsu.fit.railway.entities.topology.Track;

public class Edge extends Group {

    protected Cell source;
    protected Cell target;

    Line line;
    Track track;
    boolean isActiveSwitch = false;
    double startX, startY, endX, endY;
    public Edge(Cell source, Cell target, Track track) {

        this.source = source;
        this.target = target;
        this.track = track;

        source.addCellChild(target);
        target.addCellParent(source);

        line = new Line();
        startX = source.getBoundsInParent().getWidth() / 2.0;
        startY = source.getBoundsInParent().getHeight() / 2.0;
        line.startXProperty().bind( source.layoutXProperty().add(startX));
        line.startYProperty().bind( source.layoutYProperty().add(startY));

        endX = target.getBoundsInParent().getWidth() / 2.0;
        endY = target.getBoundsInParent().getHeight() / 2.0;
        line.endXProperty().bind( target.layoutXProperty().add(endX));
        line.endYProperty().bind( target.layoutYProperty().add(endY));
        line.setStrokeWidth(1.5);
        line.setStrokeLineJoin(StrokeLineJoin.ROUND);

        getChildren().add( line);

    }

    public Track getTrack() {
        return this.track;
    }
    public void setActiveSwitch(Boolean state) {
        isActiveSwitch = state;
    }
    public void setNewStyle() {
        double x = (startX + endX);
        double y = (startY + endY) / 2 - 5;
        if (this.track.isOccupied()) {
            Text trainText = new Text(x, y, String.valueOf(this.track.getCurrentTrain().getId()));
            getChildren().add(trainText);
            line.setStrokeWidth(2.5);
        }
        if (!track.isActive()) {
            line.setStrokeDashOffset(5);
        }
        if (this.isActiveSwitch) {
            line.setStroke(Color.DARKGREEN);
        }
        else {
            line.setStroke(Color.BLACK);
        }
        getChildren().remove(line);
        getChildren().add(line);
    }

    public Cell getSource() {
        return source;
    }

    public Cell getTarget() {
        return target;
    }

}
