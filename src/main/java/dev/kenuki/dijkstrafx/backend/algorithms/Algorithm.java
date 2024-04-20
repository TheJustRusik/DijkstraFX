package dev.kenuki.dijkstrafx.backend.algorithms;

import dev.kenuki.dijkstrafx.util.Block;
import dev.kenuki.dijkstrafx.util.Cell;

public abstract class Algorithm {
    protected final int[] dx = {1, -1, 0, 0};
    protected final int[] dy = {0, 0, 1, -1};
    abstract public void shortestPath(Object lock, Block[][] maze, Cell start, Cell end);
    protected boolean isValid(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }
}
