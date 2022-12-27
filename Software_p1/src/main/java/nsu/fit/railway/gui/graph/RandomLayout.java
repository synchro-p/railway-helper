package nsu.fit.railway.gui.graph;

import java.util.Arrays;
import java.util.Collections;
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
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).node.isInput()) Collections.swap(cells, i, 0);
            if (cells.get(i).node.isOutput()) Collections.swap(cells, i, 1);
        }
        double x, y;
        double maxY = 0;
        int inputYs = 0, outputYs = 0;
        double maxX = 0, minX = 1000;

        for (Cell cell : cells) {
            for (Cell parent : cell.getCellParents()) {
                if (parent.getX() > maxX) maxX = parent.getX();
                if (parent.getX() < minX) minX = parent.getX();
            }
            if (maxX == 0) maxX = 450;
            if (minX == 1000) minX = 50;
            if (cell.node.isInput()) {
                //x = cell.getBoundsInParent().getWidth() + 20;
                x = 20;
                y = inputYs * 50 + 100;
                inputYs++;
            }
            else if (cell.node.isOutput()) {
                //x = cell.getBoundsInParent().getWidth() - 20;
                x = 480;
                y = outputYs * 50 + 100;
                outputYs++;
            }
            else {
                x = rnd.nextDouble() * (maxX - minX) + minX;
                y = rnd.nextDouble() * (100) + maxY - 50;
            }
            if (y > maxY) maxY = y;
            cell.setX(x);
            cell.setY(y);
            cell.relocate(x, y);

        }

    }

}
