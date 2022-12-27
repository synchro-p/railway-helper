package nsu.fit.railway.gui.graph;

import java.util.List;
import java.util.Random;

public class RandomLayout extends Layout {

    Graph graph;

    Random rnd = new Random();

    public RandomLayout(Graph graph) {

        this.graph = graph;

    }

    public void execute() {

        List<Cell> cells = graph.getModel().getAllCells();
        double x, y;
        double maxY = 0;
        int inputYs = 0, outputYs = 0;
        for (Cell cell : cells) {
            if (cell.node.isInput()) {
                //x = cell.getBoundsInParent().getWidth() + 20;
                x = 20;
                y = inputYs * 100 + 20;
                inputYs++;
            }
            else if (cell.node.isOutput()) {
                //x = cell.getBoundsInParent().getWidth() - 20;
                x = 480;
                y = outputYs * 100 + 20;
                outputYs++;
            }
            else {
                x = rnd.nextDouble() * 500;
                y = rnd.nextDouble() * (maxY + 100);
            }
            if (y > maxY) maxY = y;
            cell.relocate(x, y);

        }

    }

}
