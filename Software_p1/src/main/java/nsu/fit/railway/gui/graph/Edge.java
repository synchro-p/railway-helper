package nsu.fit.railway.gui.graph;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineJoin;
import nsu.fit.railway.entities.topology.Track;

public class Edge extends Group {

    protected Cell source;
    protected Cell target;

    Line line;
    Track track;

    public Edge(Cell source, Cell target, Track track) {

        this.source = source;
        this.target = target;
        this.track = track;

        source.addCellChild(target);
        target.addCellParent(source);

        line = new Line();

        line.startXProperty().bind( source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind( source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind( target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind( target.layoutYProperty().add( target.getBoundsInParent().getHeight() / 2.0));
        line.setStrokeWidth(1.5);
        line.setStrokeLineJoin(StrokeLineJoin.ROUND);

        if (!track.isActive()) {
            line.setStrokeDashOffset(5);
        }
        if (track.isOccupied()) {
            line.setStroke(Color.DARKGREEN);

        }
        getChildren().add( line);

    }

    public Cell getSource() {
        return source;
    }

    public Cell getTarget() {
        return target;
    }

}
