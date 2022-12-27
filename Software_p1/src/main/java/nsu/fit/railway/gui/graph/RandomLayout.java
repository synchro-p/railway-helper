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
        double maxY = 0, minY = 1000;
        int inputYs = 0, outputYs = 0;
        double maxX = 0, minX = 1000;

        for (Cell cell : cells) {
            for (Cell parent : cell.getCellParents()) {
                if (parent.getX() > maxX) maxX = parent.getX();
                if (parent.getX() < minX) minX = parent.getX();
            }
            for (Cell child : cell.getCellChildren()) {
                if (child.getX() > maxX) maxX = child.getX();
                if (child.getX() < minX) minX = child.getX();
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
                x = rnd.nextDouble() * (maxX - minX) + minX + 50;
                y = rnd.nextDouble() * (200) + maxY - 100;
            }
            if (y > maxY) maxY = y;
            cell.setX(x);
            cell.setY(y);
//            cell.relocate(x, y);

        }
        for (Cell cell : cells) {
            for (Cell parent : cell.getCellParents()) {
                if (parent.getX() > maxX) maxX = parent.getX();
                if (parent.getX() < minX) minX = parent.getX();
                if (parent.getY() > maxY) maxY = parent.getY();
                if (parent.getY() < minY) minY = parent.getY();
            }
            for (Cell child : cell.getCellChildren()) {
                if (child.getX() > maxX) maxX = child.getX();
                if (child.getX() < minX) minX = child.getX();
                if (child.getY() > maxY) maxY = child.getY();
                if (child.getY() < minY) minY = child.getY();
            }
            x = cell.getX();
            y = cell.getY();
            if ((!cell.node.isInput() && !cell.node.isOutput()) && (cell.getX() <= minX || cell.getX() >= maxX))
                x = Math.random() * (maxX - minX) + minX + 20;
            if ((!cell.node.isInput() && !cell.node.isOutput()) && (cell.getY() <= minY || cell.getY() >= maxY))
                y = rnd.nextDouble() * ((maxY - 20) - (minY + 20)) + minY;
            cell.setX(x);
            cell.setY(y);
            cell.relocate(x, y);
        }

    }

}
